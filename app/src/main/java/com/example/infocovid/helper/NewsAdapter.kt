package com.example.infocovid.helper

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.infocovid.R
import com.example.infocovid.activities.NewsView
import com.example.infocovid.model.New
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.Exception

class NewsAdapter(private val news: New) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.bind(position, news)
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_slide_from_bottom)
    }

    override fun getItemCount(): Int {
        return news.news.size
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item){
        var img = item.findViewById<ImageView>(R.id.thumbnail)
        var title = item.findViewById<TextView>(R.id.news_title)
        var published = item.findViewById<TextView>(R.id.published)

        init{
            item.setOnClickListener{v ->
                val intent = Intent(v.context, NewsView::class.java)
                intent.putExtra("news", Json.encodeToString(news.news[adapterPosition]))
                Log.d("debug", news.news[adapterPosition].link)
                v.context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(v.context as Activity).toBundle())
            }
        }

        fun bind(position: Int, news: New){
            Picasso.get().load(news.news[position].urlToImage).into(img, object : Callback {
                override fun onSuccess() {}
                override fun onError(e: Exception?) {
                    img.visibility = View.GONE
                }
            })
            title.text = news.news[position].title
            published.text = "Published On: ${news.news[position].pubDate.substring(0, 10)}"
        }
    }
}