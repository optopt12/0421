package com.example.chatbot.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.chatbot.Adapter.ImageDetailAdapter
import com.example.chatbot.databinding.ShopItemScrollBinding
import com.example.chatbot.placesDetails.data


class ImageDetailFragment : Fragment() {

    private var _binding: ShopItemScrollBinding? = null
    private val binding get() = _binding!!

    private var data: String? = null
    private var receivedData: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getString("RDetailtoImage")
            receivedData = listOf(data!!)!! // create a new list with data as its only element
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShopItemScrollBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO


        val adapter = ImageDetailAdapter(receivedData!!)
        binding.apply {
            viewPager.adapter = adapter

            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 滑动方向
            viewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    // 页面变化回调
                }
            })
        }

    }

}

