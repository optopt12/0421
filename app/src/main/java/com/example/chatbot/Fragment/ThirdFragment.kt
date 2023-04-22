package com.example.chatbot.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.Adapter.NestedData
import com.example.chatbot.Adapter.RestaurantListAdapter
import com.example.chatbot.BuildConfig
import com.example.chatbot.Method
import com.example.chatbot.Network.Apiclient
import com.example.chatbot.databinding.MapShopBinding
import com.example.chatbot.placesDetails.PlacesDetails
import com.example.chatbot.placesDetails.data
import com.example.chatbot.placesSearch.Photo
import com.example.chatbot.placesSearch.PlacesSearch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var _binding: MapShopBinding? = null
private val binding get() = _binding!!
private lateinit var RAdapter: RestaurantListAdapter
private var msglist: MutableList<data> = ArrayList()//建立可改變的list
private lateinit var placeid: String
private var placeidArray: MutableList<String> = ArrayList()
private lateinit var imageUrl: String
private lateinit var name: String
private lateinit var address: String
private lateinit var phonenumber: String
private lateinit var photoref: String

class ThirdFragment : Fragment() {
    companion object {
        private const val TAG = "ThirdFragment"
        private const val DEFAULT_ZOOM = 18F
        private const val DEFAULT_LATITUDE = 25.043871531367014
        private const val DEFAULT_LONGITUDE = 121.53453374432904
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MapShopBinding.inflate(inflater, container, false)
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
        DetailSearch()
    }

    private fun initRv() {
        binding.rv.apply {
            RAdapter = RestaurantListAdapter(msglist)//建立适配器实例
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )  //布局为线性垂直
            adapter = RAdapter
        }
    }

    private fun DetailSearch() {
        var imageUrl = "https://www.edamam.com/food-img/296/296ff2b02ef3822928c3c923e22c7d19.jpg"
        var imageUrl2 = "https://www.edamam.com/food-img/515/515af390107678fce1533a31ee4cc35b.jpeg"
        var imageUrl3 = "https://www.edamam.com/food-img/42c/42c006401027d35add93113548eeaae6.jpg"

        var address = "test1address"
        var name = "test1name"
        var phonenumber = "test1phonenumber"
        var nestedDataList = mutableListOf(NestedData(imageUrl))
        nestedDataList.add(NestedData(imageUrl2))
        nestedDataList.add(NestedData(imageUrl3))



        msglist.add(
            data(
                formatted_address = address,
                name = name,
                formatted_phone_number = phonenumber,
                photoList = nestedDataList
            )
        )

        imageUrl = "https://www.edamam.com/food-img/515/515af390107678fce1533a31ee4cc35b.jpeg"
        address = "test2address"
        name = "test2name"
        phonenumber = "test2phonenumber"
        nestedDataList = mutableListOf(NestedData(imageUrl))

        msglist.add(
            data(
                formatted_address = address,
                name = name,
                formatted_phone_number = phonenumber,
                photoList = nestedDataList
            )
        )
        imageUrl = "https://www.edamam.com/food-img/42c/42c006401027d35add93113548eeaae6.jpg"
        address = "test3address"
        name = "test3name"
        phonenumber = "test3phonenumber"
        nestedDataList = mutableListOf(NestedData(imageUrl))

        msglist.add(
            data(
                formatted_address = address,
                name = name,
                formatted_phone_number = phonenumber,
                photoList = nestedDataList
            )
        )


        Log.d("msg", "msglist: $msglist")
        RAdapter.notifyDataSetChanged()
    }

}