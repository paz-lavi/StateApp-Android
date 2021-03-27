package com.paz.stateapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.paz.stateapp.R
import com.paz.stateapp.callbacks.CountrySelectedCallback
import com.paz.stateapp.fragments.CountriesListFragment
import com.paz.stateapp.fragments.CountryInfoFragment
import com.paz.stateapp.model.CountryModel


class MainActivity : AppCompatActivity(), CountrySelectedCallback {
    private lateinit var fragment: CountriesListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragment = CountriesListFragment()

        //show list on app started
        if (savedInstanceState == null) {
            showList()
        }

    }

    /** show countries list fragment*/
    private fun showList() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_FRG_fragment, fragment, "list")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .commit()
    }


    /**
     * check what is the current fragment. If the current is info fragment: replace fragment to the list. else exist the app ( super.onBackPressed() )*/
    override fun onBackPressed() {
        val f = supportFragmentManager.findFragmentByTag("info")
        if (f != null && f.isVisible) {
            showList()
        } else
            super.onBackPressed()
    }

    /**
     * callback to show a single country info fragment
     * @param country - the clicked country */
    override fun onSelected(country: CountryModel) {
        showInfo(country)

    }

    /**
     * show country info fragment
     * @param country - the country to show*/
    private fun showInfo(country: CountryModel) {
        supportFragmentManager
            .beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.main_FRG_fragment, CountryInfoFragment(country), "info")
            .commit()
    }


}