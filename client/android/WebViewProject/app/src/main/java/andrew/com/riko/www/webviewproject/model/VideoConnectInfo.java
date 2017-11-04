package andrew.com.riko.www.webviewproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import andrew.com.riko.www.webviewproject.utils.StringUtils;


/**
 * Created by Test on 2017/11/1.
 */
public class VideoConnectInfo implements Serializable {

    @SerializedName("id")
    private int taskId;
    @SerializedName("api_key")
    private String apiKey;
    @SerializedName("session_id")
    private String sessionId;
    @SerializedName("token")
    private String token;
    @SerializedName("room_name")
    private String roomName;

    public VideoConnectInfo() {
    }

    public VideoConnectInfo(String apiKey, String sessionId, String token, String roomName) {
        this.apiKey = apiKey;
        this.sessionId = sessionId;
        this.token = token;
        this.roomName = roomName;
    }

    public VideoConnectInfo(int taskId, String apiKey, String sessionId, String token, String roomName) {
        this.taskId = taskId;
        this.apiKey = apiKey;
        this.sessionId = sessionId;
        this.token = token;
        this.roomName = roomName;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isConnectable(){
        if ( StringUtils.isBlank(apiKey) ) return false ;
        if ( StringUtils.isBlank(sessionId) ) return false ;
        if ( StringUtils.isBlank(token) ) return false ;
        if ( StringUtils.isBlank(roomName) ) return false ;
        return true ;
    }

    @Override
    public String toString() {
        return "VideoConnectInfo{" +
                "taskId=" + taskId +
                ", apiKey='" + apiKey + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", token='" + token + '\'' +
                ", roomName='" + roomName + '\'' +
                '}';
    }
}
