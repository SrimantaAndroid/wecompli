package com.wecompli.screeen.fixfault

import android.view.View
import android.widget.*
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import kotlinx.android.synthetic.main.fix_fault_layout.view.*

class FixFaultViewBind(
   val fixFaultActivity: FixFaultActivity,
  val  view: View) :DeviceResolution(fixFaultActivity) {
    var tv_engineer_signaturehere: TextView=view.findViewById(R.id.tv_engineer_signaturehere)
    var tv_manager_signaturehere:TextView=view.findViewById(R.id.tv_manager_signaturehere)
    var tv_fixfaulthader:TextView=view.findViewById(R.id.tv_fixfaulthader)
    var tv_select_work:TextView=view.findViewById(R.id.tv_select_work)
    internal var tv_preescan: TextView=view.findViewById(R.id.tv_preescan)
    internal var tv_bcomp_name:TextView=view.findViewById(R.id.tv_bcomp_name)
    internal var tv_bcomp_element_name:TextView=view.findViewById(R.id.tv_bcomp_element_name)
    internal var tv_Company:TextView=view.findViewById(R.id.tv_Company)
    internal var tv_site:TextView =view.findViewById(R.id.tv_site)
    internal var tv_select_date:TextView=view.findViewById(R.id.tv_select_date)
    internal var tv_camera: TextView=view.findViewById(R.id.tv_camera)
    internal var tv_total_spend:TextView=view.findViewById(R.id.tv_total_spend)
    internal var tv_labour_charge:TextView=view.findViewById(R.id.tv_labour_charge)
    internal var tv_part_cost:TextView=view.findViewById(R.id.tv_part_cost)
    internal var tv_total_cost:TextView=view.findViewById(R.id.tv_total_cost)
    internal var tv_total_costval:TextView=view.findViewById(R.id.tv_total_costval)
    internal var tv_date:TextView=view.findViewById(R.id.tv_date)
    internal var tv_submit: TextView=view.findViewById(R.id.tv_submit)
    internal var tv_notifytask:TextView=view.findViewById(R.id.tv_notifytask)
    internal var et_servicing_description: EditText=view.findViewById(R.id.et_servicing_description)
    internal var et_hour:EditText=view.findViewById(R.id.et_hour)
    internal var et_min:EditText=view.findViewById(R.id.et_min)
    internal var et_labourcharge:EditText=view.findViewById(R.id.et_labourcharge)
    internal var et_part_cost:EditText=view.findViewById(R.id.et_part_cost)
    internal var et_name_of_enginner:EditText=view.findViewById(R.id.et_name_of_enginner)
    internal var et_name_of_company:EditText=view.findViewById(R.id.et_name_of_company)
    internal var et_managername:EditText=view.findViewById(R.id.et_managername)
    internal var rl_fault_submit: RelativeLayout=view.findViewById(R.id.rl_fault_submit)
    internal var rl_camera:RelativeLayout=view.findViewById(R.id.rl_camera)
    internal var rl_notifywho:RelativeLayout=view.findViewById(R.id.rl_notifywho)
    internal var ll_bottom_servicng: LinearLayout=view.findViewById(R.id.ll_bottom_servicng)
    internal var img_qrcode: ImageView=view.findViewById(R.id.img_qrcode)
    var img_phato: ImageView=view.findViewById(R.id.img_phato)
    var img_engineer_sign:ImageView=view.findViewById(R.id.img_engineer_sign)
    var img_manager_sign:ImageView=view.findViewById(R.id.img_manager_sign)
    var rl_back_fixfault:RelativeLayout=view.findViewById(R.id.rl_back_fixfault)
    init {
        tv_preescan.setTypeface(getgothmlight(fixFaultActivity))
        tv_bcomp_name.setTypeface(getgothmlight(fixFaultActivity))
        tv_bcomp_element_name.setTypeface(getgothmlight(fixFaultActivity))
        tv_Company.setTypeface(getgothmlight(fixFaultActivity))
        tv_site.setTypeface(getgothmlight(fixFaultActivity))
        tv_select_work.setTypeface(getgothmlight(fixFaultActivity))
        tv_select_date.setTypeface(getgothmlight(fixFaultActivity))
        et_servicing_description.setTypeface(getgothmlight(fixFaultActivity))
        tv_camera.setTypeface(getbebas(fixFaultActivity))
        tv_total_spend.setTypeface(getgothmlight(fixFaultActivity))
        et_hour.setTypeface(getgothmlight(fixFaultActivity))
        tv_labour_charge.setTypeface(getgothmlight(fixFaultActivity))
        tv_part_cost.setTypeface(getgothmlight(fixFaultActivity))
        tv_total_cost.setTypeface(getgothmlight(fixFaultActivity))
        tv_total_costval.setTypeface(getgothmlight(fixFaultActivity))
        tv_date.setTypeface(getgothmlight(fixFaultActivity))
        tv_engineer_signaturehere.setTypeface(getgothmlight(fixFaultActivity))
        tv_manager_signaturehere.setTypeface(getgothmlight(fixFaultActivity))
        tv_submit.setTypeface(getbebas(fixFaultActivity))
        et_min.setTypeface(getgothmlight(fixFaultActivity))
        et_labourcharge.setTypeface(getgothmlight(fixFaultActivity))
        et_part_cost.setTypeface(getgothmlight(fixFaultActivity))
        et_name_of_enginner.setTypeface(getgothmlight(fixFaultActivity))
        et_name_of_company.setTypeface(getgothmlight(fixFaultActivity))

        et_managername.setTypeface(getgothmlight(fixFaultActivity))
        tv_notifytask.setTypeface(getbebas(fixFaultActivity))
    }
}