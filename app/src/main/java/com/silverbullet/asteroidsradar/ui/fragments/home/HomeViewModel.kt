package com.silverbullet.asteroidsradar.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverbullet.asteroidsradar.model.Asteroid
import com.silverbullet.asteroidsradar.model.ImageOfDayResponse
import com.silverbullet.asteroidsradar.repository.AsteroidsRepository
import com.silverbullet.asteroidsradar.repository.AsteroidsRepositoryImpl.RefreshContentEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AsteroidsRepository
) : ViewModel() {

    val imageOfTheDay: LiveData<ImageOfDayResponse?>
        get() = repository.imageOfTheDay

    val asteroidsList: LiveData<List<Asteroid>>
        get() = repository.asteroidsList

    private val _loadingState = MutableLiveData(false)
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private val _homeEvent = MutableLiveData<HomeEvent>(HomeEvent.IDLE)
    val homeEvent: LiveData<HomeEvent>
        get() = _homeEvent

    init {
        refreshContent()
    }

    /**
     * fires refreshing content function in repository
     * handles loading state of the current fragment
     */
    private fun refreshContent() {
        _loadingState.value = true
        viewModelScope.launch {
            val refreshContentEvent: RefreshContentEvent = repository.refreshContent()
            when (refreshContentEvent) {
                RefreshContentEvent.NoInternet -> _homeEvent.value =
                    HomeEvent.ShowErrorMessage("No Internet Connection")
                else -> {
                    // TODO: Handle the rest of Refresh events
                }
            }
            _loadingState.value = false
        }
    }

    /**
     * Resets the home event to idle to avoid sending old event after configuration change
     */
    fun doneHandleEvent(){
     _homeEvent.value = HomeEvent.IDLE
    }

    sealed class HomeEvent {
        class ShowErrorMessage(val message: String) : HomeEvent()
        object IDLE : HomeEvent()
    }
}