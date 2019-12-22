package com.ramt57.easylocaleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ramt57.easylocale.EasyLocaleAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : EasyLocaleAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.app_name)
        button.setOnClickListener { updateLocale(Locale("en")) }
        button2.setOnClickListener { updateLocale(Locale("ar")) }
    }
}
