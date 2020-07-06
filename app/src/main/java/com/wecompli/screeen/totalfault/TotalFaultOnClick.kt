package com.wecompli.screeen.totalfault

import android.content.Intent
import android.view.View
import com.wecompli.R
import com.wecompli.screeen.checksummery.CheckSummeryActivity
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import kotlinx.android.synthetic.main.activity_totalfault.view.*

class TotalFaultOnClick(
   val totalFaultActivity: TotalFaultActivity,
    val totalFaultViewBind: TotalFaultViewBind): View.OnClickListener {
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.img_back->{

              /*  val cheksummery= Intent(totalFaultActivity,CheckSummeryActivity::class.java)
                cheksummery.putExtra("componet",
                    AppSheardPreference(totalFaultActivity).getvalue_in_preference(
                        PreferenceConstent.component_totalfault))
                cheksummery.putExtra("date",
                    AppSheardPreference(totalFaultActivity).getvalue_in_preference(
                        PreferenceConstent.date_totalfault))
                cheksummery.putExtra("sideid",
                    AppSheardPreference(totalFaultActivity).getvalue_in_preference(
                        PreferenceConstent.siteidtotalfault))
                cheksummery.putExtra("sessionname",
                    AppSheardPreference(totalFaultActivity).getvalue_in_preference(
                        PreferenceConstent.sessionname))

                totalFaultActivity.startActivity(cheksummery)*/
                totalFaultActivity.finish()


            }
        }
    }

    init {
        totalFaultViewBind.img_back!!.setOnClickListener(this)
    }
}