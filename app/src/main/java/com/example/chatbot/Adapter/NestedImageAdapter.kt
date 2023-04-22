package com.example.chatbot.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.databinding.ShopItemNestedBinding
import com.example.chatbot.placesDetails.data
import com.squareup.picasso.Picasso

class NestedImageAdapter(var MsgList: MutableList<data>) :  //只需要MsgList的imgUrl
    RecyclerView.Adapter<NestedImageAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ShopItemNestedBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val View = ShopItemNestedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(View)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = MsgList[position]
        Picasso.get().load(data.imageUrl).into(holder.binding.imgNested)
    }

    override fun getItemCount(): Int = MsgList.size

}


data class NestedData(
    val imageUrl: String
)
