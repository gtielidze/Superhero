package com.example.superhero.ui.profile


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.superhero.base.BaseViewModel
import com.example.superhero.data.models.user.UserProfile
import com.example.superhero.data.repository.Repository
import com.example.superhero.data.storage.DataStore
import com.example.superhero.utils.Event
import com.example.superhero.utils.handleNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class ProfileViewModel : BaseViewModel() {

    val userProfile: LiveData<UserProfile> =
        DataStore.db.getUserProfileDAO()
            .getUserProfile()
            .onEach {
                if (it == null) {
                    showLoading()
                    Repository.getRemoteAndSaveProfile()
                    hideLoading()
                }
            }.filter { it != null }
            .map { it!! }
            .catch {
                hideLoading()
                handleNetworkError(it)
            }.asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

    private val _loginRequired = MutableLiveData<Event<Unit>>()
    val loginRequired: LiveData<Event<Unit>> get() = _loginRequired


    fun getProfile() = viewModelScope.launch(Dispatchers.IO) {
        try {
            showLoading()
            Repository.getRemoteAndSaveProfile()
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
    }

    override fun onUnauthorized() {
        _loginRequired.postValue(Event(Unit))
    }
}