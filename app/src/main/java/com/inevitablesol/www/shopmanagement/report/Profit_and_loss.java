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

public class Profit_and_loss extends AppCompatActivity
{
    private ImageView imgPLDownload;
    private TextView currentDate;
    private ImageView datePicker;
    private String currentDateTime;

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
    }

    private void showDate(){
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                Date date1 = new Date();
                updateCalendar(myCalendar);
            }
        };

        new DatePickerDialog(Profit_and_loss.this,date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        Log.d("date", String.valueOf(myCalendar.getTime()));
    }

    private void updateCalendar(Calendar myCalendar){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
        currentDateTime = dateFormat.format(myCalendar.getTime());
        currentDate.setText(currentDateTime);
    }
}
