package com.devhassan.dotpay

import android.app.Application
import androidx.viewbinding.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DotPay : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimberLog()
    }

    private fun initTimberLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}