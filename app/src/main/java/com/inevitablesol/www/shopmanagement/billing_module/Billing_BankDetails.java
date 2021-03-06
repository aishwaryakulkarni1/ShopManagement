package com.inevitablesol.www.shopmanagement.billing_module;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inevitablesol.www.shopmanagement.R;
import com.inevitablesol.www.shopmanagement.WebApi.WebApi;
import com.inevitablesol.www.shopmanagement.printerClasses.GlobalPool;
import com.inevitablesol.www.shopmanagement.settings.Billing_Settings;
import com.inevitablesol.www.shopmanagement.sql_lite.SqlDataBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Billing_BankDetails extends AppCompatActivity implements View.OnClickListener {


    private TextView et_bankName;
    private TextView et_holderName;
    private TextView et_ifc, et_accountNumber, et_address;
    RadioGroup radioGroup;


    private String dbname;
    private SqlDataBase sqlDataBase;
    private Context context = Billing_BankDetails.this;
    private AppCompatButton appCompatButton;
    private static final String TAG = "Billing_BankDetails";
    private String data;

    private String taxableValue, total_gst, shipping_charges, totalAmount, amount_paid, balanceDue, ModeofPayment, other_charges;
    private String custMobile;
    private String invId;
    private final static int BANKREQUESTCODE = 11;
    private String email;
    private GlobalPool globalPool;

    private TextInputEditText tran_numbner;
    private String trans_num;
    private String payment_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing__bank_details);
        globalPool = (GlobalPool) this.getApplication();
        et_bankName = (TextView) findViewById(R.id._bankName);
        et_holderName = (TextView) findViewById(R.id.b_holderName);
        et_ifc = (TextView) findViewById(R.id.b_ifcNumber);
        radioGroup = (RadioGroup) findViewById(R.id.bank_bill_status);
        et_accountNumber = (TextView) findViewById(R.id.b_AccountNumber);
        et_address = (TextView) findViewById(R.id.b_Address);
        appCompatButton = (AppCompatButton) findViewById(R.id.bt_bankDetail_proceed);
        appCompatButton.setOnClickListener(this);

        tran_numbner=(TextInputEditText)findViewById(R.id.transaction_number);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ((ImageView)findViewById(R.id.emailSharing)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sendMail();

            }
        });

        ((ImageView)findViewById(R.id.whatsappSharing)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sendWhatsUpMessage();

            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("data"))
        {

            taxableValue = intent.getStringExtra("taxableValue");
            total_gst = intent.getStringExtra("totalgst");
            other_charges = intent.getStringExtra("otherCharges");
            shipping_charges = intent.getStringExtra("shippingCharges");
            amount_paid = intent.getStringExtra("amountpaid");
            balanceDue = intent.getStringExtra("balanceDue");
            ModeofPayment = intent.getStringExtra("totalAmount");
            totalAmount = intent.getStringExtra("totalAmount");
            ModeofPayment = intent.getStringExtra("modeofPayment");
            custMobile = intent.getStringExtra("custMobile");
            data = intent.getStringExtra("data");
            dbname = intent.getStringExtra("dbname");
            email=intent.getStringExtra("email");
            Log.d(TAG, "onCreate() called with: savedInstanceState = [" + data + "]");
        }

        sqlDataBase = new SqlDataBase(context);



    }

    private void sendMail()
    {

        String message = "Dear Customer \n Please find Bank Account Deatils for Mahesh \n   \n  Account Name :" + et_holderName.getText().toString().trim() + " \n Bank Name :" + et_bankName.getText().toString().trim()
                + "\n   Account Number :"+et_accountNumber.getText().toString().trim() +
                "\n IFSC No :"+et_ifc.getText().toString().trim() + "\n  \n \n Thank you for Doing Business with Us \n "+ " \n Shop Name \n  "+globalPool.getShopName() ;
        Log.d(TAG, "sendMail: Emai"+email);

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, " Bank Account Details");
        i.putExtra(Intent.EXTRA_TEXT   , message);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex)
        {
            Toast.makeText(Billing_BankDetails.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private  void sendWhatsUpMessage()
    {
        String message = "Dear Customer \n Please find Bank Account Deatils for Mahesh \n   \n  Account Name :" + et_holderName.getText().toString().trim() + " \n Bank Name :" + et_bankName.getText().toString().trim()
                + "\n   Account Number :"+et_accountNumber.getText().toString().trim() +
                "\n IFSC No :"+et_ifc.getText().toString().trim() + "\n  \n \n Thank you for Doing Business with Us \n "+ " \n Shop Name \n "+globalPool.getShopName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        if (sendIntent.resolveActivity(getApplication().getPackageManager())!=null)
        {
            startActivity(sendIntent);
        }else
        {

            Toast.makeText(this, "Whatsapp Not Installed ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Do not forget to add this to open whatsApp App specifically


    }

    private void getBankDetail() {

        final ProgressDialog loading = ProgressDialog.show(this, "Loading....", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebApi.VIEWACCOUNT_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {


                String resp = response.toString().trim();

                Log.d("Bank Deatls", resp);
                JSONObject jsonObject = null;
                try {
                    loading.dismiss();
                    JSONObject json = new JSONObject(resp);
                    JSONArray jsonArray = json.getJSONArray("records");
                    jsonObject = jsonArray.getJSONObject(0);
                    String bankname = jsonObject.getString("bankname");
                    String accountnumber = jsonObject.getString("accno");
                    String accountname = jsonObject.getString("accountname");
                    String address = jsonObject.getString("address");
                    String ifsc = jsonObject.getString("ifsc");


                    et_accountNumber.setText(accountnumber);
                    et_ifc.setText(ifsc);
                    et_holderName.setText(accountname);
                    et_bankName.setText(bankname);
                    et_address.setText(address);


                } catch (JSONException e)
                {
                    Log.d("Exception", "" + e);
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();

                if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplication(), "Please connect to the internet before continuing", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dbname", dbname);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_bankDetail_proceed:
                appCompatButton.setClickable(false);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                final String status = radioButton.getText().toString().trim();
                trans_num =tran_numbner.getText().toString().trim();
                Log.d(TAG, "onClick: "+status);

                if (status.equalsIgnoreCase("Pending"))
                {
                    try
                    {

                        JSONObject jsonObject = new JSONObject(data);
                        String total = jsonObject.getString("total");
                        jsonObject.put("balance_due", total);
                         jsonObject.put("amount_paid","0.0");
                        jsonObject.put("transactionId",trans_num);
                        Log.d(TAG, "onClick: total" + total);
                        Log.d(TAG, "onClick: JsonObject" + jsonObject);
                        amount_paid= (String) jsonObject.get("amount_paid");
                        balanceDue=(String) jsonObject.get("balance_due");
                        data = String.valueOf(jsonObject);
                        final JSONObject finalJsonObject = jsonObject;
                        _saveBankDetailsforInvocieID(finalJsonObject);
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }else
                {
                    try {
                        JSONObject jsonObject=null;

                        Log.d(TAG, "storeBillingData: Data"+data);

                        jsonObject=new JSONObject(data);

                        jsonObject.put("transactionId",trans_num);
                        Log.d(TAG, "storeBillingData: New object"+data);
                        final JSONObject finalJsonObject = jsonObject;
                        _saveBankDetailsforInvocieID(finalJsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

//
                break;

        }

    }

    private void getBankDetail(final String invID)
    {
        final String bankName = et_bankName.getText().toString().trim();
        final String holderName = et_holderName.getText().toString().trim();
        final String ifc_code = et_ifc.getText().toString().trim();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        final String status = radioButton.getText().toString().trim();
        final String account_number = et_accountNumber.getText().toString().trim();


        final ProgressDialog loading = ProgressDialog.show(this, "Loading....", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebApi.AddEMIDETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                JSONObject msg = null;
                try
                {
                    msg = new JSONObject(response);
                    String message = msg.getString("message");
                    if (message.equalsIgnoreCase("Add data succesfully"))
                    {
                        loading.dismiss();
                        sqlDataBase.deleteItemTable();

                             sendMessage();

                        Intent intent = new Intent(context, Billing_bankDetailsStatus.class);
                        intent.putExtra("bankName", bankName);
                        intent.putExtra("bankHolderName", holderName);
                        intent.putExtra("ifc_code", ifc_code);
                        intent.putExtra("account_number", account_number);
                        intent.putExtra("status", status);
                        intent.putExtra("taxableValue", taxableValue);
                        intent.putExtra("totalgst", total_gst);
                        intent.putExtra("otherCharges", other_charges);
                        intent.putExtra("shippingCharges", shipping_charges);
                        intent.putExtra("amountpaid", amount_paid);
                        intent.putExtra("balanceDue", balanceDue);
                        intent.putExtra("totalAmount", totalAmount);
                        intent.putExtra("modeofPayment", ModeofPayment);
                        intent.putExtra("transactionId", trans_num);
                        intent.putExtra("dbname", dbname);
                             startActivity(intent);


                    } else {
                        try {
                            loading.dismiss();

                            Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
                            loading.dismiss();


                        } catch (NullPointerException e) {

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                if (error instanceof NoConnectionError) {
                    Toast.makeText(Billing_BankDetails.this, "Please connect to the internet before continuing", Toast.LENGTH_SHORT).show();

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.d("dbname", dbname);
                params.put("dbname", dbname);
                params.put("book_name", bankName);
                params.put("acc_holder_name", holderName);
                params.put("ifsc_no", ifc_code);
                params.put("remark", status);
                params.put("acc_no", account_number);
                params.put("invoice_id", invID);
                params.put("paymentId",payment_id);
                Log.d(TAG, "getParams:BankDetails" + params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed: ");
        finish();
    }

    private void _saveBankDetailsforInvocieID(final JSONObject finalJsonObject) {


        try {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, WebApi.CREATEBILL, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    String resp = response.toString().trim();
                    try {
                        JSONObject jsonObject = new JSONObject(resp);
                        Log.d("RESP", resp);
                        String msg = jsonObject.getString("message");
                        invId = jsonObject.getString("data");
                        payment_id=jsonObject.getString("paymentId");
                        Log.d(TAG, "onResponse:" + data);

                        Log.d("MSG", msg);
                        if (msg.equalsIgnoreCase("Add data succesfully"))
                        {

                            getBankDetail(invId);

                        } else
                            {

                            Toast.makeText(getApplicationContext(), "Cant Add Data", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("Add Succussuflly", resp);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NoConnectionError) {
                        Toast.makeText(Billing_BankDetails.this, "Please connect to the internet before continuing", Toast.LENGTH_SHORT).show();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("data", String.valueOf(finalJsonObject));
                    params.put("dbname", dbname);
                    params.put("transactionId",trans_num);
                    Log.d(TAG, "getParams:" + params.toString());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void sendMessage()
    {

        String message1 = new String();

        String message = "Dear Customer \n Thanks for Your Business \n Your Total Amount  Rs:" + totalAmount + " \n Invoice No :" + invId + "\n  please Visit us again ! \n shop name :";
        String mobile = custMobile;


        String uri = Uri.parse("\n" +
                "http://bhashsms.com/api/sendmsg.php?")
                .buildUpon()
                .appendQueryParameter("user", "TEAM_MHOURZ")
                .appendQueryParameter("pass", "MECHATRON")
                .appendQueryParameter("text", message)
                .appendQueryParameter("sender", "MHOURZ")
                .appendQueryParameter("phone", mobile)
                .appendQueryParameter("priority", "ndnd")
                .appendQueryParameter("stype", "normal")
                .build().toString();

        Log.d(TAG, "TestMEssage: Uri"+uri);


        final Context context = getApplicationContext();
        StringRequest stringRequest = new StringRequest(uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response != null) {

                                if (response.contains("S."))
                                {
                                    Toast.makeText(getApplication(), " message sent successfully", Toast.LENGTH_LONG).show();
                                } else
                                    {
                                    Toast.makeText(getApplication(), "Message couldn't reach you, try again", Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (Exception e) {

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getBankDetail();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bank_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_bankdetails:
                Intent intent = new Intent(Billing_BankDetails.this, Billing_Settings.class);
                startActivityForResult(intent, BANKREQUESTCODE);
                Toast.makeText(context, "Please Add Bank Details", Toast.LENGTH_SHORT).show();
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BANKREQUESTCODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK)
            {
                Log.d(TAG, "onActivityResult: ");
            }
        }
    }


}
