package com.example.infocovid.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.infocovid.R
import com.example.infocovid.helper.ExploreAdapter
import com.example.infocovid.model.Country
import com.example.infocovid.services.GetService
import com.example.infocovid.services.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_explore.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Explore.newInstance] factory method to
 * create an instance of this fragment.
 */
class Explore : Fragment(), TextWatcher {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var countries: List<Country>? = null
    private var filteredCountries: MutableList<Country>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        search_bar.addTextChangedListener(this)
        loadCountries()
    }

    private fun loadCountries(){
        val api = ServiceBuilder.builderService(GetService::class.java)
        val callAsync = api.getCountries()

        callAsync.enqueue(object: Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                Log.d("debug", "onResponse: ${response.body()}")
                progress.visibility = View.GONE
                if (response.isSuccessful){
                    countries = response.body()!!
                    explore_recycler.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(context, 2)
                        adapter = ExploreAdapter(countries!!)
                        scheduleLayoutAnimation()
                    }
                }
                else{
                    Toast.makeText(context, "An error occurred while loading the data (${response.message()})", Toast.LENGTH_LONG).show()
                    Log.d("debug", response.message())
                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Toast.makeText(context, "An error occurred while loading the data $t", Toast.LENGTH_LONG).show()
                Log.d("debug", "$t")
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Explore.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Explore().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    @SuppressLint("DefaultLocale")
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        filteredCountries = mutableListOf()
        if (!s.toString().equals("")){
            for (item in countries!!){
                if (item.country.toLowerCase().matches("${s.toString().toLowerCase()}.*".toRegex())){
                    filteredCountries!!.add(item)
                }
            }
            Collections.sort(filteredCountries)
            setupRecyclerView(filteredCountries!!)
        }
        else{
            setupRecyclerView(countries!!)
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    private fun setupRecyclerView(list: List<Country>){
        explore_recycler.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = ExploreAdapter(list)
            scheduleLayoutAnimation()
        }
    }
}