package pl.ozodbek.millenium

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _isDrawerOpen = MutableLiveData<Boolean>()
    val isDrawerOpen: LiveData<Boolean> = _isDrawerOpen

    fun toggleDrawer(isOpen: Boolean) {
        _isDrawerOpen.value = isOpen
    }
}
