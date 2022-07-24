package com.silverbullet.asteroidsradar.ui.fragments.home

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.silverbullet.asteroidsradar.R
import com.silverbullet.asteroidsradar.adapters.HomeListAdapter
import com.silverbullet.asteroidsradar.databinding.HomeFragmentBinding
import com.silverbullet.asteroidsradar.model.ImageOfDayResponse
import com.silverbullet.asteroidsradar.ui.fragments.home.HomeViewModel.HomeEvent

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var homeListAdapter: HomeListAdapter
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeListAdapter = HomeListAdapter()
        homeListAdapter.setAsteroidItemClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAsteroidDataFragment(it)
            )
        }
        binding.asteroidsList.adapter = homeListAdapter
        binding.asteroidsList.adapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        setupObservers()
        activity?.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.filter_menu,menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                homeListAdapter.applyFilter(menuItem.itemId)
                return true
            }
        }, viewLifecycleOwner)
    }

    /**
     * Creates observers for live data events in Home ViewModel
     */
    private fun setupObservers() {
        homeViewModel.loadingState.observe(
            viewLifecycleOwner,
            Observer { isLoading -> handleLoadingState(isLoading) })
        homeViewModel.homeEvent.observe(
            viewLifecycleOwner,
            Observer { event -> handleHomeEvent(event) }
        )
        homeViewModel.asteroidsList.observe(
            viewLifecycleOwner,
            Observer { newAsteroidsList -> homeListAdapter.addNewList(newAsteroidsList) })
        homeViewModel.imageOfTheDay.observe(
            viewLifecycleOwner,
            Observer { imageOfTheDay -> showImageOfTheDay(imageOfTheDay) })
    }

    private fun handleLoadingState(isLoading: Boolean) {
        if (isLoading) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.INVISIBLE
        }
    }

    private fun handleHomeEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ShowErrorMessage -> {
                Snackbar.make(view!!, event.message, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.RED)
                    .show()
                homeViewModel.doneHandleEvent()
            }
            HomeEvent.IDLE -> {}
        }
    }

    private fun showImageOfTheDay(imageOfTheDay: ImageOfDayResponse?) {
        if (imageOfTheDay != null) {
            binding.imageOfDayView.apply {
                contentDescription = imageOfTheDay.title
                Glide.with(this).load(imageOfTheDay.url).into(this)
            }
        }
    }
}