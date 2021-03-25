package com.example.moviedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val id = intent.getIntExtra("id", 0)

        val url = BASE_URL + "movie/$id"

        val parameters = listOf(
                Pair("api_key", API_KEY)
        )
        url.httpGet(parameters).response{
            request, response, result ->
            Log.d("MovieDetailActivity", "Result : ${result.component1()?.decodeToString()}, Error: ${result.component2()?.exception?.message}")

            if(result.component1() != null){
                val json = result.component1()?.decodeToString()?:return@response

                //Parse the json
                val gson = Gson()
                val type = object: TypeToken<MovieResponse>(){}.type
                val movieResponse = gson.fromJson<MovieResponse>(json, type)

                Glide.with(this)
                        .load("http://image.tmdb.org/t/p/w500/" + movieResponse.posterPath)
                        .into(large_poster_image)
                movie_title.text = movieResponse.title
                movie_overview.text = movieResponse.overview
            }
        }

    }
}