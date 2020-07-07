package com.wecompli.screeen.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.wecompli.R
import com.wecompli.screeen.home.HomeActivity
import com.wecompli.screeen.intropages.InterPagesActivity
import com.wecompli.screeen.login.LoginActivity
import com.wecompli.utils.ApplicationConstant
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent

class SplashActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        AppSheardPreference(this).setvalue_in_preference(PreferenceConstent.UserSite,"")
        Handler().postDelayed({
            if (!AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.showintropage).equals("1")) {
                val intro = Intent(this, InterPagesActivity::class.java)
                startActivity(intro)
                finish()
            }else{
                if (AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.iS_LOGIN).equals("1")) {
                    val homeIntent = Intent(this@SplashActivity, HomeActivity::class.java)
                    startActivity(homeIntent)
                    finish()

                } else {
                    val mainIntent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }
            }
        }, ApplicationConstant.SPLASH_DISPLAY_LENGTH.toLong())
    }
}