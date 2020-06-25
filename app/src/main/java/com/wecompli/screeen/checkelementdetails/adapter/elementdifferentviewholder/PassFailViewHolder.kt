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

class PassFailViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
    val tv_checname:TextView=itemView.findViewById(R.id.tv_checname)
    val tv_checkdescription:TextView=itemView.findViewById(R.id.tv_checkdescription)
    val btn_pass:Button=itemView.findViewById(R.id.btn_pass)
    val btn_fail:Button=itemView.findViewById(R.id.btn_fail)
    val btn_minorfail:Button=itemView.findViewById(R.id.btn_minorfail)
    val linearLayout:LinearLayout=itemView.findViewById(R.id.constraint)

    fun Bindviewsetvalue(elementDetailsRow: ElementDetailsRow, checkElementDetailsActivity: CheckElementDetailsActivity, position: Int) {
        if (position%2==0)
            linearLayout.setBackgroundColor(checkElementDetailsActivity.getResources().getColor(R.color.item_sell_1))
            else
            linearLayout.setBackgroundColor(checkElementDetailsActivity.getResources().getColor(R.color.white))

        val deviceResolution=DeviceResolution(checkElementDetailsActivity)
         tv_checname.typeface=deviceResolution.getbebas(checkElementDetailsActivity)
        tv_checkdescription.typeface=deviceResolution.getgothmlight(checkElementDetailsActivity)
        btn_pass.typeface=deviceResolution.getbebas(checkElementDetailsActivity)
        btn_fail.typeface=deviceResolution.getbebas(checkElementDetailsActivity)
        tv_checname.setText(elementDetailsRow.checkName)
        tv_checkdescription.setText(elementDetailsRow.checkNote)

        if (elementDetailsRow.checkTypeValue!!.size==3){
            btn_minorfail.visibility=View.VISIBLE
            btn_minorfail.typeface=deviceResolution.getbebas(checkElementDetailsActivity)

            btn_minorfail.setOnClickListener {
                checkElementDetailsActivity.openfailandMinorFail(elementDetailsRow,2,position)

            }

        }
        btn_pass.setOnClickListener {
            checkElementDetailsActivity.callApiforsubmitcheck(elementDetailsRow,0,position)
        }

        btn_fail.setOnClickListener {
            checkElementDetailsActivity.openfailandMinorFail(elementDetailsRow,1,position)

        }



    }

}