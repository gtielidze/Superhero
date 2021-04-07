package com.example.superhero.app

import android.app.Application
import android.content.Context
import com.example.superhero.data.storage.DataStore


class SuperheroApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DataStore.initialize(this, getSharedPreferences("_sp_", Context.MODE_PRIVATE))
    }

}