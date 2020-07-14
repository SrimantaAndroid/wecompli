package com.wecompli.screeen.accidentreport

import android.graphics.PorterDuff
import android.view.View
import android.widget.*
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R

class AccidentReportViewBind(
    var accidentReportActivity: AccidentReportActivity,
    var view: View) :DeviceResolution(accidentReportActivity){
    var rl_back_accidentreport: RelativeLayout? = null
    var rl_chooseimage: RelativeLayout? = null
    var rl_sign_behalf: RelativeLayout? = null
    var rl_witness1: RelativeLayout? = null
    var rl_witness2: RelativeLayout? = null
    var rl_completed: RelativeLayout? = null
    var rl_injured: RelativeLayout? = null
    var tv_accident_form: TextView? = null
    var tv_servity: TextView? = null
    var tv_detailsoforgnization: TextView? = null
    var tv_company: TextView? = null
    var tv_site: TextView? = null
    var tv_detailsofpersoninjoured: TextView? = null
    var tv_detailsof_emp_complete_theform: TextView? = null
    var tv_appoprietbox: TextView? = null
    var tv_description_of_accident: TextView? = null
    var tv_fulldesc: TextView? = null
    var tv_fulldescinju: TextView? = null
    var tv_select_location: TextView? = null
    var tv_dateofoccurenceet_postcode_person: TextView? = null
    var tv_timeof_occurence: TextView? = null
    var tv_bodymap: TextView? = null
    var tv_bodymaptext: TextView? = null
    var tv_takephatoorvideo: TextView? = null
    var tv_emploment_details: TextView? = null
    var tv_empdetails: TextView? = null
    var tv_offdutytime: TextView? = null
    var tv_duty_continue: TextView? = null
    var tv_wentduty: TextView? = null
    var tv_confirm: TextView? = null
    var tv_signedconfirm: TextView? = null
    var tv_datepatient: TextView? = null
    var tvappert: TextView? = null
    var tv_statement: TextView? = null
    var tv_statement2: TextView? = null
    var tv_sin_witess2: TextView? = null
    var tv_date2: TextView? = null
    var tv_tocomply: TextView? = null
    var tv_notethis: TextView? = null
    var tv_signcompleted: TextView? = null
    var tv_sign_injuredperson: TextView? = null
    var tv_date_of_birth: TextView? = null
    var tv_witeness1: TextView? = null
    var et_date: TextView? = null
    var btnsubmit: Button? = null
    var btn_notify_who: Button? = null
    var seek_servertylevel: SeekBar? = null
    var et_nameof_organization: EditText? = null
    var et_organizationaddress: EditText? = null
    var et_postcode: EditText? = null
    var et_telephone: EditText? = null
    var et_fullnameofperson_injured: EditText? = null
    var et_postcode_person: EditText? = null
    var et_personhomeaddress: EditText? = null
    var et_telephone_person: EditText? = null
    var et_detailsofother: EditText? = null
    var et_fulldescrtpttion: EditText? = null
    var et_fulldes_inj: EditText? = null
    var et_injuredperdesc: EditText? = null
    var et_off_duty: EditText? = null
    var et_continurtowork: EditText? = null
    var et_off_duty_time: EditText? = null
    var et_prientname: EditText? = null
    var et_position: EditText? = null
    var et_statementdetails: EditText? = null
    var patient1: EditText? = null
    var et_address_wite: EditText? = null
    var et_postcodewe1: EditText? = null
    var et_statementdetails2: EditText? = null
    var patient2: EditText? = null
    var et_address_wite2: EditText? = null
    var et_postcodewe2: EditText? = null
    var et_complientby: EditText? = null
    var et_injuredperson: EditText? = null
    var rg_completeform: RadioGroup? = null
    var rg_servety_level: RadioGroup? = null
    var radio_employee: RadioButton? = null
    var radio_customer: RadioButton? = null
    var radio_visitor: RadioButton? = null
    var radio_details: CheckBox? = null
    var imgcamera: ImageView? = null
    var img_signbehlf: ImageView? = null
    var img_sign_completedby: ImageView? = null
    var img_witeness1: ImageView? = null
    var imgwiteness2: ImageView? = null
    var img_injuredperson: ImageView? = null
    var img_select: ImageView? = null
    var ll_takephato: LinearLayout? = null
    var chk_iscontent: CheckBox? = null
    var rg_other:RadioGroup?=null
    init {
        viewbinds(view)
        settypeface()
    }

    private fun settypeface() {
        tv_accident_form!!.setTypeface(getbebas(accidentReportActivity))
        btn_notify_who!!.setTypeface(getbebas(accidentReportActivity))
        btnsubmit!!.setTypeface(getbebas(accidentReportActivity))
        tv_servity!!.setTypeface(getbebas(accidentReportActivity))
        tv_detailsoforgnization!!.setTypeface(getbebas(accidentReportActivity))
        tv_company!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_site!!.setTypeface(getgothmlight(accidentReportActivity))
        et_nameof_organization!!.setTypeface(getgothmlight(accidentReportActivity))
        et_organizationaddress!!.setTypeface(getgothmlight(accidentReportActivity))
        et_postcode!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_detailsofpersoninjoured!!.setTypeface(getbebas(accidentReportActivity))
        et_fullnameofperson_injured!!.setTypeface(getgothmlight(accidentReportActivity))

        et_personhomeaddress!!.setTypeface(getgothmlight(accidentReportActivity))
        et_postcode_person!!.setTypeface(getgothmlight(accidentReportActivity))
        et_telephone_person!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_date_of_birth!!.setTypeface(getgothmlight(accidentReportActivity))

        tv_detailsof_emp_complete_theform!!.setTypeface(getbebas(accidentReportActivity))
        tv_appoprietbox!!.setTypeface(getgothmlight(accidentReportActivity))
        radio_employee!!.setTypeface(getgothmbold(accidentReportActivity))
        radio_customer!!.setTypeface(getgothmbold(accidentReportActivity))
        radio_visitor!!.setTypeface(getgothmbold(accidentReportActivity))
        radio_details!!.setTypeface(getgothmbold(accidentReportActivity))

        et_detailsofother!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_description_of_accident!!.setTypeface(getbebas(accidentReportActivity))
        tv_fulldesc!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_fulldescinju!!.setTypeface(getgothmlight(accidentReportActivity))

        et_fulldes_inj!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_select_location!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_dateofoccurenceet_postcode_person!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_timeof_occurence!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_bodymap!!.setTypeface(getgothmbold(accidentReportActivity))
        tv_bodymaptext!!.setTypeface(getgothmlight(accidentReportActivity))
        et_fulldescrtpttion!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_takephatoorvideo!!.setTypeface(getbebas(accidentReportActivity))

        tv_emploment_details!!.setTypeface(getbebas(accidentReportActivity))
        et_injuredperdesc!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_empdetails!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_offdutytime!!.setTypeface(getgothmlight(accidentReportActivity))
        et_off_duty!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_duty_continue!!.setTypeface(getgothmlight(accidentReportActivity))
        et_fulldescrtpttion!!.setTypeface(getgothmlight(accidentReportActivity))
        et_continurtowork!!.setTypeface(getgothmlight(accidentReportActivity))

        et_off_duty_time!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_confirm!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_wentduty!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_signedconfirm!!.setTypeface(getgothmbold(accidentReportActivity))
        et_prientname!!.setTypeface(getgothmlight(accidentReportActivity))
        et_position!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_datepatient!!.setTypeface(getgothmlight(accidentReportActivity))
        tvappert!!.setTypeface(getgothmlight(accidentReportActivity))

        tv_statement2!!.setTypeface(getbebas(accidentReportActivity))
        tv_statement!!.setTypeface(getbebas(accidentReportActivity))
        tv_sin_witess2!!.setTypeface(getgothmbold(accidentReportActivity))
        et_statementdetails2!!.setTypeface(getgothmlight(accidentReportActivity))
        patient2!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_date2!!.setTypeface(getgothmlight(accidentReportActivity))
        et_position!!.setTypeface(getgothmlight(accidentReportActivity))
        et_address_wite2!!.setTypeface(getgothmlight(accidentReportActivity))
        et_postcodewe2!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_witeness1!!.setTypeface(getgothmbold(accidentReportActivity))
        tv_tocomply!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_notethis!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_signcompleted!!.setTypeface(getgothmbold(accidentReportActivity))
        et_complientby!!.setTypeface(getgothmlight(accidentReportActivity))
        tv_sign_injuredperson!!.setTypeface(getgothmbold(accidentReportActivity))
        et_position!!.setTypeface(getgothmlight(accidentReportActivity))
        et_injuredperson!!.setTypeface(getgothmlight(accidentReportActivity))
        chk_iscontent!!.setTypeface(getgothmbold(accidentReportActivity))
    }

    private fun viewbinds(view: View) {
        rl_back_accidentreport = view.findViewById(R.id.rl_back_accidentreport)
        tv_accident_form = view.findViewById(R.id.tv_accident_form)
        btnsubmit = view.findViewById(R.id.btnsubmit)
        btn_notify_who = view.findViewById(R.id.btn_notify_who)
        tv_servity = view.findViewById(R.id.tv_servity)
        tv_detailsoforgnization = view.findViewById(R.id.tv_detailsoforgnization)
        seek_servertylevel = view.findViewById(R.id.seek_servertylevel)
        tv_company = view.findViewById(R.id.tv_company)
        tv_site = view.findViewById(R.id.tv_site)

        et_nameof_organization =
            view.findViewById(R.id.et_nameof_organization)
        et_organizationaddress =
            view.findViewById(R.id.et_organizationaddress)
        et_postcode = view.findViewById(R.id.et_postcode)
        et_telephone = view.findViewById(R.id.et_telephone)
        tv_detailsofpersoninjoured =
            view.findViewById(R.id.tv_detailsofpersoninjoured)
        et_fullnameofperson_injured =
            view.findViewById(R.id.et_fullnameofperson_injured)
        et_personhomeaddress = view.findViewById(R.id.et_personhomeaddress)
        et_postcode_person = view.findViewById(R.id.et_postcode_person)
        et_telephone_person = view.findViewById(R.id.et_telephone_person)

        tv_date_of_birth = view.findViewById(R.id.tv_date_of_birth)
        tv_detailsof_emp_complete_theform =
            view.findViewById(R.id.tv_detailsof_emp_complete_theform)
        tv_appoprietbox = view.findViewById(R.id.tv_appoprietbox)
        rg_completeform = view.findViewById(R.id.rg_completeform)
        radio_employee = view.findViewById(R.id.radio_employee)
        radio_customer = view.findViewById(R.id.radio_customer)
        radio_visitor = view.findViewById(R.id.radio_visitor)
        radio_details = view.findViewById(R.id.radio_details)
        et_detailsofother = view.findViewById(R.id.et_detailsofother)

        tv_description_of_accident =
            view.findViewById(R.id.tv_description_of_accident)
        tv_fulldesc = view.findViewById(R.id.tv_fulldesc)
        tv_fulldescinju = view.findViewById(R.id.tv_fulldescinju)
        et_fulldes_inj = view.findViewById(R.id.et_fulldes_inj)
        tv_select_location = view.findViewById(R.id.tv_select_location)
        tv_dateofoccurenceet_postcode_person =
            view.findViewById(R.id.tv_dateofoccurenceet_postcode_person)
        tv_timeof_occurence = view.findViewById(R.id.tv_timeof_occurence)
        tv_bodymap = view.findViewById(R.id.tv_bodymap)
        tv_bodymaptext = view.findViewById(R.id.tv_bodymaptext)

        imgcamera = view.findViewById(R.id.imgcamera)
        et_fulldescrtpttion = view.findViewById(R.id.et_fulldescrtpttion)
        tv_takephatoorvideo = view.findViewById(R.id.tv_takephatoorvideo)
        ll_takephato = view.findViewById(R.id.ll_takephato)
        tv_emploment_details = view.findViewById(R.id.tv_emploment_details)
        et_injuredperdesc = view.findViewById(R.id.et_injuredperdesc)
        tv_empdetails = view.findViewById(R.id.tv_empdetails)
        tv_offdutytime = view.findViewById(R.id.tv_offdutytime)
        et_off_duty = view.findViewById(R.id.et_off_duty)

        tv_duty_continue = view.findViewById(R.id.tv_duty_continue)
        et_continurtowork = view.findViewById(R.id.et_continurtowork)
        et_off_duty_time = view.findViewById(R.id.et_off_duty_time)
        tv_confirm = view.findViewById(R.id.tv_confirm)
        tv_wentduty = view.findViewById(R.id.tv_wentduty)
        tv_signedconfirm = view.findViewById(R.id.tv_signedconfirm)
        img_signbehlf = view.findViewById(R.id.img_signbehlf)
        et_prientname = view.findViewById(R.id.et_prientname)
        et_position = view.findViewById(R.id.et_position)

        tv_datepatient = view.findViewById(R.id.tv_datepatient)
        tvappert = view.findViewById(R.id.tvappert)
        tv_statement = view.findViewById(R.id.tv_statement)
        et_statementdetails = view.findViewById(R.id.et_statementdetails)
        tv_witeness1 = view.findViewById(R.id.tv_witeness1)
        img_witeness1 = view.findViewById(R.id.img_witeness1)
        patient1 = view.findViewById(R.id.patient1)
        et_date = view.findViewById(R.id.et_date1)
        et_address_wite = view.findViewById(R.id.et_address_wite)

        et_postcodewe1 = view.findViewById(R.id.et_postcodewe1)
        tvappert = view.findViewById(R.id.tvappert)
        tv_statement2 = view.findViewById(R.id.tv_statement2)
        tv_sin_witess2 = view.findViewById(R.id.tv_sin_witess2)
        imgwiteness2 = view.findViewById(R.id.imgwiteness2)
        et_statementdetails2 = view.findViewById(R.id.et_statementdetails2)
        patient2 = view.findViewById(R.id.patient2)
        tv_date2 = view.findViewById(R.id.tv_date2)
        et_address_wite2 = view.findViewById(R.id.et_address_wite2)

        et_postcodewe2 = view.findViewById(R.id.et_postcodewe2)
        tv_tocomply = view.findViewById(R.id.tv_tocomply)
        tv_notethis = view.findViewById(R.id.tv_notethis)
        tv_signcompleted = view.findViewById(R.id.tv_signcompleted)
        img_sign_completedby =
            view.findViewById(R.id.img_sign_completedby)
        et_complientby = view.findViewById(R.id.et_complientby)
        tv_sign_injuredperson =
            view.findViewById(R.id.tv_sign_injuredperson)
        img_injuredperson = view.findViewById(R.id.img_injuredperson)
        et_injuredperson = view.findViewById(R.id.et_injuredperson)
        chk_iscontent = view.findViewById(R.id.chk_iscontent)
        rg_servety_level = view.findViewById(R.id.rg_servety_level)
        rl_chooseimage = view.findViewById(R.id.rl_chooseimage)
        img_select = view.findViewById(R.id.img_select)
        rl_sign_behalf = view.findViewById(R.id.rl_sign_behalf)
        rl_witness1 = view.findViewById(R.id.rl_witness1)
        rl_witness2 = view.findViewById(R.id.rl_witness2)
        rl_completed = view.findViewById(R.id.rl_completed)
        rl_injured = view.findViewById(R.id.rl_injured)
      //  rg_other=view.findViewById(R.id.rg_other)
        et_detailsofother!!.setEnabled(false)
        seek_servertylevel!!.getProgressDrawable().setColorFilter(accidentReportActivity.getResources().getColor(
            R.color.tabtext_color), PorterDuff.Mode.MULTIPLY)

    }
}