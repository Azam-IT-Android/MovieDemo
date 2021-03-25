package com.example.moviedemo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_item_layout.view.*
import java.lang.Exception
import java.text.SimpleDateFormat

class MovieListItemAdapter(val list:List<MovieItem?>, val onClick: (MovieItem)->Unit): RecyclerView.Adapter<MovieListItemAdapter.MovieItemViewHolder>() {

    class MovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_item_layout, parent, false)
        return MovieItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val movieItem = list[position]?:return

        val imageUrl = "https://image.tmdb.org/t/p/w500" + movieItem.posterPath
        if(movieItem.posterPath != null){
            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.itemView.poster_image)
        }
        var dateString = ""
        try{
            val sourceFormatter = SimpleDateFormat("yyyy-MM-dd")
            val destFormatter = SimpleDateFormat("MMM dd, yyyy")
            dateString = destFormatter.format(sourceFormatter.parse(movieItem?.releaseDate))
        } catch (e: Exception){

        }
        holder.itemView.title_text.text = movieItem.title
        holder.itemView.release_date_text.text = dateString

        holder.itemView.setOnClickListener {
            onClick(movieItem)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}