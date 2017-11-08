package andrew.com.riko.www.webviewproject.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import andrew.com.riko.www.webviewproject.model.Registration;
import andrew.com.riko.www.webviewproject.utils.DatabaseHelper;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class RegistrationDAOTest {

    RegistrationDAO dao ;

    @Before
    public void setUp() throws Exception {

        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        dao = new RegistrationDAO(appContext);

    }

    @After
    public void tearDown() throws Exception {
        dao.close();
    }

    @Test
    public void create() throws Exception {
        Registration registration = new Registration();
        registration.setCity("新北市");
        registration.setDay(5);
        registration.setYear(2018);

        dao.create(registration);
    }

    @Test
    public void update() throws Exception {
        Registration registration = new Registration();
        registration.setCity("彰化市");
        registration.setDay(22);
        registration.setYear(1985);
        registration.setId(1);

        dao.create(registration);
    }

    @Test
    public void createOrUpdate() throws Exception {
        Registration registration = new Registration();
        registration.setCity("台北市");
        registration.setHospital("長庚醫院");
        registration.setDay(5);
        registration.setYear(2018);
        registration.setId(6);

        dao.createOrUpdate(registration);
    }

    @Test
    public void refresh() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void deleteById() throws Exception {
        dao.deleteById(2);
    }

    @Test
    public void list() throws Exception {
        List<Registration> registrationList = dao.list();
        for ( Registration registration : registrationList ){
            Log.i("registration = ", registration.toString());
        }
    }

    @Test
    public void get() throws Exception {
        dao.get(5);
    }

}