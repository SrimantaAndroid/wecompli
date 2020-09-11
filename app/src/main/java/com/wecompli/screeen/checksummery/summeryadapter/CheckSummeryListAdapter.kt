package com.wecompli.screeen.checksummery.summeryadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.warkiz.widget.IndicatorSeekBar
import com.wecompli.R
import com.wecompli.apiresponsemodel.checksummery.CheckRow
import com.wecompli.screeen.home.HomeActivity
import com.wecompli.screeen.summery.CheckSummeryFragment
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface

class CheckSummeryListAdapter(
    val checkSummeryActivity: HomeActivity,
    val checkSummeryFragment: CheckSummeryFragment,
    val listvalue: List<CheckRow>?,
    val param: OnItemClickInterface
) : RecyclerView.Adapter<CheckSummeryListAdapter.SummeryListView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummeryListView {
        val view = LayoutInflater.from(checkSummeryActivity).inflate(R.layout.summery_item_layout, null)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.setLayoutParams(lp)
        return  SummeryListView(view,checkSummeryActivity)
    }

    override fun getItemCount(): Int {
        return listvalue!!.size
    }

    override fun onBindViewHolder(holder: SummeryListView, position: Int) {
        var checked_percentage: Int=0
        if (position % 2 == 0)
            holder.ll_bg_summery_list.setBackgroundColor(checkSummeryActivity.getResources().getColor(R.color.item_sell_1))
        else
            holder.ll_bg_summery_list.setBackgroundColor(checkSummeryActivity.getResources().getColor(R.color.white))


        if (checkSummeryFragment.sessionname!!.contentEquals("Intra Day Check")){
            checked_percentage= ((listvalue!!.get(position).checksCount.toInt() * 100) / listvalue!!.get(position).totalChecksCount.toInt())
               holder.rl_seekindicator.visibility=View.INVISIBLE
                holder.rr_check.setBackgroundColor(checkSummeryActivity.getResources().getColor(R.color.intracheckcolor))
               holder.tv_lastcheckdate.visibility=View.VISIBLE
               holder.tv_lastcheckdate.setText("Last Checked at: "+listvalue.get(position).last_checked_at)

            }else {
             checked_percentage= ((listvalue!!.get(position).checksCount.toInt() * 100) / listvalue!!.get(position).totalChecksCount.toInt())
             holder.custom_seek_indicator.setProgress(checked_percentage.toFloat())
             holder.custom_seek_indicator.setIndicatorTextFormat("\${PROGRESS} %")
            // if (checked_percentage<=0) {
            //  summeryItemView.seekBarWithProgress.setEnabled(false);
             holder.custom_seek_indicator.setUserSeekAble(false)

            if (!listvalue.get(position).categoryNote.equals("") ||listvalue.get(position).categoryNote!=null)
              holder.check_note_text.setText(listvalue.get(position).categoryNote)
            else
                holder.check_note_text.setText("-------------")

            if (checked_percentage > 0 && checked_percentage <= 30) {
                holder.seek_red.setVisibility(View.VISIBLE)
                holder.seek_red.setProgress(checked_percentage)
                holder.seek_red.setOnTouchListener(View.OnTouchListener { v, event -> true })
                // summeryItemView.seek_red.setEnabled(false);
            } else if (checked_percentage > 30 && checked_percentage <= 60) {
                holder.seek_yellow.setVisibility(View.VISIBLE)
                holder.seek_yellow.setProgress(checked_percentage)
                holder.seek_yellow.setOnTouchListener(View.OnTouchListener { v, event -> true })
                //  summeryItemView.seek_yellow.setEnabled(false);
            } else if (checked_percentage > 60 && checked_percentage <= 100) {
                holder.seek_green.setVisibility(View.VISIBLE)
                holder.seek_green.setProgress(checked_percentage)
                holder.seek_green.setOnTouchListener(View.OnTouchListener { v, event -> true })
                // summeryItemView.seek_green.setEnabled(false);
            }
        }

        holder.tv_title.text=listvalue!!.get(position).categoryName
        holder.btn_start.setOnClickListener {
            if (checked_percentage<100)
            param.OnItemClick(position)
            else
                Alert.showalert(checkSummeryActivity,"All checks are Submitted.")
        }

    }

    class SummeryListView(itemView: View, checkSummeryActivity: HomeActivity) : RecyclerView.ViewHolder(itemView){
        val  deviceResolution=DeviceResolution(checkSummeryActivity)
        val  tv_title:TextView=itemView.findViewById(R.id.tv_title)
        var btn_start:Button=itemView.findViewById(R.id.btn_start)
         var seek_red: SeekBar=itemView.findViewById(R.id.seek_red)
         var seek_yellow:SeekBar=itemView.findViewById(R.id.seek_yellow)
        var seek_green:SeekBar=itemView.findViewById(R.id.seek_green)
        val rl_seekindicator=itemView.findViewById<RelativeLayout>(R.id.rl_seekindicator)
        val custom_seek_indicator: IndicatorSeekBar=itemView.findViewById(R.id.custom_indicator)
        val ll_bg_summery_list:LinearLayout=itemView.findViewById(R.id.ll_bg_summery_list);
        val rr_check:RelativeLayout=itemView.findViewById(R.id.rr_check)
        val tv_lastcheckdate:TextView=itemView.findViewById(R.id.tv_lastcheckdate)
        val check_note:TextView=itemView.findViewById(R.id.check_note)
        val check_note_text:TextView=itemView.findViewById(R.id.check_note_text)
        init {
            tv_title.typeface=deviceResolution.getgothmlight(checkSummeryActivity)
            btn_start.typeface=deviceResolution.getbebas(checkSummeryActivity)
            check_note.typeface=deviceResolution.getgothmbold(checkSummeryActivity)
            tv_lastcheckdate.typeface=deviceResolution.getgothmlight(checkSummeryActivity)
            check_note_text.typeface=deviceResolution.getgothmlight(checkSummeryActivity)
            seek_red.setPadding(0, 0, 0, 0)
            seek_yellow.setPadding(0, 0, 0, 0)
            seek_green.setPadding(0, 0, 0, 0)
        }
    }
}