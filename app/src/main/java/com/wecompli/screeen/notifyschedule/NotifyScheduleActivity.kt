package com.wecompli.screeen.notifyschedule

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wecompli.R
import com.wecompli.apiresponsemodel.notifyschedule.NotifyScheduleModel
import com.wecompli.screeen.notifyschedule.adapter.NotifyScheduleAdapter
import java.util.ArrayList

class NotifyScheduleActivity:AppCompatActivity() {
    var notifyScheduleViewBind:NotifyScheduleViewBind?=null
    var notifyScheduleOnClick:NotifyScheduleOnClick?=null
    var notifyschedulelist=ArrayList<NotifyScheduleModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view=LayoutInflater.from(this).inflate(R.layout.activty_notify_schedule,null)
        setContentView(view)
        notifyScheduleViewBind= NotifyScheduleViewBind(this,view)
        notifyScheduleOnClick= NotifyScheduleOnClick(this,notifyScheduleViewBind!!)
        setadapter()
    }

    private fun setadapter() {
        val notifyScheduleModel=NotifyScheduleModel("1 week",false,"1")
        val notifyScheduleModel1=NotifyScheduleModel("2 week",false,"2")
        val notifyScheduleModel2=NotifyScheduleModel("1 month",false,"3")
        val notifyScheduleModel3=NotifyScheduleModel("2 months",false,"4")
        val notifyScheduleModel5=NotifyScheduleModel("3 months",false,"5")
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        notifyScheduleViewBind!!.rec_notifyschedule!!.layoutManager = layoutManager
        notifyschedulelist!!.add(notifyScheduleModel)
        notifyschedulelist!!.add(notifyScheduleModel1)
        notifyschedulelist!!.add(notifyScheduleModel2)
        notifyschedulelist!!.add(notifyScheduleModel3)
        notifyschedulelist!!.add(notifyScheduleModel5)

        val notifyscheduleadapter=NotifyScheduleAdapter(notifyschedulelist!!,this)
        notifyScheduleViewBind!!.rec_notifyschedule!!.adapter=notifyscheduleadapter

    }
}