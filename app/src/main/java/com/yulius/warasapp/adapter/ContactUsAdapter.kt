package com.yulius.warasapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulius.warasapp.data.model.Contact
import com.yulius.warasapp.databinding.ListContactBinding
import java.util.ArrayList

class ListContactAdapter(private val listContact: ArrayList<Contact>) : RecyclerView.Adapter<ListContactAdapter.ListViewHolder>() {
    
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ListContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, email, photo) = listContact[position]
        holder.binding.imgItemPhoto.setImageResource(photo)
        holder.binding.tvItemName.text = name
        holder.binding.tvItemEmail.text = email
        
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listContact[position]) }

    }

    override fun getItemCount(): Int = listContact.size
    
    interface OnItemClickCallback {
        fun onItemClicked(data: Contact)
    }

    class ListViewHolder(var binding: ListContactBinding) : RecyclerView.ViewHolder(binding.root) {
    }

}
