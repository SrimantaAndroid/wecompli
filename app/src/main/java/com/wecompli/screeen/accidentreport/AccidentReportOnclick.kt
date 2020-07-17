package com.wecompli.screeen.accidentreport

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.view.View
import android.widget.SeekBar
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.R
import com.wecompli.apiresponsemodel.location.LocatioApiResponse
import com.wecompli.apiresponsemodel.location.LocationRow
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.screeen.bodymap.ActivityBodyMap
import com.wecompli.screeen.notifywho.NotifyWhoActivity
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.customalert.TaptoSignSubmitAccidentReport
import com.wecompli.utils.custompopupdialogforsite.AccidentLocationDialog
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class AccidentReportOnclick(
    var accidentReportActivity: AccidentReportActivity,
    var  accidentReportViewBind: AccidentReportViewBind) :View.OnClickListener{
    var onItemClickInterface: OnItemClickInterface? = null
     var  accidentLocationDialog:AccidentLocationDialog?=null
    var locationlist: List<LocationRow> = ArrayList<LocationRow>()
    var usertype:String="";
    var bodymaptext:String="";
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
        accidentReportViewBind.btnsubmit!!.setOnClickListener(this)



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
            //accidentReportViewBind.radio_customer!!.isChecked = false
            //  accidentReportViewBind.radio_details!!.isEnabled = false
             accidentReportViewBind.radio_details!!.setChecked(false);
        }
        accidentReportViewBind.rg_servety_level!!.setOnCheckedChangeListener { radioGroup, id ->
            if (id == R.id.rb_major) {
                accidentReportActivity.servityvalue = "Major"
            } else if (id == R.id.rb_minor) {
                accidentReportActivity.servityvalue = "minor"
            }
        }
        accidentReportViewBind!!.radio_details!!.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                accidentReportViewBind.radio_employee!!.isChecked = false
                accidentReportViewBind.radio_customer!!.isChecked = false
                accidentReportViewBind.radio_visitor!!.isChecked = false
                //accidentReportViewBind.radio_details!!.isChecked = true
                accidentReportViewBind.et_detailsofother!!.isEnabled = true
                accidentReportActivity.usertppe = "Other"
            }
        }
       /* accidentReportViewBind.rg_other!!.setOnCheckedChangeListener { radioGroup, i ->
            if (i==R.id.radio_details) {
                accidentReportViewBind.radio_employee!!.isChecked = false
                accidentReportViewBind.radio_customer!!.isChecked = false
                accidentReportViewBind.radio_visitor!!.isChecked = false
                //accidentReportViewBind.radio_details!!.isChecked = true
                accidentReportViewBind.et_detailsofother!!.isEnabled = true
                accidentReportActivity.usertppe = "Other"
            }
        }*/
       /* accidentReportViewBind.radio_details!!.setOnClickListener {
            if (!accidentReportViewBind!!.radio_details!!.isSelected()) {
                accidentReportViewBind!!.radio_details!!.setChecked(true);
                accidentReportViewBind!!.radio_details!!.setSelected(true);
                accidentReportViewBind.radio_employee!!.isChecked = false
                accidentReportViewBind.radio_customer!!.isChecked = false
                accidentReportViewBind.radio_visitor!!.isChecked = false
            } else {
                accidentReportViewBind!!.radio_details!!.setChecked(false);
                accidentReportViewBind!!.radio_details!!.setSelected(false);
            }

        }*/
        /*accidentReportViewBind.rg_other!!.setOnCheckedChangeListener { radioGroup, id ->
            if (id==R.id.radio_details) {
                accidentReportViewBind.radio_employee!!.isChecked = false
                accidentReportViewBind.radio_customer!!.isChecked = false
                accidentReportViewBind.radio_visitor!!.isChecked = false
                //accidentReportViewBind.radio_details!!.isChecked = true
                accidentReportViewBind.et_detailsofother!!.isEnabled = true
                accidentReportActivity.usertppe = "Other"
            }
        }*/
    }
    override fun onClick(p0: View?) {
    when(p0!!.id){
        R.id.rl_back_accidentreport->{
            accidentReportActivity.finish()
        }
        R.id.tv_select_location->{
            callApiforlocationList()


        }
        R.id.btnsubmit->{
            checkvalidation()

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
            datepickeforoccurence()
        }
        R.id.rl_chooseimage->{
           accidentReportActivity.showalertforImageSelectio()
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
            val intent = Intent(
                accidentReportActivity, ActivityBodyMap::class.java)
                accidentReportActivity.startActivityForResult(intent, 500)

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

    private fun checkvalidation() {

        if (accidentReportActivity.servityvalue.equals("")){
            Alert.showalert(accidentReportActivity,"Select Servity value.")
            return
        }
     if (accidentReportViewBind.et_nameof_organization!!.text.toString()!!.equals("")){
         accidentReportViewBind!!.et_nameof_organization!!.requestFocus()
         Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.nameoforganization))
         return
     }
        if (accidentReportViewBind.et_organizationaddress!!.text.toString().equals("")){
            accidentReportViewBind!!.et_organizationaddress!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.addressoforganization))
            return
        }
        if (accidentReportViewBind.et_postcode!!.text.toString().equals("")){
            accidentReportViewBind!!.et_postcode!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.postcode))
           return
        }
        if (accidentReportViewBind.et_telephone!!.text.toString().equals("")){
            accidentReportViewBind!!.et_telephone!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.telephone))
            return
        }
        if (accidentReportViewBind.et_fullnameofperson_injured!!.text.toString().equals("")){
            accidentReportViewBind!!.et_fullnameofperson_injured!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.fullnamepersoninjured))
            return
        }
        /*if (accidentReportViewBind.et_telephone!!.text.toString().equals("")){
            accidentReportViewBind!!.et_telephone!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.fullnamepersoninjured))
            return
        }*/
        if (accidentReportViewBind.et_personhomeaddress!!.text.toString().equals("")){
            accidentReportViewBind!!.et_personhomeaddress!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.personhomeaddress))
            return
        }
        if (accidentReportViewBind.et_postcode_person!!.text.toString().equals("")){
            accidentReportViewBind!!.et_postcode_person!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.postcode))
            return
        }
        if (accidentReportViewBind.et_telephone_person!!.text.toString().equals("")){
            accidentReportViewBind!!.et_telephone_person!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.telephone))
            return
        }
        if (accidentReportViewBind.tv_date_of_birth!!.text.toString().equals("")){
           // accidentReportViewBind!!.et_telephone_person!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.dateofbirth))
            return
        }
         if (accidentReportActivity.usertppe.equals("")){
             Alert.showalert(accidentReportActivity,"Select user type")
             return
         }
        if (accidentReportViewBind.tv_dateofoccurenceet_postcode_person!!.text.toString().equals("")){
            // accidentReportViewBind!!.et_telephone_person!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.dateofoccurence))
            return
        }
        if (accidentReportViewBind.tv_timeof_occurence!!.text.toString().equals("")){
            // accidentReportViewBind!!.et_telephone_person!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.timeofoccurence))
            return
        }
        if (accidentReportViewBind.tv_select_location!!.text.toString().equals("")){
            // accidentReportViewBind!!.et_telephone_person!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.selectaccidentlocation))
            return
        }
        if (accidentReportViewBind.tv_select_location!!.text.toString().equals("")){
            // accidentReportViewBind!!.et_telephone_person!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.selectaccidentlocation))
            return
        }
        if (accidentReportViewBind.et_fulldescrtpttion!!.text.toString().equals("")){
             accidentReportViewBind!!.et_fulldescrtpttion!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Enter full description of body.")
            return
        }
        if (bodymaptext.equals("")){
            Alert.showalert(accidentReportActivity,"Select injured body part")
            return
        }
        if (accidentReportViewBind.et_fulldes_inj!!.text.toString().equals("")){
            accidentReportViewBind!!.et_fulldes_inj!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Enter full description injured person.")
            return
        }
        if (accidentReportViewBind.et_injuredperdesc!!.text.toString().equals("")){
            accidentReportViewBind!!.et_injuredperdesc!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Enter description of injured person.")
            return
        }
        if (accidentReportViewBind.et_off_duty!!.text.toString().equals("")){
            accidentReportViewBind!!.et_off_duty!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Enter off-duty time.")
            return
        }
        if (accidentReportViewBind.et_prientname!!.text.toString().equals("")){
            accidentReportViewBind!!.et_prientname!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.patientname))
            return
        }
        if (accidentReportViewBind.et_position!!.text.toString().equals("")){
            accidentReportViewBind!!.et_position!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Enter patient position.")
            return
        }
        if (accidentReportViewBind.tv_datepatient!!.text.toString().equals("")){
            accidentReportViewBind!!.tv_datepatient!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Enter patient Date.")
            return
        }
        if (accidentReportViewBind.et_statementdetails!!.text.toString().equals("")){
            accidentReportViewBind!!.et_statementdetails!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.statementdetails))
            return
        }
        if (accidentReportViewBind.patient1!!.text.toString().equals("")){
            accidentReportViewBind!!.patient1!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Witness1 person name.")
            return
        }
        if (accidentReportViewBind.et_date!!.text.toString().equals("")){
            accidentReportViewBind!!.et_date!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Witness1 Signed date.")
            return
        }
        if (accidentReportViewBind.et_address_wite!!.text.toString().equals("")){
            accidentReportViewBind!!.et_address_wite!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Enter Witness1 address.")
            return
        }
        if (accidentReportViewBind.et_postcodewe1!!.text.toString().equals("")){
            accidentReportViewBind!!.et_postcodewe1!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Enter Witness1 postcode.")
            return
        }

        if (accidentReportViewBind.et_statementdetails2!!.text.toString().equals("")){
            accidentReportViewBind!!.et_statementdetails2!!.requestFocus()
            Alert.showalert(accidentReportActivity,accidentReportActivity.resources.getString(R.string.statementdetails)+"witness 2")
            return
        }
        if (accidentReportViewBind.patient2!!.text.toString().equals("")){
            accidentReportViewBind!!.patient1!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Witness2 person name.")
            return
        }
        if (accidentReportViewBind.tv_date2!!.text.toString().equals("")){
            //accidentReportViewBind!!.et_date!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Witness2 Signed date.")
            return
        }
        if (accidentReportViewBind.et_address_wite2!!.text.toString().equals("")){
            accidentReportViewBind!!.et_address_wite2!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Enter Witness2 address.")
            return
        }
        if (accidentReportViewBind.et_postcodewe2!!.text.toString().equals("")){
            accidentReportViewBind!!.et_postcodewe2!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Enter Witness2 postcode.")
            return
        }
        if (accidentReportViewBind.et_complientby!!.text.toString().equals("")){
            accidentReportViewBind!!.et_complientby!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Enter Name who completed the form.")
            return
        }
        if (accidentReportViewBind.et_injuredperson!!.text.toString().equals("")){
            accidentReportViewBind!!.et_injuredperson!!.requestFocus()
            Alert.showalert(accidentReportActivity,"Enter Injured person Name who completed the form")
            return
        }
        if (accidentReportActivity.signed_employment_person==null){
            Alert.showalert(accidentReportActivity,"Choose Image for Injured person.")
           return
        }
        if (accidentReportActivity.witness1==null){
            Alert.showalert(accidentReportActivity,"Choose Image for Witness1 person.")
            return
        }
        if (accidentReportActivity.witness2==null){
            Alert.showalert(accidentReportActivity,"Choose Image for Witness2 person.")
            return
        }
        if (accidentReportActivity.form_completed_person_signed==null){
            Alert.showalert(accidentReportActivity,"Choose Image for DForm completed person.")
            return
        }
        if (accidentReportActivity.form_completed_injured_person_signed==null){
            Alert.showalert(accidentReportActivity,"Choose Image for Injured person.")
            return
        }
       accidentReportActivity.callAccidentreportsubmitApi()

    }

    private fun callApiforlocationList() {
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(accidentReportActivity, "Please Wait..", false)
        val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("status_id","1")
            paramObject.put("site_id", AppSheardPreference(accidentReportActivity).getvalue_in_preference(PreferenceConstent.site_id)
            )

            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject

            val calltodayseasonApi = apiInterface.calllocationlistapi(
                AppSheardPreference(accidentReportActivity).getvalue_in_preference(PreferenceConstent.loginuser_token),
                AppSheardPreference(accidentReportActivity).getvalue_in_preference(PreferenceConstent.site_id), gsonObject)
            calltodayseasonApi.enqueue( object :Callback<LocatioApiResponse>{
                override fun onResponse(call: Call<LocatioApiResponse>, response: Response<LocatioApiResponse>) {
                    customProgress.hideProgress()
                    if (response.body()!!.status){
                        locationlist= response.body()!!.row
                        accidentLocationDialog = AccidentLocationDialog(accidentReportActivity, locationlist,object :OnItemClickInterface{
                            override fun OnItemClick(position: Int) {
                                     accidentReportViewBind.tv_select_location!!.setText(locationlist.get(position).location_name)
                                           bodymaptext=locationlist.get(position).location_name;
                                     accidentLocationDialog!!.dismiss()
                            }
                        })
                        accidentLocationDialog!!.show()
                    }
                }

                override fun onFailure(call: Call<LocatioApiResponse>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })
        }catch (e:Exception){
            e.printStackTrace()
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
    fun datepickeforoccurence() {
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog =
            DatePickerDialog(
                accidentReportActivity, R.style.DialogThemedatepicker,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val choosedate = padnumber(dayOfMonth) + "/" + padnumber(monthOfYear + 1) + "/" + year.toString()
                    val checkdate = year.toString() + "-" + padnumber(monthOfYear + 1) + "-" + padnumber(dayOfMonth)
                    val listcheckdate = padnumber(monthOfYear + 1) + "/" + padnumber(dayOfMonth) + "/" + year.toString()
                    accidentReportViewBind.tv_dateofoccurenceet_postcode_person!!.text = choosedate
                }, mYear, mMonth, mDay
            )
        //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show()
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