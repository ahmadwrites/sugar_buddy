package com.example.assignment_pe3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CheckInAdapter(private val checkinList: ArrayList<CheckInDTO>) :
    RecyclerView.Adapter<CheckInAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.checkin_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = checkinList[position]

        holder.tvCarbs.text = currentItem.carbohydratesConsumed
        holder.tvGlucose.text = currentItem.glucoseLevel
        holder.tvInsulin.text = currentItem.insulinInjected
        holder.tvInjections.text = currentItem.noInsulin
        holder.tvMeal.text = currentItem.mealDescription
        holder.tvInjuries.text = currentItem.injuries
    }

    override fun getItemCount(): Int {
        return checkinList.size;
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCarbs: TextView = itemView.findViewById(R.id.tv_carbs)
        val tvGlucose: TextView = itemView.findViewById(R.id.tv_glucose)
        val tvInsulin: TextView = itemView.findViewById(R.id.tv_insulin)
        val tvInjections: TextView = itemView.findViewById(R.id.tv_injections)
        val tvMeal: TextView = itemView.findViewById(R.id.tv_meal)
        val tvInjuries: TextView = itemView.findViewById(R.id.tv_injuries)
    }
}