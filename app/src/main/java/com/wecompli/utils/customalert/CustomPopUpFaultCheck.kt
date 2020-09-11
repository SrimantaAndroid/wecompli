package com.wecompli.utils.customalert

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
import com.wecompli.apiresponsemodel.faultapi.CheckRow
import com.wecompli.screeen.fixfault.FixFaultActivity
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent


class CustomPopUpFaultCheck(
    val mfixFaultActivity: FixFaultActivity,
    val falultrow: java.util.ArrayList<CheckRow>
):Dialog(mfixFaultActivity) {
    var tv_dialogtitle: TextView?= null
    var et_search: EditText?= null
    var rv_list: RecyclerView?= null
    var fixFaultActivity: FixFaultActivity?= mfixFaultActivity
    var deviceResolution: DeviceResolution?=null

    var customFaultListAdapter:CustomFaultListAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCanceledOnTouchOutside(true)
        setContentView(R.layout.custompopupsitelist_layout)
        rv_list = findViewById(R.id.rv_list)
        tv_dialogtitle = findViewById(R.id.tv_dialogtitle)
        tv_dialogtitle!!.setText("Select Checks")
        et_search = findViewById(R.id.et_search)
        deviceResolution = DeviceResolution(fixFaultActivity)
        tv_dialogtitle!!.setTypeface(deviceResolution!!.getbebas(fixFaultActivity))
        et_search!!.setTypeface(deviceResolution!!.getgothmlight(fixFaultActivity))
        setreclyerview()
        et_search!!.addTextChangedListener(object : TextWatcher {
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
        customFaultListAdapter= CustomFaultListAdapter(fixFaultActivity!!,falultrow!!,object :OnItemClickInterface{
            override fun OnItemClick(position: Int) {
                fixFaultActivity!!.fixFaultViewBind!!.tv_bcomp_element_name.setText(falultrow.get(position).checkName)
                fixFaultActivity!!.fixFaultViewBind!!.tv_bcomp_name.setText(falultrow.get(position).categoryName)
                AppSheardPreference(fixFaultActivity!!).setvalue_in_preference(PreferenceConstent.faultid,
                    falultrow!!.get(position).id.toString())
                dismiss()

            }
        })
        val mLayoutManager = LinearLayoutManager(fixFaultActivity)
        rv_list!!.setLayoutManager(mLayoutManager)
        rv_list!!.setAdapter(customFaultListAdapter)


    }

    public fun filter(text: String) {
        val filterdNames = ArrayList<CheckRow>()
       // startCheckFragmentOnClick.startCheckFragment!!.site_list=null
        //looping through existing elements
        for (s in falultrow) {
            //if the existing elements contains the search input
            if (s.checkName!!.toLowerCase().contains(text.toLowerCase())) {

                //adding the element to filtered list
                filterdNames.add(s)

            }
        }
        //calling a method of the adapter class and passing the filtered list
        //startCheckFragmentOnClick.startCheckFragment!!.site_list=filterdNames
        customFaultListAdapter!!.filterList(filterdNames)
    }
}