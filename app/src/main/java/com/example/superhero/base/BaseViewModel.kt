package com.example.superhero.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.superhero.R
import com.example.superhero.utils.Event
import com.example.superhero.utils.UiErrorInterface


/**ViewModel მთავარი კლასი,  აქვს ფუნქცია onCleared, რომელიც ამთავრებს აპლიკაციის ყველა სასიცოცხლო ციკლს
აპლიკაციის ყველა ვიუმოდელის მშობელი კლასი*/
abstract class BaseViewModel : ViewModel(), UiErrorInterface {

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> get() = _loading

    private val _dialog = MutableLiveData<Event<DialogData>>()
    val dialog: LiveData<Event<DialogData>> get() = _dialog

    protected fun showLoading() = _loading.postValue(true)
    protected fun hideLoading() = _loading.postValue(false)

    protected fun showDialog(data: DialogData) = _dialog.postValue(Event(data))


    override fun onNoInternet() {
        showDialog(
            DialogData(
                title = R.string.common_error,
                messageRes = R.string.common_no_connection
            )
        )
    }

    override fun onServerError(message: String) {
        _dialog.postValue(
            Event(
                DialogData(
                    title = R.string.common_error,
                    message = message
                )
            )
        )
    }

    override fun onUnauthorized() {}

}