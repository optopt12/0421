package com.example.chatbot.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.Adapter.NestedData
import com.example.chatbot.Adapter.NestedImageAdapter
import com.example.chatbot.Adapter.RestaurantDetailAdapter
import com.example.chatbot.Adapter.RestaurantListAdapter
import com.example.chatbot.BuildConfig
import com.example.chatbot.Method
import com.example.chatbot.Network.Apiclient
import com.example.chatbot.databinding.MapShopBinding
import com.example.chatbot.databinding.ShopDetailBinding
import com.example.chatbot.placesDetails.PlacesDetails
import com.example.chatbot.placesDetails.data
import com.example.chatbot.placesSearch.PlacesSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RestaurantDetailFragment : Fragment() {
    private var _binding: ShopDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var RAdapter: RestaurantDetailAdapter
    private lateinit var NAdapter: NestedImageAdapter

    private var Detailmsglist: MutableList<data> = ArrayList()//建立可改變的list
    companion object {
        private const val TAG = "RestaurantDetailFragment"
        private const val DEFAULT_ZOOM = 18F
        private const val DEFAULT_LATITUDE = 25.043871531367014
        private const val DEFAULT_LONGITUDE = 121.53453374432904
    }

    private var data: data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable("Data")
            Detailmsglist.add(data!!)


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShopDetailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv() //RecyclerView初始化
        Toast.makeText(requireContext(), "${data?.name}1234tees", Toast.LENGTH_SHORT).show()

        RAdapter.notifyDataSetChanged()
        NAdapter.notifyDataSetChanged()

    }

    private fun initRv() {
        binding.DetailrvS.apply {
            RAdapter = RestaurantDetailAdapter(Detailmsglist)//建立适配器实例
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )  //布局为线性垂直
            adapter = RAdapter
        }
        binding.DetailrvH.apply {
            NAdapter = NestedImageAdapter(Detailmsglist[0].photoList)//建立适配器实例
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )  //布局为线性垂直
            adapter = NAdapter
        }
    }
//    private fun rv(image: String) {
//        msglist.add(
//            data(
//                formatted_address = address,
//                name = name,
//                formatted_phone_number = phonenumber,
//                image = image
//            )
//        )
////        Log.d("msg", "msglist: $msglist\n")
////        Log.d("nestedDataList", "nestedDataList: $nestedDataList\n")
////        Log.d("photorefArray", "photorefArray: $photorefArray\n")
////        Log.d("image", "image: $image\n")
//        RAdapter.notifyDataSetChanged()
//
//
//    }

}