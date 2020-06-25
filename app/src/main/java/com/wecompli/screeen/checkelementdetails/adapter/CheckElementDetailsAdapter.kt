package com.wecompli.screeen.checkelementdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wecompli.R
import com.wecompli.apiresponsemodel.checkelementdetails.ElementDetailsRow
import com.wecompli.screeen.checkelementdetails.CheckElementDetailsActivity
import com.wecompli.screeen.checkelementdetails.adapter.elementdifferentviewholder.*

class CheckElementDetailsAdapter(
    val checkElementDetailsActivity: CheckElementDetailsActivity,
    var rowlist: List<ElementDetailsRow>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return when(viewType){
           TypeViewHolder.PASS_FAIL.ordinal->
               return PassFailViewHolder(LayoutInflater.from(checkElementDetailsActivity).inflate(R.layout.item_layout_pass_fail,parent,false))
           TypeViewHolder.YES_NO.ordinal->
               return YesNoViewHolder(LayoutInflater.from(checkElementDetailsActivity).inflate(R.layout.item_layout_yes_no,parent,false))
           TypeViewHolder.QR_SCAN.ordinal->
               return QrScanViewHolder(LayoutInflater.from(checkElementDetailsActivity).inflate(R.layout.item_layout_qr_scan,parent,false))
           TypeViewHolder.NOTE.ordinal->
               return NoteViewHolder(LayoutInflater.from(checkElementDetailsActivity).inflate(R.layout.item_layout_note,parent,false))
           TypeViewHolder.TEAMPARATURE.ordinal->
               return TeamparViewHolder(LayoutInflater.from(checkElementDetailsActivity).inflate(R.layout.item_layout_teaparuter,parent,false))
           TypeViewHolder.TAP_TO_SIGN.ordinal->
               return TapToSignViewHolder(LayoutInflater.from(checkElementDetailsActivity).inflate(R.layout.item_layout_tap_to_sign,parent,false))
           TypeViewHolder.CAMERA.ordinal->
               return CameraViewHolder(LayoutInflater.from(checkElementDetailsActivity).inflate(R.layout.item_layout_camera,parent,false))
           else ->
               return NothingViewHolder(LayoutInflater.from(checkElementDetailsActivity).inflate(R.layout.item_layout_nothing,parent,false))
       }
    }

    override fun getItemCount(): Int {
        if (rowlist!=null && rowlist!!.size>0)
        return rowlist!!.size
        else
            return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            TypeViewHolder.PASS_FAIL.ordinal ->{
                val passFailViewHolder= holder as PassFailViewHolder
                passFailViewHolder.Bindviewsetvalue(rowlist!!.get(position),checkElementDetailsActivity,position)


            }
            TypeViewHolder.YES_NO.ordinal ->{
                val yesNoViewHolder= holder as  YesNoViewHolder
                yesNoViewHolder.Bindview(rowlist!!.get(position),checkElementDetailsActivity,position)

            }
            TypeViewHolder.QR_SCAN.ordinal ->{
                val qrScanViewHolder= holder as QrScanViewHolder
                qrScanViewHolder.Bindview(rowlist!!.get(position),checkElementDetailsActivity,position)

            }
            TypeViewHolder.NOTE.ordinal ->{
                val noteViewHolder =holder as NoteViewHolder
                noteViewHolder.Bindview(rowlist!!.get(position),checkElementDetailsActivity,position)

            }
            TypeViewHolder.TEAMPARATURE.ordinal ->{
                val teamparViewHolder =holder as TeamparViewHolder
                teamparViewHolder.Bindview(rowlist!!.get(position),checkElementDetailsActivity,position)

            }
            TypeViewHolder.TAP_TO_SIGN.ordinal ->{
                val tapToSignViewHolder =holder as TapToSignViewHolder
                tapToSignViewHolder.Bindview(rowlist!!.get(position),checkElementDetailsActivity,position)

            }
            TypeViewHolder.CAMERA.ordinal ->{
                val cameraViewHolder =holder as CameraViewHolder
                cameraViewHolder.Bindview(rowlist!!.get(position),checkElementDetailsActivity,position)


            }
            TypeViewHolder.NOTHING.ordinal ->{
                val nothingViewHolder= holder as NothingViewHolder

            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(rowlist!!.get(position).checkTypeId!!.toInt()) {
            1 ->
                TypeViewHolder.PASS_FAIL.ordinal
            2 ->
                TypeViewHolder.NOTE.ordinal
            3 ->
                 TypeViewHolder.QR_SCAN.ordinal
            4 ->
                TypeViewHolder.YES_NO.ordinal

            5 ->
                TypeViewHolder.TEAMPARATURE.ordinal

            6 ->
                TypeViewHolder.TAP_TO_SIGN.ordinal
            7 ->
                TypeViewHolder.CAMERA.ordinal


            else ->
                TypeViewHolder.NOTHING.ordinal
        }

    }

    enum class TypeViewHolder(viewType: Int){
        PASS_FAIL(1),
        NOTE(2),
        QR_SCAN(3),
        YES_NO(4),
        TEAMPARATURE(5),
        TAP_TO_SIGN(6),
        CAMERA(7),
        NOTHING(8)
    }

}