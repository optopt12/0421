package com.example.chatbot.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.Fragment.ThirdFragment
import com.example.chatbot.Fragment.Third_imageFragment_notuse
import com.example.chatbot.R
import com.example.chatbot.databinding.ShopItemNestedBinding
import com.example.chatbot.placesDetails.data
import com.squareup.picasso.Picasso

class NestedImageAdapter(var photoList: List<String>) :  //只需要MsgList的imgUrl
    RecyclerView.Adapter<NestedImageAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ShopItemNestedBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val View = ShopItemNestedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(View)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val photoUrl  = photoList[position]

        Picasso.get().load(photoUrl).into(holder.binding.imgNested)



    }



    override fun getItemCount(): Int = photoList.size

}


data class NestedData(
    val imageUrl: String
)
