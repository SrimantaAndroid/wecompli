package com.wecompli.screeen.notifyschedule

import android.content.Intent
import android.view.View
import com.wecompli.R

class NotifyScheduleOnClick(
    val notifyScheduleActivity: NotifyScheduleActivity,
    val notifyScheduleViewBind: NotifyScheduleViewBind) :View.OnClickListener{
    init {
        notifyScheduleViewBind.tv_Done!!.setOnClickListener(this)
        notifyScheduleViewBind.rl_back_notifyschedule!!.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.rl_back_notifyschedule->{
                notifyScheduleActivity.finish()
            }
            R.id.tv_Done->{
                /* for(int m=0;m<notifyScheduleActivity.schedulelist.size();m++){
                    notifyScheduleActivity.schedulelist.get(m).setIscheck(true);
                }
                notifyScheduleActivity.scheduleNotifyAdapter.notifyDataSetChanged();*/
                var site = ""
                var ids = ""
                if (notifyScheduleActivity.notifyschedulelist.size>0) {
                    for (i in 0 until notifyScheduleActivity.notifyschedulelist.size) {
                        if (notifyScheduleActivity.notifyschedulelist.get(i).schedule_ischeck) {
                            site = notifyScheduleActivity.notifyschedulelist.get(i).sehedule_name + "," + site
                            ids =  ids+ "###" + notifyScheduleActivity.notifyschedulelist.get(i).value.toString()
                        }
                    }
                }
                val returnIntent = Intent()
                returnIntent.putExtra("result", site)
                if (!ids.equals(""))
                returnIntent.putExtra("ids", ids.substring(3))
                else
                returnIntent.putExtra("ids", ids)
                notifyScheduleActivity.setResult(1, returnIntent)
                notifyScheduleActivity.finish()
            }
        }
    }
}