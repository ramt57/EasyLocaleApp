# EasyLocale 
Android Library for easy changing locale programmatically 

### Features
- Changes language in the runtime
- Persists the changes in SharedPreferences automatically
- Detects Right-To-Left (RTL) languages and updates layout direction

## Download
- Add the JitPack repository to your build file

  ```groovy 
  allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 
- Add the dependency
 ```groovy 
  implementation 'com.github.ramt57:EasyLocaleApp:v1.0.0'
  ```
    
## Solution
**(Option 1) Using base classes**
1. Extend your app class
```kotlin
class MyApplication : EasyLocaleApplication() {
}
```
2. Extend your base activity class
```kotlin
open class BaseActivity : EasyLocaleAppCompatActivity() {  
}
```
It's Done

**(Option 2) Using delegates**

If you don't want to extend from base classes.
1. On your custom Application class override `onAttach` and `onConfiguration` change methods.
```kotlin
class MyApp : Application() {  
    private val localeAppDelegate = EasyLocaleApplicationDelegates()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(localeAppDelegate.attachBaseContext(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeAppDelegate.onConfigurationChanged(this)
    } 
}
```
2. On your base activity class override `onAttach`
```kotlin
open class BaseActivity : AppCompatActivity() {  
    private val localeDelegate = EasyLocaleActivityDelegate()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(localeDelegate.attachBaseContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localeDelegate.onCreate(this)
    }

    override fun onResume() {
        super.onResume()
        localeDelegate.onResumed(this)
    }

    override fun onPause() {
        super.onPause()
        localeDelegate.onPaused()
    }
}
```
**Usage**
=
(Usage 1)

If you're using the base classes, just call `updateLocale(newLocale)`. It will then update the locale and restart the activity.

Example:
```kotlin 
btn_locale.setOnClickListener { updateLocale(Locales("ar") }
``` 

(Usage 2)

To change the locale you can call `setLocale` on the delegate
```kotlin 
localeDelegate.setLocale(this, newLocale)
``` 
The delegate will set the new locale and recreate the activity.

(Usage 3)

To get notified of `Locale` changes implement `EasyLocaleChangeListner`
```kotlin
localeDelegate.setLocaleChangeListner(object:EasyLocaleChangeListner{
  override fun onLocaleChanged(mOldLocale:Locale,mNewLocale:Locale){
    ...
  }
})
```
**Notes**
=
1. actionbar(toolbar) title should be set when `onCreate` is called.
```kotlin 
override fun onCreate(savedInstanceState: Bundle?) {
	super.onCreate(savedInstanceState)
	setContentView(R.layout.activity_main) //sample

	setTitle(R.string.main_activity_title) //sample
}
``` 
2. If your locale is Right-To-Left(RTL) don't forget to enable it in the `AndroidManifest.xml`
```xml
<application
	android:supportsRtl="true">
</application>
``` 
3. Google introduced a new App Bundle format to split apk files in smaller sizes when they’re being installed on the client devices. However, this means that we cannot have dynamic language changes in our applications.

To prevent that split for language files we need to add extra lines in our build.gradle file inside the app folder like below.
```groovy
android {
    //...
    //... removed for brevity
    bundle {
        language {
            enableSplit = false
        }
    }
}
```
or support bundle for only those locale that your app support
```groovy
  defaultConfig {
    ...

    resConfigs "en", "de"
  }
```
