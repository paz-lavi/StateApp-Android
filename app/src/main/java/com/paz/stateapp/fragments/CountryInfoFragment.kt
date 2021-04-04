package com.paz.stateapp.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.gson.Gson
import com.paz.stateapp.adapters.BorderListAdapter
import com.paz.stateapp.data_manager.DataManager
import com.paz.stateapp.databinding.FragmentCountryInfoBinding
import com.paz.stateapp.model.CountryModel

/** Country info fragment*/
class CountryInfoFragment() : Fragment() {
    private var _binding: FragmentCountryInfoBinding? = null
    private val binding get() = _binding!!
    private var country: CountryModel? = null

    constructor(country: CountryModel) : this() {
        this.country = country
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryInfoBinding.inflate(inflater, container, false)
        savedInstanceState.let {
            if (it != null) { //restore the country from bundle
                this.country = Gson().fromJson(it.getString("country"), CountryModel::class.java)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHeaders()
        getBorders()
    }

    /** set the country data as a headers*/
    private fun setHeaders() {
        binding.infoLBLName.text = country?.name
        binding.infoLBLNativeName.text = country?.nativeName
        GlideToVectorYou.init().with(binding.infoIMGFlag.context).requestBuilder.transition(
            DrawableTransitionOptions.withCrossFade()
        )
            .apply(
                RequestOptions()
                    .centerCrop()
            ).load(Uri.parse(country?.flag)).into(binding.infoIMGFlag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** Find all the countries bordering the country and show them*/
    private fun getBorders() {
        DataManager.getCountriesList()?.let {
            if (country != null) { // if data already available
                binding.infoLSTBorders.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(activity)
                    adapter = BorderListAdapter(it.filter { countryModel ->
                        countryModel?.alpha3Code in country!!.borders
                    } as ArrayList<CountryModel?>)
                }
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("country", Gson().toJson(country))
    }

}