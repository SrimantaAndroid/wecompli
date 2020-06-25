package com.wecompli.screeen.checktaptosign

import android.content.Intent
import android.view.View
import com.wecompli.R
import com.wecompli.utils.ApplicationConstant
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.customalert.TapToSignDialog

class CheckTaptoSignOnClick(
    val checkTapToSignActivity: CheckTapToSignActivity,
    val tapToSignViewBind: CheckTapToSignViewBind) : View.OnClickListener {
    init {
        tapToSignViewBind.btn_submit_input!!.setOnClickListener(this)
        tapToSignViewBind.tv_tap_to_sign!!.setOnClickListener(this)
        tapToSignViewBind.img_sign!!.setOnClickListener(this)
        tapToSignViewBind.rl_back_temptails!!.setOnClickListener(this)

    }
    override fun onClick(p0: View?) {
      when(p0!!.id){
          R.id.tv_tap_to_sign->{
              TapToSignDialog(checkTapToSignActivity).show()
          }
          R.id.btn_submit_input->{
              if (!tapToSignViewBind.et_input!!.text.toString().equals("")) {
                  if (checkTapToSignActivity.imagesignAvaliable!!)
                      checkTapToSignActivity.isWriteStoragePermissionGranted()
                  else
                      Alert.showalert(checkTapToSignActivity!!,checkTapToSignActivity.getString(R.string.signinhere))

              }else
                  Alert.showalert(checkTapToSignActivity!!,checkTapToSignActivity.getString(R.string.enter_patient_name))
          }
          R.id.img_sign->{
              TapToSignDialog(checkTapToSignActivity).show()
          }
          R.id.rl_back_temptails->{
              checkTapToSignActivity.finish()
          }


      }

    }
}