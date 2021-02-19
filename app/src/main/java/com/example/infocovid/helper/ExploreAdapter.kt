package com.example.infocovid.helper

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.infocovid.R
import com.example.infocovid.activities.CountryDetail
import com.example.infocovid.model.Country
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ExploreAdapter(private val countries: List<Country>) : RecyclerView.Adapter<ExploreAdapter.ViewHolder>() {

    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        var flag = item.findViewById<ImageView>(R.id.country_flag)
        var title = item.findViewById<TextView>(R.id.country_title)
        var cases = item.findViewById<TextView>(R.id.cases)

        init{
            item.setOnClickListener{v ->
                val intent = Intent(v.context, CountryDetail::class.java)
                intent.putExtra("country", Json.encodeToString(countries[adapterPosition]))
                v.context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(v.context as Activity).toBundle())
            }
        }

        fun bind (country: Country){
            cases.text = "Cases: ${country.cases}"
            title.text = country.country
            Picasso.get().load(country.countryInfo.flag).into(flag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_card, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(countries[position])
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_slide_from_bottom)
    }

    override fun getItemCount(): Int {
        return countries.size
    }
}