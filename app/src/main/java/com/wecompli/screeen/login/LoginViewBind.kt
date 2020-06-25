package com.wecompli.screeen.login

import android.app.Activity
import android.view.View
import android.widget.*
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R

class LoginViewBind( activity: Activity?) : DeviceResolution(activity) {
    internal lateinit var view: View
    var activity: Activity? = null
    internal var img_toplogin: ImageView? = null
    internal lateinit var tv_login: TextView
    internal lateinit var tv_forgotpassword:TextView
    internal lateinit var tv_build_version:TextView
    internal lateinit var tv_language:TextView
    internal lateinit var username: EditText
    internal lateinit var et_pass:EditText
    internal var rl_loginbg: RelativeLayout? = null
    internal var rl_top:RelativeLayout? = null
    internal lateinit var btn_login: Button
    internal lateinit var chk_remember: CheckBox

    constructor( view: View?, loginActivity: LoginActivity) : this(loginActivity){
        this.view=view!!;
        this.activity=loginActivity
        viewbinds()
        settypeface()
    }

    private fun settypeface() {
        tv_login.typeface = getbebas(activity)
        btn_login.typeface = getbebas(activity)
        et_pass.typeface = getgothmlight(activity)
        username.typeface = getgothmlight(activity)
        chk_remember.typeface = getgothmlight(activity)
        tv_build_version.typeface = getgothmlight(activity)
        tv_forgotpassword.typeface = getgothmlight(activity)
        tv_language.typeface = getgothmbold(activity)
    }

    private fun viewbinds() {
        tv_login = view.findViewById(R.id.tv_logintop)
        username = view.findViewById(R.id.username)
        et_pass = view.findViewById(R.id.et_pass)
        tv_forgotpassword = view.findViewById(R.id.tv_forgotpassword)
        btn_login = view.findViewById(R.id.btn_login)
        chk_remember = view.findViewById(R.id.chk_remember)
        tv_build_version = view.findViewById(R.id.tv_build_version)
        chk_remember = view.findViewById(R.id.chk_remember)
        tv_language = view.findViewById(R.id.tv_language)

    }

}