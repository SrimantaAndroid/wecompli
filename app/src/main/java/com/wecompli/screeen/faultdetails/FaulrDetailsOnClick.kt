package com.wecompli.screeen.faultdetails

import android.app.DatePickerDialog
import android.content.Intent
import android.view.View
import com.wecompli.R
import com.wecompli.screeen.fixfault.FixFaultActivity
import com.wecompli.screeen.notifywho.NotifyWhoActivity
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import java.util.*

class FaulrDetailsOnClick(
   val  faultDetailsActivity: FaultDetailsActivity,
   val faultDeatilsViewBind: FaultDeatilsViewBind) :View.OnClickListener{
    init {
     faultDeatilsViewBind.rl_back_taskdetails!!.setOnClickListener(this)
        faultDeatilsViewBind.tv_choose_date!!.setOnClickListener(this)
        faultDeatilsViewBind.tv_select_faultstatus!!.setOnClickListener(this)
        faultDeatilsViewBind.rl_submit!!.setOnClickListener(this)
        faultDeatilsViewBind.rl_notifywho!!.setOnClickListener(this)
        faultDeatilsViewBind.tv_fixfault!!.setOnClickListener(this)

    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
           R.id.rl_back_taskdetails->{
              faultDetailsActivity.finish()
           }
            R.id.tv_choose_date->{
                datepickerdefault()
            }
            R.id.tv_select_faultstatus->{
                faultDetailsActivity.callApFaultStatus()
            }
            R.id.rl_submit->{
                if (!faultDeatilsViewBind.tv_select_faultstatus!!.text.toString().equals("")) {
                    if (!faultDeatilsViewBind.tv_choose_date!!.text.toString().equals("")) {
                        faultDetailsActivity.callApiforfaultsumit()
                    } else
                        Alert.showalert(faultDetailsActivity, faultDetailsActivity.resources.getString(R.string.select_status_date))
                }else
                    Alert.showalert(faultDetailsActivity, faultDetailsActivity.resources.getString(R.string.select_fault_status))


            }
            R.id.rl_notifywho->{
                val intent=Intent(faultDetailsActivity,NotifyWhoActivity::class.java)
                faultDetailsActivity.startActivity(intent)

            }
            R.id.tv_fixfault->{
               // Alert.showalert(faultDetailsActivity, faultDetailsActivity.resources.getString(R.string.underdevelopment))
                AppSheardPreference(faultDetailsActivity).setvalue_in_preference(PreferenceConstent.faultid,faultDetailsActivity!!.faultid!!)
                AppSheardPreference(faultDetailsActivity).setvalue_in_preference(PreferenceConstent.site_id,faultDetailsActivity!!.sideid)
                val intent=Intent(faultDetailsActivity,FixFaultActivity::class.java)
                faultDetailsActivity.startActivity(intent)
            }


        }
    }



    fun datepickerdefault() {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(faultDetailsActivity!!, R.style.DialogThemedatepicker,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val choosedate = padnumber(dayOfMonth) + "/" + padnumber(monthOfYear + 1) + "/" + year.toString()
                val checkdate = year.toString() + "-" + padnumber(monthOfYear + 1) + "-" + padnumber(dayOfMonth)
                val listcheckdate = padnumber(monthOfYear + 1) + "/" + padnumber(dayOfMonth) + "/" + year.toString()
                faultDeatilsViewBind.tv_choose_date!!.setText(choosedate)
                // formattedDate = choosedate
                //  foramttedmonthyear = padnumber(monthOfYear + 1) + "/" + year.toString()
                //  formattedyear = year.toString()
                //  appSheardPreference.setvalue_in_preference(PreferenceConstent.search_date, formattedDate)
                //  appSheardPreference.setvalue_in_preference(PreferenceConstent.check_date, checkdate)
                // appSheardPreference.setvalue_in_preference(PreferenceConstent.checked_list_date, listcheckdate)
               /* if (!fragmentViewBind!!.tv_select_site!!.getText().toString().equals("")) {
                    callApiforcomponent()
                } else
                    Alert.showalert(activity!!, activity!!.getResources().getString(R.string.select_site_))*/
            }, mYear, mMonth, mDay
        )
        //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show()

    }
    fun padnumber(n: Int): String {
        val num: String
        if (n > 10 || n == 10)
            num = n.toString()
        else
            num = "0$n"
        return num
    }
}