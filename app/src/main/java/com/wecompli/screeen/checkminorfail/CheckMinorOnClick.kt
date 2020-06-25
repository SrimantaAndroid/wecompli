package com.wecompli.screeen.checkminorfail

import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.wecompli.R
import com.wecompli.screeen.notifywho.NotifyWhoActivity
import com.wecompli.utils.customalert.Alert

class CheckMinorOnClick(
    val checkMinorfailActivity: CheckMinorfailActivity,
    val checkMinorFailViewBind: CheckMinorFailViewBind) :View.OnClickListener{
    init {
        checkMinorFailViewBind.rl_back_taskdetails!!.setOnClickListener(this)
        checkMinorFailViewBind.rl_notifywho!!.setOnClickListener(this)
        checkMinorFailViewBind.rl_submit!!.setOnClickListener(this)
        checkMinorFailViewBind.fail_img_1!!.setOnClickListener(this)
        checkMinorFailViewBind.fail_img_2!!.setOnClickListener(this)
        checkMinorFailViewBind.fail_img_3!!.setOnClickListener(this)
        checkMinorFailViewBind.fail_img_4!!.setOnClickListener(this)
        checkMinorFailViewBind.rl_submit!!.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.fail_img_1->{
                checkMinorfailActivity.Imagesellposition = 1
                checkMinorfailActivity.imageView =  checkMinorFailViewBind.fail_img_1!!
                Alert.showalertforImageSelectio(checkMinorfailActivity);
            }
            R.id.fail_img_2->{
                checkMinorfailActivity.Imagesellposition = 2
                checkMinorfailActivity.imageView =  checkMinorFailViewBind.fail_img_2!!
                Alert.showalertforImageSelectio(checkMinorfailActivity);
            }
            R.id.fail_img_3->{
                checkMinorfailActivity.Imagesellposition = 3
                checkMinorfailActivity.imageView =  checkMinorFailViewBind.fail_img_3!!
                Alert.showalertforImageSelectio(checkMinorfailActivity);
            }
            R.id.fail_img_4->{
                checkMinorfailActivity.Imagesellposition = 4
                checkMinorfailActivity.imageView =  checkMinorFailViewBind.fail_img_4!!
                Alert.showalertforImageSelectio(checkMinorfailActivity);
            }
            R.id.rl_back_taskdetails->{
                checkMinorfailActivity.finish()
            }
            R.id.rl_notifywho->{
                val intent= Intent(checkMinorfailActivity, NotifyWhoActivity::class.java)
                checkMinorfailActivity.startActivity(intent)
            }
            R.id.rl_submit->{
                if (!checkMinorFailViewBind.et_fault!!.text.toString().equals(""))
                   checkMinorfailActivity.callApiforCheckSubmitusingimage()
                else
                    Alert.showalert(checkMinorfailActivity,"Enter Additional information")
            }
        }
    }
}