package com.inevitablesol.www.shopmanagement.account;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inevitablesol.www.shopmanagement.R;
import com.inevitablesol.www.shopmanagement.vendor_module.*;

import java.util.ArrayList;

/**
 * Created by Pritam on 22-03-2018.
 */

public class UserTypeAdapter extends RecyclerView.Adapter<UserTypeAdapter.ViewSubUser>
{
    private ArrayList<Alluser> listData;

    public UserTypeAdapter(ArrayList<Alluser> listdata) {
        this.listData = listdata;
    }


    @Override
    public ViewSubUser onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_vendor, viewGroup, false);
        return new UserTypeAdapter.ViewSubUser(view);
    }

    @Override
    public void onBindViewHolder(ViewSubUser holder, int position)
    {
           Alluser alluser = listData.get(position);
        holder.tv_name.setText(String.valueOf(alluser.getUName()));
        holder.tv_mobile.setText(String.valueOf(alluser.getUNumber()));

    }

    @Override
    public int getItemCount() {
        return  listData == null ? 0 :listData.size();
    }

    public class ViewSubUser  extends RecyclerView.ViewHolder
    {
        private TextView tv_name,tv_mobile;//tv_product_type;

        public ViewSubUser(View itemView)
        {
            super(itemView);



            tv_name = (TextView) itemView.findViewById(R.id.tv_custname);
            tv_mobile = (TextView) itemView.findViewById(R.id.tv_mobile);
        }
    }

    public  void  clearView()
    {
        int size=listData.size();
          listData.clear();

//        if(size>0)
//        {
//            for(int i=0;i<listData.size();i++)
//            {
//                this.listData.remove(i);
//            }
            this.notifyItemRangeRemoved(0, size);
        //}
    }
}
