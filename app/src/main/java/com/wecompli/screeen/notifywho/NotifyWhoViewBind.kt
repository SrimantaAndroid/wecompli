package com.wecompli.screeen.notifywho

import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R

class NotifyWhoViewBind(
   val notifyWhoActivity: NotifyWhoActivity,
   val view: View):DeviceResolution(notifyWhoActivity) {
    var tv_notifywho:TextView?=null
    var rl_back_notifywho:RelativeLayout?=null
    var tv_notifysubmit:TextView?=null
    var rv_notifywho:RecyclerView?=null
    init {
        tv_notifywho=view.findViewById(R.id.tv_notifywho)
        rv_notifywho=view.findViewById(R.id.rv_notifywho)
        rl_back_notifywho=view.findViewById(R.id.rl_back_notifywho)
        tv_notifysubmit=view.findViewById(R.id.tv_notifysubmit)
        tv_notifywho!!.setTypeface(getgothmlight(notifyWhoActivity))
        tv_notifysubmit!!.setTypeface(getbebas(notifyWhoActivity))
    }
}