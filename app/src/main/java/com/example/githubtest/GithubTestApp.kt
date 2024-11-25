package com.example.githubtest

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

//import timber.log.Timber

class GithubTestApp: Application() {
    override fun onCreate() {
        super.onCreate()
//        Timber.plant(Timber.DebugTree())
        Napier.base(DebugAntilog())
    }
}