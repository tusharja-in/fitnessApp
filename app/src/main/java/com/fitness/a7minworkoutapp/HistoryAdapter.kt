package com.fitness.a7minworkoutapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(val context:Context,val items:ArrayList<String>):RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val ll_history_item=view.findViewById<LinearLayout>(R.id.ll_history_item)
        val tvIdDatabase=view.findViewById<TextView>(R.id.tvIdDatabase)
        val tvDateDatabase=view.findViewById<TextView>(R.id.tvDateDatabase)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.history_item_completed_exercise,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date=items.get(position)
        holder.tvIdDatabase.text=(position+1).toString()
        holder.tvDateDatabase.text=date

        if(position%2==0){
            holder.ll_history_item.setBackgroundColor(Color.parseColor("#EBEBEB"))
        }else{
            holder.ll_history_item.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}