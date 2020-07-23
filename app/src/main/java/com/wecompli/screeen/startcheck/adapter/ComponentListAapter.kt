package com.wecompli.screeen.startcheck.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.apiresponsemodel.seasonlist.Row
import com.wecompli.screeen.home.HomeActivity
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface

class ComponentListAapter(
  val  homeactivity: HomeActivity,
   val row: List<Row>?,
   val onItemClickInterface: OnItemClickInterface
) :RecyclerView.Adapter<ComponentListAapter.ViewHolderComponent>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderComponent {
        val view = LayoutInflater.from(homeactivity).inflate(R.layout.component_list_item, null)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.setLayoutParams(lp)
        return ViewHolderComponent(homeactivity,view)
    }

    override fun getItemCount(): Int {
        return  row!!.size
    }

    override fun onBindViewHolder(holder: ViewHolderComponent, position: Int) {
        holder.tv_component_name.setText(row!!.get(position).seasonName)
        holder.tv_component_name.setOnClickListener {
            onItemClickInterface.OnItemClick(position)

        }
    }

    class ViewHolderComponent(activity:HomeActivity,itemView: View) : RecyclerView.ViewHolder(itemView) {
        var deviceResolution=DeviceResolution(activity)
        var tv_component_name:TextView=itemView.findViewById(R.id.tv_component_name)
        init {
            tv_component_name.typeface=deviceResolution.getbebas(activity)
        }

    }
}