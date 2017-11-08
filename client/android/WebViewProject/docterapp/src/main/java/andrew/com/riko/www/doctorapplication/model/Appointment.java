package andrew.com.riko.www.doctorapplication.model;


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
    private int parent_id ;
    private int requester_id  ;
    private int provider_id  ;
    private int type_id  ;
    private int status_id  ;
    private int method  ;
    private String group_name ;
    private String vip_card_no  ;
    private String type_name ;
    private String requester_name ;
    private String provider_name ;
    private String status_name ;
    private String description ;
    private int mission_score ;
    private int provider_score ;
    private String suggestion ;
    private String issued_at ;
    private String took_at ;
    private String finished_at ;

    public AppointmentInfo getAppointmentInfo() {
        return appointmentInfo;
    }

    public void setAppointmentInfo(AppointmentInfo appointmentInfo) {
        this.appointmentInfo = appointmentInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getRequester_id() {
        return requester_id;
    }

    public void setRequester_id(int requester_id) {
        this.requester_id = requester_id;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getVip_card_no() {
        return vip_card_no;
    }

    public void setVip_card_no(String vip_card_no) {
        this.vip_card_no = vip_card_no;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getRequester_name() {
        return requester_name;
    }

    public void setRequester_name(String requester_name) {
        this.requester_name = requester_name;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMission_score() {
        return mission_score;
    }

    public void setMission_score(int mission_score) {
        this.mission_score = mission_score;
    }

    public int getProvider_score() {
        return provider_score;
    }

    public void setProvider_score(int provider_score) {
        this.provider_score = provider_score;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getIssued_at() {
        return issued_at;
    }

    public void setIssued_at(String issued_at) {
        this.issued_at = issued_at;
    }

    public String getTook_at() {
        return took_at;
    }

    public void setTook_at(String took_at) {
        this.took_at = took_at;
    }

    public String getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(String finished_at) {
        this.finished_at = finished_at;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentInfo=" + appointmentInfo +
                ", id=" + id +
                ", parent_id=" + parent_id +
                ", requester_id=" + requester_id +
                ", provider_id=" + provider_id +
                ", type_id=" + type_id +
                ", status_id=" + status_id +
                ", method=" + method +
                ", group_name='" + group_name + '\'' +
                ", vip_card_no='" + vip_card_no + '\'' +
                ", type_name='" + type_name + '\'' +
                ", requester_name='" + requester_name + '\'' +
                ", provider_name='" + provider_name + '\'' +
                ", status_name='" + status_name + '\'' +
                ", description='" + description + '\'' +
                ", mission_score=" + mission_score +
                ", provider_score=" + provider_score +
                ", suggestion='" + suggestion + '\'' +
                ", issued_at='" + issued_at + '\'' +
                ", took_at='" + took_at + '\'' +
                ", finished_at='" + finished_at + '\'' +
                '}';
    }
}
