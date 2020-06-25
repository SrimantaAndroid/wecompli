package com.wecompli.utils.customalert

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.screeen.fixfault.FixFaultActivity

class CustomAlertForWorkType( val activity: FixFaultActivity) : Dialog(activity) {

    internal var deviceResolution: DeviceResolution?=null
    internal var tv_dialogtitle: TextView?=null
    internal var tv_repair:TextView?=null
    internal var tv_other:TextView?=null
    internal var tv_service:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCanceledOnTouchOutside(true)
        setContentView(R.layout.work_type_layout)
        deviceResolution=DeviceResolution(activity)
        tv_repair = findViewById(R.id.tv_repair)
        tv_other = findViewById(R.id.tv_other)
        tv_service = findViewById(R.id.tv_service)
        tv_dialogtitle = findViewById(R.id.tv_dialogtitle)
        tv_dialogtitle!!.setTypeface(deviceResolution!!.getgothmbold(activity))
        tv_repair!!.setTypeface(deviceResolution!!.getgothmlight(activity))
        tv_service!!.setTypeface(deviceResolution!!.getgothmlight(activity))
        tv_other!!.setTypeface(deviceResolution!!.getgothmlight(activity))

        tv_repair!!.setOnClickListener(View.OnClickListener {
            activity.fixFaultViewBind!!.tv_select_work.setText(
                activity.getResources().getString(R.string.repair1)
            )
            activity.worktype=activity.getResources().getString(R.string.repair1)
            dismiss()
        })

        tv_other!!.setOnClickListener(View.OnClickListener {
            activity.fixFaultViewBind!!.tv_select_work.setText(
                activity.getResources().getString(
                    R.string.Other
                )
            )
            activity.worktype=activity.getResources().getString(R.string.Other)
            dismiss()
        })

        tv_service!!.setOnClickListener(View.OnClickListener {
            activity!!.fixFaultViewBind!!.tv_select_work!!.setText(
                activity.getResources().getString(
                    R.string.Service
                )
            )
            activity.worktype=activity.getResources().getString(R.string.Service)
            dismiss()
        })

    }
}