package andrew.com.riko.www.webviewproject.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import andrew.com.riko.www.webviewproject.model.Registration;
import andrew.com.riko.www.webviewproject.utils.DatabaseHelper;

/**
 * Created by Test on 2017/11/7.
 */
public class RegistrationDAO implements AutoCloseable {

    private Dao<Registration, Integer> registrationDao = null;
    private DatabaseHelper helper = null;

    public RegistrationDAO(Context context){

        helper  = new DatabaseHelper(context);
        try {
            registrationDao = helper.getRegistrationDao();
        } catch (android.database.SQLException e) {
            Log.e("in RegistrationDAO","throw excption at creating table");
        }
    }

    /**
     *
     * @param registration 要新增的 registration
     * @return The number of rows updated in the database
     */
    public int create(Registration registration){
        try {
            return registrationDao.create(registration);
        } catch (SQLException | NullPointerException e) {
            Log.e("in RegistrationDAO","throw excption at creating registration");
            return 0;
        }
    }

    /**
     *
     * @param registration 要update的 registration
     * @return The number of rows updated in the database
     */
    public int update(Registration registration){
        try {
            return registrationDao.update(registration);
        } catch (SQLException | NullPointerException e) {
            Log.e("in RegistrationDAO","throw excption at updating registration");
            return 0;
        }
    }

    /**
     *
     * @param registration 利用傳入的registration id , 做 createOrUpdate 的判別
     * @return 紀錄這次作業影響的row 跟 isUpdated or isCreated
     */
    public Dao.CreateOrUpdateStatus createOrUpdate(Registration registration){
        try {
            return registrationDao.createOrUpdate(registration);
        } catch (SQLException | NullPointerException e) {
            Log.e("in RegistrationDAO","throw excption at createOrUpdate registration");
            return null;
        }
    }

    // 讓 registration 的資料跟 db 中的相同
    public int refresh(Registration registration){
        try {
            return registrationDao.refresh(registration);
        } catch (SQLException | NullPointerException e) {
            Log.e("in RegistrationDAO","throw excption at createOrUpdate registration");
            return 0;
        }
    }

    public int delete(Registration registration){
        return this.deleteById(registration.getId());
    }

    public int deleteById(Integer id){
        try {
            return registrationDao.deleteById(id);
        } catch (SQLException | NullPointerException e) {
            Log.e("in RegistrationDAO","throw excption at deleteById");
            return 0;
        }
    }

    public List<Registration> list(){
        try {
            return registrationDao.queryForAll();
        } catch (SQLException | NullPointerException e) {
            Log.e("in RegistrationDAO","throw excption at queryForAll");
            return null;
        }
    }

    public Registration get(Integer id){
        try {
            return registrationDao.queryForId(id);
        } catch (SQLException | NullPointerException e) {
            Log.e("in RegistrationDAO","throw excption at get");
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        helper.close();
        registrationDao = null;
    }
}
