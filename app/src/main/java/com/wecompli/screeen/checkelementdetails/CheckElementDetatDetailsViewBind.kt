package com.wecompli.screeen.checkelementdetails

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R

class CheckElementDetatDetailsViewBind(val activity: Activity?,val view: View) : DeviceResolution(activity) {
   var img_back_header:ImageView=view.findViewById(R.id.img_back_header)
    var tv_checksummery:TextView=view.findViewById(R.id.tv_checksummery)
    var recview_checkdetails:RecyclerView=view.findViewById(R.id.recview_checkdetails)
    init {
        tv_checksummery.setTypeface(getgothmlight(activity))

    }
}