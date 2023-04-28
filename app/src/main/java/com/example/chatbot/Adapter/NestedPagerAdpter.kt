package com.example.chatbot.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.alexvasilkov.gestures.views.interfaces.GestureView
import com.example.chatbot.databinding.ShopItemNestedBinding

class NestedImagePagerAdpter(
    var viewPager: ViewPager,
    var settingsController: SettingsController,
    var paintings: List<NestedData>
) :
    RecyclerView.Adapter<NestedImagePagerAdpter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ShopItemNestedBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NestedImagePagerAdpter.ItemViewHolder {
        val View = ShopItemNestedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(View)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.imgNested.getController().enableScrollInViewPager(viewPager)
        settingsController.apply(holder.binding.imgNested)

    }


    override fun getItemCount(): Int = paintings.size


}


interface SettingsController {
    fun apply(view: GestureView?)
}
