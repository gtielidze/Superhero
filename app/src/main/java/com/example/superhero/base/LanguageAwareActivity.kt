package com.example.superhero.base
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import com.example.superhero.R
import com.example.superhero.data.storage.DataStore
import com.example.superhero.utils.updateLocale

abstract class LanguageAwareActivity : AppCompatActivity() {

    private lateinit var loadingView: View

    private lateinit var contentView: ContentFrameLayout

    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Superhero)
        contentView = findViewById<ContentFrameLayout>(android.R.id.content)
        loadingView = layoutInflater.inflate(R.layout.dialog_loading, contentView, false)
    }
    //ენის კონტექსტის გადატვირთვა
    override fun attachBaseContext(newBase: Context?) {
        val newLangContext = newBase?.let { updateLocale(it, DataStore.language) }
        super.attachBaseContext(newLangContext)
    }

    protected fun showDialog(@StringRes title: Int, @StringRes message: Int) {
        showDialog(title, getString(message))
    }

    /**მომხმარებელთან გამოაქვს დიალოგი
    ინტერნეტის კავშირთან დაკავშირებულ პრობლემებზე.
    @String <-- ანოტაცია სტრინგის რესურსთან დაკავშირებით
    setTitle(title) <-- მესიჯის სათაური/ setMessage(message)<-- მესიჯის შინაარსი*
    setNeutralButton<-- ok ბათონი. შესაძლებელია დაქენსელება*/

    protected fun showDialog(@StringRes title: Int, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(
                R.string.common_Ok
            ) { dialog, _ -> dialog.dismiss() }
            .setCancelable(true)
            .show()
    }

    /**ქმნის დიალოგის ინსტანს და გამოაქვს ეკრანზე*/
    fun showLoading() {
        if (loading) return
        contentView.addView(loadingView)
        loading = true
    }


    fun hideLoading() {
        if (!loading) return
        contentView.removeView(loadingView)
        loading = false
    }


}