package com.ramt57.easylocale
import android.content.Context
import android.content.pm.PackageManager.GET_META_DATA
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception
import java.util.*

open class EasyLocaleAppCompatActivity : AppCompatActivity() {
    private  val easyLocaleActivityDelegate=EasyLocaleActivityDelegate()
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(easyLocaleActivityDelegate.attachBaseContext(newBase!!))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        easyLocaleActivityDelegate.initialise(this,this)
    }
    fun setLocale(locale: Locale) {
        easyLocaleActivityDelegate.setLocale(this,locale)
    }
}