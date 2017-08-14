// Dependencies
var express = require('express'),
    OpenTok = require('../../lib/opentok');

// Verify that the API Key and API Secret are defined
var apiKey = 45932562 ;// process.env.API_KEY,
var apiSecret = 'cf14024de148af7760e6968c01f720b2c3963e7f' ;// process.env.API_SECRET;
if (!apiKey || !apiSecret) {
  console.log('You must specify API_KEY and API_SECRET environment variables');
  process.exit(1);
}

// Initialize the express app
var app = express();
app.use(express.static(__dirname + '/public'));

// Initialize OpenTok
var opentok = new OpenTok(apiKey, apiSecret);

// Create a session and store it in the express app
opentok.createSession(function(err, session) {
  if (err) throw err;
  app.set('sessionId', session.sessionId);
  // We will wait on starting the app until this is done
    //  可以將 session id 存到 array 中 ,  然後用 restful 給出此 session id
  init();
});

app.get('/', function(req, res) {
  var sessionId = app.get('sessionId'),
      // generate a fresh token for this client
      // 在 client 中取得 session id 然後 用 sdk 產生 token
      token = opentok.generateToken(sessionId);

  res.render('index.ejs', {
    apiKey: apiKey,
    sessionId: sessionId,
    token: token
  });
});

app.get('/api', function(req, res) {
   apiFunction(req,res);
});

app.get('/session', function(req, res) {
    apiFunction(req,res);
});

var apiFunction = function(req, res) {
    var sessionId = app.get('sessionId'),
        // generate a fresh token for this client
        // 在 client 中取得 session id 然後 用 sdk 產生 token
        token = opentok.generateToken(sessionId);

    var json =  {
        apiKey: apiKey,
        sessionId: sessionId,
        token: token
    };

    res.setHeader('Content-Type', 'application/json');
    res.send(json);

};

// Start the express app
function init() {
  app.listen(3000, function() {
    console.log('You\'re app is now ready at http://localhost:3000/');
  });
}
