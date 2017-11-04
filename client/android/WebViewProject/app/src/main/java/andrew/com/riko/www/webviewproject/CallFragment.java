package andrew.com.riko.www.webviewproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import andrew.com.riko.www.webviewproject.model.VideoConnectInfo;
import andrew.com.riko.www.webviewproject.properties.KeyName;
import andrew.com.riko.www.webviewproject.MultiVideoChatActivity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CallFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CallFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CallFragment newInstance(String param1, String param2) {
        CallFragment fragment = new CallFragment();
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("打給客服");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = (Button) getView().findViewById(R.id.callBtn);
        EditText editText = (EditText) getView().findViewById(R.id.taskIdET);

        String taskId = editText.getText().toString();
        final String url = "https://www.yoecare.com/calls/"+taskId;

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {

                    private VideoConnectInfo getVideoConnectInfo(){

                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        try {
                            Response response = client.newCall(request).execute();

                            if ( response.code() == 200 ){

                                String body = response.body().string();
                                Gson gson = new Gson();
                                VideoConnectInfo videoConnectInfo = gson.fromJson(body, VideoConnectInfo.class);
                                return videoConnectInfo ;
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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // 要在 ui thread 上跑 toast
                                    Toast.makeText(getActivity(),"cannot get videoConnectInfo",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }


                    }
                });
                thread.start();

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_call, container, false);
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
            Toast.makeText(context,"call fragment attached",Toast.LENGTH_SHORT).show();
            /*
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
                    */
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
