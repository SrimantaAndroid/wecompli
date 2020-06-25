package com.wecompli.screeen.checkelementdetails.adapter.elementdifferentviewholder

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.apiresponsemodel.checkelementdetails.ElementDetailsRow
import com.wecompli.screeen.checkelementdetails.CheckElementDetailsActivity

class CameraViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
    val tv_checname: TextView =itemView.findViewById(R.id.tv_checname)
    val tv_checkdescription: TextView =itemView.findViewById(R.id.tv_checkdescription)
    val btn_camera: Button =itemView.findViewById(R.id.btn_camera)
    val ll_bgcamera: LinearLayout =itemView.findViewById(R.id.ll_bgcamera)
    fun Bindview(elementdetailsrow: ElementDetailsRow, checkElementDetailsActivity: CheckElementDetailsActivity, position: Int) {
        if (position%2==0)
            ll_bgcamera.setBackgroundColor(checkElementDetailsActivity.getResources().getColor(R.color.item_sell_1))
        else
            ll_bgcamera.setBackgroundColor(checkElementDetailsActivity.getResources().getColor(R.color.white))

        val deviceResolution= DeviceResolution(checkElementDetailsActivity)
        tv_checname.typeface=deviceResolution.getbebas(checkElementDetailsActivity)
        tv_checkdescription.typeface=deviceResolution.getgothmlight(checkElementDetailsActivity)
        btn_camera.typeface=deviceResolution.getbebas(checkElementDetailsActivity)
        tv_checname.setText(elementdetailsrow.checkName)
        tv_checkdescription.setText(elementdetailsrow.checkNote)
        btn_camera.setOnClickListener {
            checkElementDetailsActivity.opeenCamera(elementdetailsrow, position)
           // Toast.makeText(checkElementDetailsActivity,"Under Development",Toast.LENGTH_LONG).show()

        }

    }

}