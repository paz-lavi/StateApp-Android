package com.paz.stateapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paz.stateapp.R
import com.paz.stateapp.adapters.viewHolders.CountryModelViewHolder
import com.paz.stateapp.model.CountryModel

class BorderListAdapter(
    private val countries: ArrayList<CountryModel?>,
) :
    RecyclerView.Adapter<CountryModelViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_country, parent, false)
        return CountryModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryModelViewHolder, position: Int) {
        countries[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return countries.size
    }
}


