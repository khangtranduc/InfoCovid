package com.example.infocovid.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.infocovid.R
import com.example.infocovid.model.New
import kotlinx.android.synthetic.main.activity_news_view.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class NewsView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_view)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("News Viewer")
        val news : New.New = Json.decodeFromString(getIntent().extras?.get("news") as String)
        Log.d("debug", news.link)
        webview.loadUrl(news.link)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finishAfterTransition()
        }
        return super.onOptionsItemSelected(item)
    }
}