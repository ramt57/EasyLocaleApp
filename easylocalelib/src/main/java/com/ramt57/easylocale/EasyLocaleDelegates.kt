package com.ramt57.easylocale

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import androidx.core.text.layoutDirection
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.util.*

class EasyLocaleActivityDelegate : LifecycleObserver {
    private var locale: Locale = Locale.getDefault()
    private var localeChangeListner: EasyLocaleChangeListner? = null
    private lateinit var activity:Activity
    fun setLocale(activity: Activity, mLocale: Locale) {
        if (mLocale == Locale.getDefault()) {
            return
        }
        if (localeChangeListner != null) {
            localeChangeListner?.let { it.onLocaleChanged(locale, mLocale) }
        }
        EasyLocaleHelper.setEasylocale(activity, mLocale)
        locale = mLocale
        activity.recreate()
    }

    fun initialise(lifecycleOwner: LifecycleOwner,activity: Activity) {
        this.activity=activity
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.window.decorView.layoutDirection = Locale.getDefault().layoutDirection
        }
    }

    fun attachBaseContext(ctx: Context): Context {
        return EasyLocaleHelper.onAttachBaseContext(ctx)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        locale = Locale.getDefault()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (locale == Locale.getDefault())
            return
        else
            activity.recreate()
    }

    fun setLocaleChangeListner(listner: EasyLocaleChangeListner) {
        localeChangeListner = listner
    }
}

class EasyLocaleApplicationDelegates {
    fun attachBaseContext(ctx: Context): Context {
        return EasyLocaleHelper.onAttachBaseContext(ctx)
    }

    fun onConfigurationChanged(ctx: Context) {
        EasyLocaleHelper.onAttachBaseContext(ctx)
    }
}

interface EasyLocaleChangeListner {
    fun onLocaleChanged(mOldLocale: Locale, mNewLocale: Locale)
}
