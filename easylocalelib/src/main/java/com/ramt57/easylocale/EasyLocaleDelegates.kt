package com.ramt57.easylocale

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import androidx.core.text.layoutDirection
import java.util.*

class EasyLocaleActivityDelegate {
    private var locale: Locale = Locale.getDefault()
    private var localeChangeListner:EasyLocaleChangeListner?=null
    fun setLocale(activity: Activity, mLocale: Locale) {
        if(mLocale== Locale.getDefault()){
            return
        }
        if(localeChangeListner!=null){
            localeChangeListner?.let {it.onLocaleChanged(locale,mLocale)}
        }
        EasyLocaleHelper.setEasylocale(activity, mLocale)
        locale = mLocale
        activity.recreate()
    }

    fun onCreate(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.window.decorView.layoutDirection = Locale.getDefault().layoutDirection
        }
    }

    fun attachBaseContext(ctx: Context): Context {
        return EasyLocaleHelper.onAttachBaseContext(ctx)
    }

    fun onPause() {
        locale = Locale.getDefault()
    }

    fun onResume(activity: Activity) {
        if (locale == Locale.getDefault())
            return
        else
            activity.recreate()
    }
    fun setLocaleChangeListner(listner: EasyLocaleChangeListner){
        localeChangeListner=listner
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
interface EasyLocaleChangeListner{
    fun onLocaleChanged(mOldLocale:Locale,mNewLocale:Locale)
}
