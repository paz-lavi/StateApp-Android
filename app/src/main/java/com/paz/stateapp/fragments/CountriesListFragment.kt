package com.paz.stateapp.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paz.stateapp.R
import com.paz.stateapp.adapters.CountryListAdapter
import com.paz.stateapp.callbacks.CountrySelectedCallback
import com.paz.stateapp.data_manager.DataManager
import com.paz.stateapp.databinding.FragmentCountriesListBinding

/** countries list fragment*/
class CountriesListFragment : Fragment() {
    private var _binding: FragmentCountriesListBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: CountrySelectedCallback
    private lateinit var _adapter: CountryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountriesListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCountries()
        onSwipe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** get all countries and add them to the list*/
    private fun getCountries() {
        DataManager.instance.apply {
            if (isDataReady()) { // if data already available
                binding.countriesLSTAll.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(activity)
                    _adapter = CountryListAdapter(getCountriesList(), listener)
                    adapter = _adapter
                }
                binding.countriesLAYLoading.visibility = GONE
            } else { // try again after 0.5 sec
                Handler(Looper.getMainLooper()).postDelayed({
                    getCountries()
                }, 500)
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CountrySelectedCallback) {
            listener = context

        } else {
            throw ClassCastException(
                "$context must implement CountrySelectedCallback."
            )
        }
    }

    /** set search bar*/
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)

        val item = menu.findItem(R.id.action_search);
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("onQueryTextSubmit", "query: $query")
                _adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                Log.d("onQueryTextChange", "query: $query")
                _adapter.filter.filter(query)
                return true
            }
        })

        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                _adapter.filter.filter("")
                return true
            }

            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }
        })

    }

    /** remove item from the list on a swipe*/
    private fun onSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                _adapter.removeItem(position)
                _adapter.notifyDataSetChanged()
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.countriesLSTAll)
    }
}