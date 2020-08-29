package com.wecompli.screeen.startcheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wecompli.R
import com.wecompli.apiresponsemodel.login.SiteList
import com.wecompli.screeen.home.HomeActivity
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import java.text.SimpleDateFormat
import java.util.*

class StartCheckFragment: Fragment() {
    var fragmentOnClick:StartCheckFragmentOnClick?=null
    var fragmentViewBind:StartCheckFragmentViewBind?=null
    lateinit var formattedDate: String
    var homeactivity:HomeActivity?=null
    var site_list:List<SiteList>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        homeactivity=getActivity() as HomeActivity
        val view:View=LayoutInflater.from(activity).inflate(R.layout.fragment_start_check,null)
        fragmentViewBind= StartCheckFragmentViewBind(view,homeactivity!!)
        fragmentOnClick= StartCheckFragmentOnClick(homeactivity!!,fragmentViewBind!!,this)
        setcompanyvalueanddate()
        return view;

    }

    private fun setcompanyvalueanddate() {
        fragmentViewBind!!.tv_Selected_company!!.text=homeactivity!!.userData!!.company_name
        val c = Calendar.getInstance()
        val df = SimpleDateFormat("dd/MM/yyyy")
        formattedDate = df.format(c.time)
        fragmentViewBind!!.tv_check_date!!.visibility=View.VISIBLE
        fragmentViewBind!!.tv_check_date!!.text=formattedDate
        site_list=homeactivity!!.userData!!.site_list
       // AppSheardPreference(homeactivity!!).setvalue_in_preference(PreferenceConstent.chk_selectiondate,formattedDate)

    }


}