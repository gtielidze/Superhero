package com.example.superhero.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superhero.data.models.superhero.SuperheroCard
import com.example.superhero.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//მოაქვს მონაცემები სერჩვიუდან, უზრუნველყობს ნეთვორქთან კავშირს
class SearchViewModel : ViewModel() {

    //_cards ქმნის ქარდების ლისტს, რომელსაც უყურებს ობსერვებლი და ანახლებს მონაცემებს, გარედან ვერ ვწვდებით
    private val _cards = MutableLiveData<List<SuperheroCard>>()
    val cards: LiveData<List<SuperheroCard>> get() = _cards

    val message = MutableLiveData<String>()

    //გამოძახების მერე, რექვესთი წამოიღების მონაცემებს (ქარდებს), მიენიჭება LiveData-ს
    fun onSearchTextChange(string: CharSequence?) {
        //თუ ცარიელია, _cards გადაეცემა ცარიელი მასივი და გასუფთავდება
        if (string.isNullOrEmpty()) _cards.postValue(emptyList())
        //თუ სიმბოლოების რაოდენობა სამზე ნაკლებია, არ მოძებნოს
        if (string?.length ?: 0 < 3) return
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                try {
                    val cards = Repository.getCardsByName(string = string.toString())
                    _cards.postValue(cards)
                    //ითვლის მოძებნილი ქარდების რაოდენობას
                    message.postValue("Count ${cards.size}")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}

