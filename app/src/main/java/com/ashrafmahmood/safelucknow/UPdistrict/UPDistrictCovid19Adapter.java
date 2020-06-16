package com.ashrafmahmood.safelucknow.UPdistrict;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashrafmahmood.safelucknow.R;
import com.ashrafmahmood.safelucknow.StateCovid19;

import java.util.ArrayList;

public class UPDistrictCovid19Adapter extends RecyclerView.Adapter<UPDistrictCovid19Adapter.ViewHolder>
{
    private ArrayList<UPDistrictCovid19> data;

    public  UPDistrictCovid19Adapter (Context context, ArrayList<UPDistrictCovid19> list)
    {
        data = list;

    }

    public class  ViewHolder extends RecyclerView.ViewHolder
    {
        TextView district, dTotal, DdTotal, dActive, dRecov, DdRecov,dDeaths, DdDeaths;
        ImageView redArrow, greenArrow,greyArrow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            district = itemView.findViewById(R.id.district);
            dTotal = itemView.findViewById(R.id.dTotal);
            DdTotal = itemView.findViewById(R.id.DdTotal);
            dActive = itemView.findViewById(R.id.dActive);
            dRecov = itemView.findViewById(R.id.dRecov);
             DdRecov= itemView.findViewById(R.id.DdRecov);
            dDeaths = itemView.findViewById(R.id.dDeaths);
            DdDeaths = itemView.findViewById(R.id.DdDeaths);
            redArrow = itemView.findViewById(R.id.redArrow);
            greenArrow = itemView.findViewById(R.id.greenArrow);
            greyArrow = itemView.findViewById(R.id.greyArrow);
            redArrow.setVisibility(View.GONE);
            greenArrow.setVisibility(View.GONE);
            greyArrow.setVisibility(View.GONE);
            DdTotal.setVisibility(View.GONE);
            DdRecov.setVisibility(View.GONE);
            DdDeaths.setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
    }

    @NonNull
    @Override
    public UPDistrictCovid19Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.updistrict_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UPDistrictCovid19Adapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(data.get(i));
        viewHolder.district.setText(data.get(i).getState());
        viewHolder.dTotal.setText(data.get(i).getsTotal());
        viewHolder.DdTotal.setText(data.get(i).getSdTotal());
        viewHolder.dActive.setText(data.get(i).getsActive());
        viewHolder.dRecov.setText(data.get(i).getsRecov());
        viewHolder.DdRecov.setText(data.get(i).getSdDeaths());
        viewHolder.dDeaths.setText(data.get(i).getsDeaths());
        viewHolder.DdDeaths.setText(data.get(i).getSdDeaths());
        if(!data.get(i).getSdTotal().equals("0"))
        {
            viewHolder.redArrow.setVisibility(View.VISIBLE);
            viewHolder.DdTotal.setVisibility(View.VISIBLE);

        }
        if(!data.get(i).getSdRecov().equals("0"))
        {
            viewHolder.greenArrow.setVisibility(View.VISIBLE);
            viewHolder.DdRecov.setVisibility(View.VISIBLE);
        }
        if(!data.get(i).getSdDeaths().equals("0"))
        {
            viewHolder.greyArrow.setVisibility(View.VISIBLE);
            viewHolder.DdDeaths.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
