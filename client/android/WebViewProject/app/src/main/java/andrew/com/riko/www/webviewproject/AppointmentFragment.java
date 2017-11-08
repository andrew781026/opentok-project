package andrew.com.riko.www.webviewproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import andrew.com.riko.www.webviewproject.model.VideoConnectInfo;
import andrew.com.riko.www.webviewproject.properties.KeyName;
import andrew.com.riko.www.webviewproject.utils.IntUtils;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AppointmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private AppointmentFragment.ViewHolder viewHolder ;
    private class ViewHolder {

        private Spinner citySpinner;
        private Spinner hospitalSpinner;
        private Spinner divisionSpinner;
        private Spinner monthSpinner;
        private Spinner daySpinner;
        private Button appointmentBTN;
        private EditText doctor;

        public ViewHolder(Spinner citySpinner, Spinner hospitalSpinner,
                          Spinner divisionSpinner,Spinner monthSpinner,
                          Spinner daySpinner, Button appointmentBTN,EditText doctor) {
            this.citySpinner = citySpinner;
            this.hospitalSpinner = hospitalSpinner;
            this.divisionSpinner = divisionSpinner;
            this.monthSpinner = monthSpinner;
            this.daySpinner = daySpinner;
            this.appointmentBTN = appointmentBTN;
            this.doctor = doctor;
        }
    }


    public AppointmentFragment() {
        // Required empty public constructor
    }

    public void sendAppointment(Context context){
        this.postToCallCenter(context);
    }

    private void postToCallCenter(final Context context){

        final String url = "https://www.yoecare.com/missions";

        Thread thread = new Thread(new Runnable() {

            private String getDescription(){
                StringBuffer result = new StringBuffer();

                String city = viewHolder.citySpinner.getSelectedItem().toString();
                String hospital = viewHolder.hospitalSpinner.getSelectedItem().toString();
                String division = viewHolder.divisionSpinner.getSelectedItem().toString();
                String year = "2017";
                String month = String.format("%02d",Integer.valueOf(viewHolder.monthSpinner.getSelectedItem().toString()));
                String day = String.format("%02d",Integer.valueOf(viewHolder.daySpinner.getSelectedItem().toString()));
                String doctor = viewHolder.doctor.getText().toString();

                result.append(city).append(hospital).append(division).append(year).append(month).append(day).append(doctor);
                return result.toString();
            }

            private FormBody getFormBody() {

                String description = this.getDescription();

                FormBody formBody = new FormBody.Builder()
                        .add("parent_id", "0") //其实会自动编码，但是无法控制编码格式
                        .add("requester_id","1")
                        .add("provider_id","0")
                        .add("type_id","2")
                        .add("status_id","1")
                        .add("method","0")
                        // .addEncoded("group_name", URLEncoder.encode("聯發科","UTF-8")) // //添加已编码的键值对
                        .add("group_name","聯發科") // //添加已编码的键值对
                        .add("vip_card_no","F0008-0005")
                        .add("type_name","掛號")
                        .add("requester_name","賴珠珠")
                        .add("provider_name","客服人員")
                        .add("status_name","未執行")
                        .add("description",description)
                        .add("mission_score","0")
                        .add("provider_score","0")
                        .add("suggestion","此欄位為醫師填寫欄位")
                        .add("issued_at","發送日期(yyyyMMdd)")
                        .add("took_at","0")
                        .add("finished_at","0")
                        .build();

                return formBody ;
            }

            private VideoConnectInfo getVideoConnectInfo(){

                OkHttpClient client = new OkHttpClient();
                FormBody formBody = this.getFormBody();
                Request request = new Request.Builder().url(url).post(formBody).build();

                try {
                    Response response = client.newCall(request).execute();

                    if ( response.code() == 200 ){

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppointmentFragment.this.showDialog(context,null);
                            }
                        });
                        /*
                                                String body = response.body().string();
                                                Gson gson = new Gson();
                                                VideoConnectInfo videoConnectInfo = gson.fromJson(body, VideoConnectInfo.class);
                                                return videoConnectInfo ;
                                                */
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null ;
            }

            @Override
            public void run() {
                final VideoConnectInfo videoConnectInfo = this.getVideoConnectInfo();

                if ( videoConnectInfo != null ){

                    // 新的執行續不能更新 ui
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 要在 ui thread
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(KeyName.VIDEO_CONNECT_INFO,videoConnectInfo);

                            Intent intent = new Intent(getActivity(),MultiVideoChatActivity.class);
                            intent.putExtras(bundle);
                            getActivity().startActivity(intent);
                        }
                    });

                }else {
                    // 新的執行續不能更新 ui
                    /*
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                // 要在 ui thread 上跑 toast
                                                Toast.makeText(getActivity(),"cannot get videoConnectInfo",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        */

                }


            }
        });
        thread.start();
    }

    private void showDialog(Context context, final VideoConnectInfo videoConnectInfo){

        // 處理掛號的事情
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setTitle("掛號中")
                .setMessage("您的掛號訊息已發送成功 , 專人正在處理中");
                /*
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Bundle bundle = new Bundle();
                        bundle.putSerializable(KeyName.VIDEO_CONNECT_INFO,videoConnectInfo);

                        Intent intent = new Intent(getActivity(),MultiVideoChatActivity.class);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);

                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                */

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();

    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentFragment newInstance(String param1, String param2) {
        AppointmentFragment fragment = new AppointmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 改變 Title
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("掛號");
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner citySpinner = (Spinner) getView().findViewById(R.id.citySpinner);
        Spinner hospitalSpinner = (Spinner) getView().findViewById(R.id.hospitalSpinner);
        Spinner divisionSpinner = (Spinner) getView().findViewById(R.id.divisionSpinner);
        EditText doctorET = (EditText) getView().findViewById(R.id.doctorET);

        //使用Spinner
        Spinner monthSpinner = (Spinner) getView().findViewById(R.id.monthSpinner);
        Integer[] months = IntUtils.makeSequence(1,12);
        ArrayAdapter<Integer> monthAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,months);
        monthSpinner.setAdapter(monthAdapter);

        //使用Spinner
        Spinner daySpinner = (Spinner) getView().findViewById(R.id.daySpinner);
        Integer[] days = IntUtils.makeSequence(1,31);
        ArrayAdapter<Integer> dayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,days);
        daySpinner.setAdapter(dayAdapter);

        Button appointmentBTN = (Button) getView().findViewById(R.id.appointmentBTN);

        appointmentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointmentFragment.this.sendAppointment(getActivity());
            }
        });

        viewHolder = new AppointmentFragment.ViewHolder(citySpinner,hospitalSpinner,divisionSpinner,monthSpinner,daySpinner,appointmentBTN,doctorET);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            Toast.makeText(context,"Appointment fragment attached",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
