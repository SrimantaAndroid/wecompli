package com.wecompli.utils.custompopupdialogforsite

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.apiresponsemodel.faultstatuslist.FaultStatusRow
import com.wecompli.apiresponsemodel.login.SiteList
import com.wecompli.screeen.faultdetails.FaultDetailsActivity
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface


class CustomPopUpFaultStatus(
    val faultDetailsActivity: FaultDetailsActivity,
    val faultstatuslist: List<FaultStatusRow>?) : Dialog(faultDetailsActivity!!) {

     var tv_dialogtitle: TextView ?= null
     var et_search: EditText?= null
     var rv_list: RecyclerView?= null
     var deviceResolution: DeviceResolution?=null
     var customPopupListAdapter:CustomPopupFaultStatusListAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCanceledOnTouchOutside(true)
        setContentView(R.layout.custompopupsitelist_layout)
        rv_list = findViewById(R.id.rv_list)
        tv_dialogtitle = findViewById(R.id.tv_dialogtitle)
        et_search = findViewById(R.id.et_search)
        deviceResolution = DeviceResolution(faultDetailsActivity)
        tv_dialogtitle!!.setTypeface(deviceResolution!!.getbebas(faultDetailsActivity))
        et_search!!.setTypeface(deviceResolution!!.getgothmlight(faultDetailsActivity))
        setreclyerview()
        et_search!!.addTextChangedListener(object :TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               // Alert.showalert(homeActivity!!, homeActivity!!.getResources().getString(R.string.underdevelopment))
            }

            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }
    private fun setreclyerview() {

        customPopupListAdapter = CustomPopupFaultStatusListAdapter(faultDetailsActivity!!,faultstatuslist!!,object :OnItemClickInterface{
            override fun OnItemClick(position: Int) {
               faultDetailsActivity.faultDeatilsViewBind!!.tv_select_faultstatus!!.setText( faultDetailsActivity.faultstatusrow.get(position).status_message)
               faultDetailsActivity.statusmessageid= faultDetailsActivity.faultstatusrow.get(position).id
                dismiss()
            }
        })
        val mLayoutManager = LinearLayoutManager(faultDetailsActivity)
        rv_list!!.setLayoutManager(mLayoutManager)
        rv_list!!.setAdapter(customPopupListAdapter)

    }

    private fun filter(text: String) {
        val filterdNames = ArrayList<FaultStatusRow>()

        //looping through existing elements
        for (s in faultstatuslist!!) {
            //if the existing elements contains the search input
            if (s.status_message!!.toLowerCase().contains(text.toLowerCase())) {

                //adding the element to filtered list
                filterdNames.add(s)

            }
        }
        //calling a method of the adapter class and passing the filtered list
        faultDetailsActivity.faultstatusrow=filterdNames
        customPopupListAdapter!!.filterList(filterdNames)
    }
}