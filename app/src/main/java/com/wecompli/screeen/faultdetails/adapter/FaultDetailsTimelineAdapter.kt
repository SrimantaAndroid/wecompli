package com.wecompli.screeen.faultdetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.apiresponsemodel.faultdetails.Timeline
import com.wecompli.screeen.faultdetails.FaultDetailsActivity

class FaultDetailsTimelineAdapter(
   val faultDetailsActivity: FaultDetailsActivity,
   val timeline: List<Timeline>
) : RecyclerView.Adapter<FaultDetailsTimelineAdapter.LOGView>() {
     lateinit var deviceResolution: DeviceResolution
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LOGView {
        val view = LayoutInflater.from(faultDetailsActivity).inflate(R.layout.fault_log_item_layout, null)
        return LOGView(view)
    }

    override fun getItemCount(): Int {
        return timeline.size
    }

    override fun onBindViewHolder(logView: LOGView, position: Int) {
        if (position % 2 == 0)
            logView.rl_fault.setBackgroundColor(faultDetailsActivity.getResources().getColor(R.color.item_sell_1))
        else
            logView.rl_fault.setBackgroundColor(faultDetailsActivity.getResources().getColor(R.color.white))
         logView.log_name!!.setText(timeline.get(position).repairMessage)
         logView.log_date!!.setText(faultDetailsActivity.getResources().getString(R.string.Date) + " " + timeline.get(position).repairDatetime)
    }

    inner class LOGView( itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var rl_fault: RelativeLayout
        internal var log_date: TextView
        internal var log_name: TextView

        init {
            deviceResolution=DeviceResolution(faultDetailsActivity)
            rl_fault = itemView.findViewById(R.id.rl_fault)
            log_date = itemView.findViewById(R.id.log_date)
            log_name = itemView.findViewById(R.id.log_name)
            log_name.setTypeface(deviceResolution.getgothmbold(faultDetailsActivity))
            log_date.setTypeface(deviceResolution.getgothmlight(faultDetailsActivity))
        }
    }
}