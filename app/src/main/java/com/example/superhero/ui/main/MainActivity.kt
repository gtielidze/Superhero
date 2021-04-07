package com.example.superhero.ui.main


import android.os.Bundle
import com.example.superhero.base.LanguageAwareActivity
import com.example.superhero.databinding.MainActivityBinding


//მთავარი აქტივიტი, აპლიკაციის კარი ;)
//by lazy <-- დელეგირებული პროპერტი, რომელიც ერთხელ ეშვება და მერე ინახება
//appCompactActivity-ს შვილი supportFragmentManager.beginTransaction() გვეხმარება ფრაგმენტების ამ კლასში გამოძახებაში
//commit() აკეთებს ტრანზაქციის გამოძახებას ასინქრონულად

class MainActivity : LanguageAwareActivity() {

    private val binding: MainActivityBinding by lazy { MainActivityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}




