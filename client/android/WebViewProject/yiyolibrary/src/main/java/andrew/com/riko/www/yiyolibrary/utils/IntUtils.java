package andrew.com.riko.www.yiyolibrary.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Test on 2017/11/4.
 */

public class IntUtils {

    public static Integer[] makeSequence(int begin, int end){
        Integer[] result = new Integer[0];
        List<Integer> ret = new ArrayList(end - begin + 1);

        for(int i = begin; i <= end; ret.add(i),i++ );

        return ret.toArray(result);
    }


}
