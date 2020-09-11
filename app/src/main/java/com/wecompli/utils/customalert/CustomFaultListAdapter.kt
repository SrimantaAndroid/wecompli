package com.wecompli.utils.customalert

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wecompli.R
import com.wecompli.apiresponsemodel.faultapi.CheckRow
import com.wecompli.screeen.fixfault.FixFaultActivity
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface
import java.util.ArrayList

class CustomFaultListAdapter(
    val fixFaultActivity: FixFaultActivity,
    var falultrow: ArrayList<CheckRow>,
    val onItemClickInterface: OnItemClickInterface
):
    RecyclerView.Adapter<CustomFaultListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomFaultListAdapter.ViewHolder {
        val view= LayoutInflater.from(fixFaultActivity).inflate(R.layout.site_list_item,null)
        return CustomFaultListAdapter.ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return falultrow.size
    }

    override fun onBindViewHolder(holder: CustomFaultListAdapter.ViewHolder, position: Int) {
       holder.textView!!.text=falultrow.get(position).checkName
        holder.rl_sitename!!.setOnClickListener {
            onItemClickInterface.OnItemClick(position)
        }

    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textView: TextView?=itemView.findViewById(R.id.tv_site)
        var rl_sitename: RelativeLayout?=itemView.findViewById(R.id.rl_sitename)

    }

    fun filterList(filterdNames: ArrayList<CheckRow>) {
        this.falultrow = filterdNames
        notifyDataSetChanged()
    }

}