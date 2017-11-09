package andrew.com.riko.www.doctorapplication.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import andrew.com.riko.www.doctorapplication.model.Task;

import static org.junit.Assert.*;

/**
 * Created by Test on 2017/11/9.
 */
@RunWith(AndroidJUnit4.class)
public class TaskDAOTest {

    TaskDAO dao ;

    @Before
    public void setUp() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        dao = new TaskDAO(appContext);
    }

    @After
    public void tearDown() throws Exception {
        dao.close();
    }

    @Test
    public void create() throws Exception {

        Task task = new Task();
        task.setMissionId(11);
        task.setAge(55);
        task.setTitle("掛號");
        task.setName("李石頭");
        task.setDescription("北區新北市長庚醫院復健科楊龍之醫師");
        dao.create(task);

    }

    @Test
    public void update() throws Exception {

        Task task = new Task();
        task.setMissionId(5);
        task.setAge(8);
        task.setTitle("掛號");
        task.setName("王八炭");
        task.setDescription("北區嘉義縣榮總神經科陳大耳醫師");
        dao.update(task);

    }

    @Test
    public void createOrUpdate() throws Exception {

        Task task = new Task();
        task.setMissionId(9);
        task.setAge(46);
        task.setTitle("出診");
        task.setName("小小強");
        task.setDescription("南區高雄市成大醫院家醫科黃曉玫醫師");
        dao.createOrUpdate(task);
    }

    @Test
    public void refresh() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void deleteById() throws Exception {

    }

    @Test
    public void list() throws Exception {
        List<Task> taskList = dao.list();
        for ( Task task : taskList ){
            Log.i("task = " , task.toString());
        }
    }

    @Test
    public void get() throws Exception {

    }


}