package com.inevitablesol.www.shopmanagement.report;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.inevitablesol.www.shopmanagement.R;
import com.inevitablesol.www.shopmanagement.analysis.date.DatePickFragments;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private String DayBook;

    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1,mParam2,mParam3,mParam4,mParam5,mParam6,mParam7,mParam8;
//    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private  Context context;

    private static final String GET_DAYBOOKREPORT ="http://35.161.99.113:9000/webapi/" ;

    private static final String TAG = "ReportFragment";
    private FragmentActivity fragmentActivity;

    TextView txt_FromDate,txt_fromMonth,txt_fromYear,txt_tillDate,txt_toDate;
    Button download;
    RadioButton pdf,xls,total_from,total_month,total_year,till;
    ImageView fromDate,toDate,month,onlyYear;

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

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

        sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        Bundle b = getArguments();
        if (b != null)
        {
            mParam1 = b.getString("DayBook");
            mParam2 = b.getString("Discount");
            mParam3 = b.getString("ProfitLoss");
            mParam4 = b.getString("Sales");
            mParam5 = b.getString("Purchase");
            mParam6 = b.getString("Expenses");
            mParam7 = b.getString("Tax");
            mParam8 = b.getString("TaxRate");

        }

        Log.d(TAG, "onCreate: ");

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.report_dialog, container, false);
        TextView tx_title= (TextView) view.findViewById(R.id.fm_title);


        txt_FromDate= (TextView) view.findViewById(R.id.total_fromdate);
        txt_fromMonth=(TextView) view.findViewById(R.id.t_month);
        txt_fromYear = (TextView) view.findViewById(R.id.txt_onlyYear);
        txt_tillDate = (TextView) view.findViewById(R.id.txt_tillDate);
        txt_toDate = (TextView) view.findViewById(R.id.total_toDate);
        //Doing Something

        pdf = (RadioButton) view.findViewById(R.id.pdf);
        xls = (RadioButton) view.findViewById(R.id.xls);
        download = (Button) view.findViewById(R.id.total_saleDownload);

