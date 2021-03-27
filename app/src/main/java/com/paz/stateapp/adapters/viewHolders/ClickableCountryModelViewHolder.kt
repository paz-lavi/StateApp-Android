package com.paz.stateapp.adapters.viewHolders

import android.view.View
import com.paz.stateapp.callbacks.CountrySelectedCallback
import com.paz.stateapp.model.CountryModel

/** Clickable View Model*/
class ClickableCountryModelViewHolder(
    itemView: View,
    private val onClick: CountrySelectedCallback
) : CountryModelViewHolder(itemView) {

    override fun bind(c: CountryModel) {
        super.bind(c)
        itemView.setOnClickListener { onClick.onSelected(c) }
    }

}