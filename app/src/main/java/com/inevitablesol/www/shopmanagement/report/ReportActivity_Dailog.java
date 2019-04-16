package com.inevitablesol.www.shopmanagement.report;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.inevitablesol.www.shopmanagement.R;

public class ReportActivity_Dailog extends AppCompatActivity implements ReportFragment.OnFragmentInteractionListener {

    private static final String TAG = "ReportActivity_Dailog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testfragment);
        ReportFragment reportFragment = new ReportFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle b = getIntent().getExtras();

        if (("DayBook").equalsIgnoreCase(b.getString("DayBook"))) {
            String value1 = b.getString("DayBook");
            Log.d("value1", value1);
            b.putString(value1, "DayBook");
        } else if (("Discount").equalsIgnoreCase(b.getString("Discount"))) {
            String value2 = b.getString("Discount");
            b.putString(value2, "Discount");
        } else if (("ProfitLoss").equalsIgnoreCase(b.getString("ProfitLoss"))) {
            String value3 = b.getString("ProfitLoss");
            b.putString(value3, "ProfitLoss");
        } else if (("Sales").equalsIgnoreCase(b.getString("Sales"))) {
            String value4 = b.getString("Sales");
            b.putString(value4, "Sales");
        } else if (("Purchase").equalsIgnoreCase(b.getString("Purchase"))) {
            String value5 = b.getString("Purchase");
            b.putString(value5, "Purchase");
        } else if (("Expenses").equalsIgnoreCase(b.getString("Expenses"))) {
            String value6 = b.getString("Expenses");
            b.putString(value6, "Expenses");
        } else if (("Tax").equalsIgnoreCase(b.getString("Tax"))) {
            String value7 = b.getString("Tax");
            b.putString(value7, "Tax");
        } else if (("TaxRate").equalsIgnoreCase(b.getString("TaxRate"))){
            String value8 = b.getString("TaxRate");
            b.putString(value8, "TaxRate");
        }
        reportFragment.setArguments(b);
        fragmentTransaction.add(R.id.fragment,reportFragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {
        Log.d(TAG, "onFragmentInteraction: ");

    }
}