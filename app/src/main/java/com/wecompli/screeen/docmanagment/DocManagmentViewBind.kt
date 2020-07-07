package com.wecompli.screeen.docmanagment

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R

class DocManagmentViewBind(val docManagmentActivity: DocManagmentActivity, val view: View) :DeviceResolution(docManagmentActivity){
    var tv_Select_company: TextView? = null
    var tv_select_site:TextView? = null
    var tv_notify_who:TextView? = null
    var tv_select_week: TextView? = null
    var tv_update_image:android.widget.TextView? = null
    var tv_end_date:android.widget.TextView? = null
    var tv_start_date:android.widget.TextView? = null
    var tv_submit:android.widget.TextView? = null
    var tv_reset:android.widget.TextView? = null
    var et_document_name: EditText? = null
    var btn_choose_file: Button? = null

    var rec_imaglist: RecyclerView? = null
    var rl_start_date: RelativeLayout? = null
    var rl_end_date:android.widget.RelativeLayout? = null
    var rl_reset:android.widget.RelativeLayout? = null
    var rl_submit:android.widget.RelativeLayout? = null
    var rl_back_docmanagment:RelativeLayout?=null
    init {
       bindview()
        settypeface()
    }

    private fun settypeface() {
        tv_Select_company!!.setTypeface(getgothmlight(docManagmentActivity))
        tv_select_site!!.setTypeface(getgothmlight(docManagmentActivity))
        tv_notify_who!!.setTypeface(getgothmlight(docManagmentActivity))
        tv_select_week!!.setTypeface(getgothmlight(docManagmentActivity))
        tv_end_date!!.setTypeface(getgothmlight(docManagmentActivity))
        tv_update_image!!.setTypeface(getgothmbold(docManagmentActivity))
        tv_start_date!!.setTypeface(getgothmlight(docManagmentActivity))
        et_document_name!!.setTypeface(getgothmlight(docManagmentActivity))
        btn_choose_file!!.setTypeface(getbebas(docManagmentActivity))
        tv_submit!!.setTypeface(getbebas(docManagmentActivity))
        tv_reset!!.setTypeface(getbebas(docManagmentActivity))
    }
    private fun bindview() {
        tv_Select_company = view.findViewById(R.id.tv_Select_company)
        tv_select_site = view.findViewById(R.id.tv_select_site)
        tv_notify_who = view.findViewById(R.id.tv_notify_who)
        tv_select_week = view.findViewById(R.id.tv_select_week)
        tv_end_date = view.findViewById(R.id.tv_end_date)
        tv_update_image = view.findViewById(R.id.tv_update_image)
        tv_start_date = view.findViewById(R.id.tv_start_date)
        et_document_name = view.findViewById(R.id.et_document_name)
        btn_choose_file = view.findViewById(R.id.btn_choose_file)
        rl_start_date = view.findViewById(R.id.rl_start_date)
        rl_end_date = view.findViewById(R.id.rl_end_date)
        rec_imaglist = view.findViewById(R.id.rec_imaglist)
        tv_reset = view.findViewById(R.id.tv_reset)
        rl_reset = view.findViewById(R.id.rl_reset)
        rl_submit = view.findViewById(R.id.rl_submit)
        tv_submit = view.findViewById(R.id.tv_submit)
        rl_back_docmanagment=view.findViewById(R.id.rl_back_docmanagment)
    }

}