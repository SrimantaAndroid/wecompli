package com.wecompli.screeen.accidentreport.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.apiresponsemodel.location.LocationRow
import com.wecompli.screeen.accidentreport.AccidentReportActivity
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface

class LocationAdpter(
    val activity: AccidentReportActivity, val locationlist: List<LocationRow>,
    val onItemClickInterface: OnItemClickInterface) : RecyclerView.Adapter<LocationAdpter.Viewholder>() {
        override fun onBindViewHolder(viewholder: Viewholder, i: Int) {
            var deviceResolution: DeviceResolution? = null
            deviceResolution = DeviceResolution(activity)
            viewholder.textView.setTypeface(deviceResolution!!.getgothmlight(activity))
            viewholder.textView!!.setText(locationlist.get(i).location_name)
            viewholder.textView.setOnClickListener(View.OnClickListener {
                onItemClickInterface!!.OnItemClick(i)
            })
        }

        override fun getItemCount(): Int {
            return locationlist.size
        }

        class Viewholder( itemView: View) : RecyclerView.ViewHolder(itemView) {
            var textView: TextView
            init {
                textView = itemView.findViewById(R.id.tv_site)

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
            val view: View = LayoutInflater.from(activity).inflate(R.layout.site_list_item, null)
            return Viewholder(view)
        }
    }