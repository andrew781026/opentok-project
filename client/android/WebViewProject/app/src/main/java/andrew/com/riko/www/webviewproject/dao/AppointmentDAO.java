package andrew.com.riko.www.webviewproject.dao;

import android.database.sqlite.SQLiteDatabase;

import andrew.com.riko.www.webviewproject.properties.VipConfig;

/**
 * Created by Test on 2017/11/7.
 */

public class AppointmentDAO {

    private SQLiteDatabase db ;

    public AppointmentDAO(){

        db = SQLiteDatabase.openOrCreateDatabase(VipConfig.DATABASE_NAME,null);

    }


}
