package com.example.moviedemo

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val parameters = listOf(
                Pair("method", "geo.gettoptracks"),
                Pair("country", "india"),
                Pair("api_key", "0d6815b4c3f6ba69c7fc40739a09d552"),
                Pair("format", "json"),
        )

        LAST_FM_BASE_URL.httpGet(parameters).response { request, response, result ->
            Log.d("Result", "Response: ${result.component1()?.decodeToString()}, Error : ${result.component2()?.exception?.message}, ${result.component2()?.response?.data?.decodeToString()}")

            if (result.component1() != null) {
                //Parse Response Json to PopularMoviesResponse object
                val gson = Gson()
                val type = object : TypeToken<TopTracksResponse>() {}.type
                val popularMoviesResponse = gson.fromJson<TopTracksResponse>(result.component1()?.decodeToString(), type)

                //Display movies in RecyclerView
                movieItemsRv.adapter = TracksListAdapter(popularMoviesResponse.tracks?.track
                        ?: arrayListOf())
                movieItemsRv.layoutManager = GridLayoutManager(this, 3)
                Log.d("Result", "After deserialize: $popularMoviesResponse")
            } else {
                Toast.makeText(this, "Error getting response", Toast.LENGTH_SHORT).show()
            }
        }
    }
}