package com.wecompli.screeen.faultdetails

import android.app.DatePickerDialog
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.R
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.screeen.fixfault.FixFaultActivity
import com.wecompli.screeen.notifywho.NotifyWhoActivity
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import com.wecompli.utils.utilsview.ZoomImageDialog
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FaulrDetailsOnClick(
   val  faultDetailsActivity: FaultDetailsActivity,
   val faultDeatilsViewBind: FaultDeatilsViewBind) :View.OnClickListener{
    init {
     faultDeatilsViewBind.rl_back_taskdetails!!.setOnClickListener(this)
        faultDeatilsViewBind.tv_choose_date!!.setOnClickListener(this)
        faultDeatilsViewBind.tv_select_faultstatus!!.setOnClickListener(this)
        faultDeatilsViewBind.rl_submit!!.setOnClickListener(this)
        faultDeatilsViewBind.tv_updatefault!!.setOnClickListener(this)
        faultDeatilsViewBind.rl_notifywho!!.setOnClickListener(this)
        faultDeatilsViewBind.tv_fixfault!!.setOnClickListener(this)
        faultDeatilsViewBind.fault_img_1!!.setOnClickListener(this)
        faultDeatilsViewBind.fault_img_2!!.setOnClickListener(this)
        faultDeatilsViewBind.fault_img_3!!.setOnClickListener(this)
        faultDeatilsViewBind.fault_img_4!!.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
           R.id.rl_back_taskdetails->{
              faultDetailsActivity.finish()
           }
            R.id.fault_img_1->{
                ZoomImageDialog(
                    faultDetailsActivity,
                    faultDeatilsViewBind!!.tv_taskname!!.text.toString(),
                    faultDetailsActivity.faultrow.fault_files[0]).show()

            }
            R.id.fault_img_2->{
                ZoomImageDialog(
                    faultDetailsActivity,
                    faultDeatilsViewBind!!.tv_taskname!!.text.toString(),
                    faultDetailsActivity.faultrow.fault_files[1]).show()

            }
            R.id.fault_img_3->{
                ZoomImageDialog(
                    faultDetailsActivity,
                    faultDeatilsViewBind!!.tv_taskname!!.text.toString(),
                    faultDetailsActivity.faultrow.fault_files[2]).show()

            }
            R.id.fault_img_4->{
                ZoomImageDialog(
                    faultDetailsActivity,
                    faultDeatilsViewBind!!.tv_taskname!!.text.toString(),
                    faultDetailsActivity.faultrow.fault_files[3]).show()

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
                intent.putExtra(PreferenceConstent.FaultType,faultDetailsActivity.FaultType)
                faultDetailsActivity.startActivity(intent)
            }
            R.id.tv_closedfault->{
               Alert.showclosedfaultAlert(faultDetailsActivity,"Do you want to close this fault?");
            }


        }
    }
     public  fun callApiforemovefault() {
         val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
         customProgress.showProgress(faultDetailsActivity, "Please Wait..", false)
         val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)
         try {
             val paramObject = JSONObject()
             paramObject.put("checks_process_fault_id",faultDetailsActivity!!.faultid!!)
             paramObject.put("work_type","repair")
             paramObject.put("fault_status_id",6)
             paramObject.put("site_id",AppSheardPreference(faultDetailsActivity).getvalue_in_preference(PreferenceConstent.site_id))
             var obj: JSONObject = paramObject
             var jsonParser: JsonParser = JsonParser()
             var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
             val callApi = apiInterface.calllApiForRemoveFalt(
                 AppSheardPreference(faultDetailsActivity).getvalue_in_preference(PreferenceConstent.loginuser_token),
                 AppSheardPreference(faultDetailsActivity).getvalue_in_preference(PreferenceConstent.site_id),
                 gsonObject!!
             )
             callApi.enqueue(object :Callback<ResponseBody>{
                 override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                     customProgress.hideProgress()
                     var response_obj= JSONObject(response.body()!!.string())
                     //val response_obj = JSONObject(response.body()!!.string())
                     if (response_obj.getBoolean("status")){
                         faultDetailsActivity.finish()
                     }else
                         Toast.makeText(faultDetailsActivity, response_obj.getString("message"), Toast.LENGTH_LONG).show()

                 }

                 override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                     customProgress.hideProgress()
                 }
             })

         }catch (e: Exception){
             e.printStackTrace()
             customProgress.hideProgress()
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