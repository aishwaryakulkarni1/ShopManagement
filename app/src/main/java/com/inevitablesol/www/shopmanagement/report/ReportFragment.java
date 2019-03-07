package com.inevitablesol.www.shopmanagement.report;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inevitablesol.www.shopmanagement.R;
import com.inevitablesol.www.shopmanagement.analysis.date.DatePickFragments;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.*/

public class ReportFragment extends Fragment implements DatePickFragments.OnDateSetItem {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DayBook = "DayBook";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private  Context context;

    private static final String TAG = "ReportFragment";
    private FragmentActivity fragmentActivity;;

    public ReportFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ReportFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ReportFragment newInstance(String param1, String param2)
//    {
//        Log.d(TAG, "newInstance:  mParam"+param1);
//        Log.d(TAG, "newInstance: mParam2"+param2);
//        ReportFragment fragment = new ReportFragment();
//        Bundle args = new Bundle();
//       // args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(DayBook);
            Log.d(TAG, "onCreate: mParam"+mParam1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.report_dialog, container, false);
        TextView tx_title= (TextView) view.findViewById(R.id.fm_title);
        if(mParam1.equalsIgnoreCase("DayBook"))
        {
            tx_title.setText("Day Book Report");
        }

        ImageView  imageView=(ImageView)view.findViewById(R.id.report_imgfromDate);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                Log.d(TAG, "onClick: datePicker"+datePickFragments);

             //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        fragmentActivity=(FragmentActivity)context;
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void getDate(String date)
    {
        Log.d(TAG, "getDate in Fragment:"+date);

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
