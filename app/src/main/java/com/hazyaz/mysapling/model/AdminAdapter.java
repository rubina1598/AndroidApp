package com.hazyaz.mysapling.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hazyaz.mysapling.AdminViewUser;
import com.hazyaz.mysapling.R;
import com.hazyaz.mysapling.RequestSapling;

import java.util.ArrayList;


/**
 * Created by Raff on 1/10/2020.
 */

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.myViewHolder> {

    private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
    private Context context;
    private Context mc;
    private String m_Text = "";


    AdminAdapter() {
    }


    public AdminAdapter(ArrayList<ArrayList<String>> sa, Context c) {
        this.data = sa;
        this.context = c;

    }


    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list, parent, false);

        return new AdminAdapter.myViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {


        String nam = data.get(position).get(0);
        final String userid = data.get(position).get(1);
        String status = data.get(position).get(2);



        holder.tUserName.setText(nam);
        holder.tUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "abbho ahsd asd", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mc, AdminViewUser.class);
                intent.putExtra("userKey", userid);
                mc.startActivity(intent);

            }
        });
        if (status.equals("not_requested")) {

            holder.tUserStatus.setText(status);


        } else if (status.equals("requested")) {

            holder.tUserStatus.setText(status);
            holder.tUserStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, RequestSapling.class);
                    sta


                }
            });

        } else if (status.equals("given")) {

            holder.tUserStatus.setText(status);


        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {


        TextView tUserName;
        TextView tUserStatus;

        public myViewHolder(View itemView) {
            super(itemView);
            mc = itemView.getContext();

            tUserName = itemView.findViewById(R.id.singleUserName);
            tUserStatus = itemView.findViewById(R.id.singleUserStatus);


        }

    }
}