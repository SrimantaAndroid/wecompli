package com.wecompli.screeen.checkteaprature

import android.view.View
import com.wecompli.R
import com.wecompli.utils.customalert.Alert

class CheckTemparatureOnClick(
   val checkTempuratureActivity: CheckTempuratureActivity,
    val checkTemparatureViewBind: CheckTemparatureViewBind) : View.OnClickListener{
    init {
        checkTemparatureViewBind.btn_submit_input!!.setOnClickListener(this)
        checkTemparatureViewBind.rl_back_temptails!!.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_submit_input->{

                if (!checkTemparatureViewBind.et_input!!.text.toString().equals(""))
                    checkTempuratureActivity.callApifortemp()
                else
                    Alert.showalert(checkTempuratureActivity,checkTempuratureActivity.getResources().getString(R.string.pleaseenetrteamp))

            }
            R.id.rl_back_temptails->{
                checkTempuratureActivity.finish()
            }
        }

    }
}