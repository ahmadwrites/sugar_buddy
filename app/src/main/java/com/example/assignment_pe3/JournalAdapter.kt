package com.example.assignment_pe3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JournalAdapter(private val journalList: ArrayList<JournalDTO>) : RecyclerView.Adapter<JournalAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.journal_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = journalList[position]

        holder.tvTitle.text = currentItem.title
        holder.tvContents.text = currentItem.contents
        holder.tvTags.text = currentItem.tags
        holder.tvFeelings.text = currentItem.feelings
    }

    override fun getItemCount(): Int {
        return journalList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvContents: TextView = itemView.findViewById(R.id.tv_contents)
        val tvTags: TextView = itemView.findViewById(R.id.tv_tags)
        val tvFeelings: TextView = itemView.findViewById(R.id.tv_feelings)
    }
}