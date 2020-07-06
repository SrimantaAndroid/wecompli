package com.wecompli.screeen.checksummery

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.wecompli.R
import com.wecompli.apiresponsemodel.checkelementdetails.SelectedSiteSessionForCheck
import com.wecompli.screeen.totalfault.TotalFaultActivity
import com.wecompli.utils.ApplicationConstant
import java.io.Serializable

class CheckSummeryOnClick : View.OnClickListener
{
    var checkSummeryActivity: CheckSummeryActivity?=null
    var checkSummeryViewBind: CheckSummeryViewBind?=null
    override fun onClick(p0: View?) {
        when(p0!!.id){
           R.id.img_back->{
              // val homeActivity=
               checkSummeryActivity!!.finish()
           }
            R.id.tv_total_fault_count->{
                val intent=Intent(checkSummeryActivity,TotalFaultActivity::class.java)
                //val selectedSiteSessionForCheck= SelectedSiteSessionForCheck(checkSummeryActivity!!.userData!!.company_id,checkSummeryActivity!!.sideid.toString(),checkSummeryActivity!!.checkcomponent.toString(),checkSummeryActivity!!.listvalue!!.get(position).categoryName,listvalue!!.get(position).id,checkdate!!)
               // intent.putExtra(ApplicationConstant.INTENT_COMPONENETDETAILS,selectedSiteSessionForCheck as Serializable)
                intent.putExtra("componet",checkSummeryActivity!!.checkcomponent)
                intent.putExtra("date",checkSummeryActivity!!.checkdate)
                intent.putExtra("sideid",checkSummeryActivity!!.sideid)
                intent.putExtra("companyid",checkSummeryActivity!!.userData!!.company_id)
               // intent.putExtra("cat_id",checkSummeryActivity!!.userData!!.company_id)
                checkSummeryActivity!!.startActivity(intent)
              //  Toast.makeText(checkSummeryActivity,"Under Development",Toast.LENGTH_LONG).show()
            }


        }
    }

    constructor(checkSummeryActivity: CheckSummeryActivity, checkSummeryViewBind: CheckSummeryViewBind){
        this.checkSummeryActivity=checkSummeryActivity;
        this.checkSummeryViewBind=checkSummeryViewBind
        setonclicklistner()
    }

    private fun setonclicklistner() {
        checkSummeryViewBind!!.img_back!!.setOnClickListener(this)
        checkSummeryViewBind!!.tv_total_fault_count!!.setOnClickListener(this)
    }
}