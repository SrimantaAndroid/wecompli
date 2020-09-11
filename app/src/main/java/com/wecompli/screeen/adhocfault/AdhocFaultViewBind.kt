package com.wecompli.screeen.adhocfault

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R

class AdhocFaultViewBind(val adHocFaultActivity: AdHocFaultActivity,val view: View) :DeviceResolution(adHocFaultActivity){
    var rl_back_addhocfault: RelativeLayout? = null
    var rl_notifywho:android.widget.RelativeLayout? = null
    var rl_submit:android.widget.RelativeLayout? = null
    var tv_taskname: TextView? = null
    var tv_severty_level:android.widget.TextView? = null
    var tv_add_hoc_image1:android.widget.TextView? = null
    var tv_add_hocimage2:android.widget.TextView? = null
    var tv_add_hocimage3:android.widget.TextView? = null
    var tv_add_hoc_image4:android.widget.TextView? = null
    var tv_notifytask:android.widget.TextView? = null
    var tv_submit:android.widget.TextView? = null
    var et_adhocfault_note:android.widget.EditText? = null
    var img_img1: ImageView? = null
    var add_hoc_img_2:android.widget.ImageView? = null
    var adhoc_img_1:android.widget.ImageView? = null
    var img_img2:android.widget.ImageView? = null
    var img_img3:android.widget.ImageView? = null
    var img_img4:android.widget.ImageView? = null
    var adhoc_img_3:android.widget.ImageView? = null
    var adhoc_img_4:android.widget.ImageView? = null

    init {
        rl_back_addhocfault = view.findViewById(R.id.rl_back_addhocfault)
        img_img1 = view.findViewById(R.id.img_img1)
        add_hoc_img_2 = view.findViewById(R.id.add_hoc_img_2)
        adhoc_img_1 = view.findViewById(R.id.adhoc_img_1)
        img_img2 = view.findViewById(R.id.img_img2)
        img_img3 = view.findViewById(R.id.img_img3)
        img_img4 = view.findViewById(R.id.img_img4)
        img_img1 = view.findViewById(R.id.img_img1)
        adhoc_img_3 = view.findViewById(R.id.adhoc_img_3)
       // adhoc_img_1 = view.findViewById(R.id.adhoc_img_1)
        adhoc_img_4 = view.findViewById(R.id.adhoc_img_4)

        et_adhocfault_note = view.findViewById<EditText>(R.id.et_adhocfault_description)

        tv_taskname = view.findViewById(R.id.tv_taskname)
        tv_severty_level = view.findViewById(R.id.tv_severty_level)
        tv_add_hoc_image1 = view.findViewById(R.id.tv_add_hoc_image_1)
        tv_add_hocimage2 = view.findViewById(R.id.tv_add_hocimage_2)
        tv_add_hocimage3 = view.findViewById(R.id.tv_add_hocimage_3)

        tv_add_hoc_image4 = view.findViewById(R.id.tv_add_hoc_image_4)
        tv_notifytask = view.findViewById(R.id.tv_notifytask)
        tv_submit = view.findViewById(R.id.tv_submit)
        rl_back_addhocfault = view.findViewById(R.id.rl_back_addhocfault)
        rl_notifywho = view.findViewById(R.id.rl_notifywho)
        rl_submit = view.findViewById(R.id.rl_submit)




        et_adhocfault_note!!.setTypeface(getgothmlight(adHocFaultActivity))
        tv_submit!!.setTypeface(getbebas(adHocFaultActivity))
        tv_notifytask!!.setTypeface(getbebas(adHocFaultActivity))
        tv_taskname!!.setTypeface(getbebas(adHocFaultActivity))
        tv_add_hoc_image1!!.setTypeface(getgothmlight(adHocFaultActivity))
        tv_add_hocimage2!!.setTypeface(getgothmlight(adHocFaultActivity))
        tv_add_hocimage3!!.setTypeface(getgothmlight(adHocFaultActivity))
        tv_add_hoc_image4!!.setTypeface(getgothmlight(adHocFaultActivity))


    }
}