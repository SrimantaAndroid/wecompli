package com.wecompli.screeen.notifyschedule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.apiresponsemodel.notifyschedule.NotifyScheduleModel
import com.wecompli.screeen.notifyschedule.NotifyScheduleActivity
import java.util.*

class NotifyScheduleAdapter(var notifyschedulelist: ArrayList<NotifyScheduleModel>,val activity: NotifyScheduleActivity): RecyclerView.Adapter<NotifyScheduleAdapter.ItemView>() {
    var deviceResolution:DeviceResolution?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        deviceResolution= DeviceResolution(activity)
        val view = LayoutInflater.from(activity).inflate(R.layout.schedule_notify_list, null)
        return ItemView(view,activity)

    }

    override fun getItemCount(): Int {
       return notifyschedulelist.size

    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        holder.tv_schedule_notify_name!!.setTypeface(deviceResolution!!.getgothmbold(activity))
        holder.tv_schedule_notify_name!!.setText(notifyschedulelist.get(position).sehedule_name)
        if (notifyschedulelist.get(position).schedule_ischeck)
            holder.chk_sehedule_notify!!.isChecked=true
        else
            holder.chk_sehedule_notify!!.isChecked=false
        holder!!.chk_sehedule_notify!!.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                notifyschedulelist.get(position).schedule_ischeck=true
            } else {
                notifyschedulelist.get(position).schedule_ischeck=false
            }
        })
    }

    class  ItemView(val view:View, val activity:NotifyScheduleActivity):RecyclerView.ViewHolder(view){
        var tv_schedule_notify_name: TextView? =itemView.findViewById(R.id.tv_schedule_notify_name)
        var chk_sehedule_notify: CheckBox? = itemView.findViewById(R.id.chk_sehedule_notify)
        var rr_notifybg: RelativeLayout? = itemView.findViewById(R.id.rr_notifybg)

    }

}