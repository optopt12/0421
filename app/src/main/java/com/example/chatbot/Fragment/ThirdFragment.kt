package com.example.chatbot.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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

private var _binding : MapShopBinding? = null
private val binding get() = _binding!!
private lateinit var RAdapter: RestaurantListAdapter
private var msglist: MutableList<data> = ArrayList()//建立可改變的list
private lateinit var placeid :String
private lateinit var name :String
private lateinit var address :String
private lateinit var phonenumber :String
private lateinit var photoref :String
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
        _binding = MapShopBinding.inflate(inflater,container,false)
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
        SearchShop()
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
    private fun SearchShop() {
        val search = binding.editText.text.toString()
        binding.button.setOnClickListener()
        {
//                val manager = requireActivity().supportFragmentManager.beginTransaction()
//                manager.add(R.id.map, ShopFragment())
//                    .addToBackStack(null)//在map新增一個叫做ShopFragment的Fragment
//                manager.hide(PlacesFragment())//把map原本的PlacesFragment hide
//                manager.show(ShopFragment()).commit()
            Apiclient.googlePlaces.getPlaceSearch(
                location = "$DEFAULT_LATITUDE,$DEFAULT_LONGITUDE",
                radius = "500",
                language = "zh-TW",
                keyword = search,
                key = BuildConfig.GOOGLE_API_KEY
            ).enqueue(object : Callback<PlacesSearch> {
                override fun onResponse(
                    call: Call<PlacesSearch>,
                    response: Response<PlacesSearch>
                ) {
                    response.body()?.let { res ->
                        for(i in 0 until 3  )
                        {
                            res.results.forEach { result ->
                                placeid = result.place_id
                                name = result.name ?: ""
                                result.photos.forEach { photo ->
                                    photoref = photo.photo_reference

                                }
                            }
                            DetailSearch()
                        }
                    }
                }
                override fun onFailure(
                    call: Call<PlacesSearch>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                    Method.logE(TAG, "onFailure: ${t.message}")
                }
            })
        }
    }
    private fun DetailSearch(){
        Apiclient.googlePlaces.getPlaceDetails(
            placeID = placeid,
            language = "zh-TW",
            key = BuildConfig.GOOGLE_API_KEY
        ).enqueue(object : Callback<PlacesDetails> {
            override fun onResponse(
                call: Call<PlacesDetails>,
                response: Response<PlacesDetails>
            ) {
                    response.body()?.let { res ->
                        address= res.result.formatted_address ?: ""
                        name = res.result.name ?: ""
                        phonenumber = res.result.formatted_phone_number ?: ""
                        CoroutineScope(Dispatchers.Main).launch {
                            msglist.add(data(address,name,phonenumber))
                            RAdapter.notifyDataSetChanged()
                        }
                }
            }
            override fun onFailure(
                call: Call<PlacesDetails>,
                t: Throwable
            ) {
                t.printStackTrace()
                Method.logE(TAG, "onFailure: ${t.message}")
            }
        })
    }
}