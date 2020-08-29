package com.wecompli.screeen.fixfault

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.google.zxing.integration.android.IntentIntegrator
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.screeen.elementfaultlist.ElementFaultListActivity
import com.wecompli.screeen.notifywho.NotifyWhoActivity
import com.wecompli.utils.customalert.*
import java.util.*

class FixFaultOnClick(
   val fixFaultActivity: FixFaultActivity,
   val fixFaultViewBind: FixFaultViewBind

) :View.OnClickListener{
    init {
        fixFaultViewBind.img_qrcode.setOnClickListener(this)
        fixFaultViewBind.rl_camera.setOnClickListener(this)
        fixFaultViewBind.tv_engineer_signaturehere.setOnClickListener(this)
        fixFaultViewBind.tv_manager_signaturehere.setOnClickListener(this)
        fixFaultViewBind.tv_select_work.setOnClickListener(this)
        fixFaultViewBind.tv_date.setOnClickListener(this)
        fixFaultViewBind.et_hour.setOnClickListener(this)
        fixFaultViewBind.et_min.setOnClickListener(this)
        fixFaultViewBind.tv_select_date.setOnClickListener(this)
        fixFaultViewBind.rl_fault_submit.setOnClickListener(this)
        fixFaultViewBind.rl_notifywho.setOnClickListener(this)
        fixFaultViewBind!!.tv_select_work.setOnClickListener(this)
        fixFaultViewBind!!.rl_back_fixfault.setOnClickListener(this)
        fixFaultViewBind!!.tv_select_date.setOnClickListener(this)
        fixFaultViewBind!!.tv_bcomp_element_name.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.tv_bcomp_element_name->{
                val customPopUpFaultCheck= CustomPopUpFaultCheck(fixFaultActivity,fixFaultActivity!!.falultrow)
                customPopUpFaultCheck.show()
            }
            R.id.tv_date->{
                datepickerdefault()
            }
            R.id.et_hour->{
                opentimepickerhour()
            }
            R.id.img_qrcode->{
               // showalertforqr()
                IntentIntegrator(fixFaultActivity).initiateScan()
                val integrator = IntentIntegrator(fixFaultActivity)
                integrator.setOrientationLocked(true)
                integrator.initiateScan()
            }
            R.id.et_min->{
                opentimepickermin()
            }
            R.id.rl_camera->{
                fixFaultActivity.showPictureDialog()
            }
            R.id.tv_select_work->{
                CustomAlertForWorkType(fixFaultActivity).show()
            }
            R.id.tv_engineer_signaturehere->{
                TaptoSignEngineerDialog(fixFaultActivity).show()
            }
            R.id.tv_manager_signaturehere->{
                 TaptoSignManagerDialog(fixFaultActivity).show()
            }
            R.id.rl_back_fixfault->{
                fixFaultActivity.finish()
            }
            R.id.tv_select_date->{
                datepickerdnextservice()
            }
            R.id.rl_notifywho->{
                val intent=Intent(fixFaultActivity,NotifyWhoActivity::class.java)
                fixFaultActivity.startActivity(intent)
            }
            R.id.rl_fault_submit->{
                if (fixFaultViewBind.tv_bcomp_element_name.text.toString().equals("")){
                    Alert.showalert(fixFaultActivity,"Select Check name")
                    return
                }
               /* if (fixFaultViewBind.tv_bcomp_name.text.toString().equals("")){
                    Alert.showalert(fixFaultActivity,"Select Check list name")
                    return
                }*/

                if (fixFaultViewBind.tv_select_work.text.toString().equals("")){
                    Alert.showalert(fixFaultActivity,"Select Work Carried Today")
                    return
                    }
                if (fixFaultViewBind.tv_select_date.text.toString().equals("")){
                    Alert.showalert(fixFaultActivity,"Select Service for next service due date")
                    return
                }
                if (fixFaultViewBind.et_servicing_description.text.toString().equals("")){
                    Alert.showalert(fixFaultActivity,"Enter description.")
                    return
                }
                if (fixFaultActivity.selectfaultimage==false){
                    Alert.showalert(fixFaultActivity,"Choose Image for "+fixFaultViewBind.tv_select_work.text.toString())
                    return
                }
                if (fixFaultViewBind.et_hour.text.toString().equals("")){
                    Alert.showalert(fixFaultActivity,"Enter spend hour")
                    return
                }
                if (fixFaultViewBind.et_min.text.toString().equals("")){
                    Alert.showalert(fixFaultActivity,"Enter spend min")
                    return
                }
                if (fixFaultViewBind.et_labourcharge.text.toString().equals("")){
                    Alert.showalert(fixFaultActivity,"Enter Labour Charge")
                    return
                }
                if (fixFaultViewBind.et_part_cost.text.toString().equals("")){
                    Alert.showalert(fixFaultActivity,"Enter part cost")
                    return
                }
                if (fixFaultViewBind.et_name_of_enginner.text.toString().equals("")){
                    Alert.showalert(fixFaultActivity,"Enter Engineer name")
                    return
                }
                if (fixFaultViewBind.et_name_of_company.text.toString().equals("")){
                    Alert.showalert(fixFaultActivity,"Enter Company Name")
                    return
                }
                if (fixFaultViewBind.tv_date.text.toString().equals("")){
                    Alert.showalert(fixFaultActivity,"Select repair date")
                    return
                }
                if (fixFaultActivity.selectenginnersign==false){
                    Alert.showalert(fixFaultActivity,"Please Sign Engineer ")
                    return
                }
                if (fixFaultViewBind.et_managername.text.toString().equals("")){
                    Alert.showalert(fixFaultActivity,"Enter Manager name")
                    return
                }
                if (fixFaultActivity.selectmanagertimage==false){
                    Alert.showalert(fixFaultActivity,"Please Sign Manager ")
                    return
                }
                  fixFaultActivity.callApiforfaultSubmit()
            }
        }

    }
    private fun showalertforqr() {
        val deviceResolution = DeviceResolution(fixFaultActivity)
        val dialog = Dialog(fixFaultActivity)
        val view = LayoutInflater.from(fixFaultActivity)
            .inflate(R.layout.custom_alert_layout_for_qrcodescan, null)
        val tv_qrcodescan: TextView
        val tv_selectfault: TextView
        val tv_cancel: TextView
        tv_qrcodescan = view.findViewById(R.id.tv_qrcodescan)
        tv_selectfault = view.findViewById(R.id.tv_selectfault)
        tv_cancel = view.findViewById(R.id.tv_cancel)
        tv_qrcodescan.setTypeface(deviceResolution.getgothmbold(fixFaultActivity))
        tv_cancel.setTypeface(deviceResolution.getgothmbold(fixFaultActivity))
        tv_selectfault.setTypeface(deviceResolution.getgothmbold(fixFaultActivity))
        tv_cancel.setOnClickListener { dialog.dismiss() }

        tv_qrcodescan.setOnClickListener {
            dialog.dismiss()
               /*IntentIntegrator(fixFaultActivity).initiateScan();
                  var integrator:IntentIntegrator= IntentIntegrator(fixFaultActivity);
                  integrator.setOrientationLocked(false);
                  integrator.initiateScan();*/

            IntentIntegrator(fixFaultActivity).initiateScan()
            val integrator = IntentIntegrator(fixFaultActivity)
            integrator.setOrientationLocked(true)
            integrator.initiateScan()
           // IntentIntegrator.forSupportFragment(fixFaultActivity!!).initiateScan()
        }

        tv_selectfault.setOnClickListener {
            dialog.dismiss()
           val intent = Intent(fixFaultActivity, ElementFaultListActivity::class.java)
              fixFaultActivity.startActivityForResult(intent, 155);
            //homeActivity.startActivity(intent)
        }
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.BOTTOM
        lp.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes = lp
        dialog.setContentView(view)
        dialog.show()

        /* new IntentIntegrator(homeActivity).initiateScan();
        IntentIntegrator integrator = new IntentIntegrator(homeActivity);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();*/
    }

    fun opentimepickermin() {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(fixFaultActivity, R.style.DialogThemedatepicker,
            TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                fixFaultViewBind.et_min.setText(
                    padnumber(selectedMinute)
                )
            }, hour, minute, true
        )//Yes 24 hour time

        mTimePicker.setTitle("Select Min")
        mTimePicker.show()
    }
    fun opentimepickerhour() {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(fixFaultActivity, R.style.DialogThemedatepicker,
            TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                fixFaultViewBind.et_hour.setText(padnumber(selectedHour))
            }, hour, minute, true
        )//Yes 24 hour time
        mTimePicker.setTitle("Select Hour")
        mTimePicker.show()
    }

    fun datepickerdefault() {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(fixFaultActivity, R.style.DialogThemedatepicker,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val choosedate =
                    padnumber(dayOfMonth) + "/" + padnumber(monthOfYear + 1) + "/" + year.toString()
                val checkdate =
                    year.toString() + "-" + padnumber(monthOfYear + 1) + "-" + padnumber(dayOfMonth)
                val listcheckdate =
                    padnumber(monthOfYear + 1) + "/" + padnumber(dayOfMonth) + "/" + year.toString()
                fixFaultViewBind.tv_date.setText(listcheckdate)
            }, mYear, mMonth, mDay
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()

    }

    fun padnumber(n: Int): String {
        val num: String
        if (n > 10)
            num = n.toString()
        else
            num = "0$n"
        return num
    }
    fun datepickerdnextservice() {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(fixFaultActivity, R.style.DialogThemedatepicker,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val choosedate =
                    padnumber(dayOfMonth) + "/" + padnumber(monthOfYear + 1) + "/" + year.toString()
                val checkdate =
                    year.toString() + "-" + padnumber(monthOfYear + 1) + "-" + padnumber(dayOfMonth)
                val listcheckdate =
                    padnumber(monthOfYear + 1) + "/" + padnumber(dayOfMonth) + "/" + year.toString()
                fixFaultViewBind.tv_select_date.setText(listcheckdate)
            }, mYear, mMonth, mDay
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()

    }
}