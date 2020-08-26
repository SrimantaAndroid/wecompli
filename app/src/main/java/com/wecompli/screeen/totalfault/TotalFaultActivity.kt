package com.wecompli.screeen.totalfault

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.R
import com.wecompli.apiresponsemodel.faultapi.CheckRow
import com.wecompli.apiresponsemodel.faultapi.FaultApiResponse
import com.wecompli.apiresponsemodel.home.LoginUserDetailsModel
import com.wecompli.apiresponsemodel.regeratetokenresponse.RegenerateTokenResponse
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.screeen.faultdetails.FaultDetailsActivity
import com.wecompli.screeen.totalfault.adapter.TotalfaultAdapter
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class TotalFaultActivity: AppCompatActivity() {
    var totalFaultViewBind:TotalFaultViewBind?=null
    var totalFaultOnClick:TotalFaultOnClick?=null
    var totalfaultAdapter:TotalfaultAdapter?=null
    var checkdate:String?=null
    var checkcomponent:String?=null
    var sideid:String?=null
    var companyid:String?=null
    var falultrow=ArrayList<CheckRow>()
    public  var userData: LoginUserDetailsModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_totalfault,null)
        totalFaultViewBind= TotalFaultViewBind(this,view)
        totalFaultOnClick= TotalFaultOnClick(this,totalFaultViewBind!!)
        setContentView(view)
        checkcomponent=intent.getStringExtra("componet")
        checkdate=intent.getStringExtra("date")
        sideid=intent.getStringExtra("sideid")
        companyid=intent.getStringExtra("companyid")
       // setAdpter()

        AppSheardPreference(this).setvalue_in_preference(PreferenceConstent.component_totalfault,checkcomponent!!)
        AppSheardPreference(this).setvalue_in_preference(PreferenceConstent.date_totalfault,checkdate!!)

        AppSheardPreference(this).setvalue_in_preference(PreferenceConstent.siteidtotalfault,sideid!!)
        AppSheardPreference(this).setvalue_in_preference(PreferenceConstent.companyidtotalfault,companyid!!)

        getuserdataafterlogin()
       // callApifortotalfault()


    }

    override fun onResume() {
        super.onResume()
        callApifortotalfault()
    }
    private fun getuserdataafterlogin() {
        val gson = Gson()
        val json = AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_data)
        userData = gson.fromJson<LoginUserDetailsModel>(json, LoginUserDetailsModel::class.java!!)
    }
    private fun callApifortotalfault() {
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this,"Please Wait..",false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("company_id", companyid!!.toInt())
           // paramObject.put("site_id", sideid)
           // paramObject.put("category_id", 1)
            //paramObject.put("from_date", checkdate)
            //paramObject.put("to_date", checkdate)
            paramObject.put("status_id","1")
            paramObject.put("check_process_type",PreferenceConstent.category_purpose)
           // paramObject.put("list_start",0)
           // paramObject.put("list_count",10)
            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callApi=apiInterface.callApiforfaultlist("application/json", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token),sideid!!, gsonObject!!)
            callApi.enqueue(object : Callback<FaultApiResponse> {
                override fun onResponse(call: Call<FaultApiResponse>, response: Response<FaultApiResponse>) {
                    customProgress.hideProgress()
                    if (response.code()==200){

                        if (response.body()!!.row!!.size>0) {
                            falultrow.clear()
                            for (i in 0 until response.body()!!.row!!.size){
                                falultrow!!.add(response.body()!!.row!!.get(i))
                            }
                           // falultrow = response.body()!!.row as ArrayList<CheckRow>?
                           // totalfaultAdapter!!.notifyDataSetChanged()
                            setAdpter()
                        }
                        else
                            Alert.showalert(this@TotalFaultActivity,response!!.body()!!.message!!)
                      //  totalfaultAdapter!!.notifyDataSetChanged()

                    }else if (response.code()==401){
                        callApiforregeneratetoken()
                    }
                }

                override fun onFailure(call: Call<FaultApiResponse>, t: Throwable) {
                    customProgress.hideProgress()

                }
            })


        }catch (e: Exception){
            e.printStackTrace()
            customProgress.hideProgress()

        }

    }
    private fun callApiforregeneratetoken() {
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this,"Please Wait..",false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("user_email",userData!!.email)
            paramObject.put("id",userData!!.user_id)

            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callregeneratetoken = apiInterface.regeratetoken(gsonObject!!)
            callregeneratetoken.enqueue(object : Callback<RegenerateTokenResponse>{
                override fun onResponse(call: Call<RegenerateTokenResponse>, response: Response<RegenerateTokenResponse>) {
                    customProgress.hideProgress()
                    if (response.body()!!.status!!){
                        AppSheardPreference(this@TotalFaultActivity!!).setvalue_in_preference(PreferenceConstent.loginuser_token,response!!.body()!!.data!!.token.toString())
                        callApifortotalfault()
                    }
                    else
                        Alert.showalert(this@TotalFaultActivity,response.body()!!.message!!)
                }

                override fun onFailure(call: Call<RegenerateTokenResponse>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })

        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }
    private fun setAdpter() {
        totalfaultAdapter= TotalfaultAdapter(this,falultrow!!,object : OnItemClickInterface{
            override fun OnItemClick(position: Int) {
                  val faultdetails=Intent(this@TotalFaultActivity,FaultDetailsActivity::class.java)
                  faultdetails.putExtra(PreferenceConstent.faultid,falultrow!!.get(position).id.toString())
                  faultdetails.putExtra(PreferenceConstent.site_id,sideid)
                  if (falultrow.get(position).categoryName.isNullOrEmpty()) {
                      faultdetails.putExtra(PreferenceConstent.FaultType, "adhoc")
                      AppSheardPreference(this@TotalFaultActivity).setvalue_in_preference(PreferenceConstent.AdHocNote,falultrow!!.get(position).faultDescription.toString())
                  }else
                      faultdetails.putExtra(PreferenceConstent.FaultType,"normal")
                  this@TotalFaultActivity.startActivity(faultdetails)

            }

        })
        val lmanager= LinearLayoutManager(this)
        totalFaultViewBind!!.recview_totalfault!!.layoutManager=lmanager
        totalFaultViewBind!!.recview_totalfault!!.adapter=totalfaultAdapter
    }
}