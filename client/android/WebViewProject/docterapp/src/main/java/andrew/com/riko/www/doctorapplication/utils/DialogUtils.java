package andrew.com.riko.www.doctorapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by Test on 2017/11/9.
 */

public class DialogUtils {

    private static void makeSimpleInfoDialog(Activity activity, String title, String message){
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setTitle(title)
                .setMessage(message);

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showSimpleInfoDialog(final Activity activity, final String title, final String message){

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                makeSimpleInfoDialog(activity,title,message);
            }
        });

    }

}
