package com.wecompli.screeen.login

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.wecompli.R
import com.wecompli.screeen.intropages.InterPagesActivity
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import java.util.*

class LoginActivity : AppCompatActivity() {
    lateinit var loginViewBind: LoginViewBind
    lateinit var loginOnClick: LoginOnClick
    internal var MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 344
    var IMEINumber: String=""
    var softwareVersion: Int = 0
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(this).inflate(R.layout.activity_login, null)
        setContentView(view)
        loginViewBind = LoginViewBind(view, this)
        loginOnClick = LoginOnClick(loginViewBind, this)
        setversionnumber()
        setusernameandpasswordifchecked()
        setselctedlanguage()
        loadIMEI()
    }


    private fun setusernameandpasswordifchecked() {
        if (AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.iS_Checked_login) != null &&
            AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.iS_Checked_login) == "1") {

            loginViewBind.username!!.setText(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.username_key))
            loginViewBind.et_pass.setText(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.password_key))
            loginViewBind.chk_remember.isChecked = true

        }
    }

    private fun setversionnumber() {
        try {
            var pinfo: PackageInfo? = null
            pinfo = packageManager.getPackageInfo(packageName, 0)
            // int versionNumber = pinfo.versionCode;
            val versionName = pinfo!!.versionName
            loginViewBind.tv_build_version.text = resources.getString(R.string.version) + " " + versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun setselctedlanguage() {
        if (AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.selectedlanagage) == resources.getString(R.string.english_code)) {
            loginViewBind.tv_language.text = resources.getString(R.string.english)
            //  changelanguage(getResources().getString(R.string.english_code));
        } else if (AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.selectedlanagage) == resources.getString(
                R.string.german_code)) {
            loginViewBind.tv_language.text = resources.getString(R.string.german)
            //changelanguage(getResources().getString(R.string.german_code));
        } else if (AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.selectedlanagage) == resources.getString(
                R.string.dutch_code)) {
            loginViewBind.tv_language.text = resources.getString(R.string.dutch)
            //changelanguage(getResources().getString(R.string.dutch_code));
        } else if (AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.selectedlanagage) == resources.getString(
                R.string.spanish_code)) {
            loginViewBind.tv_language.text = resources.getString(R.string.spnish)
            // changelanguage(getResources().getString(R.string.spanish_code));
        } else {
            loginViewBind.tv_language.text = resources.getString(R.string.english)
            // changelanguage(getResources().getString(R.string.english_code));
        }
    }

    fun loadIMEI() {
        // Check if the READ_PHONE_STATE permission is already available.
        try {
            if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) !== PackageManager.PERMISSION_GRANTED) {
                requestReadPhoneStatePermission()
            } else {
                doPermissionGrantedStuffs()
                requestPermission()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // READ_PHONE_STATE permission has been granted, proceed with displaying IMEI Number
                //alertAlert(getString(R.string.permision_available_read_phone_state));
                doPermissionGrantedStuffs()
                requestPermission()
            } else {
                alertAlert(getString(R.string.permissions_not_granted_read_phone_state))
                requestPermission()
            }
        }
    }

    private fun alertAlert(msg: String) {
        AlertDialog.Builder(this@LoginActivity)
            .setTitle(resources.getString(R.string.permession_request))
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(
                android.R.string.yes
            ) { dialog, which ->
                // do somthing here
            }.show()
    }

    private fun requestPermission() {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this@LoginActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                val alertBuilder = android.app.AlertDialog.Builder(this@LoginActivity)
                alertBuilder.setCancelable(true)
                alertBuilder.setTitle(resources.getString(R.string.permessionnecessay))
                alertBuilder.setMessage(resources.getString(R.string.externalstoragepermession))
                alertBuilder.setPositiveButton(
                    android.R.string.yes,
                    object : DialogInterface.OnClickListener {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            ActivityCompat.requestPermissions(
                                this@LoginActivity as Activity,
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                            )
                        }
                    })
                val alert = alertBuilder.create()
                alert.show()
            } else {
                ActivityCompat.requestPermissions(
                    this@LoginActivity,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun doPermissionGrantedStuffs() {
        //Have an  object of TelephonyManager
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        //Get IMEI Number of Phone  //////////////// for this example i only need the IMEI
        IMEINumber = tm.deviceId
        /* androidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);*/
        //  Get the device software version
        //  softwareVersion=tm.getDeviceSoftwareVersion();
        softwareVersion = android.os.Build.VERSION.SDK_INT
        /************************************************
         * **********************************************
         * This is just an icing on the cake
         * the following are other children of TELEPHONY_SERVICE
         *
         * //Get Subscriber ID
         * String subscriberID=tm.getDeviceId();
         *
         * //Get SIM Serial Number
         * String SIMSerialNumber=tm.getSimSerialNumber();
         *
         * //Get Network Country ISO Code
         * String networkCountryISO=tm.getNetworkCountryIso();
         *
         * //Get SIM Country ISO Code
         * String SIMCountryISO=tm.getSimCountryIso();
         *
         * Get the device software version
         * String softwareVersion=tm.getDeviceSoftwareVersion()
         *
         * //Get the Voice mail number
         * String voiceMailNumber=tm.getVoiceMailNumber();
         *
         *
         * //Get the Phone Type CDMA/GSM/NONE
         * int phoneType=tm.getPhoneType();
         *
         * switch (phoneType)
         * {
         * case (TelephonyManager.PHONE_TYPE_CDMA):
         * // your code
         * break;
         * case (TelephonyManager.PHONE_TYPE_GSM)
         * // your code
         * break;
         * case (TelephonyManager.PHONE_TYPE_NONE):
         * // your code
         * break;
         * }
         *
         * //Find whether the Phone is in Roaming, returns true if in roaming
         * boolean isRoaming=tm.isNetworkRoaming();
         * if(isRoaming)
         * phoneDetails+="\nIs In Roaming : "+"YES";
         * else
         * phoneDetails+="\nIs In Roaming : "+"NO";
         * //Get the SIM state
         * int SIMState=tm.getSimState();
         * switch(SIMState)
         * {
         * case TelephonyManager.SIM_STATE_ABSENT :
         * // your code
         * break;
         * case TelephonyManager.SIM_STATE_NETWORK_LOCKED :
         * // your code
         * break;
         * case TelephonyManager.SIM_STATE_PIN_REQUIRED :
         * // your code
         * break;
         * case TelephonyManager.SIM_STATE_PUK_REQUIRED :
         * // your code
         * break;
         * case TelephonyManager.SIM_STATE_READY :
         * // your code
         * break;
         * case TelephonyManager.SIM_STATE_UNKNOWN :
         * // your code
         * break;
         *
         * }
         */
        // Now read the desired content to a textview.
    }

    private fun requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            AlertDialog.Builder(this)
                .setTitle(resources.getString(R.string.permession_request))
                .setMessage(getString(R.string.permission_read_phone_state_rationale))
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes,
                    DialogInterface.OnClickListener { dialog, which ->
                        //re-request
                        ActivityCompat.requestPermissions(
                            this@LoginActivity, arrayOf(Manifest.permission.READ_PHONE_STATE),
                            MY_PERMISSIONS_REQUEST_READ_PHONE_STATE
                        )
                    })
                .show()
        } else {
            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_PHONE_STATE),
                MY_PERMISSIONS_REQUEST_READ_PHONE_STATE
            )
        }
    }

    fun changelanguage(language: String) {
        val myLocale = Locale(language)
        val res = getResources()
        val dm = res.getDisplayMetrics()
        val conf = res.getConfiguration()
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(this, InterPagesActivity::class.java)
        startActivity(refresh)
        finish()

    }

    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else capitalize(manufacturer) + " " + model
    }

    private fun capitalize(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true

        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c))
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }

        return phrase.toString()
    }

}