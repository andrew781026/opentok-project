package andrew.com.riko.www.webviewproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import andrew.com.riko.www.webviewproject.model.RestConnectInfo;
import andrew.com.riko.www.webviewproject.model.VideoConnectInfo;
import andrew.com.riko.www.webviewproject.properties.KeyName;
import andrew.com.riko.www.webviewproject.MultiVideoChatActivity;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CallFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CallFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CallFragment newInstance(String param1, String param2) {
        CallFragment fragment = new CallFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 改變 Title
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("打給客服");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = (Button) getView().findViewById(R.id.callBtn);
        EditText editText = (EditText) getView().findViewById(R.id.taskIdET);

        final String roomName = editText.getText().toString();
        final String restServerUrl = "https://oriact-video-chat-project.herokuapp.com/room/"+URLEncoder.encode(roomName);
        final String callCenterUrl = "https://www.yoecare.com/calls";

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {

                    private void postToCallCenter(VideoConnectInfo videoConnectInfo,OkHttpClient client){

                        String fcmToken = FirebaseInstanceId.getInstance().getToken();

                        FormBody formBody = new FormBody.Builder()
                                .add("api_key", videoConnectInfo.getApiKey())
                                .add("room_name",videoConnectInfo.getRoomName() )
                                .add("session_id",videoConnectInfo.getSessionId())
                                .add("token",videoConnectInfo.getToken())
                                .add("fcmToken",fcmToken)
                                .build();

                        Request request = new Request.Builder().url(callCenterUrl).post(formBody).build();

                        try {
                            Response response = client.newCall(request).execute();

                            Log.i("videoConnectInfo pc",response.code()+"");

                            if (response.code() == 200) {

                                String body = response.body().string();
                                JSONObject bodyObject = new JSONObject(body);
                                JSONObject data = bodyObject.getJSONObject("data");
                                int taskId = data.getInt("id");
                                videoConnectInfo.setTaskId(taskId);

                            }else {
                                String body = response.body().string();
                                Log.i("videoConnectInfo pc",body);
                            }
                        } catch (IOException | JSONException e) {
                            Log.e("in CallFragment","postToCallCenter fail");
                        }

                    }

                    private VideoConnectInfo getVideoConnectInfo(){

                        try {
                            OkHttpClient client = new OkHttpClient();

                            Request request = new Request.Builder()
                                    .url(restServerUrl)
                                    .build();

                            Response response = client.newCall(request).execute();

                            if ( response.code() == 200 ){
                                String body = response.body().string();
                                Gson gson = new Gson();
                                RestConnectInfo restConnectInfo = gson.fromJson(body, RestConnectInfo.class);
                                VideoConnectInfo videoConnectInfo = new VideoConnectInfo();
                                videoConnectInfo.setApiKey(restConnectInfo.getApiKey());
                                videoConnectInfo.setSessionId(restConnectInfo.getSessionId());
                                videoConnectInfo.setToken(restConnectInfo.getToken());
                                videoConnectInfo.setRoomName(roomName);
                                Log.i("videoConnectInfo",videoConnectInfo.toString());
                                this.postToCallCenter(videoConnectInfo,client);
                                return videoConnectInfo ;
                            }

                        } catch (IOException e) {
                            Log.e("in CallFragment","getVideoConnectInfo fail");
                        }
                        return null ;
                    }

                    @Override
                    public void run() {
                        final VideoConnectInfo videoConnectInfo = this.getVideoConnectInfo();

                        if ( videoConnectInfo != null ){

                            // 新的執行續不能更新 ui
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // 要在 ui thread
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(KeyName.VIDEO_CONNECT_INFO,videoConnectInfo);

                                    Intent intent = new Intent(getActivity(),MultiVideoChatActivity.class);
                                    intent.putExtras(bundle);
                                    getActivity().startActivity(intent);
                                }
                            });

                        }else {
                            // 新的執行續不能更新 ui
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // 要在 ui thread 上跑 toast
                                    Toast.makeText(getActivity(),"cannot get videoConnectInfo",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }


                    }
                });
                thread.start();

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_call, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            Toast.makeText(context,"call fragment attached",Toast.LENGTH_SHORT).show();
            /*
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
                    */
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
