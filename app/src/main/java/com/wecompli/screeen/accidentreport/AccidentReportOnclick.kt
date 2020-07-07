package com.wecompli.screeen.accidentreport

import android.view.View
import com.wecompli.R


class AccidentReportOnclick(
    var accidentReportActivity: AccidentReportActivity,
    var  accidentReportViewBind: AccidentReportViewBind) :View.OnClickListener{

    init {
        accidentReportViewBind.rl_back_accidentreport!!.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
    when(p0!!.id){
        R.id.rl_back_accidentreport->{
            accidentReportActivity.finish()
        }
     }
    }
}