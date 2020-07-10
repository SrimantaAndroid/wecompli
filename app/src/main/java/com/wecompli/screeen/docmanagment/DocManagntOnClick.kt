package com.wecompli.screeen.docmanagment

import android.app.DatePickerDialog
import android.content.Intent
import android.view.View
import com.wecompli.R
import com.wecompli.screeen.notifyschedule.NotifyScheduleActivity
import com.wecompli.screeen.notifywho.NotifyWhoActivity
import java.util.*


class DocManagntOnClick(
    val docManagmentActivity: DocManagmentActivity,
    val docManagmentViewBind: DocManagmentViewBind):View.OnClickListener {
    init {
     docManagmentViewBind.rl_back_docmanagment!!.setOnClickListener(this)
        docManagmentViewBind.btn_choose_file!!.setOnClickListener(this)
        docManagmentViewBind.rl_start_date!!.setOnClickListener(this)
        docManagmentViewBind.rl_end_date!!.setOnClickListener(this)
        docManagmentViewBind.tv_notify_who!!.setOnClickListener(this)
        docManagmentViewBind.rl_reset!!.setOnClickListener(this)
        docManagmentViewBind.rl_submit!!.setOnClickListener(this)
        docManagmentViewBind.tv_select_week!!.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
       when(p0!!.id){
           R.id.rl_back_docmanagment->{
               docManagmentActivity.finish()
           }
           R.id.btn_choose_file->{
               docManagmentActivity.showalertforImageSelectio()
           }
           R.id.rl_end_date->{
               datepickerdefaultenddsate()
           }
           R.id.rl_start_date->{
               datepickerdefaultsratdate()
           }
           R.id.tv_notify_who->{
               val intent=Intent(docManagmentActivity,NotifyWhoActivity::class.java)
               docManagmentActivity.startActivityForResult(intent,2)
           }
           R.id.rl_reset->{
               clearallfeild()
           }
           R.id.rl_submit->{
               docManagmentActivity.callApifordocSubmit()
           }
           R.id.tv_select_week->{
               val intent=Intent(docManagmentActivity,NotifyScheduleActivity::class.java)
               docManagmentActivity.startActivityForResult(intent,1)
           }
       }
    }

    public fun clearallfeild() {
        //docManagmentViewBind.tv_Select_company!!.setText(docManagmentActivity.getResources().getString(R.string.select_company))
        //docManagmentViewBind!!.tv_select_site!!.setText(docManagmentActivity.getResources().getString(R.string.select_site_))
        docManagmentViewBind!!.et_document_name!!.setText("")
        docManagmentViewBind.tv_start_date!!.setText(docManagmentActivity.getResources().getString(R.string.start_date))
        docManagmentViewBind.tv_end_date!!.setText(docManagmentActivity.getResources().getString(R.string.end_date))
        docManagmentViewBind.tv_select_week!!.setText(docManagmentActivity.getResources().getString(R.string.notifyme_about_exp))
        docManagmentViewBind.tv_notify_who!!.setText(docManagmentActivity.getResources().getString(R.string.notify_who))
        docManagmentActivity!!.docImagelist.clear()
        docManagmentActivity.imageCertificateAdapter!!.notifyDataSetChanged()


    }

    fun datepickerdefaultenddsate() {
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog =
            DatePickerDialog(
                docManagmentActivity, R.style.DialogThemedatepicker,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val choosedate = padnumber(dayOfMonth) + "/" + padnumber(monthOfYear + 1) + "/" + year.toString()
                    val checkdate = year.toString() + "-" + padnumber(monthOfYear + 1) + "-" + padnumber(dayOfMonth)
                    val listcheckdate = padnumber(monthOfYear + 1) + "/" + padnumber(dayOfMonth) + "/" + year.toString()
                    docManagmentViewBind.tv_end_date!!.setText(choosedate)
                }, mYear, mMonth, mDay
            )
        // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show()
    }

    fun padnumber(n: Int): String {
        val num: String
        num = if (n >= 10) n.toString() else "0$n"
        return num
    }
    fun datepickerdefaultsratdate() {
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog =
            DatePickerDialog(
                docManagmentActivity, R.style.DialogThemedatepicker,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val choosedate: String = padnumber(dayOfMonth) + "/" + padnumber(monthOfYear + 1) + "/" + year.toString()
                    val checkdate = year.toString() + "-" + padnumber(monthOfYear + 1) + "-" + padnumber(dayOfMonth)
                    val listcheckdate: String = padnumber(monthOfYear + 1) + "/" + padnumber(dayOfMonth) + "/" + year.toString()
                    docManagmentViewBind.tv_start_date!!.setText(choosedate)
                }, mYear, mMonth, mDay
            )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }
}