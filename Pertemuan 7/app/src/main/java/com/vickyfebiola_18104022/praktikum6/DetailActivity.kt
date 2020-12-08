package com.vickyfebiola_18104022.praktikum6

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_scrolling.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val myData by getParcelableExtra<MyData>(DetailActivity.EXTRA_MYDATA)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            val moveWithObjectIntent = Intent(this,MapsActivity::class.java)
            moveWithObjectIntent.putExtra(MapsActivity.EXTRA_MYDATA, myData)
            startActivity(moveWithObjectIntent)
        }


        supportActionBar?.title = myData!!.name.toString()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tv_detail_description.text = myData!!.description.toString()

        Glide.with(this)
            .load(myData!!.photo.toString())
            .apply(RequestOptions().override(700, 700))
            .into(iv_detail_photo)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_MYDATA = "extra_mydata"
    }

    inline fun <reified T : Parcelable> Activity.getParcelableExtra(key: String) = lazy {
        intent.getParcelableExtra<T>(key)
    }
}