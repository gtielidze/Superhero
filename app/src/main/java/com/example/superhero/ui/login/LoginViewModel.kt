package com.example.superhero.ui.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.superhero.R
import com.example.superhero.base.BaseViewModel
import com.example.superhero.data.repository.Repository
import com.example.superhero.data.storage.DataStore
import com.example.superhero.utils.Event
import com.example.superhero.utils.handleNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginViewModel : BaseViewModel() {

    private val _inputError = MutableLiveData<Int>()
    val inputError: LiveData<Int> get() = _inputError

    private val _loginSuccess = MutableLiveData<Event<Unit>>()
    val loginSuccess: LiveData<Event<Unit>> get() = _loginSuccess

    private val _loginFlowFinished = MutableLiveData<Event<Boolean>>()
    val loginFlowFinished: LiveData<Event<Boolean>> get() = _loginFlowFinished


    fun login(username: CharSequence?, password: CharSequence?) = viewModelScope.launch {
        withContext(Dispatchers.IO) { clearData() }
        if (username.isNullOrBlank() || password.isNullOrBlank()) {
            _inputError.postValue(R.string.login_enter_username)
            return@launch
        }
        showLoading()
        try {
            withContext(Dispatchers.IO) {
                Repository.loginAndSetToken(
                    username = username.toString(),
                    password = password.toString()
                )
            }
            _loginSuccess.postValue(Event(Unit))
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
    }

    private suspend fun clearData() {
        DataStore.authToken = null
        Repository.clearSavedCards()
        Repository.invalidateSavedIds()
        Repository.clearProfile()
    }

    fun logOut() = viewModelScope.launch(Dispatchers.IO) {
        clearData()
    }

    internal fun loginFragmentDestroyed() {
        _loginFlowFinished.postValue(Event(!DataStore.authToken.isNullOrBlank()))
    }

    internal fun loginFragmentStarted() {
        DataStore.authToken = null
    }

    override fun onUnauthorized() {
        _inputError.postValue(R.string.login_screen_wrong_credentials)
    }

}