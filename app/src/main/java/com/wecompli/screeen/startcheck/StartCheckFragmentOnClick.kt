package com.wecompli.screeen.startcheck

import android.app.DatePickerDialog
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.R
import com.wecompli.apiresponsemodel.notifywho.NotifyWhoModel
import com.wecompli.apiresponsemodel.regeratetokenresponse.RegenerateTokenResponse
import com.wecompli.apiresponsemodel.seasonlist.Row
import com.wecompli.apiresponsemodel.seasonlist.SeasonListApiresponse
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.screeen.checksummery.CheckSummeryActivity
import com.wecompli.screeen.home.HomeActivity
import com.wecompli.screeen.startcheck.adapter.ComponentListAapter
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.custompopupdialogforsite.CustomPopUpDialogSiteList
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*
import com.google.gson.Gson





class StartCheckFragmentOnClick: View.OnClickListener{
   var activity: HomeActivity?=null
    var fragmentViewBind: StartCheckFragmentViewBind?=null
    var customPopUpDialogSiteList:CustomPopUpDialogSiteList?=null
    var startCheckFragment:StartCheckFragment?=null
    var email_list=ArrayList<NotifyWhoModel>()
    var site_id:String?=null
    constructor(
        activity: HomeActivity,
        fragmentViewBind: StartCheckFragmentViewBind,
        startCheckFragment: StartCheckFragment
    ){
        this.activity=activity
        this.fragmentViewBind=fragmentViewBind
        this.startCheckFragment=startCheckFragment
        setonclick()
    }

