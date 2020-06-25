package com.wecompli.screeen.forgotpassword

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R

class ForgotPasswordViewBind(activity: Activity?) : DeviceResolution(activity) {
    var forGotPasswordActivity:ForGotPasswordActivity?=null
    var view:View?=null
     var username: EditText?=null
     var btn_forgotpass: Button?=null
     var tv_loginforgotpass: TextView?=null
     var tv_login:TextView?=null

    constructor(activity: ForGotPasswordActivity, view: View) : this(activity){
        this.forGotPasswordActivity=activity
        this.view=view
        viewbinds()
        settypeface()
    }

    private fun settypeface() {
        tv_loginforgotpass!!.setTypeface(getgothmlight(forGotPasswordActivity))
        btn_forgotpass!!.setTypeface(getbebas(forGotPasswordActivity))
        tv_login!!.setTypeface(getbebas(forGotPasswordActivity))
        username!!.setTypeface(getgothmlight(forGotPasswordActivity))
    }

    private fun viewbinds() {
        tv_loginforgotpass = view!!.findViewById(R.id.tv_loginforgotpass)
        btn_forgotpass = view!!.findViewById(R.id.btn_forgotpass)
        username = view!!.findViewById(R.id.username)
        tv_login = view!!.findViewById(R.id.tv_login)
    }

}