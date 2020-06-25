package com.wecompli.screeen.forgotpassword

import android.view.View
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.wecompli.R
import com.wecompli.utils.customalert.Alert

class ForgotPasswordOnClick : View.OnClickListener{
     var forGotPasswordActivity: ForGotPasswordActivity?=null
     var forgotPasswordViewBind: ForgotPasswordViewBind?=null
    constructor(forGotPasswordActivity: ForGotPasswordActivity, forgotPasswordBinding: ForgotPasswordViewBind){
        this.forGotPasswordActivity=forGotPasswordActivity
        this.forgotPasswordViewBind=forgotPasswordBinding
        setonclick()
    }

    private fun setonclick() {
        forgotPasswordViewBind!!.btn_forgotpass!!.setOnClickListener(this)
        forgotPasswordViewBind!!.tv_loginforgotpass!!.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_forgotpass->
                checkblank()
            R.id.tv_loginforgotpass->
                forGotPasswordActivity!!.finish()
        }

    }
    private fun checkblank() {
        if (!forgotPasswordViewBind!!.username!!.getText().toString().equals("")) {
            if (ConnectionDetector.isConnectingToInternet(forGotPasswordActivity))
                callApiforforgotpass()
            else
                Alert.showalert(forGotPasswordActivity!!, forGotPasswordActivity!!.getResources().getString(R.string.nointerner_connection))

        } else {
            Alert.showalert(
                forGotPasswordActivity!!, forGotPasswordActivity!!.getResources().getString(R.string.pleaseeneteremail))
        }
    }

    private fun callApiforforgotpass() {
        Alert.showalert(forGotPasswordActivity!!, forGotPasswordActivity!!.getResources().getString(R.string.underdevelopment))
    }
}