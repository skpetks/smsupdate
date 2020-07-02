package com.example.contactapp.adater;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactapp.Modal.ContactModal;
import com.example.contactapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class contactadapter extends RecyclerView.Adapter<contactadapter.SettingViewHolder> {


    Activity context;
    List<ContactModal> List;
    public contactadapter(Activity context,
                          List<ContactModal> Lst) {
        this.context = context;
        this.List=Lst;

    }


    @Override
    public SettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView ;


        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        return new SettingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SettingViewHolder holder, final int position) {
        try{


            holder.lblMsg.setText(List.get(position).Message);

            holder.lblNumber.setText(List.get(position).body);


            holder.lbldate.setText(List.get(position).getDate());
            if(List.get(position).body.contains("credit"))
            {

                holder.img.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.greenarrow));

            }
            else
            {
                holder.img.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.blueup));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int Countsize=0;
        if(List!=null)
        {
            Countsize=List.size();
        }


        return Countsize;
    }

    public static class SettingViewHolder extends RecyclerView.ViewHolder {
        TextView lblMsg, lblNumber,lbldate;
        ImageView img;

        SettingViewHolder(View view) {
            super(view);
            lblMsg = (TextView) view.findViewById(R.id.lblMsg);
            lblNumber = (TextView) view.findViewById(R.id.lblNumber);
            lbldate = (TextView) view.findViewById(R.id.lbldate);
            img= (ImageView) view.findViewById(R.id.arrowicon);
        }
    }
}
