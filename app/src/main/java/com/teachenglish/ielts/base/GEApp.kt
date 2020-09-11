package com.teachenglish.ielts.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.teachenglish.ielts.di.module.appModule
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GEApp : Application(){

    companion object{
        val eventBus: PublishSubject<HashMap<String, Any>> by lazy { PublishSubject.create<HashMap<String, Any>>() }
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        context = applicationContext
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@GEApp)
            modules(appModule)
        }
    }
}