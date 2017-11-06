package andrew.com.riko.www.yiyolibrary.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Test on 2017/11/4.
 */

public class FragmentUtils {

    public static void switchFragment(AppCompatActivity activity, int viewId, Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(viewId, fragment).commit();
    }

}
