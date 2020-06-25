package com.wecompli.screeen.checkminorfail

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R

class CheckMinorFailViewBind(
   val checkMinorfailActivity: CheckMinorfailActivity,
   val view: View) :DeviceResolution(checkMinorfailActivity){
    var tv_task_name: TextView?=null
    internal var tv_fault_managment: TextView?=null
    internal var tv_taskname: TextView?=null
    internal var tv_notifytask: TextView?=null
    internal var tv_submit: TextView?=null
    internal var tv_addimage1: TextView?=null
    internal var tv_addimage2: TextView?=null
    internal var tv_addimage3: TextView?=null
    internal var tv_addimage4: TextView?=null
    internal var et_fault: EditText?=null
    internal var rl_back_taskdetails: RelativeLayout?=null
    internal var rl_notifywho: RelativeLayout?=null
    internal var rl_submit: RelativeLayout?=null
    internal var img1: ImageView?=null
    internal var img2: ImageView?=null
    internal var img3: ImageView?=null
    internal var img4: ImageView?=null
    internal var fail_img_1: ImageView?=null
    internal var fail_img_4: ImageView?=null
    internal var fail_img_3: ImageView?=null
    internal var fail_img_2: ImageView?=null
    init {
        rl_back_taskdetails = view.findViewById(R.id.rl_back_taskdetails)
        rl_notifywho = view.findViewById(R.id.rl_notifywho)
        rl_submit = view.findViewById(R.id.rl_submit)
        et_fault = view.findViewById(R.id.et_fault)
        tv_task_name = view.findViewById(R.id.tv_task_name)
        tv_taskname = view.findViewById(R.id.tv_taskname)
        tv_notifytask = view.findViewById(R.id.tv_notifytask)
        tv_submit = view.findViewById(R.id.tv_submit)
        tv_addimage1 = view.findViewById(R.id.tv_addimage1)
        tv_addimage2 = view.findViewById(R.id.tv_addimage2)
        tv_addimage3 = view.findViewById(R.id.tv_addimage3)
        tv_addimage4 = view.findViewById(R.id.tv_addimage4)
        img1 = view.findViewById(R.id.img1)
        img2 = view.findViewById(R.id.img2)
        img3 = view.findViewById(R.id.img3)
        img4 = view.findViewById(R.id.img4)
        fail_img_1 = view.findViewById(R.id.fail_img_1)
        fail_img_4 = view.findViewById(R.id.fail_img_4)
        fail_img_3 = view.findViewById(R.id.fail_img_3)
        fail_img_2 = view.findViewById(R.id.fail_img_2)


        tv_fault_managment = view.findViewById(R.id.tv_fault_managment)
        tv_task_name!!.setTypeface(getgothmlight(checkMinorfailActivity))
        tv_fault_managment!!.setTypeface(getbebas(checkMinorfailActivity))
        et_fault!!.setTypeface(getgothmlight(checkMinorfailActivity))
        tv_taskname!!.setTypeface(getbebas(checkMinorfailActivity))
        tv_submit!!.setTypeface(getbebas(checkMinorfailActivity))
        tv_notifytask!!.setTypeface(getbebas(checkMinorfailActivity))
        tv_addimage1!!.setTypeface(getgothmlight(checkMinorfailActivity))
        tv_addimage2!!.setTypeface(getgothmlight(checkMinorfailActivity))
        tv_addimage3!!.setTypeface(getgothmlight(checkMinorfailActivity))
        tv_addimage4!!.setTypeface(getgothmlight(checkMinorfailActivity))
    }
}
