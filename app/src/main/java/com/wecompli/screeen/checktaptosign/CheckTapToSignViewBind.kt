package com.wecompli.screeen.checktaptosign

import android.view.View
import android.widget.*
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R

class CheckTapToSignViewBind(
    val checkTapToSignActivity: CheckTapToSignActivity,
    val view: View):DeviceResolution(checkTapToSignActivity) {
    var tv_task_completedby: TextView?=null
    var tv_general_task_name:TextView?=null
    var tv_tap_to_sign:TextView?=null
    internal var rl_back_temptails: RelativeLayout?=null
    internal var tv_etarea:RelativeLayout?=null
    internal var btn_submit_input: Button?=null
    internal var et_input: EditText?=null
    var img_sign: ImageView?=null
    init {

        tv_task_completedby = view.findViewById(R.id.tv_task_completedby)
        tv_general_task_name = view.findViewById(R.id.tv_general_task_name)
        tv_tap_to_sign = view.findViewById(R.id.tv_tap_to_sign)
        rl_back_temptails = view.findViewById(R.id.rl_back_temptails)
        tv_etarea = view.findViewById(R.id.tv_etarea)
        btn_submit_input = view.findViewById(R.id.btn_submit_input)
        et_input = view.findViewById(R.id.et_input)
        img_sign = view.findViewById(R.id.img_sign)

        tv_general_task_name!!.setTypeface(getbebas(checkTapToSignActivity))
        et_input!!.setTypeface(getgothmlight(checkTapToSignActivity))
        btn_submit_input!!.setTypeface(getbebas(checkTapToSignActivity))
        tv_tap_to_sign!!.setTypeface(getgothmlight(checkTapToSignActivity))
        tv_task_completedby!!.setTypeface(getgothmbold(checkTapToSignActivity))
    }
}