//        total_from = (RadioButton) view.findViewById(R.id.total_from);
//        total_month = (RadioButton) view.findViewById(R.id.total_month);
//        total_year = (RadioButton) view.findViewById(R.id.total_year);
//        till = (RadioButton) view.findViewById(R.id.till);
//
        fromDate = (ImageView)view.findViewById(R.id.report_imgfromDate);
        toDate = (ImageView)view.findViewById(R.id.total_todate_image);
        month = (ImageView)view.findViewById(R.id.total_imgMonth);
        onlyYear = (ImageView) view.findViewById(R.id.img_onlyYear);

        final RadioGroup rg1 = (RadioGroup) view.findViewById(R.id.total_SaleRadio);
        final RadioGroup rg2 = (RadioGroup) view.findViewById(R.id.total_Sale);

        if(("DayBook").equalsIgnoreCase(mParam1)){

            tx_title.setText("Day Book Report");

            fromDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String fromDateString;

                    DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                    Log.d(TAG, "onClick: datePicker"+datePickFragments);

                    //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                    // datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");
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

                            //updateDate(myCalendar);
                            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                            String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                            txt_FromDate.setText(currentDateTimeString);

                        }

                    };

                    new DatePickerDialog(getContext(),date,
                            myCalendar.get(Calendar.YEAR),
                            myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            toDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                    Log.d(TAG, "onClick: datePicker"+datePickFragments);

                    //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                    //datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");

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

                            //updateDate(myCalendar);
                            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                            String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                            txt_toDate.setText(currentDateTimeString);

                        }

                    };

                    new DatePickerDialog(getContext(),date,
                            myCalendar.get(Calendar.YEAR),
                            myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                     YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                          new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });
            onlyYear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                    new YearMonthPickerDialog(getActivity(),yd).show();
                }
            });
            Date dt = new Date();
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyy-MM-dd");
            String currentDateTimeString = dateFormat.format(dt);
            txt_tillDate.setText(currentDateTimeString);

            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View rootView = inflater.inflate(R.layout.report_dialog, container, false);
                    int selectedId1 = rg1.getCheckedRadioButtonId();
                    RadioButton radioButton1 = (RadioButton)rootView.findViewById(selectedId1);
                    String  fileFormat1   =     radioButton1.getText().toString().trim();

                    int selectedId = rg2.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton)rootView.findViewById(selectedId);
                    String  fileFormat   = radioButton.getText().toString().trim();

                    //String Year = txttillDate.getText().toString().trim();

                    Log.d(TAG, "onClick:pdf xls "+fileFormat);
                    Log.d(TAG, "onClick: from to"+    fileFormat1);

                    if(("From").equalsIgnoreCase(fileFormat1) ){

                        if((".pdf").equalsIgnoreCase(fileFormat)) {


                            String dbname = sharedPreferences.getString("dbname","empty");
                            String fromDateString=txt_FromDate.getText().toString();
                            String toDateString = txt_toDate.getText().toString();
                            String month = txt_fromMonth.getText().toString();
                            String year = txt_fromYear.getText().toString();
                            String till = txt_tillDate.getText().toString();

                            ArrayList<String> shopInfo = new ArrayList<>();

                            shopInfo.add(sharedPreferences.getString("userId","empty"));
                            shopInfo.add(sharedPreferences.getString("username","empty"));
                            shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                            shopInfo.add(sharedPreferences.getString("useremail","empty"));
                            shopInfo.add(sharedPreferences.getString("userrole","empty"));
                            shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                            shopInfo.add(sharedPreferences.getString("s_id","empty"));
                            shopInfo.add(sharedPreferences.getString("dbname","empty"));
                            shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                            shopInfo.add(sharedPreferences.getString("address","empty"));
                            shopInfo.add(sharedPreferences.getString("state","empty"));
                            shopInfo.add(sharedPreferences.getString("shopName","empty"));


                            String pdfUrl = "http://mhourz-shopmgmt.surge.sh/DayBookReport?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=Yes";
                            Uri uri = Uri.parse(pdfUrl);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri,"text/html");
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.d("final url:",uri.toString());
                        }
                        else if ( fileFormat.equalsIgnoreCase(".xls")){
                            Toast.makeText(getActivity(),"In From  XLS",Toast.LENGTH_SHORT);
                        }
                    }
                    if(fileFormat1.equalsIgnoreCase("Month") ){

                        if(fileFormat.equalsIgnoreCase(".pdf")) {
                            String dbname = sharedPreferences.getString("dbname","empty");
                            String fromDateString=txt_FromDate.getText().toString();
                            String toDateString = txt_toDate.getText().toString();
                            String month = txt_fromMonth.getText().toString();
                            String year = txt_fromYear.getText().toString();
                            String till = txt_tillDate.getText().toString();

                            ArrayList<String> shopInfo = new ArrayList<>();

                            shopInfo.add(sharedPreferences.getString("userId","empty"));
                            shopInfo.add(sharedPreferences.getString("username","empty"));
                            shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                            shopInfo.add(sharedPreferences.getString("useremail","empty"));
                            shopInfo.add(sharedPreferences.getString("userrole","empty"));
                            shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                            shopInfo.add(sharedPreferences.getString("s_id","empty"));
                            shopInfo.add(sharedPreferences.getString("dbname","empty"));
                            shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                            shopInfo.add(sharedPreferences.getString("address","empty"));
                            shopInfo.add(sharedPreferences.getString("state","empty"));
                            shopInfo.add(sharedPreferences.getString("shopName","empty"));

                            String pdfUrl = "http://mhourz-shopmgmt.surge.sh/DayBookReport?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=month";
                            Uri uri = Uri.parse(pdfUrl);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri,"text/html");
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.d("final url:",uri.toString());

                        }
                        else if ( fileFormat.equalsIgnoreCase(".xls")){
                            Toast.makeText(getActivity(),"In Month  XLS",Toast.LENGTH_SHORT);
                        }
                    }
                    if(fileFormat1.equalsIgnoreCase("Year") ){

                        if(fileFormat.equalsIgnoreCase(".pdf")) {
                            String dbname = sharedPreferences.getString("dbname","empty");
                            String fromDateString=txt_FromDate.getText().toString();
                            String toDateString = txt_toDate.getText().toString();
                            String month = txt_fromMonth.getText().toString();
                            String year = txt_fromYear.getText().toString();
                            String till = txt_tillDate.getText().toString();

                            ArrayList<String> shopInfo = new ArrayList<>();

                            shopInfo.add(sharedPreferences.getString("userId","empty"));
                            shopInfo.add(sharedPreferences.getString("username","empty"));
                            shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                            shopInfo.add(sharedPreferences.getString("useremail","empty"));
                            shopInfo.add(sharedPreferences.getString("userrole","empty"));
                            shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                            shopInfo.add(sharedPreferences.getString("s_id","empty"));
                            shopInfo.add(sharedPreferences.getString("dbname","empty"));
                            shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                            shopInfo.add(sharedPreferences.getString("address","empty"));
                            shopInfo.add(sharedPreferences.getString("state","empty"));
                            shopInfo.add(sharedPreferences.getString("shopName","empty"));
                            String pdfUrl = "http://mhourz-shopmgmt.surge.sh/DayBookReport?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=year";
                            Uri uri = Uri.parse(pdfUrl);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri,"text/html");
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.d("final url:",uri.toString());
                        }
                        else if ( fileFormat.equalsIgnoreCase(".xls")){
                            Toast.makeText(getActivity(),"In Year  XLS",Toast.LENGTH_SHORT);
                        }
                    }
                    if(fileFormat1.equalsIgnoreCase("Till") ){

                        String dbname = sharedPreferences.getString("dbname","empty");
                        String fromDateString=txt_FromDate.getText().toString();
                        String toDateString = txt_toDate.getText().toString();
                        String month = txt_fromMonth.getText().toString();
                        String year = txt_fromYear.getText().toString();
                        String till = txt_tillDate.getText().toString();

                        ArrayList<String> shopInfo = new ArrayList<>();

                        shopInfo.add(sharedPreferences.getString("userId","empty"));
                        shopInfo.add(sharedPreferences.getString("username","empty"));
                        shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                        shopInfo.add(sharedPreferences.getString("useremail","empty"));
                        shopInfo.add(sharedPreferences.getString("userrole","empty"));
                        shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                        shopInfo.add(sharedPreferences.getString("s_id","empty"));
                        shopInfo.add(sharedPreferences.getString("dbname","empty"));
                        shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                        shopInfo.add(sharedPreferences.getString("address","empty"));
                        shopInfo.add(sharedPreferences.getString("state","empty"));
                        shopInfo.add(sharedPreferences.getString("shopName","empty"));
                        String pdfUrl = "http://mhourz-shopmgmt.surge.sh/DayBookReport?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=till";
                        Uri uri = Uri.parse(pdfUrl);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri,"text/html");
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Log.d("final url:",uri.toString());
                        }
                        else if ( fileFormat.equalsIgnoreCase(".xls")){
                            Toast.makeText(getActivity(),"In Till  XLS",Toast.LENGTH_SHORT);
                        }
                }
            });
        }
