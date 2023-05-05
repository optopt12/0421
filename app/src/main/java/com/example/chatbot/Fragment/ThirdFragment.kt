package com.example.chatbot.Fragment

import android.os.Bundle
import android.util.Log
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
import com.example.chatbot.placesDetails.Review
import com.example.chatbot.placesDetails.data
import com.example.chatbot.placesSearch.PlacesSearch
import kotlinx.android.synthetic.main.shop_detail_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ThirdFragment : Fragment() {
    //binding
    private var _binding: MapShopBinding? = null
    private val binding get() = _binding!!
    //adapter
    private lateinit var RAdapter: RestaurantListAdapter
    //Rv
    private var msglist: MutableList<data> = ArrayList()//建立可改變的list
    //Google places search
    private var photorefArray: MutableList<String>  = ArrayList()
    private var placeidArray: MutableList<String> = ArrayList()
    private var nestedDataList: MutableList<NestedData> = ArrayList()
    private lateinit var placeid: String
    private lateinit var image: String
    private lateinit var photoref: String
    //Google Detail search
    private var photoList: MutableList<String> = ArrayList()
    private var DetailphotorefArray: MutableList<String>  = ArrayList()
    private lateinit var reviews: List<Review>
    private lateinit var Detailphoto: String
    private lateinit var Detailphotoref: String
    private lateinit var Detailimage: String
    private lateinit var name: String
    private lateinit var phonenumber: String
    private lateinit var address: String
    //Google places search 評論
    private lateinit var author_name: String
    private lateinit var user_language: String
    private lateinit var profile_photo_url: String
    private lateinit var text: String
    private lateinit var reference: String
    private var rating: Int = 0
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
                b.putParcelable("ThirdtoRdetail", data)

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
        binding.button.setOnClickListener() //TODO DetailSearch只在這裡做一次，photolist不會有變化
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
                    for(i in 0 .. photorefArray.size - 1)
                    {
                        photoref = photorefArray[i]
                        image = "https://maps.googleapis.com/maps/api/place/photo" +
                                "?maxwidth=4000" +
                                "&maxheight=4000" +
                                "&photo_reference=" + photoref +
                                "&key=" + BuildConfig.GOOGLE_API_KEY
                        nestedDataList.add(NestedData(image))
                    }
                    for(i in 0 .. placeidArray.size - 1)
                    {
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
    private fun DetailSearch(placeid :String,image:String){
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
                        reference = res.result.reference
                        res.result.photos.forEach{ photo ->
                                DetailphotorefArray.add(photo.photo_reference)
                        }
                        res.result.reviews.forEach{ Review ->
                            author_name = Review.author_name
                            user_language = Review.language
                            rating = Review.rating
                            text = Review.text
                            profile_photo_url = Review.profile_photo_url
                        }
                    }

                for(i in 0 .. DetailphotorefArray.size - 1)
                {
                    Detailphotoref = DetailphotorefArray[i]
                    Detailimage ="https://maps.googleapis.com/maps/api/place/photo" +
                            "?maxwidth=300" +
                            "&maxheight=200" +
                            "&photo_reference=" + Detailphotoref +
                            "&key=" + BuildConfig.GOOGLE_API_KEY
                    photoList.add(Detailimage)
                }
                Log.d("DetailimagephotoList", "photoList: $photoList\n")
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
        msglist.add(data(
            formatted_address = address,
            name = name,
            formatted_phone_number = phonenumber,
            image = image,
            photoList = photoList, //photolist是單一店家的所有圖片
            reference = reference,
            author_name = author_name,
            language = user_language,
            rating = rating,
            text = text,
            profile_photo_url = profile_photo_url
            ))
//        Log.d("msg", "msglist: $msglist\n")
//        Log.d("nestedDataList", "nestedDataList: $nestedDataList\n")
//        Log.d("photorefArray", "photorefArray: $photorefArray\n")
//        Log.d("image", "image: $image\n")
        RAdapter.notifyDataSetChanged()


    }

}

//TODO rv image 統一圖片大小