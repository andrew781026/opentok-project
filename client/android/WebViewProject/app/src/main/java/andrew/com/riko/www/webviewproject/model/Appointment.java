package andrew.com.riko.www.webviewproject.model;


/**
 * Created by Test on 2017/11/7.
 */
public class Appointment {

    private AppointmentInfo appointmentInfo ;
    private class AppointmentInfo{

        private String city ;
        private String hospital ;
        private String division;
        private int year ;
        private int month ;
        private int day ;

    }

    private int id ;
    private int parent_id = 0;
    private int requester_id = 1 ;
    private int provider_id = 0 ;
    private int type_id = 2 ;
    private int status_id = 1 ;
    private int method = 0 ;
    private String group_name = "聯發科" ;
    private String vip_card_no = "F0008-0005" ;
    private String type_name = "掛號" ;
    private String requester_name = "賴珠珠" ;
    private String provider_name = "客服人員" ;
    private String status_name = "未執行" ;
    private String description = "" ;
    private int mission_score = 0 ;
    private int provider_score = 0 ;
    private String suggestion = "此欄位為醫師填寫欄位" ;
    private String issued_at = "發送日期(yyyyMMdd)" ;
    private String took_at = "0" ;
    private String finished_at = "0" ;



}
