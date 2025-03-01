package com.sumaqada.vocabulary

import android.app.Application

class VocabularyApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}