package com.wecompli.screeen.adhocfault

import android.content.Intent
import android.view.View
import com.wecompli.R
import com.wecompli.screeen.notifywho.NotifyWhoActivity
import com.wecompli.utils.customalert.Alert
import kotlinx.android.synthetic.main.activity_adhoc_fault.view.*

class AdHocFaultOnClick(val adHocFaultActivity: AdHocFaultActivity, val adhocFaultViewBind: AdhocFaultViewBind) :View.OnClickListener{
    init {
        adhocFaultViewBind.rl_back_addhocfault!!.setOnClickListener(this)
        adhocFaultViewBind.rl_notifywho!!.setOnClickListener(this)
        adhocFaultViewBind.rl_submit!!.setOnClickListener(this)
        adhocFaultViewBind.adhoc_img_1!!.setOnClickListener(this)
        adhocFaultViewBind.add_hoc_img_2!!.setOnClickListener(this)
        adhocFaultViewBind.adhoc_img_3!!.setOnClickListener(this)
        adhocFaultViewBind.adhoc_img_4!!.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
         R.id.adhoc_img_1->{
             adHocFaultActivity.Imagesellposition = 1
             adHocFaultActivity.imageView =  adhocFaultViewBind.adhoc_img_1!!
             Alert.showalertforImageSelectionAdhocfault(adHocFaultActivity);
         }
            R.id.add_hoc_img_2->{
                adHocFaultActivity.Imagesellposition = 2
                adHocFaultActivity.imageView =  adhocFaultViewBind.add_hoc_img_2!!
                Alert.showalertforImageSelectionAdhocfault(adHocFaultActivity);
            }
            R.id.adhoc_img_3->{
                adHocFaultActivity.Imagesellposition = 3
                adHocFaultActivity.imageView =  adhocFaultViewBind.adhoc_img_3!!
                Alert.showalertforImageSelectionAdhocfault(adHocFaultActivity);
            }
            R.id.adhoc_img_4->{
                adHocFaultActivity.Imagesellposition = 4
                adHocFaultActivity.imageView =  adhocFaultViewBind.adhoc_img_4!!
                Alert.showalertforImageSelectionAdhocfault(adHocFaultActivity);
            }
            R.id.rl_back_addhocfault->{
                adHocFaultActivity.finish()

            }
            R.id.rl_notifywho->{
                val intent= Intent(adHocFaultActivity, NotifyWhoActivity::class.java)
                adHocFaultActivity.startActivity(intent)
            }
            R.id.rl_submit->{
                if (!adhocFaultViewBind.et_adhocfault_note!!.text.toString().equals(""))
                    adHocFaultActivity.callApiforAdhocSubmitusingimage()
                else
                    Alert.showalert(adHocFaultActivity,"Add note")
            }
        }
    }
}