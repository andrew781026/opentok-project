package andrew.com.riko.www.doctorapplication.properties;

import okhttp3.MediaType;

/**
 * Created by Test on 2017/11/9.
 */
public class ContentType {

    public static final MediaType MIXED = MediaType.parse("multipart/mixed");
    public static final MediaType ALTERNATIVE = MediaType.parse("multipart/alternative");
    public static final MediaType DIGEST = MediaType.parse("multipart/digest");
    public static final MediaType PARALLEL = MediaType.parse("multipart/parallel");
    public static final MediaType FORM = MediaType.parse("multipart/form-data");
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType X_WWW_FORM_URLENCODED = MediaType.parse("application/x-www-form-urlencoded");
    /*
    作者：许宏川
    链接：http://www.jianshu.com/p/1873287eed87
    來源：简书
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    */
}
