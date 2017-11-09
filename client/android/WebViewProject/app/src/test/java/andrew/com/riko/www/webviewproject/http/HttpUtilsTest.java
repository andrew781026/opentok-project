package andrew.com.riko.www.webviewproject.http;

import org.junit.Test;

import java.util.List;

import andrew.com.riko.www.webviewproject.model.Division;

import static org.junit.Assert.*;

/**
 * Created by Test on 2017/11/8.
 */
public class HttpUtilsTest {
    @Test
    public void getModel() throws Exception {

        String url = "https://www.yoecare.com/divisions";
        int id = 3 ;

        Division division = HttpUtils.getModel(url,id,Division.class);
        System.out.println("division = " + division);
    }

    @Test
    public void getModels() throws Exception {
        String url = "https://www.yoecare.com/divisions";

        List<Division> divisionList = HttpUtils.getModels(url,Division.class);
        for ( Division division : divisionList ){
            System.out.println("division = " + division);
        }
    }

}