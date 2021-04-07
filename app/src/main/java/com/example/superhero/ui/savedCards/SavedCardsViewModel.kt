package com.example.superhero.ui.savedCards


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.superhero.base.BaseViewModel
import com.example.superhero.data.models.superhero.SuperheroCard
import com.example.superhero.data.repository.Repository
import com.example.superhero.utils.Event
import com.example.superhero.utils.handleNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class SavedCardsViewModel : BaseViewModel() {

    private val _requestLogin = MutableLiveData<Event<Unit>>()
    val requestLogin: LiveData<Event<Unit>> get() = _requestLogin

    val userCards: LiveData<List<SuperheroCard>> =
        Repository.getLocalSavedCardsFlow()
            .map { list ->
                list.map { id ->
                    var card: SuperheroCard?
                    card = Repository.getLocalCardById(id)
                    if (card == null) {
                        showLoading()
                        card = Repository.getRemoteCardById(id)
                        hideLoading()
                    }
                    card
                }
            }.catch { error -> handleNetworkError(error) }
            .flowOn(Dispatchers.IO)
            .asLiveData(viewModelScope.coroutineContext)

    init {
        getSavedCards()
    }

    fun getSavedCards() = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (!Repository.checkSavedIdsValidity()) {
                showLoading()
                Repository.updateRemoteSavedCards()
            }
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
    }

    fun refresh() = viewModelScope.launch(Dispatchers.IO) {
        try {
            showLoading()
            Repository.updateRemoteSavedCards()
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
        getSavedCards()
    }

    override fun onUnauthorized() {
        _requestLogin.postValue(Event(Unit))
    }

}