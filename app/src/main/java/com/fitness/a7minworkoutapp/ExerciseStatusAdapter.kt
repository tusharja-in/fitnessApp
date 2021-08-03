package com.fitness.a7minworkoutapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseStatusAdapter(val items:ArrayList<ExerciseModel>,val context : Context):RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>(){
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tvItem=view.findViewById<TextView>(R.id.tvItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.exercise_status_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model:ExerciseModel=items[position]
        holder.tvItem.text=model.getId().toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}