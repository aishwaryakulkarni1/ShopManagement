package com.inevitablesol.www.shopmanagement.report;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inevitablesol.www.shopmanagement.R;
import com.inevitablesol.www.shopmanagement.printerClasses.GlobalPool;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Report_TaxRate extends AppCompatActivity {

    private ImageView imgTaxRateDownload;
    private String currentDateTimeString;
    private ImageView datePicker;
    private TextView currentDate;
    private static final String TAG = "Report_TaxRate";
    private ProgressDialog loading;

    private String urlJsonArray7 = "http://35.161.99.113:9000/webapi/report/taxRategView28";
    private TextView txt_taxvalue1, txt_cgst1, txt_sgst1, txt_igst1;
    private TextView txt_taxvalue2, txt_cgst2, txt_sgst2, txt_igst2;
    private GlobalPool globalPool;
    private String TotalTaxableValue, TotalCGst, TotalSgst, TotalIGST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tax_rate_report);

        globalPool = (GlobalPool) this.getApplicationContext();
        datePicker = (ImageView) findViewById(R.id.total_imgfromDate);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        currentDate = (TextView) findViewById(R.id.bill_curruntDate);
        currentDateTimeString = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        currentDate.setText(currentDateTimeString);

        imgTaxRateDownload = (ImageView) findViewById(R.id.sale_download_product);
        imgTaxRateDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Report_TaxRate.this, ReportActivity_Dailog.class));
            }
        });

        txt_taxvalue1 = (TextView) findViewById(R.id.tax_value_28);
        txt_cgst1 = (TextView) findViewById(R.id.cgst_28);
        txt_sgst1 = (TextView) findViewById(R.id.sgst_28);
        txt_igst1 = (TextView) findViewById(R.id.igst_28);
    }

    private void showDate() {
        new YearMonthPickerDialog(this, new YearMonthPickerDialog.OnDateSetListener() {
            @Override
            public void onYearMonthSet(int year, int month) {
                Log.d(TAG, "onYearMonthSet: Year Month" + year + " " + month);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, 1);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                currentDateTimeString = dateFormat.format(calendar.getTime());

                currentDate.setText(dateFormat.format(calendar.getTime()));
                // calendar.set(calendar.MONTH,month+1);
                Log.d(TAG, "onYearMonthSet: " + dateFormat.format(calendar.getTime()));
                calendar.add(Calendar.MONTH, 1);
                calendar.add(Calendar.DATE, -1);

                Log.d(TAG, "onYearMonthSet: " + dateFormat.format(calendar.getTime()));

                //calendar.add(Calendar.DATE,-1);

                // nextMonth=dateFormat.format(calendar.getTime());

                Log.d(TAG, "onYearMonthSet:" + dateFormat.format(calendar.getTime()));

                getTaxRate();
            }
        }).show();
    }

    private void getTaxRate() {
        Log.d(TAG, "getTaxRate");
        loading = ProgressDialog.show(this, "Loading....", "Please wait...", false, false);

        final StringRequest req1 = new StringRequest(Request.Method.POST, urlJsonArray7,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        loading.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            /*String message = jsonObject.getString("Data Available");
                            if(message.equalsIgnoreCase("Data Available"))
                            {*/
                            JSONArray jsonArray = jsonObject.getJSONArray("taxRate28");

                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                            TotalTaxableValue = jsonObject1.getString("TotalTaxableValue");
                            TotalCGst = jsonObject1.getString("TotalCGst");
                            TotalSgst = jsonObject1.getString("TotalSgst");
                            TotalIGST = jsonObject1.getString("TotalIGST");

                            txt_taxvalue1.setText(TotalTaxableValue);
                            txt_cgst1.setText(TotalCGst);
                            txt_sgst1.setText(TotalSgst);
                            txt_igst1.setText(TotalIGST);
                           /* }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Data Unavailable",Toast.LENGTH_LONG).show();
                            }*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (NumberFormatException e) {
                            loading.dismiss();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

                if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplication(), "Please connect to the internet before continuing", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("dbname", globalPool.getDbname());
                param.put("startDate", currentDateTimeString);
                Log.d(TAG, "getParams:" + param.toString());

                return param;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(req1);

        final StringRequest req2 = new StringRequest(Request.Method.POST, urlJsonArray7,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        loading.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            /*String message = jsonObject.getString("Data Available");
                            if(message.equalsIgnoreCase("Data Available"))
                            {*/
                            JSONArray jsonArray = jsonObject.getJSONArray("taxRate28");

                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);


                           /* }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Data Unavailable",Toast.LENGTH_LONG).show();
                            }*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (NumberFormatException e) {
                            loading.dismiss();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

                if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplication(), "Please connect to the internet before continuing", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("dbname", globalPool.getDbname());
                param.put("startDate", currentDateTimeString);
                Log.d(TAG, "getParams:" + param.toString());

                return param;
            }
        };

        RequestQueue rq1 = Volley.newRequestQueue(getApplicationContext());
        rq1.add(req2);
    }
}
