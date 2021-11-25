package com.hazyaz.mysapling.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hazyaz.mysapling.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.myViewHolder> {


    private ArrayList<ArrayList<String>> data = new ArrayList<>();

    private Context context;

    public CustomAdapter(ArrayList<ArrayList<String>> Maindata , Context c ) {
        this.data = Maindata;
        this.context = c;
    }


    @Override
    public CustomAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout,parent,false);

        return new myViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(CustomAdapter.myViewHolder holder, int position) {


        Log.v("BindViewHolder", "in onBindViewHolder");
//        ContactPOJO contact = arrayList.get(position);
//        Sapling pos = data.get(position);
          String img = data.get(position).get(0);
          String tim = data.get(position).get(1);
        String nam = data.get(position).get(2);
        final String mar = data.get(position).get(3);
        String ss = data.get(position).get(4);

        if (ss.equals("FUCKOFF")) {

            final String userid = data.get(position).get(5);
            final String userSaplingIUd = data.get(position).get(6);
//            Toast.makeText(context,"fuck off",Toast.LENGTH_LONG).show();


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "fuck off twice", Toast.LENGTH_LONG).show();

                    Toast.makeText(context, "Under developent", Toast.LENGTH_LONG).show();

//                    new settingMarks(mar,userid,context,userSaplingIUd);
                }
            });


        }


        Log.d("llllllllllllllll",img);
//        Log.d("iiiiiii",""+ma);

//        holder.name.setText(contact.getmName());

//        holder.tUserName.setText(contact.getmNumber());


        holder.tUserMarks.setText(mar + "/100");
        holder.tUserName.setText("Address : " + nam);
        holder.tUserWeek.setText("Week : " + position);
        holder.tUserDate.setText("Uploaded on :" + dateConvertor.timeLele(tim));
        Picasso.with(context).load(img).fit().centerCrop().into(holder.tImageView);


    }

    @Override
    public int getItemCount() {
        return  data.size();
    }


    public class myViewHolder extends RecyclerView.ViewHolder{


        ImageView tImageView;
        TextView tUserName;
        TextView tUserDate;
        TextView tUserWeek;
        TextView tUserMarks;

        public myViewHolder(View itemView) {
            super(itemView);


            tImageView = itemView.findViewById(R.id.tUserSapling);
            tUserName = itemView.findViewById(R.id.tuserNamem);
            tUserDate = itemView.findViewById(R.id.tUserUploaddate);
            tUserWeek = itemView.findViewById(R.id.tuserWeek);
            tUserMarks = itemView.findViewById(R.id.tUserMarks);

        }


    }




}
