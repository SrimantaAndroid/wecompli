package com.wecompli.screeen.totalfault

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import kotlinx.android.synthetic.main.activity_totalfault.view.*

class TotalFaultViewBind(
   val totalFaultActivity: TotalFaultActivity, val view: View) :DeviceResolution(totalFaultActivity){
    var img_back:ImageView?=null
    var tv_totalfault:TextView?=null
    var recview_totalfault:RecyclerView?=null
    init {
        img_back=view.findViewById(R.id.img_back)
        tv_totalfault=view.findViewById(R.id.tv_totalfault)

        recview_totalfault=view.findViewById(R.id.recview_totalfault)

        tv_totalfault!!.typeface=getgothmlight(totalFaultActivity)

    }
}