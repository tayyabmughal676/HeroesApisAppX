package com.example.mrtayyab.heroesapisapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class heroesActivity : AppCompatActivity() {

//    please change URL_Root with your URL
    private val URL_ROOT = "http://192.168.43.11/heroapi/HeroApi/v1/?op="
    val URL_GET_HEROES = URL_ROOT + "getheroes"

    lateinit var mHeroListView : ListView
    lateinit var mherolist : MutableList<hero>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heroes)

        mHeroListView = findViewById(R.id.heroeslistView) as ListView
        mherolist = mutableListOf<hero>()
        loadArtists()

    }

    private fun loadArtists() {

        val stringRequest = StringRequest(Request.Method.GET,
                URL_GET_HEROES,
                Response.Listener<String> { s ->
                    try {
                        val obj = JSONObject(s)
                        if (!obj.getBoolean("error")) {
                            // here change
                            val array = obj.getJSONArray("hero")

                            for (i in 0..array.length() - 1) {
                                val objectHeroes = array.getJSONObject(i)
                                val myHeroListIN = hero(
                                        objectHeroes.getString("name"),
                                        objectHeroes.getString("role")
                                )
                                mherolist.add(myHeroListIN)
                                val adapter = HeroesList(this, mherolist)
                                mHeroListView.adapter = adapter
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)

    }
}
