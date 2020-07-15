package com.wecompli.utils.custompopupdialogforsite

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.apiresponsemodel.location.LocationRow
import com.wecompli.screeen.accidentreport.AccidentReportActivity
import com.wecompli.screeen.accidentreport.adapter.LocationAdpter
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface

class AccidentLocationDialog(
    val accidentReportActivity: AccidentReportActivity?, var locationlist: List<LocationRow>,
    onItemClickInterface: OnItemClickInterface): Dialog(
    accidentReportActivity!!) {
    var deviceResolution: DeviceResolution? = DeviceResolution(accidentReportActivity)
    var rec_accidetlocation: RecyclerView? = null
    var view: View? = null

    var locationAdapter: LocationAdpter? = null

    var onItemClickInterface: OnItemClickInterface? = onItemClickInterface
    var tv_dialogtitle: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCanceledOnTouchOutside(true)
        view = LayoutInflater.from(accidentReportActivity).inflate(R.layout.accident_location_dialog, null)
        setContentView(view!!)
        rec_accidetlocation = view!!.findViewById(R.id.rec_accidetlocation)
        tv_dialogtitle = view!!.findViewById(R.id.tv_dialogtitle)
        tv_dialogtitle!!.setTypeface(deviceResolution!!.getbebas(accidentReportActivity))
        setreclyerview()
    }

    private fun setreclyerview() {
        locationAdapter = LocationAdpter(accidentReportActivity!!, locationlist!!, onItemClickInterface!!)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(accidentReportActivity)
        rec_accidetlocation!!.setLayoutManager(mLayoutManager)
        rec_accidetlocation!!.setAdapter(locationAdapter)
    }
}