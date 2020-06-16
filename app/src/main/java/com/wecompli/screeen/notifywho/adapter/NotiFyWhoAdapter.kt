package com.wecompli.screeen.notifywho.adapter

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
import com.wecompli.apiresponsemodel.faultdetails.NotifyEmail
import com.wecompli.apiresponsemodel.notifywho.NotifyWhoModel
import com.wecompli.screeen.notifywho.NotifyWhoActivity

class NotiFyWhoAdapter(
    val notifyWhoActivity: NotifyWhoActivity,
    val emillist: ArrayList<NotifyWhoModel>) : RecyclerView.Adapter<NotiFyWhoAdapter.notifyview>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notifyview {
        val view = LayoutInflater.from(notifyWhoActivity).inflate(R.layout.notify_who_item_list, null)
        return notifyview(view)
    }

    override fun getItemCount(): Int {
       return emillist.size
    }

    override fun onBindViewHolder(holder: notifyview, position: Int) {
        if (position % 2 == 0) {
            holder.rr_notifybg.setBackgroundColor(notifyWhoActivity!!.getResources().getColor(R.color.item_sell_1))
        }
        holder.tv_notify_name!!.setText(emillist.get(position).name)
       holder.tv_notify_email.setText(emillist.get(position).email)
        holder.chk_notify.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                notifyWhoActivity.emillist.get(position).ischeck=true
            } else {
                notifyWhoActivity.emillist.get(position).ischeck=false
            }
        })
    }

    inner class notifyview( itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tv_notify_name: TextView
        internal var tv_notify_email: TextView
        internal var chk_notify: CheckBox
        internal var rr_notifybg: RelativeLayout
        internal var deviceResolution= DeviceResolution(notifyWhoActivity)

        init {
            chk_notify = itemView.findViewById(R.id.chk_notify)
            tv_notify_name = itemView.findViewById(R.id.tv_notify_name)
            tv_notify_email = itemView.findViewById(R.id.tv_notify_email)
            rr_notifybg = itemView.findViewById(R.id.rr_notifybg)
            tv_notify_name.setTypeface(deviceResolution.getgothmbold(notifyWhoActivity))
            tv_notify_email.setTypeface(deviceResolution.getgothmlight(notifyWhoActivity))

        }
    }
}

private operator fun Boolean.invoke(b: Boolean) {

}
