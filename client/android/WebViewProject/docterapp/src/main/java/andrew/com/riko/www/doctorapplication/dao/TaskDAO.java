package andrew.com.riko.www.doctorapplication.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import andrew.com.riko.www.doctorapplication.model.Task;
import andrew.com.riko.www.doctorapplication.utils.DatabaseHelper;

/**
 * Created by Test on 2017/11/7.
 */
public class TaskDAO implements AutoCloseable {

    private Dao<Task, Integer> taskDao = null;
    private DatabaseHelper helper = null;
    private static final String TAG = "in TaskDAO";

    public TaskDAO(Context context){

        helper = new DatabaseHelper(context);
        try {
            taskDao = helper.getTaskDao();
        } catch (android.database.SQLException e) {
            Log.e(TAG,"throw excption at creating table");
        }
    }

    /**
     *
     * @param task 要新增的 registration
     * @return The number of rows updated in the database
     */
    public int create(Task task){
        try {
            return taskDao.create(task);
        } catch (SQLException | NullPointerException e) {
            Log.e(TAG,"throw excption at creating registration");
            return 0;
        }
    }

    /**
     *
     * @param task 要update的 registration
     * @return The number of rows updated in the database
     */
    public int update(Task task){
        try {
            return taskDao.update(task);
        } catch (SQLException | NullPointerException e) {
            Log.e(TAG,"throw excption at updating registration");
            return 0;
        }
    }

    /**
     *
     * @param task 利用傳入的registration id , 做 createOrUpdate 的判別
     * @return 紀錄這次作業影響的row 跟 isUpdated or isCreated
     */
    public Dao.CreateOrUpdateStatus createOrUpdate(Task task){
        try {
            return taskDao.createOrUpdate(task);
        } catch (SQLException | NullPointerException e) {
            Log.e(TAG,"throw excption at createOrUpdate registration");
            return null;
        }
    }

    // 讓 registration 的資料跟 db 中的相同
    public int refresh(Task task){
        try {
            return taskDao.refresh(task);
        } catch (SQLException | NullPointerException e) {
            Log.e(TAG,"throw excption at createOrUpdate registration");
            return 0;
        }
    }

    public int delete(Task task){
        return this.deleteById(task.getMissionId());
    }

    public int deleteById(Integer id){
        try {
            return taskDao.deleteById(id);
        } catch (SQLException | NullPointerException e) {
            Log.e(TAG,"throw excption at deleteById");
            return 0;
        }
    }

    public List<Task> list(){
        try {
            return taskDao.queryForAll();
        } catch (SQLException | NullPointerException e) {
            Log.e(TAG,"throw excption at queryForAll");
            return null;
        }
    }

    public Task get(Integer id){
        try {
            return taskDao.queryForId(id);
        } catch (SQLException | NullPointerException e) {
            Log.e(TAG,"throw excption at get");
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        helper.close();
        taskDao = null;
    }
}
