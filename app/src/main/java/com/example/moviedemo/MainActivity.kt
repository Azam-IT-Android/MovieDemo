package com.example.moviedemo

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val LATEST_MOVIES_ENDPOINT = "movie/popular"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED")
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED")

        registerReceiver(ChargingReceiver(), intentFilter)



        val url = BASE_URL + LATEST_MOVIES_ENDPOINT

        val parameters = listOf(
                Pair("api_key", API_KEY),
                Pair("language", "es"),
        )
        url.httpGet(parameters).response {
            request, response, result ->
            Log.d("Result", "Response: ${result.component1()?.decodeToString()}, Error : ${result.component2()?.exception?.message}, ${result.component2()?.response?.data?.decodeToString()}")

            if(result.component1() != null) {
                //Parse Response Json to PopularMoviesResponse object
                val gson = Gson()
                val type = object : TypeToken<PopularMoviesResponse>() {}.type
                val popularMoviesResponse = gson.fromJson<PopularMoviesResponse>(result.component1()?.decodeToString(), type)

                //Display movies in RecyclerView
                movieItemsRv.adapter = MovieListItemAdapter(popularMoviesResponse.results
                        ?: arrayListOf()){
                    val intent = Intent(this, MovieDetailActivity::class.java)
                    intent.putExtra("id", it.id)
                    startActivity(intent)
                }
                movieItemsRv.layoutManager = GridLayoutManager(this, 3)
                Log.d("Result", "After deserialize: $popularMoviesResponse")
            } else{
                Toast.makeText(this, "Error getting response", Toast.LENGTH_SHORT).show()
            }
        }


    }
}