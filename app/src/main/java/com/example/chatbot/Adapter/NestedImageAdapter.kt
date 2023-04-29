package com.example.chatbot.Adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.Fragment.Third_imageFragment
import com.example.chatbot.R
import com.example.chatbot.databinding.ShopItemNestedBinding
import com.example.chatbot.placesDetails.data
import com.squareup.picasso.Picasso

class NestedImageAdapter(var photoList: List<NestedData>) :  //只需要MsgList的imgUrl
    RecyclerView.Adapter<NestedImageAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ShopItemNestedBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val View = ShopItemNestedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(View)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val photoUrl  = photoList[position].imageUrl

        Picasso.get().load(photoUrl).into(holder.binding.imgNested)

//        holder.binding.imgNested.setOnClickListener{
//            // 建立要傳遞的資料
//            val bundle = Bundle()
//            bundle.putString("data_key", "要傳遞的資料")
//
//            // 建立目標 fragment
//            val targetFragment = Third_imageFragment()
//            targetFragment.arguments = bundle
//
//            // 取得 FragmentManager 和 FragmentTransaction
//            val manager = (holder.itemView.context as Fragment).fragmentManager
//            val transaction = manager?.beginTransaction()
//
//            // 執行 fragment 跳轉
//            transaction?.replace(R.id.fragment_container_view_tag , targetFragment)?.addToBackStack(null)?.commit()
//        }


    }



    override fun getItemCount(): Int = photoList.size

}


data class NestedData(
    val imageUrl: String
)
