package com.example.infocovid

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.infocovid.activities.Explore
import com.example.infocovid.activities.News
import com.example.infocovid.activities.Vaccines
import com.example.infocovid.helper.PagerAdapter
import com.example.infocovid.model.Country
import com.example.infocovid.model.New
import com.example.infocovid.services.GetService
import com.example.infocovid.services.NewsServiceBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.fragment_vaccines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    companion object{
        val fragments: MutableList<Fragment> = mutableListOf(Explore(), Vaccines(), News())
        var countries: List<Country>? = null
        var vacc_new: New? = null
        var health_new: New? = null
        var cov_new: New? = null
    }

    private var menu: Menu? = null
    private var search_on: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadVaccNews()
        loadHealthNews()
        loadCovNews()

        configureLayout()
    }

    private fun loadCovNews(){
        val api = NewsServiceBuilder.builderService(GetService::class.java)
        val callAsync = api.getCovNews()

        callAsync.enqueue(object: Callback<New> {
            override fun onResponse(call: Call<New>, response: Response<New>) {
                Log.d("debug", "onNewResponse: ${response.body()}")
                if (response.isSuccessful){
                    cov_new = response.body()!!
                }
                else{
                    Toast.makeText(baseContext, "An error occurred while loading the data (${response.message()})", Toast.LENGTH_LONG).show()
                    Log.d("debug", response.message())
                }
            }

            override fun onFailure(call: Call<New>, t: Throwable) {
                Toast.makeText(baseContext, "An error occurred while loading the data $t", Toast.LENGTH_LONG).show()
                Log.d("debug", "$t")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.refresh -> {
                loadHealthNews()
                loadCovNews()
                loadVaccNews()
                fragments[0] = Explore()
                fragments[1] = Vaccines()
                Snackbar.make(pager, "Refreshed", Snackbar.LENGTH_SHORT)
                    .setAnchorView(bottom_navigation)
                    .show()
            }
            R.id.search_button -> {
                if (search_on){
                    search_bar.visibility = View.GONE
                    if (!search_bar.text.toString().equals("")) search_bar.setText("")
                }
                else {
                    search_bar.visibility = View.VISIBLE
                }
                search_on = !search_on
            }
        }
        return true
    }

    private fun loadHealthNews(){
        val api = NewsServiceBuilder.builderService(GetService::class.java)
        val callAsync = api.getHealthNews()

        callAsync.enqueue(object: Callback<New> {
            override fun onResponse(call: Call<New>, response: Response<New>) {
                Log.d("debug", "onNewResponse: ${response.body()}")
                if (response.isSuccessful){
                    health_new = response.body()!!
                }
                else{
                    Toast.makeText(baseContext, "An error occurred while loading the data (${response.message()})", Toast.LENGTH_LONG).show()
                    Log.d("debug", response.message())
                }
            }

            override fun onFailure(call: Call<New>, t: Throwable) {
                Toast.makeText(baseContext, "An error occurred while loading the data $t", Toast.LENGTH_LONG).show()
                Log.d("debug", "$t")
            }
        })
    }

    private fun loadVaccNews(){
        val api = NewsServiceBuilder.builderService(GetService::class.java)
        val callAsync = api.getVaccNews()

        callAsync.enqueue(object: Callback<New> {
            override fun onResponse(call: Call<New>, response: Response<New>) {
                Log.d("debug", "onNewResponse: ${response.body()}")
                if (response.isSuccessful){
                    vacc_new = response.body()!!
                }
                else{
                    Toast.makeText(baseContext, "An error occurred while loading the data (${response.message()})", Toast.LENGTH_LONG).show()
                    Log.d("debug", response.message())
                }
            }

            override fun onFailure(call: Call<New>, t: Throwable) {
                Toast.makeText(baseContext, "An error occurred while loading the data $t", Toast.LENGTH_LONG).show()
                Log.d("debug", "$t")
            }
        })
    }

    private fun configureLayout(){
        Log.d("debug", bottom_navigation.menu.size().toString())
        val adapter = PagerAdapter(supportFragmentManager, bottom_navigation.menu.size())
        pager.adapter = adapter
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.explore -> pager.currentItem = 0
                R.id.vaccine -> pager.currentItem = 1
                R.id.news -> pager.currentItem = 2
            }
            Log.d("debug", pager.currentItem.toString())
            true
        }

        pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when (position){
                    0 -> {
                        bottom_navigation.menu.findItem(R.id.explore).isChecked = true
                        menu?.findItem(R.id.search_button)?.isVisible = true
                    }
                    1 -> {
                        bottom_navigation.menu.findItem(R.id.vaccine).isChecked = true
                        if (menu?.findItem(R.id.search_button)?.isVisible == true)
                            menu?.findItem(R.id.search_button)?.isVisible = false
                    }
                    2 -> {
                        bottom_navigation.menu.findItem(R.id.news).isChecked = true
                        if (menu?.findItem(R.id.search_button)?.isVisible == true)
                            menu?.findItem(R.id.search_button)?.isVisible = false
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }


}