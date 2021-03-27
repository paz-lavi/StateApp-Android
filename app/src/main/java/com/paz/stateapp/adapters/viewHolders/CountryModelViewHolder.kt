package com.paz.stateapp.adapters.viewHolders

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.paz.stateapp.R
import com.paz.stateapp.model.CountryModel

/** Country info RecyclerView.ViewHolder  */
open class CountryModelViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.card_LBL_name)
    private val nativeName: TextView = itemView.findViewById(R.id.card_LBL_nativeName)
    private val flag: ImageView = itemView.findViewById(R.id.card_IMG_flag)

    open fun bind(c: CountryModel) {
        name.text = c.name
        nativeName.text = c.nativeName
        GlideToVectorYou.init().with(itemView.context).requestBuilder.transition(
            DrawableTransitionOptions.withCrossFade()
        )
            .apply(
                RequestOptions()
                    .centerCrop()
            ).load(Uri.parse(c.flag)).into(flag)
    }

}