package com.wecompli.screeen.elementfaultlist

import android.R.id.message
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wecompli.R


class ElementFaultListActivity: AppCompatActivity() {
    var elementFaultListViewBind:ElementFaultListViewBind?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_element_fault_list,null)
        setContentView(view)
        elementFaultListViewBind= ElementFaultListViewBind(view,this)
        elementFaultListViewBind!!.rl_elementfaultlist!!.setOnClickListener {
            val intent = Intent()
            intent.putExtra("MESSAGE", message)
            setResult(155, intent)
            finish()
        }
    }
}