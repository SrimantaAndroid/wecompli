package com.wecompli.screeen.checkelementdetails.adapter.elementdifferentviewholder

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.apiresponsemodel.checkelementdetails.ElementDetailsRow
import com.wecompli.screeen.checkelementdetails.CheckElementDetailsActivity

class YesNoViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
   val tv_checname:TextView=itemView.findViewById(R.id.tv_ychecname)
    val tv_checkdescription:TextView=itemView.findViewById(R.id.tv_ycheckdescription)
    val btn_yyes: Button =itemView.findViewById(R.id.btn_yyes)
    val btn_yno: Button =itemView.findViewById(R.id.btn_yno)
    val llbgyesno: LinearLayout =itemView.findViewById(R.id.ll_bgyesno)
    fun Bindview(
        elementDetailsRow: ElementDetailsRow, checkElementDetailsActivity: CheckElementDetailsActivity, position: Int ) {
        if (position%2==0)
            llbgyesno.setBackgroundColor(checkElementDetailsActivity.getResources().getColor(R.color.item_sell_1))
        else
            llbgyesno.setBackgroundColor(checkElementDetailsActivity.getResources().getColor(R.color.white))

        val deviceResolution=DeviceResolution(checkElementDetailsActivity)
        tv_checname.typeface=deviceResolution.getbebas(checkElementDetailsActivity)
        tv_checkdescription.typeface=deviceResolution.getgothmlight(checkElementDetailsActivity)
        btn_yyes.typeface=deviceResolution.getbebas(checkElementDetailsActivity)
        btn_yno.typeface=deviceResolution.getbebas(checkElementDetailsActivity)
        tv_checname.setText(elementDetailsRow.checkName)
        tv_checkdescription.setText(elementDetailsRow.checkNote)

        btn_yyes.setOnClickListener {
            checkElementDetailsActivity.callApiforsubmitcheck(elementDetailsRow, 0, position)
        }

        btn_yno.setOnClickListener {
            checkElementDetailsActivity.openfailandMinorFail(elementDetailsRow, 1, position)

        }

    }
    
}