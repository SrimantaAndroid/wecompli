package com.wecompli.screeen.checkteaprature

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R

class CheckTemparatureViewBind(
    checkTempuratureActivity: CheckTempuratureActivity,
    view: View
):DeviceResolution(checkTempuratureActivity) {
    var et_input: EditText?=null
    var btn_submit_input: Button?=null
    var rl_back_temptails: RelativeLayout?=null
    var tv_tesk_description: TextView?=null
    var tv_general_task_name: TextView?=null
    var tv_task: TextView?=null

    init {
        et_input=view.findViewById(R.id.et_input)
        btn_submit_input=view.findViewById(R.id.btn_submit_input)
        rl_back_temptails=view.findViewById(R.id.rl_back_temptails)
        tv_tesk_description=view.findViewById(R.id.tv_tesk_description)
        tv_general_task_name=view.findViewById(R.id.tv_general_task_name)
        tv_task=view.findViewById(R.id.tv_task)


        btn_submit_input!!.setTypeface(getbebas(checkTempuratureActivity))
        tv_general_task_name!!.setTypeface(getgothamthin(checkTempuratureActivity))
        tv_tesk_description!!.setTypeface(getgothamthin(checkTempuratureActivity))
        et_input!!.setTypeface(getgothmlight(checkTempuratureActivity))
        tv_task!!.setTypeface(getbebas(checkTempuratureActivity))
    }
}