package com.wecompli.screeen.faultdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.R
import com.wecompli.apiresponsemodel.faultdetails.FaultDetailsRow
import com.wecompli.apiresponsemodel.faultdetails.FaultadetailsModel
import com.wecompli.apiresponsemodel.faultdetails.Timeline
import com.wecompli.apiresponsemodel.faultstatuslist.FaultStatusListModel
import com.wecompli.apiresponsemodel.faultstatuslist.FaultStatusRow
import com.wecompli.apiresponsemodel.faultstatusmessagechange.FaultStatusMessageChange
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.screeen.faultdetails.adapter.FaultDetailsTimelineAdapter
import com.wecompli.utils.custompopupdialogforsite.CustomPopUpFaultStatus
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FaultDetailsActivity:AppCompatActivity() {
    var faultDeatilsViewBind:FaultDeatilsViewBind?=null
    var faulrDetailsOnClick:FaulrDetailsOnClick?=null
    var faultid:String?=""
    var sideid=""
    var faultstatusrow=ArrayList<FaultStatusRow>()
    var statusmessageid=""
    lateinit var faultTimelineAdapter:FaultDetailsTimelineAdapter
    var timeline= ArrayList<Timeline>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View =LayoutInflater.from(this).inflate(R.layout.fault_element_details_layout,null)
        setContentView(view)
        faultDeatilsViewBind= FaultDeatilsViewBind(this,view)
        faulrDetailsOnClick= FaulrDetailsOnClick(this,faultDeatilsViewBind!!)
        val intent=intent
        faultid= intent.getStringExtra(PreferenceConstent.faultid)
        sideid=intent.getStringExtra(PreferenceConstent.site_id)
        callApiforfaultdetails()
    }

    private fun callApiforfaultdetails() {

        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this,"Please Wait..",false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("id", faultid)
            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callApi = apiInterface.callfaultdetailsapi(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token),sideid , gsonObject!!)
            callApi.enqueue(object : Callback<FaultadetailsModel>{
                override fun onResponse(call: Call<FaultadetailsModel>, response: Response<FaultadetailsModel>) {
                    customProgress.hideProgress()
                    if (response.isSuccessful){
                        if(response.body()!!.status) {
                            if (response.body()!=null)
                            setvalue(response.body()!!.row)
                            AppSheardPreference(this@FaultDetailsActivity).setvalue_in_preference(PreferenceConstent.Category_name, response.body()!!.row.category_name)
                            AppSheardPreference(this@FaultDetailsActivity).setvalue_in_preference(PreferenceConstent.CheckName, response.body()!!.row.check_name)
                            AppSheardPreference(this@FaultDetailsActivity).setvalue_in_preference(PreferenceConstent.SelectedFaultId, response.body()!!.row.id)
                        }
                    }
                }

                override fun onFailure(call: Call<FaultadetailsModel>, t: Throwable) {
                    customProgress.hideProgress()

                }
            })

        }catch (e:Exception){
            e.printStackTrace()
        }

        }

    private fun faultTimelinerAdapter( timelinelist: ArrayList<Timeline>) {
        timeline=timelinelist
        val layoutManager = LinearLayoutManager(this)
        faultDeatilsViewBind!!.rec_faultsatusreport!!.setLayoutManager(layoutManager)
        faultTimelineAdapter = FaultDetailsTimelineAdapter(this, timeline)
        faultDeatilsViewBind!!.rec_faultsatusreport!!.setAdapter(faultTimelineAdapter)
    }
    private fun setvalue(row: FaultDetailsRow) {
        faultDeatilsViewBind!!.tv_taskname!!.setText(row!!.category_name!!)
        faultDeatilsViewBind!!.tv_taskdatetimevalue!!.setText(row!!.created_at!!)
        faultDeatilsViewBind!!.tv_taskdescriptionvalue!!.setText(row!!.fault_description!!)

        if (row.timeline.size>0)
        faultTimelinerAdapter(row.timeline as ArrayList<Timeline>)

        if (row.fault_files.size>0) {
            faultDeatilsViewBind!!.tv_fault_image!!.visibility = View.GONE
            for (i in 0 until row.fault_files.size){
                if(i==0){
                    Glide.with(this)
                        .load(row.fault_files[0])
                        .into(faultDeatilsViewBind!!.fault_img_1!!);
                }
               else if(i==1){
                    Glide.with(this)
                        .load(row.fault_files[1])
                        .into(faultDeatilsViewBind!!.fault_img_2!!);
                }

               else if(i==2){
                    Glide.with(this)
                        .load(row.fault_files[2])
                        .into(faultDeatilsViewBind!!.fault_img_3!!);
                }

                else if(i==3){
                    Glide.with(this)
                        .load(row.fault_files[3])
                        .into(faultDeatilsViewBind!!.fault_img_4!!);
                }


            }
        }

    }


    fun callApFaultStatus() {
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this,"Please Wait..",false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val callApi = apiInterface.callfaultstatuslist(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token),sideid!!)
            callApi.enqueue(object : Callback<FaultStatusListModel>{
                override fun onResponse(call: Call<FaultStatusListModel>, response: Response<FaultStatusListModel>) {
                    customProgress.hideProgress()
                    if (response.isSuccessful){
                        faultstatusrow= response.body()!!.row as ArrayList<FaultStatusRow>
                     val customPopUpFaultStatus=CustomPopUpFaultStatus(this@FaultDetailsActivity,response.body()!!.row)
                        customPopUpFaultStatus.show()
                    }
                }

                override fun onFailure(call: Call<FaultStatusListModel>, t: Throwable) {
                    customProgress.hideProgress()

                }
            })

        }catch (e:Exception){
            e.printStackTrace()
        }

    }

     fun callApiforfaultsumit() {
         val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
         customProgress.showProgress(this,"Please Wait..",false)
         val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
         try {
             val paramObject = JSONObject()
             paramObject.put("checks_process_fault_id", faultid)
             paramObject.put("faultrepair_status_master_id", statusmessageid)
             paramObject.put("fault_status_change_date", faultDeatilsViewBind!!.tv_choose_date!!.text.toString())
             var obj: JSONObject = paramObject
             var jsonParser: JsonParser = JsonParser()
             var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
             val callApi = apiInterface.callfaultstatusmessagechange(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token),sideid!!,gsonObject)
             callApi.enqueue(object : Callback<FaultStatusMessageChange>{
                 override fun onResponse(call: Call<FaultStatusMessageChange>, response: Response<FaultStatusMessageChange>) {
                     customProgress.hideProgress()
                     if (response.isSuccessful){
                         if (response.body()!!.status){
                             timeline.clear()
                             for (i in 0 until  response!!.body()!!.timeLine.size){
                                val timelineobj =Timeline(response!!.body()!!.timeLine.get(i).repairDatetime,
                                    response!!.body()!!.timeLine.get(i).repairMessage,"")
                                 timeline.add(timelineobj)
                             }
                             faultTimelineAdapter.notifyDataSetChanged()
                         }
                     }
                 }

                 override fun onFailure(call: Call<FaultStatusMessageChange>, t: Throwable) {
                     customProgress.hideProgress()

                 }
             })

         }catch (e:Exception){
             e.printStackTrace()
         }
    }
}