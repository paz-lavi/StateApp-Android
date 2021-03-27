package com.paz.stateapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.paz.stateapp.R
import com.paz.stateapp.adapters.viewHolders.ClickableCountryModelViewHolder
import com.paz.stateapp.callbacks.CountrySelectedCallback
import com.paz.stateapp.model.CountryModel
import java.util.*
import kotlin.collections.ArrayList

/** Filterable countries RecyclerView */
class CountryListAdapter(
    private val countries: ArrayList<CountryModel?>,
    private val onClick: CountrySelectedCallback

) :
    RecyclerView.Adapter<ClickableCountryModelViewHolder>(), Filterable {
    var countryFilterList = ArrayList<CountryModel?>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClickableCountryModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_country, parent, false)
        return ClickableCountryModelViewHolder(view, onClick)
    }


    init {
        countryFilterList = countries
    }

    override fun onBindViewHolder(holder: ClickableCountryModelViewHolder, position: Int) {
        countryFilterList[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return countryFilterList.size
    }

    fun removeItem(position: Int) {
        countryFilterList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                countryFilterList = if (charSearch.isEmpty()) {
                    countries
                } else {
                    val resultList = ArrayList<CountryModel?>()
                    for (row in countries) {
                        if (row?.name?.toLowerCase(Locale.ROOT)!!.contains(
                                constraint.toString().toLowerCase(
                                    Locale.ROOT
                                )
                            )
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<CountryModel?>
                notifyDataSetChanged()
            }
        }
    }
}

