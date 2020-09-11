package com.wecompli.screeen.summery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import com.wecompli.screeen.home.HomeActivity
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

class CheckSummeryFragment: Fragment() {
    var homeactivity:HomeActivity?=null
    var summeryListAdapter:CheckSummeryListAdapter?=null
    var  listvalue:List<CheckRow>?=null
    var componet:String=""
    var sessionname:String=""
    var sideid:String=""
    var date:String=""
    var summerryViewBind:SummerryViewBind?=null
    public  var userData:LoginUserDetailsModel?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeactivity=getActivity() as HomeActivity
        val view:View=LayoutInflater.from(activity).inflate(R.layout.fragment_check_summery,null)
        summerryViewBind= SummerryViewBind(homeactivity!!,view)
        getuserdataafterlogin()
        return  view;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         componet = arguments!!.getString("componet")!!
         sessionname = arguments!!.getString("sessionname")!!
         sideid = arguments!!.getString("sideid")!!
         date = arguments!!.getString("date")!!

    }

    override fun onResume() {
        super.onResume()
        callApiforchecksummery();
    }

    private fun setsummeryitemadapter() {
        summeryListAdapter= CheckSummeryListAdapter(homeactivity!!,this,listvalue,object : OnItemClickInterface {
            override fun OnItemClick(position: Int) {
                val intent= Intent(homeactivity, CheckElementDetailsActivity::class.java)
                val selectedSiteSessionForCheck= SelectedSiteSessionForCheck(userData!!.company_id,sideid.toString(),componet.toString(),listvalue!!.get(position).categoryName,listvalue!!.get(position).id,date!!)
                intent.putExtra(ApplicationConstant.INTENT_COMPONENETDETAILS,selectedSiteSessionForCheck as Serializable)
                intent.putExtra("componet",componet)
                intent.putExtra("date",date)
                intent.putExtra("sideid",sideid)
                startActivityForResult(intent,1)
            }
        })
        val layoutInflater= LinearLayoutManager(homeactivity)
        summerryViewBind!!.recview_check_list!!.layoutManager=layoutInflater
        summerryViewBind!!.recview_check_list!!.adapter=summeryListAdapter
    }

    private fun callApiforchecksummery() {
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(homeactivity!!,"Please Wait..",false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("company_id", userData!!.company_id)
            paramObject.put("site_id",sideid)
            paramObject.put("season_id",componet)
            paramObject.put("category_purpose", PreferenceConstent.category_purpose)
            paramObject.put("check_date", date)
            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callApi=apiInterface.callcomponetChecklist( AppSheardPreference(homeactivity!!).getvalue_in_preference(
                PreferenceConstent.loginuser_token),sideid!!, gsonObject!!)
            callApi.enqueue(object : Callback<CheckSummeryResponse> {
                override fun onResponse(call: Call<CheckSummeryResponse>, response: Response<CheckSummeryResponse>) {
                    customProgress.hideProgress()
                    if (response.code()==200){
                        summerryViewBind!!.tv_total_fault_count!!.text=resources.getString(R.string.total_fault)+"  "+ response.body()!!.faultCount.toString()
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
        val json = AppSheardPreference(homeactivity!!).getvalue_in_preference(PreferenceConstent.loginuser_data)
        userData = gson.fromJson<LoginUserDetailsModel>(json, LoginUserDetailsModel::class.java!!)
    }

    private fun callApiforregeneratetoken() {
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(homeactivity!!,"Please Wait..",false)
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
                        AppSheardPreference(homeactivity!!).setvalue_in_preference(PreferenceConstent.loginuser_token,response!!.body()!!.data!!.token.toString())
                        callApiforchecksummery()
                    }
                    else
                        Alert.showalert(homeactivity!!,response.body()!!.message!!)
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
                componet = arguments!!.getString("componet")!!
                sessionname = arguments!!.getString("sessionname")!!
                sideid = arguments!!.getString("sideid")!!
                date = arguments!!.getString("date")!!
                getuserdataafterlogin()
                callApiforchecksummery()
            }
        }
    }
}