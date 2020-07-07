package com.wecompli.screeen.accidentreport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wecompli.R

class AccidentReportActivity :AppCompatActivity(){
    var accidentReportViewBind:AccidentReportViewBind?=null
    var accidentReportOnclick:AccidentReportOnclick?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_accident_report,null)
        setContentView(view)
        accidentReportViewBind= AccidentReportViewBind(this,view)
        accidentReportOnclick=AccidentReportOnclick(this,accidentReportViewBind!!)
    }
}