package com.example.moviedemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.track_item_layout.view.*
import java.lang.Exception
import java.text.SimpleDateFormat

class TracksListAdapter(val list:List<TrackItem?>): RecyclerView.Adapter<TracksListAdapter.TrackItemViewHolder>() {

    class TrackItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.track_item_layout, parent, false)
        return TrackItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackItemViewHolder, position: Int) {
        val trackItem = list[position]?:return

        val image = trackItem.image?.firstOrNull {
            it?.size == "medium"
        }

        if(image != null){
            Glide.with(holder.itemView)
                    .load(image.text)
                    .into(holder.itemView.poster_image)
        }

        holder.itemView.title_text.text = trackItem.name
        holder.itemView.artist_text.text = trackItem.artist?.name
    }

    override fun getItemCount(): Int {
        return list.size
    }

}