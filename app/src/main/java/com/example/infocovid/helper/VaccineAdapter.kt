package com.example.infocovid.helper

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.infocovid.R
import com.example.infocovid.activities.CountryDetail
import com.example.infocovid.activities.InfoVaccine
import com.example.infocovid.model.Vaccine
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.StringBuilder

class VaccineAdapter(private val vaccine: Vaccine): RecyclerView.Adapter<VaccineAdapter.ViewHolder>() {

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item){
        var title = item.findViewById<TextView>(R.id.vaccine_title)
        var sponsor = item.findViewById<TextView>(R.id.sponsor)

        init{
            item.setOnClickListener{v ->
                val intent = Intent(v.context, InfoVaccine::class.java)
                Log.d("debug", vaccine.data[adapterPosition].candidate)
                intent.putExtra("vaccine", Json.encodeToString(vaccine.data[adapterPosition]))
                v.context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(v.context as Activity).toBundle())
            }
        }

        fun bind(position: Int, vaccine: Vaccine){
            title.text = vaccine.data[position].candidate
            val str = StringBuilder()
            for (i in 0..(vaccine.data[position].sponsors.size - 2)){
                str.append(vaccine.data[position].sponsors[i]).append(",")
            }
            sponsor.text = str.append(vaccine.data[position].sponsors[vaccine.data[position].sponsors.size - 1]).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vaccine_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, vaccine)
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_slide_from_bottom)
    }

    override fun getItemCount(): Int {
        return vaccine.data.size
    }
}