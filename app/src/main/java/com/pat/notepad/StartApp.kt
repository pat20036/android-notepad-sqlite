package com.pat.notepad

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class StartApp(): Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()

    }

    private fun initKoin()
    {
        startKoin {
            androidLogger()
            androidContext(this@StartApp)
            modules(mainModule)
        }
    }

}