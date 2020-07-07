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
import com.wecompli.apiresponsemodel.login.SiteList
import com.wecompli.screeen.home.HomeActivity
import com.wecompli.screeen.startcheck.StartCheckFragmentOnClick
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent


class CustomPopUpDialogSiteList(
    context: HomeActivity?,
    val startCheckFragmentOnClick: StartCheckFragmentOnClick,
   val siteList: List<SiteList>?
) : Dialog(context!!) {

     var tv_dialogtitle: TextView ?= null
     var et_search: EditText?= null
     var rv_list: RecyclerView?= null
     var homeActivity: HomeActivity?= context
     var deviceResolution: DeviceResolution?=null
     var customPopupListAdapter:CustomPopupListAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCanceledOnTouchOutside(true)
        setContentView(R.layout.custompopupsitelist_layout)
        rv_list = findViewById(R.id.rv_list)
        tv_dialogtitle = findViewById(R.id.tv_dialogtitle)
        et_search = findViewById(R.id.et_search)
        deviceResolution = DeviceResolution(homeActivity)
        tv_dialogtitle!!.setTypeface(deviceResolution!!.getbebas(homeActivity))
        et_search!!.setTypeface(deviceResolution!!.getgothmlight(homeActivity))
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

        customPopupListAdapter = CustomPopupListAdapter(homeActivity!!,siteList!!,object :OnItemClickInterface{
            override fun OnItemClick(position: Int) {
                startCheckFragmentOnClick.fragmentViewBind!!.tv_select_site!!.setText(startCheckFragmentOnClick.startCheckFragment!!.site_list!!.get(position).siteName)
                startCheckFragmentOnClick!!.site_id=startCheckFragmentOnClick!!.startCheckFragment!!.site_list!!.get(position).id
                startCheckFragmentOnClick.activity!!.userData!!.site_id=startCheckFragmentOnClick!!.startCheckFragment!!.site_list!!.get(position).id.toString()
                AppSheardPreference(homeActivity!!).setvalue_in_preference(PreferenceConstent.UserSite,startCheckFragmentOnClick!!.startCheckFragment!!.site_list!!.get(position).siteName.toString())
                AppSheardPreference(homeActivity!!).setvalue_in_preference(PreferenceConstent.site_id,startCheckFragmentOnClick!!.startCheckFragment!!.site_list!!.get(position).id.toString())
                dismiss()
                startCheckFragmentOnClick.callApiforcomponent()
            }
        })
        val mLayoutManager = LinearLayoutManager(homeActivity)
        rv_list!!.setLayoutManager(mLayoutManager)
        rv_list!!.setAdapter(customPopupListAdapter)

    }

    private fun filter(text: String) {
        val filterdNames = ArrayList<SiteList>()
        startCheckFragmentOnClick.startCheckFragment!!.site_list=null
        //looping through existing elements
        for (s in siteList!!) {
            //if the existing elements contains the search input
            if (s.siteName!!.toLowerCase().contains(text.toLowerCase())) {

                //adding the element to filtered list
                filterdNames.add(s)

            }
        }
        //calling a method of the adapter class and passing the filtered list
        startCheckFragmentOnClick.startCheckFragment!!.site_list=filterdNames
        customPopupListAdapter!!.filterList(filterdNames)
    }
}