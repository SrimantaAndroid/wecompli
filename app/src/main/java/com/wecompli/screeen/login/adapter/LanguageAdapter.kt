package com.wecompli.screeen.login.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.screeen.login.LoginActivity
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface

class LanguageAdapter(val loginActivity:LoginActivity,  val laanguagelist:List<String>, val onItemClickInterface: OnItemClickInterface): RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(loginActivity).inflate(R.layout.language_listitem_layout, null)
        return ViewHolder(view,loginActivity)
    }

    override fun getItemCount(): Int {
       return laanguagelist.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tv_languagename.setText(laanguagelist.get(position))
        viewHolder.ll_language.setOnClickListener(View.OnClickListener {
            onItemClickInterface.OnItemClick(position)
        })
    }


    class ViewHolder(@NonNull itemView: View, loginActivity: LoginActivity) : RecyclerView.ViewHolder(itemView) {
        internal var tv_languagename: TextView
        internal var ll_language: LinearLayout
        var deviceResolution = DeviceResolution(loginActivity)

        init {
            tv_languagename = itemView.findViewById(R.id.tv_languagename)
            ll_language = itemView.findViewById(R.id.ll_language)
            tv_languagename.setTypeface(deviceResolution.getgothmlight(loginActivity))
        }
    }
}