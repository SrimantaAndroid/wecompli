package com.wecompli.screeen.totalfault

import android.view.View
import com.wecompli.R
import kotlinx.android.synthetic.main.activity_totalfault.view.*

class TotalFaultOnClick(
   val totalFaultActivity: TotalFaultActivity,
    val totalFaultViewBind: TotalFaultViewBind): View.OnClickListener {
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.img_back->{
                totalFaultActivity.finish()
            }
        }
    }

    init {
        totalFaultViewBind.img_back!!.setOnClickListener(this)
    }
}