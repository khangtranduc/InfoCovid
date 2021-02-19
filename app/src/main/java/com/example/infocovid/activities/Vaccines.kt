package com.example.infocovid.activities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infocovid.R
import com.example.infocovid.helper.VaccineAdapter
import com.example.infocovid.model.Vaccine
import com.example.infocovid.services.GetService
import com.example.infocovid.services.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_vaccines.*
import kotlinx.android.synthetic.main.fragment_vaccines.progress
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Explore.newInstance] factory method to
 * create an instance of this fragment.
 */
class Vaccines : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var vaccine: Vaccine? = null

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
        return inflater.inflate(R.layout.fragment_vaccines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadVaccines()
    }

    private fun loadVaccines(){
        val api = ServiceBuilder.builderService(GetService::class.java)
        val callAsync = api.getVaccines()

        callAsync.enqueue(object: Callback<Vaccine> {
            override fun onResponse(call: Call<Vaccine>, response: Response<Vaccine>) {
                Log.d("debug", "onResponse: ${response.body()}")
                progress.visibility = View.GONE
                if (response.isSuccessful){
                    vaccine = response.body()!!
                    vaccine_recycler.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter = VaccineAdapter(vaccine!!)
                        scheduleLayoutAnimation()
                    }
                }
                else{
                    Toast.makeText(context, "An error occurred while loading the data (${response.message()})", Toast.LENGTH_LONG).show()
                    Log.d("debug", response.message())
                }
            }

            override fun onFailure(call: Call<Vaccine>, t: Throwable) {
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
}