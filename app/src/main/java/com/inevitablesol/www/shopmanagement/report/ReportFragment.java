package com.inevitablesol.www.shopmanagement.report;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.inevitablesol.www.shopmanagement.R;
import com.inevitablesol.www.shopmanagement.analysis.date.DatePickFragments;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    private FragmentActivity fragmentActivity;

    TextView txt_FromDate,txt_fromMonth,txt_fromYear,txt_tillDate;
    Button download;
    RadioButton pdf,xls,total_from,total_month,total_year,till;
    ImageView fromDate,toDate,month,year,onlyYear;

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

        txt_FromDate= (TextView) view.findViewById(R.id.total_fromdate);
        txt_fromMonth=(TextView) view.findViewById(R.id.t_month);
        txt_fromYear = (TextView) view.findViewById(R.id.txt_onlyYear);
        txt_tillDate = (TextView) view.findViewById(R.id.txt_tillDate);

        //Doing Something

        pdf = (RadioButton) view.findViewById(R.id.pdf);
        xls = (RadioButton) view.findViewById(R.id.xls);
        download = (Button) view.findViewById(R.id.total_saleDownload);

        total_from = (RadioButton) view.findViewById(R.id.total_from);
        total_month = (RadioButton) view.findViewById(R.id.total_month);
        total_year = (RadioButton) view.findViewById(R.id.total_year);
        till = (RadioButton) view.findViewById(R.id.till);

        fromDate = (ImageView)view.findViewById(R.id.report_imgfromDate);
        toDate = (ImageView)view.findViewById(R.id.total_todate_image);
        month = (ImageView)view.findViewById(R.id.total_imgMonth);
        onlyYear = (ImageView) view.findViewById(R.id.img_onlyYear);

        //FROM TO RADIOBUTTON
        if(total_from.isChecked()){
            fromDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                    Log.d(TAG, "onClick: datePicker"+datePickFragments);

                    //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                    datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");
                    final Calendar myCalendar = Calendar.getInstance();

                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            Date date1 = myCalendar.getTime();

                            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                            String currentDateTimeString= dateFormat.format(date1);
                            txt_FromDate.setText(currentDateTimeString);
                        }

                    };
                    Log.d("date", String.valueOf(myCalendar.getTime()));
                }
            });
            toDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                    Log.d(TAG, "onClick: datePicker"+datePickFragments);

                    datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");
                    final Calendar myCalendar = Calendar.getInstance();
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            Date date1 = myCalendar.getTime();

                            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                            String currentDateTimeString= dateFormat.format(date1);
                            txt_FromDate.setText(currentDateTimeString);
                        }

                    };
                    Log.d("date", String.valueOf(myCalendar.getTime()));
                }
            });
            if(pdf.isChecked())
            {
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Uri uri = Uri.parse("http://www.google.co.in");
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT,uri);
                        startActivity(intent);
                    }
                });
            }
            else if(xls.isChecked()){
                download.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){


                    }
                });
            }
            else{
                Toast.makeText(getContext(),"Please select one option to proceed",Toast.LENGTH_LONG);
            }
        }

        //MONTH RADIOBUTTON

         else if(total_month.isChecked())
        {
            month.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                    Log.d(TAG, "onClick: datePicker"+datePickFragments);

                    datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");

                    new YearMonthPickerDialog.OnDateSetListener() {
                        @Override
                        public void onYearMonthSet(int year, int month) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.YEAR,year);
                            calendar.set(Calendar.MONTH,month);
                            calendar.set(Calendar.DAY_OF_MONTH,1);

                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
                            String currentMonthYear = dateFormat.format(calendar.getTime());
                            txt_fromMonth.setText(currentMonthYear);

                            calendar.add(Calendar.MONTH,+1);
                            calendar.add(Calendar.DATE,-1);
                        }
                    };
                }
            });

            if(pdf.isChecked())
            {
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Uri uri = Uri.parse("http://www.google.co.in");
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT,uri);
                        startActivity(intent);
                    }
                });
            }
            else if(xls.isChecked()){
                download.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){


                    }
                });
            }
            else{
                Toast.makeText(getContext(),"Please select one option to proceed",Toast.LENGTH_LONG);
            }
        }

        //year = (ImageView)view.findViewById(R.id.total_imgYear);

        //YEAR RADIOBUTTON
        else if (total_year.isChecked()){
            year.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                    Log.d(TAG, "onClick: datePicker"+datePickFragments);

                    datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");

                    new YearMonthPickerDialog.OnDateSetListener() {
                        @Override
                        public void onYearMonthSet(int year, int month) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.YEAR,year);
                            calendar.set(Calendar.MONTH,month);
                            calendar.set(Calendar.DAY_OF_MONTH,1);

                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
                            String currentMonthYear = dateFormat.format(calendar.getTime());
                            txt_fromYear.setText(currentMonthYear);

                            calendar.add(Calendar.MONTH,+1);
                            calendar.add(Calendar.DATE,-1);
                        }
                    };
                }
            });
            if(pdf.isChecked())
            {
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Uri uri = Uri.parse("http://www.google.co.in");
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT,uri);
                        startActivity(intent);
                    }
                });
            }
            else if(xls.isChecked()){
                download.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){


                    }
                });
            }
            else{
                Toast.makeText(getContext(),"Please select one option to proceed",Toast.LENGTH_LONG);
            }
        }

        //TILL RADIO BUTTON
        else if(till.isChecked()){
            txt_tillDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                    Log.d(TAG, "onClick: datePicker"+datePickFragments);

                    datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");
                    final Calendar myCalendar = Calendar.getInstance();
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            Date date1 = myCalendar.getTime();

                            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                            String currentDateTimeString= dateFormat.format(date1);
                            txt_tillDate.setText(currentDateTimeString);
                        }

                    };
                    Log.d("date", String.valueOf(myCalendar.getTime()));
                }
            });
            if(pdf.isChecked())
            {
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Uri uri = Uri.parse("http://www.google.co.in");
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT,uri);
                        startActivity(intent);
                    }
                });
            }
            else if(xls.isChecked()){
                download.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){


                    }
                });
            }
            else{
                Toast.makeText(getContext(),"Please select one option to proceed",Toast.LENGTH_LONG);
            }
        }
        else{
            Toast.makeText(getContext(),"Please select one option to proceed",Toast.LENGTH_LONG);
        }

        return view;
    }

//    private void show(){
//        new YearMonthPickerDialog(this, new YearMonthPickerDialog.OnDateSetListener() {
//            @Override
//            public void onYearMonthSet(int year, int month)
//            {
//                Log.d(TAG, "onYearMonthSet: Year Month"+year+" "+month);
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(Calendar.YEAR, year);
//                calendar.set(Calendar.MONTH, month);
//                calendar.set(Calendar.DAY_OF_MONTH,1);
//
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
//                currentDateTime= dateFormat.format(calendar.getTime());
//
//                currentDate.setText(dateFormat.format(calendar.getTime()));
//                // calendar.set(calendar.MONTH,month+1);
//                Log.d(TAG, "onYearMonthSet: " + dateFormat.format(calendar.getTime()));
//                calendar.add(Calendar.MONTH,1);
//                calendar.add(Calendar.DATE,-1);
//
//                getProfitLoss_Status();
//                Log.d(TAG, "onYearMonthSet: "+dateFormat.format(calendar.getTime()));
//
//                //calendar.add(Calendar.DATE,-1);
//
//                // nextMonth=dateFormat.format(calendar.getTime());
//
//                Log.d(TAG, "onYearMonthSet:" + dateFormat.format(calendar.getTime()));
//            }
//        }).show();
//    }



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
