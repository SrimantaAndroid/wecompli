package com.wecompli.screeen.startcheck

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.screeen.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_start_check.view.*

class StartCheckFragmentViewBind:DeviceResolution {
    var tv_Selected_company:TextView?=null
    var tv_select_site:TextView?=null
    var tv_check_date:TextView?=null
    var ll_component:LinearLayout?=null
    var activity: HomeActivity?=null
    var rev_componentlist:RecyclerView?=null
    //var deviceResolution:DeviceResolution?=null
    constructor( view: View, activity: HomeActivity) : super(activity) {
       // deviceResolution=DeviceResolution(activity)
        this.activity=activity
        viewbinds(view)
        settypeface()
    }

    private fun settypeface() {
        tv_Selected_company!!.typeface=getgothmlight(activity)
        tv_select_site!!.typeface=getgothmlight(activity)
        tv_check_date!!.typeface=getgothmlight(activity)
    }

    private fun viewbinds(view:View) {
        tv_Selected_company=view.findViewById(R.id.tv_Selected_company)
        tv_select_site=view.findViewById(R.id.tv_select_site)
        tv_check_date=view.findViewById(R.id.tv_check_date)
        ll_component=view.findViewById(R.id.ll_component)
        rev_componentlist=view.findViewById(R.id.rev_componentlist)

    }

}