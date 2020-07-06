package com.wecompli.screeen.elementfaultlist

import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R

class ElementFaultListViewBind(val view1: View, val activity: ElementFaultListActivity):DeviceResolution(activity) {
    var elementFaultListActivity: ElementFaultListActivity? = null
    var view: View? = null
    var rl_elementfaultlist: RelativeLayout? = null
    var tv_elementfaultlist: TextView? = null
    var tv_Select_company:TextView? = null
    var tv_select_site:TextView? = null
    var norecord:TextView? = null
    var rec_elementfault: RecyclerView? = null
    init {
        view=view1
        rl_elementfaultlist = view!!.findViewById(R.id.rl_elementfaultlist)
        tv_elementfaultlist = view!!.findViewById(R.id.tv_elementfaultlist)
        tv_Select_company = view!!.findViewById(R.id.tv_Select_company)
        tv_select_site = view!!.findViewById(R.id.tv_select_site)
        rec_elementfault = view!!.findViewById(R.id.rec_elementfault)
        norecord = view!!.findViewById(R.id.norecord)

        tv_elementfaultlist!!.setTypeface(getbebas(activity))
        tv_Select_company!!.setTypeface(getgothmlight(activity))
        tv_select_site!!.setTypeface(getgothmlight(activity))
    }
}