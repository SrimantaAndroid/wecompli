package com.wecompli.screeen.checksummery

import android.app.Activity
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
import com.wecompli.apiresponsemodel.checkelementdetails.SelectedSiteSessionForCheck
import com.wecompli.apiresponsemodel.checksummery.CheckRow
import com.wecompli.apiresponsemodel.checksummery.CheckSummeryResponse
import com.wecompli.apiresponsemodel.home.LoginUserDetailsModel
import com.wecompli.apiresponsemodel.regeratetokenresponse.RegenerateTokenResponse
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.screeen.checkelementdetails.CheckElementDetailsActivity
import com.wecompli.screeen.checksummery.summeryadapter.CheckSummeryListAdapter
import com.wecompli.utils.ApplicationConstant
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable


class CheckSummeryActivity:AppCompatActivity() {
    var checkSummeryViewBind:CheckSummeryViewBind?=null
    var checkSummeryOnClick:CheckSummeryOnClick?=null
    public  var userData:LoginUserDetailsModel?=null
    var checkdate:String?=null
    var checkcomponent:String?=null
    var sideid:String?=null
    var summeryListAdapter:CheckSummeryListAdapter?=null
    var  listvalue:List<CheckRow>?=null
    var sessionname:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_check_summery,null)
        checkSummeryViewBind= CheckSummeryViewBind(this,view)
        checkSummeryOnClick= CheckSummeryOnClick(this,checkSummeryViewBind!!)
        val intent=intent
        checkcomponent=intent.getStringExtra("componet")
        checkdate=intent.getStringExtra("date")
        sideid=intent.getStringExtra("sideid")
        sessionname=intent.getStringExtra("sessionname")
        setContentView(view)
        getuserdataafterlogin()
        callApiforchecksummery()
    }

    private fun setsummeryitemadapter() {
        summeryListAdapter= CheckSummeryListAdapter(this,listvalue,object :OnItemClickInterface{
            override fun OnItemClick(position: Int) {
                val intent=Intent(this@CheckSummeryActivity,CheckElementDetailsActivity::class.java)
                val selectedSiteSessionForCheck=SelectedSiteSessionForCheck(userData!!.company_id,sideid.toString(),checkcomponent.toString(),listvalue!!.get(position).categoryName,listvalue!!.get(position).id,checkdate!!)
                intent.putExtra(ApplicationConstant.INTENT_COMPONENETDETAILS,selectedSiteSessionForCheck as Serializable)
                intent.putExtra("componet",checkcomponent)
                intent.putExtra("date",checkdate)
                intent.putExtra("sideid",sideid)
                startActivityForResult(intent,1)
            }
        })
        val layoutInflater=LinearLayoutManager(this)
        checkSummeryViewBind!!.recview_check_list!!.layoutManager=layoutInflater
        checkSummeryViewBind!!.recview_check_list!!.adapter=summeryListAdapter
    }

    private fun callApiforchecksummery() {
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this,"Please Wait..",false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("company_id", userData!!.company_id)
            paramObject.put("site_id",sideid)
            paramObject.put("season_id",checkcomponent)
            paramObject.put("category_purpose", PreferenceConstent.category_purpose)
            paramObject.put("check_date", checkdate)
            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callApi=apiInterface.callcomponetChecklist( AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token),sideid!!, gsonObject!!)
            callApi.enqueue(object : Callback<CheckSummeryResponse>{
                override fun onResponse(call: Call<CheckSummeryResponse>, response: Response<CheckSummeryResponse>) {
                   customProgress.hideProgress()
                    if (response.code()==200){
                        checkSummeryViewBind!!.tv_total_fault_count!!.text=resources.getString(R.string.total_fault)+"  "+ response.body()!!.faultCount.toString()
                        listvalue=response!!.body()!!.row
                        setsummeryitemadapter()
                        //summeryListAdapter!!.notifyDataSetChanged()
                    }else if (response.code()==401){
                        callApiforregeneratetoken()
                    }
                }

                override fun onFailure(call: Call<CheckSummeryResponse>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })

        }catch (e:Exception){
            customProgress.hideProgress()
            e.printStackTrace()
        }

    }

    private fun getuserdataafterlogin() {
        val gson = Gson()
        val json = AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_data)
        userData = gson.fromJson<LoginUserDetailsModel>(json, LoginUserDetailsModel::class.java!!)
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
                        AppSheardPreference(this@CheckSummeryActivity!!).setvalue_in_preference(PreferenceConstent.loginuser_token,response!!.body()!!.data!!.token.toString())
                        callApiforchecksummery()
                    }
                    else
                        Alert.showalert(this@CheckSummeryActivity,response.body()!!.message!!)
                }

                override fun onFailure(call: Call<RegenerateTokenResponse>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })

        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1) {
            if (resultCode == Activity.RESULT_OK) {
                checkcomponent = intent!!.getStringExtra("componet")
                checkdate = intent.getStringExtra("date")
                sideid = intent.getStringExtra("sideid")
                getuserdataafterlogin()
                callApiforchecksummery()
            }
        }
    }
}