    private fun setonclick() {
        fragmentViewBind!!.tv_select_site!!.setOnClickListener(this)
        fragmentViewBind!!.tv_check_date!!.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.tv_select_site->{
             showsitelist()
            }
            R.id.tv_check_date->{
                datepickerdefault()
            }
        }

    }

    private fun showsitelist() {
        customPopUpDialogSiteList= CustomPopUpDialogSiteList(activity,this,startCheckFragment!!.site_list)
        customPopUpDialogSiteList!!.show()
    }

    fun datepickerdefault() {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(activity!!, R.style.DialogThemedatepicker,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val choosedate = padnumber(dayOfMonth) + "/" + padnumber(monthOfYear + 1) + "/" + year.toString()
                val checkdate = year.toString() + "-" + padnumber(monthOfYear + 1) + "-" + padnumber(dayOfMonth)
                val listcheckdate = padnumber(monthOfYear + 1) + "/" + padnumber(dayOfMonth) + "/" + year.toString()
                fragmentViewBind!!.tv_check_date!!.setText(choosedate)
               // formattedDate = choosedate
               //  foramttedmonthyear = padnumber(monthOfYear + 1) + "/" + year.toString()
               //  formattedyear = year.toString()
               //  appSheardPreference.setvalue_in_preference(PreferenceConstent.search_date, formattedDate)
               //  appSheardPreference.setvalue_in_preference(PreferenceConstent.check_date, checkdate)
                // appSheardPreference.setvalue_in_preference(PreferenceConstent.checked_list_date, listcheckdate)
                if (!fragmentViewBind!!.tv_select_site!!.getText().toString().equals("")) {
                     callApiforcomponent()
                } else
                    Alert.showalert(activity!!, activity!!.getResources().getString(R.string.select_site_))
            }, mYear, mMonth, mDay
        )
        //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show()

    }

    public fun callApiforcomponent() {
        if (ConnectionDetector.isConnectingToInternet(activity!!)) {
           // fragmentViewBind!!.ll_component!!.removeAllViews()
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            customProgress.showProgress(activity!!, "Please Wait..", false)
            val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)

            try {
                val paramObject = JSONObject()
                paramObject.put("company_id", activity!!.userData!!.company_id)
                paramObject.put("site_id", activity!!.userData!!.site_id)
                paramObject.put("check_date", fragmentViewBind!!.tv_check_date!!.text.toString())
                var obj: JSONObject = paramObject
                var jsonParser: JsonParser = JsonParser()
                var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
                val calltodayseasonApi = apiInterface.calltodayseason(
                    AppSheardPreference(activity!!).getvalue_in_preference(PreferenceConstent.loginuser_token),
                    gsonObject!!
                )
                calltodayseasonApi.enqueue(object : Callback<SeasonListApiresponse> {
                    override fun onResponse(call: Call<SeasonListApiresponse>, response: Response<SeasonListApiresponse>) {
                        customProgress.hideProgress()
                        if (response.code() == 200) {
                           // for (i in 0 until response.body()!!.row!!.size) {
                            if(response.body()!!.notify_emails.size>0){
                              //  val sb = StringBuffer()
                                for (i in 0 until response.body()!!.notify_emails.size) {
                                   // sb.append(response.body()!!.notify_emails[i]+",")
                                    var  notifymodel=NotifyWhoModel(response.body()!!.notify_emails.get(i).name,response.body()!!.notify_emails.get(i).email,false)
                                    email_list.add(notifymodel)

                                }
                               // val str = sb.toString()
                                val gson = Gson()
                                val json = gson.toJson(email_list)
                                AppSheardPreference(activity!!).setvalue_in_preference(PreferenceConstent.notifyemil_list,json)
                            }

                                loadreclyerviewforcomponentlist(response.body()!!.row)
                              //  showcomponentValue(response!!.body()!!.row!!.get(i).seasonName.toString())
                           // }
                        } else if (response.code() == 401) {
                            callApiforregeneratetoken()

                        } else
                            Alert.showalert(activity!!, response.body()!!.message!!)
                    }

                    override fun onFailure(call: Call<SeasonListApiresponse>, t: Throwable) {
                        customProgress.hideProgress()
                    }
                })

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }else
            Alert.showalert(activity!!,activity!!.resources.getString(R.string.nointerner_connection))
        }

    private fun loadreclyerviewforcomponentlist(row: List<Row>?) {
      //  val mLayoutManager = LinearLayoutManager(activity)
        var componentlistadapter=ComponentListAapter(activity!!,row!!,object :OnItemClickInterface{
            override fun OnItemClick(position: Int) {
                val intent = Intent(activity, CheckSummeryActivity::class.java)
                //  appSheardPreference.setvalue_in_preference(PreferenceConstent.component_mode, value)
                intent.putExtra("componet",row!!.get(position).id);
                intent.putExtra("sessionname",row!!.get(position).seasonName)
                intent.putExtra("sideid",activity!!.userData!!.site_id)
                intent.putExtra("date",fragmentViewBind!!.tv_check_date!!.text.toString())
                activity!!.startActivity(intent!!)
            }
        })
        fragmentViewBind!!.rev_componentlist!!.setAdapter(componentlistadapter)
        val mLayoutManager = LinearLayoutManager(activity)
      //  mLayoutManager.setAutoMeasureEnabled(false);
        fragmentViewBind!!.rev_componentlist!!.setLayoutManager(mLayoutManager)


        //fragmentViewBind!!.rev_componentlist!!.layoutManager=mLayoutManager
        //fragmentViewBind!!.rev_componentlist!!.adapter=componentlistadapter
    }

    private fun callApiforregeneratetoken() {
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(activity!!,"Please Wait..",false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("user_email",activity!!.userData!!.email)
            paramObject.put("id", activity!!.userData!!.user_id)

            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callregeneratetoken = apiInterface.regeratetoken(gsonObject!!)
            callregeneratetoken.enqueue(object : Callback<RegenerateTokenResponse>{
                override fun onResponse(call: Call<RegenerateTokenResponse>, response: Response<RegenerateTokenResponse>) {
                    customProgress.hideProgress()
                    if (response.body()!!.status!!){
                      AppSheardPreference(activity!!).setvalue_in_preference(PreferenceConstent.loginuser_token,response!!.body()!!.data!!.token.toString())
                        callApiforcomponent()
                    }
                    else
                        Alert.showalert(activity!!,response.body()!!.message!!)
                }

                override fun onFailure(call: Call<RegenerateTokenResponse>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun padnumber(n: Int): String {
        val num: String
        if (n > 10 || n == 10)
            num = n.toString()
        else
            num = "0$n"
        return num
    }

    private fun showcomponentValue(value: String) {
        val deviceResolution = DeviceResolution(activity)
        val lparams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, activity!!.getResources().getDimension(R.dimen._37sdp).toInt())

        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, activity!!.getResources().getDimension(R.dimen._37sdp).toInt())
        params.setMargins(10, activity!!.getResources().getDimension(R.dimen._12sdp).toInt(), 10, 10)
        val tv = TextView(activity)
        tv.setBackgroundResource(R.drawable.rectangular_shape_blue)
        tv.text = value
      //  tv.textSize=activity!!.getResources().getDimension(R.dimen._10sdp)
        tv.setTextColor(activity!!.getResources().getColor(R.color.white))
        tv.gravity = Gravity.CENTER
        tv.setTypeface(deviceResolution.getbebas(activity))
        tv.layoutParams = params
        //  tv.setTextSize(homeActivity.getResources().getDimension(R.dimen._7sdp));
        tv.setOnClickListener {
            //   Alert.showalert(homeActivity,"Under Development");
            val intent = Intent(activity, CheckSummeryActivity::class.java)
          //  appSheardPreference.setvalue_in_preference(PreferenceConstent.component_mode, value)
            intent.putExtra("componet",value);
            activity!!.startActivity(intent!!)
        }
        fragmentViewBind!!.ll_component!!.addView(tv)
    }


}