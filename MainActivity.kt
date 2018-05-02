package com.example.mrtayyab.heroesapisapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    lateinit var addHeroBtn : Button
    lateinit var viewHeroBtn : Button
    lateinit var heroNameText : EditText
    lateinit var heroSpinner : Spinner

    private val URL_ROOT = "http://192.168.43.11/heroapi/HeroApi/v1/?op="
    val URL_ADD_HERO = URL_ROOT + "addheroes"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addHeroBtn = findViewById(R.id.addHeroes);
        viewHeroBtn = findViewById(R.id.viewHeroes);
        heroNameText = findViewById(R.id.heroNameText);
        heroSpinner = findViewById(R.id.spinnerGenre);

        addHeroBtn.setOnClickListener {

            addNewHeroes()
        }

        viewHeroBtn.setOnClickListener {

            val intent = Intent(application , heroesActivity::class.java);

            startActivity(intent)
        }


    }

    private fun addNewHeroes() {

        //getting the record values
        val name = heroNameText.text.toString()
        val mspinner = heroSpinner.selectedItem.toString()

        val stringRequest = object : StringRequest(Request.Method.POST, URL_ADD_HERO ,
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)

                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },

                object : Response.ErrorListener {
                    override fun onErrorResponse(volleyError: VolleyError) {
                        Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                    }
                }) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("name", name)
                params.put("role", mspinner)
                return params
            }
        }


        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}
