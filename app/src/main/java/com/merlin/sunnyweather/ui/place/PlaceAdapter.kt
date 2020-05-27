package com.merlin.sunnyweather.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.merlin.sunnyweather.R
import com.merlin.sunnyweather.logic.model.Place
import com.merlin.sunnyweather.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*

class PlaceAdapter(private val fragment: PlaceFragment,private val places: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {


   inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
       val placeName : TextView = view.findViewById(R.id.placeName)
       val placeAddress : TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener{
            val position = holder.adapterPosition
            val place = places[position]
            val activity = fragment.activity
            if (activity is WeatherActivity){
                activity.drawerLayout.closeDrawers()
                activity.viewModel.locationLat = place.location.lng
                activity.viewModel.locationLat = place.location.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()
            }else{
                val intent = Intent(parent.context,WeatherActivity::class.java).apply {
                    putExtra("locationLng",place.location.lng)
                    putExtra("locationLat",place.location.lat)
                    putExtra("placeName",place.name)
                }
                fragment.startActivity(intent)
                fragment.activity?.finish()
            }
            fragment.viewModel.savePlace(place)


        }
        return holder
    }

    override fun getItemCount(): Int {
        return places.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }


}