package com.example.chatbot.Fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.Adapter.NestedData
import com.example.chatbot.Adapter.RestaurantListAdapter
import com.example.chatbot.BuildConfig
import com.example.chatbot.Method
import com.example.chatbot.Network.Apiclient
import com.example.chatbot.R
import com.example.chatbot.databinding.MapShopBinding
import com.example.chatbot.placesDetails.PlacesDetails
import com.example.chatbot.placesDetails.data
import com.example.chatbot.placesSearch.PlacesSearch
import com.google.android.gms.common.internal.ImagesContract.URL
import com.google.gson.internal.bind.TypeAdapters.URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.net.URL


private var _binding: MapShopBinding? = null
private val binding get() = _binding!!
private lateinit var RAdapter: RestaurantListAdapter
private var msglist: MutableList<data> = ArrayList()//建立可改變的list
private var photorefArray: MutableList<String> = ArrayList()
private lateinit var placeid: String
private var placeidArray: MutableList<String> = ArrayList()
private lateinit var image: String
private lateinit var imageUrl: String
private var nestedDataList: MutableList<NestedData> = ArrayList()
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
            RAdapter.onClick = { data ->
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

                val b = Bundle()
                b.putParcelable("Data", data)

                val fragment = RestaurantDetailFragment()
                fragment.arguments = b
                fragmentTransaction.add(R.id.container, fragment, fragment.javaClass.name)
                fragmentTransaction.addToBackStack(fragment.javaClass.name)
                fragmentTransaction.commit()
            }
        }
    }

    private fun SearchShop() {
        val search = binding.editText.text.toString()
        binding.button.setOnClickListener()
        {

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
                        res.results.forEach { result ->
                            placeid = result.place_id
                            name = result.name
                            placeidArray.add(placeid)
                            result.photos.forEach { photo ->
                                photorefArray.add(photo.photo_reference)
                            }
                        }
                    }
                    for (i in 0..photorefArray.size - 1) {
                        photoref = photorefArray[i]
                        image = "https://maps.googleapis.com/maps/api/place/photo" +
                                "?maxwidth=4000" +
                                "&maxheight=4000" +
                                "&photo_reference=" + photoref +
                                "&key=" + BuildConfig.GOOGLE_API_KEY
//                        var newimage = testuniformimage(image)
                        nestedDataList.add(NestedData(image))
//                        nestedDataList.add(NestedData(newimage))

                    }
                    for (i in 0..placeidArray.size - 1) {
                        placeid = placeidArray[i]
                        image = nestedDataList[i].imageUrl
                        DetailSearch(placeid, image)
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

    private fun DetailSearch(placeid: String, image: String) {
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
                    address = res.result.formatted_address ?: ""
                    name = res.result.name ?: ""
                    phonenumber = res.result.formatted_phone_number ?: ""
                }
                rv(image)
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

    private fun rv(image: String) {
        msglist.add(
            data(
                formatted_address = address,
                name = name,
                formatted_phone_number = phonenumber,
                image = image
            )
        )
//        Log.d("msg", "msglist: $msglist\n")
//        Log.d("nestedDataList", "nestedDataList: $nestedDataList\n")
//        Log.d("photorefArray", "photorefArray: $photorefArray\n")
//        Log.d("image", "image: $image\n")
        RAdapter.notifyDataSetChanged()


    }

    private fun testuniformimage(image: String): String {
        val width = 300 // 设置目标宽度
        val height = 200 // 设置目标高度
        val resizedImages = mutableListOf<Bitmap>()

        val input = URL(image).openStream() // 从 URL 中读取图片数据流
        val bitmap = BitmapFactory.decodeStream(input) // 解码图片数据流为 Bitmap 对象
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false) // 缩放 Bitmap 对象

        resizedImages.add(resizedBitmap) // 将缩放后的 Bitmap 对象添加到列表中

        val stream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        val encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT)
        return encodedString


    }

}

//TODO rv image 統一圖片大小