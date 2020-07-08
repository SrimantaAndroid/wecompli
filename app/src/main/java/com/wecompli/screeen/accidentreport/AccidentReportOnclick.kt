package com.wecompli.screeen.accidentreport

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.view.View
import android.widget.SeekBar
import com.wecompli.R
import com.wecompli.screeen.notifywho.NotifyWhoActivity
import com.wecompli.utils.customalert.TaptoSignSubmitAccidentReport
import com.wecompli.utils.sheardpreference.PreferenceConstent
import java.util.*


class AccidentReportOnclick(
    var accidentReportActivity: AccidentReportActivity,
    var  accidentReportViewBind: AccidentReportViewBind) :View.OnClickListener{

    init {
        accidentReportViewBind.rl_back_accidentreport!!.setOnClickListener(this)
        accidentReportViewBind.rl_chooseimage!!.setOnClickListener(this)
        accidentReportViewBind.rl_sign_behalf!!.setOnClickListener(this)

        accidentReportViewBind.rl_witness1!!.setOnClickListener(this)
        accidentReportViewBind.rl_witness2!!.setOnClickListener(this)
        accidentReportViewBind.rl_completed!!.setOnClickListener(this)
        accidentReportViewBind.rl_injured!!.setOnClickListener(this)
        accidentReportViewBind.tv_bodymap!!.setOnClickListener(this)
        accidentReportViewBind.tv_date2!!.setOnClickListener(this)
        accidentReportViewBind.et_date!!.setOnClickListener(this)
        accidentReportViewBind.tv_datepatient!!.setOnClickListener(this)
        accidentReportViewBind.tv_dateofoccurenceet_postcode_person!!.setOnClickListener(this)
        accidentReportViewBind.tv_date_of_birth!!.setOnClickListener(this)
        accidentReportViewBind.tv_timeof_occurence!!.setOnClickListener(this)
        accidentReportViewBind.btn_notify_who!!.setOnClickListener(this)
        accidentReportViewBind.btnsubmit!!.setOnClickListener(this)
        accidentReportViewBind.tv_select_location!!.setOnClickListener(this)



        accidentReportViewBind.seek_servertylevel!!.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                    accidentReportViewBind.tv_servity!!.setText(
                        accidentReportActivity.getResources().getString(R.string.servertylevel).toString() + " " + progress.toString())
                    accidentReportActivity.servityvalue = progress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })

        accidentReportViewBind.rg_completeform!!.setOnCheckedChangeListener { radioGroup, id ->
            // accidentReportViewBind.radio_details.setChecked(false);
            if (id == R.id.radio_employee) {
                accidentReportActivity.usertppe = "Employee"
            } else if (id == R.id.radio_customer) {
                accidentReportActivity.usertppe = "Customer"
            } else if (id == R.id.radio_visitor) {
                accidentReportActivity.usertppe = "Visitor"
            }
            accidentReportViewBind.et_detailsofother!!.isEnabled = false
            // accidentReportViewBind.radio_details.setChecked(false);
        }
        accidentReportViewBind.rg_servety_level!!.setOnCheckedChangeListener { radioGroup, id ->
            if (id == R.id.rb_major) {
                accidentReportActivity.servityvalue = "Major"
            } else if (id == R.id.rb_minor) {
                accidentReportActivity.servityvalue = "minor"
            }
        }
        accidentReportViewBind.radio_details!!.setOnCheckedChangeListener { compoundButton, b ->
            accidentReportViewBind.rg_completeform!!.clearCheck()
            accidentReportViewBind.radio_details!!.isChecked = true
            accidentReportViewBind.et_detailsofother!!.isEnabled = true
            accidentReportActivity.usertppe = "Other"
        }
    }
    override fun onClick(p0: View?) {
    when(p0!!.id){
        R.id.rl_back_accidentreport->{
            accidentReportActivity.finish()
        }
        R.id.tv_select_location->{

        }
        R.id.btnsubmit->{

        }
        R.id.btn_notify_who->{
            val intent=Intent(accidentReportActivity,NotifyWhoActivity::class.java)
            accidentReportActivity.startActivityForResult(intent,2)
        }
        R.id.tv_timeof_occurence->{
            opentimepicker()
        }
        R.id.tv_date_of_birth->{
            datepickefordateofbirth()
        }
        R.id.tv_dateofoccurenceet_postcode_person->{

        }
        R.id.rl_chooseimage->{

        }
        R.id.rl_sign_behalf->{
            TaptoSignSubmitAccidentReport(accidentReportActivity,accidentReportViewBind!!.img_signbehlf!!,
                accidentReportViewBind!!.tv_signedconfirm!!, PreferenceConstent.SignonBehalf).show();
        }
        R.id.rl_witness1->{
            TaptoSignSubmitAccidentReport(accidentReportActivity,accidentReportViewBind.img_witeness1,
                accidentReportViewBind.tv_witeness1,PreferenceConstent.Signbywiteness1).show();
        }
        R.id.rl_witness2->{
            TaptoSignSubmitAccidentReport(accidentReportActivity,accidentReportViewBind.imgwiteness2,accidentReportViewBind.tv_sin_witess2,PreferenceConstent.Signbywiteness2).show();

        }
        R.id.rl_completed->{
            TaptoSignSubmitAccidentReport(accidentReportActivity,accidentReportViewBind.img_sign_completedby,accidentReportViewBind.tv_signcompleted,PreferenceConstent.SignCompletredby).show();

        }

        R.id.rl_injured->{
            TaptoSignSubmitAccidentReport(accidentReportActivity,accidentReportViewBind.img_injuredperson!!,accidentReportViewBind.tv_sign_injuredperson,PreferenceConstent.Signbyinjuredpuresn).show();

        }
        R.id.tv_bodymap->{

        }
        R.id.tv_date2->{
            datepickeforeitnedddate1()
        }
        R.id.tv_datepatient->{
            datepickeforepatient()
        }
        R.id.et_date1->{
            datepickeforewitness1date()
        }
     }
    }
    fun padnumber(n: Int): String? {
        val num: String
        num = if (n > 10) n.toString() else "0$n"
        return num
    }
    fun opentimepicker() {
        val c = Calendar.getInstance()
        val mHour = c[Calendar.HOUR_OF_DAY]
        val mMinute = c[Calendar.MINUTE]

        // Launch Time Picker Dialog
        val timePickerDialog =
            TimePickerDialog(accidentReportActivity, R.style.DialogThemedatepicker,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute -> accidentReportViewBind.tv_timeof_occurence!!.text =
                        padnumber(hourOfDay) + ":" + padnumber(minute) + ":" + "00"
                }, mHour, mMinute, true
            )
        timePickerDialog.show()
    }
    fun datepickeforepatient() {
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog =
            DatePickerDialog(accidentReportActivity, R.style.DialogThemedatepicker,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val choosedate = padnumber(dayOfMonth) + "/" + padnumber(monthOfYear + 1) + "/" + year.toString()
                    val checkdate = year.toString() + "-" + padnumber(monthOfYear + 1) + "-" + padnumber(dayOfMonth)
                    val listcheckdate = padnumber(monthOfYear + 1) + "/" + padnumber(dayOfMonth) + "/" + year.toString()
                    accidentReportViewBind.tv_datepatient!!.text = choosedate
                }, mYear, mMonth, mDay
            )
        //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show()
    }
    fun datepickeforeitnedddate1() {
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog =
            DatePickerDialog(accidentReportActivity, R.style.DialogThemedatepicker,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val choosedate: String = padnumber(dayOfMonth) + "/" + padnumber(monthOfYear + 1) + "/" + year.toString()
                    val checkdate = year.toString() + "-" + padnumber(monthOfYear + 1) + "-" + padnumber(dayOfMonth)
                    val listcheckdate: String = padnumber(monthOfYear + 1) + "/" + padnumber(dayOfMonth) + "/" + year.toString()
                    accidentReportViewBind.tv_date2!!.text = choosedate
                }, mYear, mMonth, mDay
            )
        //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show()
    }
    fun datepickefordateofbirth() {
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog =
            DatePickerDialog(accidentReportActivity, R.style.DialogThemedatepicker,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val choosedate = padnumber(dayOfMonth) + "/" + padnumber(monthOfYear + 1) + "/" + year.toString()
                    val checkdate = year.toString() + "-" + padnumber(monthOfYear + 1) + "-" + padnumber(dayOfMonth)
                    val listcheckdate = padnumber(monthOfYear + 1) + "/" + padnumber(dayOfMonth) + "/" + year.toString()
                    accidentReportViewBind.tv_date_of_birth!!.text = choosedate
                }, mYear, mMonth, mDay
            )
        //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show()
    }

    fun datepickeforewitness1date() {
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog =
            DatePickerDialog(accidentReportActivity, R.style.DialogThemedatepicker,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val choosedate = padnumber(dayOfMonth) + "/" + padnumber(monthOfYear + 1) + "/" + year.toString()
                    val checkdate = year.toString() + "-" + padnumber(monthOfYear + 1) + "-" + padnumber(dayOfMonth)
                    val listcheckdate = padnumber(monthOfYear + 1) + "/" + padnumber(dayOfMonth) + "/" + year.toString()
                    accidentReportViewBind.et_date!!.text = choosedate
                }, mYear, mMonth, mDay
            )
        //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show()
    }

}