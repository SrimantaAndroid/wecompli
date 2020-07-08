package com.wecompli.screeen.notifyschedule

import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R

class NotifyScheduleViewBind(val notifyScheduleActivity: NotifyScheduleActivity, val view: View) :DeviceResolution(notifyScheduleActivity){

    var tv_select_notify_schedule: TextView? = null
    var tv_Done:android.widget.TextView? = null
    var rl_back_notifyschedule: RelativeLayout? = null
    var rec_notifyschedule: RecyclerView? = null
    init {
        tv_select_notify_schedule = view.findViewById(R.id.tv_select_notify_schedule)
        tv_Done = view.findViewById(R.id.tv_Done)
        rl_back_notifyschedule = view.findViewById(R.id.rl_back_notifyschedule)
        rec_notifyschedule = view.findViewById(R.id.rec_notifyschedule)

        tv_select_notify_schedule!!.setTypeface(getbebas(notifyScheduleActivity))
        tv_Done!!.setTypeface(getbebas(notifyScheduleActivity))
    }

}