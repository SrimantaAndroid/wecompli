package com.wecompli.screeen.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wecompli.R
import com.wecompli.databinding.ActivityForgotPasswordBinding


class ForGotPasswordActivity:AppCompatActivity() {
    var forgotPasswordBinding:ForgotPasswordViewBind?=null
    var forgotPasswordOnClick:ForgotPasswordOnClick?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_forgot_password,null)
       // var forgotPasswordViewModel:ForgotPasswordViewModel =ViewModelProviders.of(this,ForgotpassModel()).get(ForgotpassModel::class)
      //  val forgotbinding:ActivityForgotPasswordBinding= DataBindingUtil.setContentView(this,R.layout.activity_forgot_password)
      //  var forgotpassModel: ForgotpassModel? =null
      //  var forgotPasswordViewModel=ForgotPasswordViewModel(this,forgotpassModel!!)
        //forgotbinding.forgotviewmodel=forgotpassModel!!
      //  val forgotPasswordBinding:ActivityForgotPasswordBinding=DataBindingUtil.setContentView(this,R.layout.activity_forgot_password)
       forgotPasswordBinding= ForgotPasswordViewBind(this,view)
        forgotPasswordOnClick=ForgotPasswordOnClick(this,forgotPasswordBinding!!)
        setContentView(view)
    }
}