package com.example.chatbot.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator
import com.example.chatbot.Fragment.PlacesFragment
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

        holder.binding.Address.text = data.formatted_address
        holder.binding.Shopname.text = data.name
        holder.binding.PhoneNumber.text = data.formatted_phone_number
        Picasso.get().load(data.image).into(holder.binding.imageView)
        holder.binding.imageView.setOnClickListener()
        {
            val manager = requireActivity().supportFragmentManager.beginTransaction()
            manager.add(R.id.map,ShopFragment())
                .addToBackStack(null)//在map新增一個叫做ShopFragment的Fragment
            manager.hide(PlacesFragment())//把map原本的PlacesFragment hide
            manager.show(ShopFragment()).commit()
        }
        val layoutManager = LinearLayoutManager(holder.binding.root.context, LinearLayoutManager.HORIZONTAL, false)
//        holder.binding.rv.layoutManager = layoutManager
//        val nestedAdapter = NestedImageAdapter(data.photoList)
//        holder.binding.rv.adapter = nestedAdapter

    }

    override fun getItemCount(): Int = MsgList.size
}
// TODO:Activity transition

