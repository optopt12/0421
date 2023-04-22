package com.example.chatbot.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.databinding.*
import com.example.chatbot.placesDetails.data
import com.squareup.picasso.Picasso

class RestaurantListAdapter(var MsgList: MutableList<data>) :
    RecyclerView.Adapter<RestaurantListAdapter.ItemViewHolder>() {
    /**
     * 設定資料
     */
    inner class ItemViewHolder(val binding: ShopItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val View = ShopItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(View)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = MsgList[position]
//        Picasso.get().load(data.imageUrl).into(holder.binding.img)
        holder.binding.Address.text = data.formatted_address
        holder.binding.Shopname.text = data.name
        holder.binding.PhoneNumber.text = data.formatted_phone_number

        val layoutManager = LinearLayoutManager(holder.binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        holder.binding.rv.layoutManager = layoutManager
        val nestedAdapter = NestedImageAdapter(MsgList)
        holder.binding.rv.adapter = nestedAdapter

    }

    override fun getItemCount(): Int = MsgList.size
}
