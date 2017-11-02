package andrew.com.riko.www.webviewproject.utils;

/**
 * Created by Test on 2017/11/1.
 */

public class StringUtils {

    public static boolean isEmpty(final Object src){
        if ( src == null ) return true ;
        if ( !(src instanceof String) ) return true ;
        if ( src.equals("") ) return true ;
        return false ;
    }

    public static boolean isBlank(final Object src){
        if ( isEmpty(src) == true ) return isEmpty(src);
        else {
            String trimmed = ((String)src).trim();
            if ( trimmed.equals("") ) return true;
            else return false;
        }
    }

}
