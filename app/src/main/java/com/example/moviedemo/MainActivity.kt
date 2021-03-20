package com.example.moviedemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val API_KEY = "25611267ecba2d97519b04498964b070"
    val BASE_URL = "https://api.themoviedb.org/3/"
    val LATEST_MOVIES_ENDPOINT = "movie/popular"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "$BASE_URL$LATEST_MOVIES_ENDPOINT"

        val parameters = listOf(
                Pair("api_key", API_KEY),
                Pair("language", "es"),
        )
        url.httpGet(parameters).response {
            request, response, result ->
            Log.d("Result", "Response: ${result.component1()?.decodeToString()}, Error : ${result.component2()?.exception?.message}, ${result.component2()?.response?.data?.decodeToString()}")
            val gson = Gson()
            val type = object:TypeToken<PopularMoviesResponse>(){}.type
            val popularMoviesResponse = gson.fromJson<PopularMoviesResponse>(result.component1()?.decodeToString(), type)

            movieItemsRv.adapter = MovieListItemAdapter(popularMoviesResponse.results?: arrayListOf())
            movieItemsRv.layoutManager = GridLayoutManager(this, 3)
            Log.d("Result", "After deserialize: $popularMoviesResponse")
        }
    }
}