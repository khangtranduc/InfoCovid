package com.example.infocovid.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import com.example.infocovid.R
import com.example.infocovid.model.Vaccine
import kotlinx.android.synthetic.main.activity_info_vaccine.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.StringBuilder

class InfoVaccine : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_vaccine)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val vaccineData: Vaccine.Data = Json.decodeFromString(intent.extras?.get("vaccine") as String)
        supportActionBar!!.setTitle(vaccineData.candidate)

        setUpPage(vaccineData)
    }

    private fun setUpPage(vaccineData: Vaccine.Data){
        candidate.text = vaccineData.candidate

        val ins_str = StringBuilder()
        for (i in 0..(vaccineData.institutions.size - 2)){
            ins_str.append(vaccineData.institutions[i]).append(", ")
        }
        ins_str.append(vaccineData.institutions.last())
        institution.text = "by ${ins_str.toString()}"

        val str = StringBuilder()
        for (i in 0..(vaccineData.sponsors.size - 2)){
            str.append(vaccineData.sponsors[i]).append(", ")
        }
        str.append(vaccineData.sponsors.last())
        sponsor.text = "sponsored by ${str.toString()}"

        mechanism.text = vaccineData.mechanism

        phase.text = vaccineData.trialPhase

        details_candidate.text = Html.fromHtml(vaccineData.details , Html.FROM_HTML_MODE_LEGACY)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finishAfterTransition()
        }
        return super.onOptionsItemSelected(item)
    }
}