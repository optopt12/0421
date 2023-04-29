package com.example.chatbot.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator
import com.example.chatbot.Fragment.ThirdFragment
import com.example.chatbot.Fragment.Third_imageFragment
import com.example.chatbot.R
import com.example.chatbot.databinding.*
import com.example.chatbot.placesDetails.data
import com.squareup.picasso.Picasso

private lateinit var animator: ViewsTransitionAnimator<Int>
private lateinit var list: RecyclerView
private lateinit var pager: ViewPager
private lateinit var background: View

private lateinit var pagerAdapter: PagerAdapter

class RestaurantListAdapter(var MsgList: MutableList<data>) :
    RecyclerView.Adapter<RestaurantListAdapter.ItemViewHolder>() {
    /**....
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
        val nestedAdapter = NestedImageAdapter(data.photoList )
        holder.binding.rv.adapter = nestedAdapter






    }

    override fun getItemCount(): Int = MsgList.size
}
// TODO:Activity transition

