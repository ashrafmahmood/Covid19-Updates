package com.ashrafmahmood.safelucknow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StateCovid19Adapter extends RecyclerView.Adapter<StateCovid19Adapter.ViewHolder>
{
    private ArrayList<StateCovid19> data;

    public  StateCovid19Adapter (Context context, ArrayList<StateCovid19> list)
    {
        data = list;

    }

    public class  ViewHolder extends RecyclerView.ViewHolder
    {
        TextView state, sTotal, dsTotal, sActive, sRecov, dsRecov,sDeaths, dsDeaths;
        ImageView redArrow, greenArrow,greyArrow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            state = itemView.findViewById(R.id.state);
            sTotal = itemView.findViewById(R.id.sTotal);
            dsTotal = itemView.findViewById(R.id.dsTotal);
            sActive = itemView.findViewById(R.id.sActive);
            sRecov = itemView.findViewById(R.id.sRecov);
            dsRecov = itemView.findViewById(R.id.dsRecov);
            sDeaths = itemView.findViewById(R.id.sDeaths);
            dsDeaths = itemView.findViewById(R.id.dsDeaths);
            redArrow = itemView.findViewById(R.id.redArrow);
            greenArrow = itemView.findViewById(R.id.greenArrow);
            greyArrow = itemView.findViewById(R.id.greyArrow);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
    }

    @NonNull
    @Override
    public StateCovid19Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.state_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StateCovid19Adapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(data.get(i));
        viewHolder.state.setText(data.get(i).getState());
        viewHolder.sTotal.setText(data.get(i).getsTotal());
        viewHolder.dsTotal.setText(data.get(i).getSdTotal());
        viewHolder.sActive.setText(data.get(i).getsActive());
        viewHolder.sRecov.setText(data.get(i).getsRecov());
        viewHolder.dsRecov.setText(data.get(i).getSdRecov());
        viewHolder.sDeaths.setText(data.get(i).getsDeaths());
        viewHolder.dsDeaths.setText(data.get(i).getSdDeaths());
        if(data.get(i).getSdTotal().equals("0"))
        {
            viewHolder.redArrow.setVisibility(View.GONE);
            viewHolder.dsTotal.setVisibility(View.GONE);

        }
        if(data.get(i).getSdRecov().equals("0"))
        {
            viewHolder.greenArrow.setVisibility(View.GONE);
            viewHolder.dsRecov.setVisibility(View.GONE);
        }
        if(data.get(i).getSdDeaths().equals("0"))
        {
            viewHolder.greyArrow.setVisibility(View.GONE);
            viewHolder.dsDeaths.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
