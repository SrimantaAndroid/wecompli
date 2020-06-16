package com.wecompli.utils.custompopupdialogforsite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wecompli.R
import com.wecompli.apiresponsemodel.faultstatuslist.FaultStatusRow
import com.wecompli.screeen.faultdetails.FaultDetailsActivity
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface

class CustomPopupFaultStatusListAdapter(
    val faultDetailsActivity: FaultDetailsActivity,
    var faultstatusrow: List<FaultStatusRow>,
    val onItemClickInterface: OnItemClickInterface) : RecyclerView.Adapter<CustomPopupFaultStatusListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(faultDetailsActivity).inflate(R.layout.site_list_item,null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return faultstatusrow.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView!!.text=faultstatusrow.get(position).status_message
        holder.rl_sitename!!.setOnClickListener {
            onItemClickInterface.OnItemClick(position)
        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         var textView: TextView?=itemView.findViewById(R.id.tv_site)
          var rl_sitename:RelativeLayout?=itemView.findViewById(R.id.rl_sitename)

    }

    fun filterList(filterdNames: List<FaultStatusRow>) {
        this.faultstatusrow = filterdNames
        notifyDataSetChanged()
    }
}
