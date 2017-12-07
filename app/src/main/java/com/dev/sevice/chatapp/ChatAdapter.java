package com.dev.sevice.chatapp;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 11/29/2017 AD.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    private ArrayList<String> arrayListData = new ArrayList<String>();
    private ArrayList<String> arrayListPesan = new ArrayList<String>();
    public ChatAdapter(ArrayList<String> _arrayListData,ArrayList<String> _arrayListPesan){
        this.arrayListData = _arrayListData;
        this.arrayListPesan = _arrayListPesan;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(arrayListPesan.get(position) == "me"){
            holder.getTxt_message().setText(arrayListData.get(position));
            holder.getTxt_head().setText(arrayListPesan.get(position));
            holder.getTxt_message().setBackgroundResource(R.color.colorAccent);
            holder.getLinear().setGravity(Gravity.RIGHT);
        }else {
            holder.getTxt_message().setText(arrayListData.get(position));
            holder.getTxt_head().setText("User");
            holder.getTxt_message().setBackgroundResource(R.color.colorSky);
            holder.getLinear().setGravity(Gravity.LEFT);
        }

    }

    @Override
    public int getItemCount() {
        return arrayListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_head,txt_message;
        LinearLayout linear;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_head = (TextView)itemView.findViewById(R.id.txt_head);
            txt_message = (TextView)itemView.findViewById(R.id.txt_message);
            linear = (LinearLayout)itemView.findViewById(R.id.linear);
        }

        public TextView getTxt_head() {
            return txt_head;
        }

        public TextView getTxt_message() {
            return txt_message;
        }

        public LinearLayout getLinear() {
            return linear;
        }
    }
}