//
        else
            if(("Discount").equalsIgnoreCase(mParam2)){
//
            tx_title.setText("Discount Report");

                fromDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String fromDateString;

                        DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                        Log.d(TAG, "onClick: datePicker"+datePickFragments);

                        //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                        // datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");
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

                                //updateDate(myCalendar);
                                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                                txt_FromDate.setText(currentDateTimeString);

                            }

                        };

                        new DatePickerDialog(getContext(),date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                toDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                        Log.d(TAG, "onClick: datePicker"+datePickFragments);

                        //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                        //datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");

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

                                //updateDate(myCalendar);
                                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                                txt_toDate.setText(currentDateTimeString);

                            }

                        };

                        new DatePickerDialog(getContext(),date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                        new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });
                onlyYear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                        new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });

                Date dt = new Date();
                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyy-MM-dd");
                String currentDateTimeString = dateFormat.format(dt);
                txt_tillDate.setText(currentDateTimeString);

                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        View rootView = inflater.inflate(R.layout.report_dialog, container, false);
                        int selectedId1 = rg1.getCheckedRadioButtonId();
                        RadioButton radioButton1 = (RadioButton)rootView.findViewById(selectedId1);
                        String  fileFormat1   =     radioButton1.getText().toString().trim();

                        int selectedId = rg2.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton)rootView.findViewById(selectedId);
                        String  fileFormat   = radioButton.getText().toString().trim();

                        //String Year = txttillDate.getText().toString().trim();

                        Log.d(TAG, "onClick:pdf xls "+fileFormat);
                        Log.d(TAG, "onClick: from to"+    fileFormat1);

                        if(("From").equalsIgnoreCase(fileFormat1) ){

                            if((".pdf").equalsIgnoreCase(fileFormat)) {


                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));


                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Discount_Report_PDF?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=Yes";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());
                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In From  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Month") ){

                            if(fileFormat.equalsIgnoreCase(".pdf")) {
                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));

                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Discount_Report_PDF?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=month";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());

                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In Month  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Year") ){

                            if(fileFormat.equalsIgnoreCase(".pdf")) {
                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));
                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Discount_Report_PDF?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=year";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());
                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In Year  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Till") ){

                            String dbname = sharedPreferences.getString("dbname","empty");
                            String fromDateString=txt_FromDate.getText().toString();
                            String toDateString = txt_toDate.getText().toString();
                            String month = txt_fromMonth.getText().toString();
                            String year = txt_fromYear.getText().toString();
                            String till = txt_tillDate.getText().toString();

                            ArrayList<String> shopInfo = new ArrayList<>();

                            shopInfo.add(sharedPreferences.getString("userId","empty"));
                            shopInfo.add(sharedPreferences.getString("username","empty"));
                            shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                            shopInfo.add(sharedPreferences.getString("useremail","empty"));
                            shopInfo.add(sharedPreferences.getString("userrole","empty"));
                            shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                            shopInfo.add(sharedPreferences.getString("s_id","empty"));
                            shopInfo.add(sharedPreferences.getString("dbname","empty"));
                            shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                            shopInfo.add(sharedPreferences.getString("address","empty"));
                            shopInfo.add(sharedPreferences.getString("state","empty"));
                            shopInfo.add(sharedPreferences.getString("shopName","empty"));

                            String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Discount_Report_PDF?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=till";
                            Uri uri = Uri.parse(pdfUrl);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri,"text/html");
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.d("final url:",uri.toString());
                        }
                        else if ( fileFormat.equalsIgnoreCase(".xls")){
                            Toast.makeText(getActivity(),"In Till  XLS",Toast.LENGTH_SHORT);
                        }
                    }
                });
        }

        else if(("ProfitLoss").equalsIgnoreCase(mParam3)){

            tx_title.setText("Profit & Loss Report");
                fromDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String fromDateString;

                        DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                        Log.d(TAG, "onClick: datePicker"+datePickFragments);

                        //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                        // datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");
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

                                //updateDate(myCalendar);
                                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                                txt_FromDate.setText(currentDateTimeString);

                            }

                        };

                        new DatePickerDialog(getContext(),date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                toDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                        Log.d(TAG, "onClick: datePicker"+datePickFragments);

                        //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                        //datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");

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

                                //updateDate(myCalendar);
                                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                                txt_toDate.setText(currentDateTimeString);

                            }

                        };

                        new DatePickerDialog(getContext(),date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                        new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });
                onlyYear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                        new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });

                Date dt = new Date();
                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyy-MM-dd");
                String currentDateTimeString = dateFormat.format(dt);
                txt_tillDate.setText(currentDateTimeString);

                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        View rootView = inflater.inflate(R.layout.report_dialog, container, false);
                        int selectedId1 = rg1.getCheckedRadioButtonId();
                        RadioButton radioButton1 = (RadioButton)rootView.findViewById(selectedId1);
                        String  fileFormat1   =     radioButton1.getText().toString().trim();

                        int selectedId = rg2.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton)rootView.findViewById(selectedId);
                        String  fileFormat   = radioButton.getText().toString().trim();

                        //String Year = txttillDate.getText().toString().trim();

                        Log.d(TAG, "onClick:pdf xls "+fileFormat);
                        Log.d(TAG, "onClick: from to"+    fileFormat1);

                        if(("From").equalsIgnoreCase(fileFormat1) ){

                            if((".pdf").equalsIgnoreCase(fileFormat)) {


                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));


                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Discount_Report_PDF?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=Yes";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());
                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In From  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Month") ){

                            if(fileFormat.equalsIgnoreCase(".pdf")) {
                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));

                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Discount_Report_PDF?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=month";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());

                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In Month  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Year") ){

                            if(fileFormat.equalsIgnoreCase(".pdf")) {
                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));
                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Discount_Report_PDF?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=year";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());
                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In Year  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Till") ){

                            String dbname = sharedPreferences.getString("dbname","empty");
                            String fromDateString=txt_FromDate.getText().toString();
                            String toDateString = txt_toDate.getText().toString();
                            String month = txt_fromMonth.getText().toString();
                            String year = txt_fromYear.getText().toString();
                            String till = txt_tillDate.getText().toString();

                            ArrayList<String> shopInfo = new ArrayList<>();

                            shopInfo.add(sharedPreferences.getString("userId","empty"));
                            shopInfo.add(sharedPreferences.getString("username","empty"));
                            shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                            shopInfo.add(sharedPreferences.getString("useremail","empty"));
                            shopInfo.add(sharedPreferences.getString("userrole","empty"));
                            shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                            shopInfo.add(sharedPreferences.getString("s_id","empty"));
                            shopInfo.add(sharedPreferences.getString("dbname","empty"));
                            shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                            shopInfo.add(sharedPreferences.getString("address","empty"));
                            shopInfo.add(sharedPreferences.getString("state","empty"));
                            shopInfo.add(sharedPreferences.getString("shopName","empty"));

                            String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Discount_Report_PDF?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=till";
                            Uri uri = Uri.parse(pdfUrl);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri,"text/html");
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.d("final url:",uri.toString());
                        }
                        else if ( fileFormat.equalsIgnoreCase(".xls")){
                            Toast.makeText(getActivity(),"In Till  XLS",Toast.LENGTH_SHORT);
                        }
                    }
                });


        }

            else if(("Sales").equalsIgnoreCase(mParam4)){

            tx_title.setText("Sales Report");
                fromDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String fromDateString;

                        DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                        Log.d(TAG, "onClick: datePicker"+datePickFragments);

                        //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                        // datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");
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

                                //updateDate(myCalendar);
                                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                                txt_FromDate.setText(currentDateTimeString);

                            }

                        };

                        new DatePickerDialog(getContext(),date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                toDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                        Log.d(TAG, "onClick: datePicker"+datePickFragments);

                        //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                        //datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");

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

                                //updateDate(myCalendar);
                                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                                txt_toDate.setText(currentDateTimeString);

                            }

                        };

                        new DatePickerDialog(getContext(),date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                        new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });
                onlyYear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                        new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });

                Date dt = new Date();
                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyy-MM-dd");
                String currentDateTimeString = dateFormat.format(dt);
                txt_tillDate.setText(currentDateTimeString);

                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        View rootView = inflater.inflate(R.layout.report_dialog, container, false);
                        int selectedId1 = rg1.getCheckedRadioButtonId();
                        RadioButton radioButton1 = (RadioButton)rootView.findViewById(selectedId1);
                        String  fileFormat1   =     radioButton1.getText().toString().trim();

                        int selectedId = rg2.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton)rootView.findViewById(selectedId);
                        String  fileFormat   = radioButton.getText().toString().trim();

                        //String Year = txttillDate.getText().toString().trim();

                        Log.d(TAG, "onClick:pdf xls "+fileFormat);
                        Log.d(TAG, "onClick: from to"+    fileFormat1);

                        if(("From").equalsIgnoreCase(fileFormat1) ){

                            if((".pdf").equalsIgnoreCase(fileFormat)) {


                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));


                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/TotalSaleReport?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=Yes";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());
                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In From  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Month") ){

                            if(fileFormat.equalsIgnoreCase(".pdf")) {
                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));

                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/TotalSaleReport?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=month";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());

                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In Month  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Year") ){

                            if(fileFormat.equalsIgnoreCase(".pdf")) {
                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));
                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/TotalSaleReport?ShopInfo=&"+shopInfo+"fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=year";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());
                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In Year  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Till") ){

                            String dbname = sharedPreferences.getString("dbname","empty");
                            String fromDateString=txt_FromDate.getText().toString();
                            String toDateString = txt_toDate.getText().toString();
                            String month = txt_fromMonth.getText().toString();
                            String year = txt_fromYear.getText().toString();
                            String till = txt_tillDate.getText().toString();

                            ArrayList<String> shopInfo = new ArrayList<>();

                            shopInfo.add(sharedPreferences.getString("userId","empty"));
                            shopInfo.add(sharedPreferences.getString("username","empty"));
                            shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                            shopInfo.add(sharedPreferences.getString("useremail","empty"));
                            shopInfo.add(sharedPreferences.getString("userrole","empty"));
                            shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                            shopInfo.add(sharedPreferences.getString("s_id","empty"));
                            shopInfo.add(sharedPreferences.getString("dbname","empty"));
                            shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                            shopInfo.add(sharedPreferences.getString("address","empty"));
                            shopInfo.add(sharedPreferences.getString("state","empty"));
                            shopInfo.add(sharedPreferences.getString("shopName","empty"));

                            String pdfUrl = "http://mhourz-shopmgmt.surge.sh/TotalSaleReport?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=till";
                            Uri uri = Uri.parse(pdfUrl);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri,"text/html");
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.d("final url:",uri.toString());
                        }
                        else if ( fileFormat.equalsIgnoreCase(".xls")){
                            Toast.makeText(getActivity(),"In Till  XLS",Toast.LENGTH_SHORT);
                        }
                    }
                });
        }
            else if(("Purchase").equalsIgnoreCase(mParam5)){

                tx_title.setText("Purchase Report");

                fromDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String fromDateString;

                        DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                        Log.d(TAG, "onClick: datePicker"+datePickFragments);

                        //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                        // datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");
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

                                //updateDate(myCalendar);
                                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                                txt_FromDate.setText(currentDateTimeString);

                            }

                        };

                        new DatePickerDialog(getContext(),date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                toDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                        Log.d(TAG, "onClick: datePicker"+datePickFragments);

                        //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                        //datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");

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

                                //updateDate(myCalendar);
                                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                                txt_toDate.setText(currentDateTimeString);

                            }

                        };

                        new DatePickerDialog(getContext(),date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                        new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });
                onlyYear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                        new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });

                Date dt = new Date();
                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyy-MM-dd");
                String currentDateTimeString = dateFormat.format(dt);
                txt_tillDate.setText(currentDateTimeString);

                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        View rootView = inflater.inflate(R.layout.report_dialog, container, false);
                        int selectedId1 = rg1.getCheckedRadioButtonId();
                        RadioButton radioButton1 = (RadioButton)rootView.findViewById(selectedId1);
                        String  fileFormat1   =     radioButton1.getText().toString().trim();

                        int selectedId = rg2.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton)rootView.findViewById(selectedId);
                        String  fileFormat   = radioButton.getText().toString().trim();

                        //String Year = txttillDate.getText().toString().trim();

                        Log.d(TAG, "onClick:pdf xls "+fileFormat);
                        Log.d(TAG, "onClick: from to"+    fileFormat1);

                        if(("From").equalsIgnoreCase(fileFormat1) ){

                            if((".pdf").equalsIgnoreCase(fileFormat)) {


                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));


                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Purchase_Report_All_Vendors?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=Yes";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());
                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In From  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Month") ){

                            if(fileFormat.equalsIgnoreCase(".pdf")) {
                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));

                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Purchase_Report_All_Vendors?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=month";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());

                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In Month  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Year") ){

                            if(fileFormat.equalsIgnoreCase(".pdf")) {
                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));
                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Purchase_Report_All_Vendors?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=year";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());
                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In Year  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Till") ){

                            String dbname = sharedPreferences.getString("dbname","empty");
                            String fromDateString=txt_FromDate.getText().toString();
                            String toDateString = txt_toDate.getText().toString();
                            String month = txt_fromMonth.getText().toString();
                            String year = txt_fromYear.getText().toString();
                            String till = txt_tillDate.getText().toString();

                            ArrayList<String> shopInfo = new ArrayList<>();

                            shopInfo.add(sharedPreferences.getString("userId","empty"));
                            shopInfo.add(sharedPreferences.getString("username","empty"));
                            shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                            shopInfo.add(sharedPreferences.getString("useremail","empty"));
                            shopInfo.add(sharedPreferences.getString("userrole","empty"));
                            shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                            shopInfo.add(sharedPreferences.getString("s_id","empty"));
                            shopInfo.add(sharedPreferences.getString("dbname","empty"));
                            shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                            shopInfo.add(sharedPreferences.getString("address","empty"));
                            shopInfo.add(sharedPreferences.getString("state","empty"));
                            shopInfo.add(sharedPreferences.getString("shopName","empty"));

                            String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Purchase_Report_All_Vendors?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=till";
                            Uri uri = Uri.parse(pdfUrl);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri,"text/html");
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.d("final url:",uri.toString());
                        }
                        else if ( fileFormat.equalsIgnoreCase(".xls")){
                            Toast.makeText(getActivity(),"In Till  XLS",Toast.LENGTH_SHORT);
                        }
                    }
                });


            }
            else if(("Expenses").equalsIgnoreCase(mParam6)){

                tx_title.setText("Expenses Report");

                fromDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String fromDateString;

                        DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                        Log.d(TAG, "onClick: datePicker"+datePickFragments);

                        //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                        // datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");
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

                                //updateDate(myCalendar);
                                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                                txt_FromDate.setText(currentDateTimeString);

                            }

                        };

                        new DatePickerDialog(getContext(),date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                toDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                        Log.d(TAG, "onClick: datePicker"+datePickFragments);

                        //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                        //datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");

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

                                //updateDate(myCalendar);
                                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                                txt_toDate.setText(currentDateTimeString);

                            }

                        };

                        new DatePickerDialog(getContext(),date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                        new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });
                onlyYear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                        new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });

                Date dt = new Date();
                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyy-MM-dd");
                String currentDateTimeString = dateFormat.format(dt);
                txt_tillDate.setText(currentDateTimeString);

                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        View rootView = inflater.inflate(R.layout.report_dialog, container, false);
                        int selectedId1 = rg1.getCheckedRadioButtonId();
                        RadioButton radioButton1 = (RadioButton)rootView.findViewById(selectedId1);
                        String  fileFormat1   =     radioButton1.getText().toString().trim();

                        int selectedId = rg2.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton)rootView.findViewById(selectedId);
                        String  fileFormat   = radioButton.getText().toString().trim();

                        //String Year = txttillDate.getText().toString().trim();

                        Log.d(TAG, "onClick:pdf xls "+fileFormat);
                        Log.d(TAG, "onClick: from to"+    fileFormat1);

                        if(("From").equalsIgnoreCase(fileFormat1) ){

                            if((".pdf").equalsIgnoreCase(fileFormat)) {


                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));


                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Expense_by_shop?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=Yes";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());
                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In From  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Month") ){

                            if(fileFormat.equalsIgnoreCase(".pdf")) {
                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));

                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Expense_by_shop?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=month";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());

                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In Month  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Year") ){

                            if(fileFormat.equalsIgnoreCase(".pdf")) {
                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));
                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Expense_by_shop?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=year";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());
                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In Year  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Till") ){

                            String dbname = sharedPreferences.getString("dbname","empty");
                            String fromDateString=txt_FromDate.getText().toString();
                            String toDateString = txt_toDate.getText().toString();
                            String month = txt_fromMonth.getText().toString();
                            String year = txt_fromYear.getText().toString();
                            String till = txt_tillDate.getText().toString();

                            ArrayList<String> shopInfo = new ArrayList<>();

                            shopInfo.add(sharedPreferences.getString("userId","empty"));
                            shopInfo.add(sharedPreferences.getString("username","empty"));
                            shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                            shopInfo.add(sharedPreferences.getString("useremail","empty"));
                            shopInfo.add(sharedPreferences.getString("userrole","empty"));
                            shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                            shopInfo.add(sharedPreferences.getString("s_id","empty"));
                            shopInfo.add(sharedPreferences.getString("dbname","empty"));
                            shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                            shopInfo.add(sharedPreferences.getString("address","empty"));
                            shopInfo.add(sharedPreferences.getString("state","empty"));
                            shopInfo.add(sharedPreferences.getString("shopName","empty"));

                            String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Expense_by_shop?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=till";
                            Uri uri = Uri.parse(pdfUrl);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri,"text/html");
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.d("final url:",uri.toString());
                        }
                        else if ( fileFormat.equalsIgnoreCase(".xls")){
                            Toast.makeText(getActivity(),"In Till  XLS",Toast.LENGTH_SHORT);
                        }
                    }
                });


            }
            else if(("Tax").equalsIgnoreCase(mParam7)){

                tx_title.setText("Tax Report");

                fromDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String fromDateString;

                        DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                        Log.d(TAG, "onClick: datePicker"+datePickFragments);

                        //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                        // datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");
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

                                //updateDate(myCalendar);
                                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                                txt_FromDate.setText(currentDateTimeString);

                            }

                        };

                        new DatePickerDialog(getContext(),date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                toDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                        Log.d(TAG, "onClick: datePicker"+datePickFragments);

                        //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                        //datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");

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

                                //updateDate(myCalendar);
                                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                                txt_toDate.setText(currentDateTimeString);

                            }

                        };

                        new DatePickerDialog(getContext(),date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                        new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });
                onlyYear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                        new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });

                Date dt = new Date();
                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyy-MM-dd");
                String currentDateTimeString = dateFormat.format(dt);
                txt_tillDate.setText(currentDateTimeString);

                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        View rootView = inflater.inflate(R.layout.report_dialog, container, false);
                        int selectedId1 = rg1.getCheckedRadioButtonId();
                        RadioButton radioButton1 = (RadioButton)rootView.findViewById(selectedId1);
                        String  fileFormat1   =     radioButton1.getText().toString().trim();

                        int selectedId = rg2.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton)rootView.findViewById(selectedId);
                        String  fileFormat   = radioButton.getText().toString().trim();

                        //String Year = txttillDate.getText().toString().trim();

                        Log.d(TAG, "onClick:pdf xls "+fileFormat);
                        Log.d(TAG, "onClick: from to"+    fileFormat1);

                        if(("From").equalsIgnoreCase(fileFormat1) ){

                            if((".pdf").equalsIgnoreCase(fileFormat)) {


                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));


                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Tax_Report?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=Yes";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());
                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In From  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Month") ){

                            if(fileFormat.equalsIgnoreCase(".pdf")) {
                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));

                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Tax_Report?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=month";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());

                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In Month  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Year") ){

                            if(fileFormat.equalsIgnoreCase(".pdf")) {
                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));
                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Tax_Report?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=year";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());
                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In Year  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Till") ){

                            String dbname = sharedPreferences.getString("dbname","empty");
                            String fromDateString=txt_FromDate.getText().toString();
                            String toDateString = txt_toDate.getText().toString();
                            String month = txt_fromMonth.getText().toString();
                            String year = txt_fromYear.getText().toString();
                            String till = txt_tillDate.getText().toString();

                            ArrayList<String> shopInfo = new ArrayList<>();

                            shopInfo.add(sharedPreferences.getString("userId","empty"));
                            shopInfo.add(sharedPreferences.getString("username","empty"));
                            shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                            shopInfo.add(sharedPreferences.getString("useremail","empty"));
                            shopInfo.add(sharedPreferences.getString("userrole","empty"));
                            shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                            shopInfo.add(sharedPreferences.getString("s_id","empty"));
                            shopInfo.add(sharedPreferences.getString("dbname","empty"));
                            shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                            shopInfo.add(sharedPreferences.getString("address","empty"));
                            shopInfo.add(sharedPreferences.getString("state","empty"));
                            shopInfo.add(sharedPreferences.getString("shopName","empty"));

                            String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Tax_Report?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=till";
                            Uri uri = Uri.parse(pdfUrl);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri,"text/html");
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.d("final url:",uri.toString());
                        }
                        else if ( fileFormat.equalsIgnoreCase(".xls")){
                            Toast.makeText(getActivity(),"In Till  XLS",Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
            else if(("TaxRate").equalsIgnoreCase(mParam8)){

                tx_title.setText("TaxRate Report");

                fromDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String fromDateString;

                        DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                        Log.d(TAG, "onClick: datePicker"+datePickFragments);

                        //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                        // datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");
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

                                //updateDate(myCalendar);
                                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                                txt_FromDate.setText(currentDateTimeString);

                            }

                        };

                        new DatePickerDialog(getContext(),date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                toDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickFragments datePickFragments=  DatePickFragments.getInstance(ReportFragment.this);
                        Log.d(TAG, "onClick: datePicker"+datePickFragments);

                        //   DatePickFragments datePickFragments=new DatePickFragments(ReportFragment.this);
                        //datePickFragments.show((fragmentActivity.getSupportFragmentManager()),"date");

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

                                //updateDate(myCalendar);
                                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                String currentDateTimeString = dateFormat.format(myCalendar.getTime());
                                txt_toDate.setText(currentDateTimeString);

                            }

                        };

                        new DatePickerDialog(getContext(),date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                        new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });
                onlyYear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        YearMonthPickerDialog.OnDateSetListener yd =  new YearMonthPickerDialog.OnDateSetListener() {
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
                        new YearMonthPickerDialog(getActivity(),yd).show();
                    }
                });

                Date dt = new Date();
                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyy-MM-dd");
                String currentDateTimeString = dateFormat.format(dt);
                txt_tillDate.setText(currentDateTimeString);

                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        View rootView = inflater.inflate(R.layout.report_dialog, container, false);
                        int selectedId1 = rg1.getCheckedRadioButtonId();
                        RadioButton radioButton1 = (RadioButton)rootView.findViewById(selectedId1);
                        String  fileFormat1   =     radioButton1.getText().toString().trim();

                        int selectedId = rg2.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton)rootView.findViewById(selectedId);
                        String  fileFormat   = radioButton.getText().toString().trim();

                        //String Year = txttillDate.getText().toString().trim();

                        Log.d(TAG, "onClick:pdf xls "+fileFormat);
                        Log.d(TAG, "onClick: from to"+    fileFormat1);

                        if(("From").equalsIgnoreCase(fileFormat1) ){

                            if((".pdf").equalsIgnoreCase(fileFormat)) {


                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));


                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Tax_Rate_Report?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=Yes";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());
                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In From  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Month") ){

                            if(fileFormat.equalsIgnoreCase(".pdf")) {
                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));

                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Tax_Rate_Report?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=month";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());

                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In Month  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Year") ){

                            if(fileFormat.equalsIgnoreCase(".pdf")) {
                                String dbname = sharedPreferences.getString("dbname","empty");
                                String fromDateString=txt_FromDate.getText().toString();
                                String toDateString = txt_toDate.getText().toString();
                                String month = txt_fromMonth.getText().toString();
                                String year = txt_fromYear.getText().toString();
                                String till = txt_tillDate.getText().toString();

                                ArrayList<String> shopInfo = new ArrayList<>();

                                shopInfo.add(sharedPreferences.getString("userId","empty"));
                                shopInfo.add(sharedPreferences.getString("username","empty"));
                                shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                                shopInfo.add(sharedPreferences.getString("useremail","empty"));
                                shopInfo.add(sharedPreferences.getString("userrole","empty"));
                                shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                                shopInfo.add(sharedPreferences.getString("s_id","empty"));
                                shopInfo.add(sharedPreferences.getString("dbname","empty"));
                                shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                                shopInfo.add(sharedPreferences.getString("address","empty"));
                                shopInfo.add(sharedPreferences.getString("state","empty"));
                                shopInfo.add(sharedPreferences.getString("shopName","empty"));
                                String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Tax_Rate_Report?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=year";
                                Uri uri = Uri.parse(pdfUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri,"text/html");
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.d("final url:",uri.toString());
                            }
                            else if ( fileFormat.equalsIgnoreCase(".xls")){
                                Toast.makeText(getActivity(),"In Year  XLS",Toast.LENGTH_SHORT);
                            }
                        }
                        if(fileFormat1.equalsIgnoreCase("Till") ){

                            String dbname = sharedPreferences.getString("dbname","empty");
                            String fromDateString=txt_FromDate.getText().toString();
                            String toDateString = txt_toDate.getText().toString();
                            String month = txt_fromMonth.getText().toString();
                            String year = txt_fromYear.getText().toString();
                            String till = txt_tillDate.getText().toString();

                            ArrayList<String> shopInfo = new ArrayList<>();

                            shopInfo.add(sharedPreferences.getString("userId","empty"));
                            shopInfo.add(sharedPreferences.getString("username","empty"));
                            shopInfo.add(sharedPreferences.getString("usermobile","empty"));
                            shopInfo.add(sharedPreferences.getString("useremail","empty"));
                            shopInfo.add(sharedPreferences.getString("userrole","empty"));
                            shopInfo.add(sharedPreferences.getString("userpassword","empty"));
                            shopInfo.add(sharedPreferences.getString("s_id","empty"));
                            shopInfo.add(sharedPreferences.getString("dbname","empty"));
                            shopInfo.add(sharedPreferences.getString("gstNo","empty"));
                            shopInfo.add(sharedPreferences.getString("address","empty"));
                            shopInfo.add(sharedPreferences.getString("state","empty"));
                            shopInfo.add(sharedPreferences.getString("shopName","empty"));

                            String pdfUrl = "http://mhourz-shopmgmt.surge.sh/Tax_Rate_Report?ShopInfo="+shopInfo+"&fromDate=" +fromDateString + "&toDate="+toDateString+"&month="+month+"&year="+year+"&till="+till+"&dbname="+dbname+"&radioValue=till";
                            Uri uri = Uri.parse(pdfUrl);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri,"text/html");
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.d("final url:",uri.toString());
                        }
                        else if ( fileFormat.equalsIgnoreCase(".xls")){
                            Toast.makeText(getActivity(),"In Till  XLS",Toast.LENGTH_SHORT);
                        }
                    }
                });
            }


//        else{Toast.makeText(getContext(),"Please select one option to proceed",Toast.LENGTH_LONG); }

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