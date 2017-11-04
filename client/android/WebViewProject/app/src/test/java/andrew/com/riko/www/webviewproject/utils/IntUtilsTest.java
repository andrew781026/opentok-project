package andrew.com.riko.www.webviewproject.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Test on 2017/11/4.
 */
public class IntUtilsTest {

    @Test
    public void makeSequence(){

        Integer[] ints = IntUtils.makeSequence(1,12);
        assertEquals(12,ints.length);
        for ( int i : ints ){
            System.out.println("i = " + i);
        }

    }


}