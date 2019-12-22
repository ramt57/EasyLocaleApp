package com.ramt57.easylocale
import android.content.Context
import android.content.pm.PackageManager.GET_META_DATA
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception
import java.util.*

open class EasyLocaleAppCompatActivity : AppCompatActivity() {
    private val easyLocaleActivityDelegate = EasyLocaleActivityDelegate()
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(easyLocaleActivityDelegate.attachBaseContext(newBase!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        easyLocaleActivityDelegate.onCreate(this)
        resetTitle()
    }

    override fun onResume() {
        super.onResume()
        easyLocaleActivityDelegate.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        easyLocaleActivityDelegate.onPause()
    }

    fun updateLocale(locale: Locale) {
        easyLocaleActivityDelegate.setLocale(this,locale)
    }
    private fun resetTitle(){
        try {
            val label=packageManager.getActivityInfo(componentName,GET_META_DATA).labelRes
            if(label!=0){
                setTitle(label)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}