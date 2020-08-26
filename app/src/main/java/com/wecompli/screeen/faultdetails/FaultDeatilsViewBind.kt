package com.wecompli.screeen.faultdetails

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R

class FaultDeatilsViewBind(
  val  faultDetailsActivity: FaultDetailsActivity, val view: View) :DeviceResolution(faultDetailsActivity){
    var tv_taskdatetime:TextView?=null
    var tv_fault_image:TextView?=null
    var tv_taskdatetimevalue:TextView?=null
    var tv_taskdescription:TextView?=null
    var tv_taskdescriptionvalue:TextView?=null
    var fault_img_1:ImageView?=null
    var fault_img_2:ImageView?=null
    var fault_img_3:ImageView?=null
    var fault_img_4:ImageView?=null

    var tv_imgur11:TextView?=null
    var tv_imgurl2:TextView?=null
    var tv_imgurl3:TextView?=null
    var tv_imgurl4:TextView?=null
     var tv_faultstatusreport:TextView?=null
    var rec_faultsatusreport:RecyclerView?=null
    var tv_select_faultstatus:TextView?=null
    var tv_choose_date:TextView?=null
    var rl_submit:RelativeLayout?=null
    var rl_notifywho:RelativeLayout?=null
    var tv_fixfault:TextView?=null
    var tv_taskname:TextView?=null
    var tv_submit:TextView?=null
    var tv_notifytask:TextView?=null
    var rl_back_taskdetails:RelativeLayout?=null
    var tv_updatefault:TextView?=null

    init {
        tv_taskname=view.findViewById(R.id.tv_taskname)
        tv_taskdatetime=view.findViewById(R.id.tv_taskdatetime)
        tv_taskdatetimevalue=view.findViewById(R.id.tv_taskdatetimevalue)
        tv_taskdescription=view.findViewById(R.id.tv_taskdescription)
        tv_taskdescriptionvalue=view.findViewById(R.id.tv_taskdescriptionvalue)
        fault_img_1=view.findViewById(R.id.fault_img_1)
        fault_img_2=view.findViewById(R.id.fault_img_2)
        fault_img_3=view.findViewById(R.id.fault_img_3)
        fault_img_4=view.findViewById(R.id.fault_img_4)
        tv_imgur11=view.findViewById(R.id.tv_imgurl1)
        tv_imgurl2=view.findViewById(R.id.tv_imgurl2)
        tv_submit=view.findViewById(R.id.tv_submit)
        tv_notifytask=view.findViewById(R.id.tv_notifytask)

        tv_imgurl3=view.findViewById(R.id.tv_imgurl3)
        tv_imgurl4=view.findViewById(R.id.tv_imgurl4)
        tv_faultstatusreport=view.findViewById(R.id.tv_faultstatusreport)
        rec_faultsatusreport=view.findViewById(R.id.rec_faultsatusreport)
        tv_select_faultstatus=view.findViewById(R.id.tv_select_faultstatus)
        tv_choose_date=view.findViewById(R.id.tv_choose_date)
        rl_back_taskdetails=view.findViewById(R.id.rl_back_taskdetails)

        rl_submit=view.findViewById(R.id.rl_submit)
        rl_notifywho=view.findViewById(R.id.rl_notifywho)
        tv_fixfault=view.findViewById(R.id.tv_fixfault)
        tv_fault_image=view.findViewById(R.id.tv_fault_image)
        tv_updatefault=view.findViewById(R.id.tv_closedfault)

        tv_taskname!!.setTypeface(getgothmlight(faultDetailsActivity))
        tv_taskdatetime!!.setTypeface(getbebas(faultDetailsActivity))
        tv_taskdatetimevalue!!.setTypeface(getgothmlight(faultDetailsActivity))
        tv_taskdescription!!.setTypeface(getbebas(faultDetailsActivity))
        tv_taskdescriptionvalue!!.setTypeface(getgothmlight(faultDetailsActivity))
        tv_faultstatusreport!!.setTypeface(getbebas(faultDetailsActivity))
        tv_select_faultstatus!!.setTypeface(getgothmbold(faultDetailsActivity))
        tv_choose_date!!.setTypeface(getgothmlight(faultDetailsActivity))
        tv_fault_image!!.setTypeface(getgothmbold(faultDetailsActivity))

        tv_submit!!.setTypeface(getbebas(faultDetailsActivity))
        tv_notifytask!!.setTypeface(getbebas(faultDetailsActivity))
        tv_fixfault!!.setTypeface(getbebas(faultDetailsActivity))
        tv_updatefault!!.setTypeface(getbebas(faultDetailsActivity))

    }
}