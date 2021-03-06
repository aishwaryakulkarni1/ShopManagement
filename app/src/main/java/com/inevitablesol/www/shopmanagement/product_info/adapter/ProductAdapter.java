package com.inevitablesol.www.shopmanagement.product_info.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.inevitablesol.www.shopmanagement.R;


/**
 * Created by Pritam on 05-05-2017.
 */

public class ProductAdapter extends ArrayAdapter<String>
{
    private static final String TAG = "ProductAdapter";

    private final Activity context;
    private final String[] pid;
    private final String [] p_name;
    public ProductAdapter(Activity context, int resource, String[] name, String[] id)
    {
        super(context, R.layout.product_list, R.id.txt_productId, id);
        this.context = context;
        this.pid = id;
        this.p_name = name;



    }

    public ProductAdapter(Context context, int product_list, String[] vName, String[] vId)
    {
        super(context, R.layout.product_list, R.id.txt_productId, vId);
        this.context = (Activity) context;
        this.pid = vId;
        this.p_name = vName;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
      //  LayoutInflater inflater = context.getLayoutInflater();


        return getCustomView(position, view, parent);
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) 
    {
        Log.d(TAG, "getDropDownView: ");
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent)
    {
        Log.d(TAG, "getCustomView:");
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.product_list, null, true);
        TextView txtname = (TextView) rowView.findViewById(R.id.txt_productName);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt_productId);


        txtname.setText(p_name[position]);

        return rowView;
    }
}
