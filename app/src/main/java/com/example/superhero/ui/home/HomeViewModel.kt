package com.example.superhero.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.superhero.R
import com.example.superhero.base.BaseViewModel
import com.example.superhero.base.DialogData
import com.example.superhero.data.models.superhero.SuperheroCard
import com.example.superhero.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel : BaseViewModel() {

    private val _items = MutableLiveData<List<SuperheroCard>>()
    val items: LiveData<List<SuperheroCard>> get() = _items

    private val _loadingMore = MutableLiveData(false)
    val loadingMore: LiveData<Boolean> get() = _loadingMore

    private var noMoreData = false

    private var offset = 1

    init {
        loadMore()
    }

    fun onScrollEndReached() {
        if (loadingMore.value == true || noMoreData) return
        loadMore()
    }

    fun onRefresh() {
        offset = 1
        _items.postValue(emptyList())
        loadMore()
    }

    //უზრუნველყობს სქროლის მართვას და მონაცემების ჩატვირთვას.
    private fun loadMore() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadingMore.postValue(true)
                val cards = Repository.getRemoteCardsCardsAndStore(
                    offset = offset,
                    pageSize = PAGE_SIZE
                )
                noMoreData = cards.isLast
                offset++
                _items.postValue((_items.value ?: emptyList()) + cards.content)
            } catch (e: Exception) {
                showDialog(DialogData(title = R.string.common_error, message = e.message ?: ""))
            } finally {
                _loadingMore.postValue(false)

            }
        }
    }


    companion object {
        const val PAGE_SIZE = 700
    }
}