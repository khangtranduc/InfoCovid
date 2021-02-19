package com.example.infocovid.activities

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.infocovid.R
import com.example.infocovid.model.Country
import kotlinx.android.synthetic.main.activity_country_detail.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.eazegraph.lib.models.PieModel

class CountryDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val country: Country = Json.decodeFromString(intent.extras?.get("country") as String)
        supportActionBar!!.setTitle(country.country)

        setUpPage(country)
    }

    @SuppressLint("SetTextI18n")
    private fun setUpPage(country: Country){
        country_name.text = country.country
        continent.text = country.continent
        population.text = "Population: ${country.population}"
        cases_tv.text = country.cases.toString()

//        val total_percent: Double = (country.active + country.deaths + country.recovered).toDouble()
//
//        active_tv.text = "Active (${String.format("%.2f", (country.active.toDouble() /total_percent) * 100)}%)"
//        deaths_tv.text = "Deaths (${String.format("%.2f", (country.deaths.toDouble() /total_percent) * 100)}%)"
//        recovered_tv.text = "Recovered (${String.format("%.2f", (country.recovered.toDouble()/total_percent) * 100)}%)"

        piechart.addPieSlice(PieModel("Active", country.active.toFloat(), Color.RED))
        piechart.addPieSlice(PieModel("Deaths", country.deaths.toFloat(), Color.GRAY))
        piechart.addPieSlice(PieModel("Recovered", country.recovered.toFloat(), Color.GREEN))


        pieCard.setOnClickListener{v ->
            PieDialog(country.active, country.deaths, country.recovered).show(supportFragmentManager, PieDialog.TAG)
        }

        casesCard.setOnClickListener{v ->
            CasesDialog(country.todayCases, country.todayDeaths, country.todayRecovered).show(supportFragmentManager, CasesDialog.TAG)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finishAfterTransition()
        }
        return super.onOptionsItemSelected(item)
    }
}