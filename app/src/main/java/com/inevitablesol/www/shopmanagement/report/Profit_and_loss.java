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
import com.payu.payuui.Widget.MonthYearPickerDialog;
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

public class Profit_and_loss extends AppCompatActivity
{
    private String total_taxable,TotalGST, Total_Amount ;
    private String TotalSelling,TotalSgst,totalsale;
    private String total_Exp_taxable,Exp_TotalGST,Total_Exp_Amount;


    private ImageView imgPLDownload;
    private TextView currentDate;
    private ImageView datePicker;
    private String currentDateTime;

    private TextView p_tax_value,p_tot_gst,p_tot_amt;
    private TextView s_tax_value,s_tot_gst,s_tot_amt;
    private TextView e_tax_value,e_tot_gst,e_tot_amt;
    private TextView total_tax_value,tot_gst,tot_amt,profit,loss;

    private static final String TAG = "Profit_and_loss";
    private ProgressDialog loading;
    private String urlJsonArray1= "http://35.161.99.113:9000/webapi/report/profitPurchase";
    private String urlJsonArray2= "http://35.161.99.113:9000/webapi/report/profitSale";
    private String urlJsonArray3= "http://35.161.99.113:9000/webapi/report/exp_profit";
    private GlobalPool globalPool;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_business_status);

        currentDate = (TextView) findViewById(R.id.bill_curruntDate);
        currentDateTime = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
        currentDate.setText(currentDateTime);

        datePicker = (ImageView) findViewById(R.id.date_billingDate);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        imgPLDownload = (ImageView) findViewById(R.id.sale_download_product);
        imgPLDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profit_and_loss.this,ReportActivity_Dailog.class));
            }
        });

        globalPool= (GlobalPool) this.getApplicationContext();
        p_tax_value = (TextView) findViewById(R.id.p_tax_value);
        p_tot_gst = (TextView) findViewById(R.id.p_tot_gst);
        p_tot_amt = (TextView) findViewById(R.id.p_tot_amt);

        s_tax_value = (TextView) findViewById(R.id.s_tax_value);
        s_tot_gst = (TextView) findViewById(R.id.s_tot_gst);
        s_tot_amt = (TextView) findViewById(R.id.s_tot_amt);

        e_tax_value=(TextView) findViewById(R.id.e_tax_value);
        e_tot_gst = (TextView) findViewById(R.id.e_tot_gst);
        e_tot_amt = (TextView) findViewById(R.id.e_tot_amt);

        total_tax_value = (TextView) findViewById(R.id.total_tax_value);
        tot_gst = (TextView) findViewById(R.id.tot_gst);
        tot_amt = (TextView) findViewById(R.id.tot_amt);
        profit = (TextView) findViewById(R.id.profit);
        loss = (TextView) findViewById(R.id.loss);

    }

    private void showDate(){
        new YearMonthPickerDialog(this, new YearMonthPickerDialog.OnDateSetListener() {
            @Override
            public void onYearMonthSet(int year, int month)
            {
                Log.d(TAG, "onYearMonthSet: Year Month"+year+" "+month);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH,1);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                currentDateTime= dateFormat.format(calendar.getTime());

                currentDate.setText(dateFormat.format(calendar.getTime()));
                // calendar.set(calendar.MONTH,month+1);
                Log.d(TAG, "onYearMonthSet: " + dateFormat.format(calendar.getTime()));
                calendar.add(Calendar.MONTH,1);
                calendar.add(Calendar.DATE,-1);

                Log.d(TAG, "onYearMonthSet: "+dateFormat.format(calendar.getTime()));

                //calendar.add(Calendar.DATE,-1);

                // nextMonth=dateFormat.format(calendar.getTime());

                Log.d(TAG, "onYearMonthSet:" + dateFormat.format(calendar.getTime()));

                getProfitLoss_Status();

            }
        }).show();
    }

    private void getProfitLoss_Status(){


        Log.d(TAG,"getProfitLossStatus");
        loading = ProgressDialog.show(this, "Loading....", "Please wait...", false, false);
        //Purchase
        final StringRequest req1 = new StringRequest(Request.Method.POST,urlJsonArray1,
                new Response.Listener<String>()
                {

                    @Override
                    public void onResponse(String response)
                    {
                        Log.d(TAG, response.toString());
                        loading.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            /*String message = jsonObject.getString("Data Available");
                            if(message.equalsIgnoreCase("Data Available"))
                            {*/
                                JSONArray jsonArray = jsonObject.getJSONArray("PurchaseProfit");

                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                                total_taxable = jsonObject1.getString("total_taxable");
                                TotalGST = jsonObject1.getString("TotalGST");
                                 Total_Amount = jsonObject1.getString("Total_Amount");

                                p_tax_value.setText(total_taxable);
                                p_tot_gst.setText(TotalGST);
                                p_tot_amt.setText(Total_Amount);
                           /* }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Data Unavailable",Toast.LENGTH_LONG).show();
                            }*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        catch (NumberFormatException e)
                        {
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
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("dbname",globalPool.getDbname());
                param.put("date", currentDateTime);
                Log.d(TAG,"getParams:"+param.toString());

                return param;
            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(req1);

 //Sale
        final StringRequest req2 = new StringRequest(Request.Method.POST,urlJsonArray2,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d(TAG, response.toString());
                        loading.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            /*String message = jsonObject.getString("Data Available");
                            if(message.equalsIgnoreCase("Data Available"))
                            {*/
                                JSONArray jsonArray = jsonObject.getJSONArray("salereprofit");

                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                TotalSelling = jsonObject1.getString("TotalSelling");
                                 TotalSgst = jsonObject1.getString("TotalGst");
                                totalsale=jsonObject1.getString("totalsale");

                                s_tax_value.setText(TotalSelling);
                                s_tot_gst.setText(TotalSgst);
                                s_tot_amt.setText(totalsale);

//                            }
//                            else
//                            {
//                                Toast.makeText(getApplicationContext(),"Data Unavailable",Toast.LENGTH_LONG).show();
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        catch (NumberFormatException e)
                        {
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

                if (error instanceof NoConnectionError)
                {
                    Toast.makeText(getApplication(), "Please connect to the internet before continuing", Toast.LENGTH_SHORT).show();
                }

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("dbname",globalPool.getDbname());
                param.put("date", currentDateTime);
                Log.d(TAG,"getParams:"+param.toString());

                return param;
            }
        };
        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        requestQueue2.add(req2);

//Expense
        final StringRequest req3 = new StringRequest(Request.Method.POST,urlJsonArray3,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d(TAG, response.toString());
                        loading.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            /*String message = jsonObject.getString("Data Available");
                            if(message.equalsIgnoreCase("Data Available"))
                            {*/
                                JSONArray jsonArray = jsonObject.getJSONArray("ExpenseProfit");

                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                total_Exp_taxable =jsonObject1.getString("total_Exp_taxable");
                                Exp_TotalGST = jsonObject1.getString("Exp_TotalGST");
                                Total_Exp_Amount = jsonObject1.getString("Total_Exp_Amount");

                                e_tax_value.setText(total_Exp_taxable);
                                e_tot_gst.setText(Exp_TotalGST);
                                e_tot_amt.setText(Total_Exp_Amount);
                           /* }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Data Unavailable", Toast.LENGTH_LONG).show();
                            }*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        catch (NumberFormatException e)
                        {
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

                if (error instanceof NoConnectionError)
                {
                    Toast.makeText(getApplication(), "Please connect to the internet before continuing", Toast.LENGTH_SHORT).show();
                }

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("dbname",globalPool.getDbname());
                param.put("date", currentDateTime);
                Log.d(TAG,"getParams:"+param.toString());

                return param;
            }
        };
        RequestQueue requestQueue3 = Volley.newRequestQueue(getApplicationContext());
        requestQueue3.add(req3);


        Log.d(total_taxable,"::total_taxable");
        //Calcultions

        int p_taxable = Integer.parseInt(total_taxable);
        int s_taxable = Integer.parseInt(TotalSelling);
        int e_taxable = Integer.parseInt(total_Exp_taxable);
        int totalTax = p_taxable+s_taxable+e_taxable;
        total_tax_value.setText(String.valueOf(totalTax));

        int p_gst = Integer.parseInt(TotalGST);
        int s_gst = Integer.parseInt(TotalSgst);
        int e_gst = Integer.parseInt(Exp_TotalGST);
        int totalGst = p_gst + s_gst + e_gst;
        tot_gst.setText(String.valueOf(totalGst));

        int p_amt = Integer.parseInt(Total_Amount);
        int s_amt = Integer.parseInt(totalsale);
        int e_amt = Integer.parseInt(Total_Exp_Amount);
        int totalAmt = p_amt + s_amt +e_amt;
        tot_amt.setText(String.valueOf(totalAmt));

    }
}
