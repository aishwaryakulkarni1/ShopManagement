package com.inevitablesol.www.shopmanagement.report;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.inevitablesol.www.shopmanagement.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Report_TaxRate extends AppCompatActivity {

    private ImageView imgTaxRateDownload;
    private String currentDateTimeString;
    private ImageView datePicker;
    private TextView currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tax_rate_report);

        datePicker= (ImageView) findViewById(R.id.total_imgfromDate);
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

                startActivity(new Intent(Report_TaxRate.this,ReportActivity_Dailog.class));
            }
        });
    }

    private void showDate(){
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

                updateDate(myCalendar);
            }
        };
        new DatePickerDialog(Report_TaxRate.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        Log.d("date", String.valueOf(myCalendar.getTime()));
    }

    private void updateDate(Calendar myCalendar){
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        currentDateTimeString= dateFormat.format(myCalendar.getTime());
        currentDate.setText(currentDateTimeString);
    }
}
