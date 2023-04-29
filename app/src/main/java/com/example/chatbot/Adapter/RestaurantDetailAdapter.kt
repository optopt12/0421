package com.example.chatbot.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.Activity.MainActivity
import com.example.chatbot.Fragment.RestaurantDetailFragment
import com.example.chatbot.Fragment.ThirdFragment
import com.example.chatbot.R
import com.example.chatbot.databinding.ShopDetailBinding
import com.example.chatbot.databinding.ShopItemBinding
import com.example.chatbot.placesDetails.data
import com.squareup.picasso.Picasso

class RestaurantDetailAdapter(var MsgList: MutableList<data>) :
    RecyclerView.Adapter<RestaurantDetailAdapter.ItemViewHolder>() {
    /**
     * 設定資料
     */
    inner class ItemViewHolder(val binding: ShopDetailBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val View = ShopDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(View)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = MsgList[position]
    }
    override fun getItemCount(): Int = MsgList.size
}