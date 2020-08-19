package com.wecompli.screeen.checkinputnote

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.R
import com.wecompli.apiresponsemodel.checkelementdetails.ElementDetailsRow
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.utils.ApplicationConstant
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckInputNoteActivity: AppCompatActivity() {
    var checkInputNoteViewBind:CheckInputNoteViewBind?=null
    var checkInputNoteOnClick:CheckInputNoteOnClick?=null
    var elementDetailsRow:ElementDetailsRow?=null
    var checkdate:String?=null
    var checkcomponent:String?=null
    var sideid:String?=null
    var sessionid:String?=null
    var selectedcategory:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_check_input_note,null)
        setContentView(view)
        checkInputNoteViewBind=CheckInputNoteViewBind(this,view!!)
        checkInputNoteOnClick= CheckInputNoteOnClick(this,checkInputNoteViewBind!!)

       val intent=intent
        elementDetailsRow=intent.getSerializableExtra("elementdetails")as ElementDetailsRow
        checkcomponent=intent.getStringExtra("componet")
        checkdate=intent.getStringExtra("date")
        sideid=intent.getStringExtra("sideid")
        sessionid=intent.getStringExtra("session")
        selectedcategory=intent.getStringExtra("selectedcategory")
        setelementnameanddesc()
    }

    private fun setelementnameanddesc() {
        checkInputNoteViewBind!!.tv_general_task_name!!.text=elementDetailsRow!!.checkName
        checkInputNoteViewBind!!.tv_tesk_description!!.text=elementDetailsRow!!.checkNote
        checkInputNoteViewBind!!.tv_task!!.text=selectedcategory
    }

    fun callApiforinputnote() {
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("check_id", elementDetailsRow!!.id)
            paramObject.put("season_id", sessionid)
            paramObject.put("check_type_id", elementDetailsRow!!.checkTypeId)
            paramObject.put("check_type_values_id", elementDetailsRow!!.checkTypeValue!!.get(0).id)
            paramObject.put("check_process_type", "checks")
            paramObject.put("check_date",checkdate)
            paramObject.put("process_remark",checkInputNoteViewBind!!.et_input!!.text.toString())
            paramObject.put("process_status","Y")
            paramObject.put("checks_process_log_entry_date", AppSheardPreference(this!!).getvalue_in_preference(PreferenceConstent.chk_selectiondate))

            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callApi= apiInterface.callcomponetCheckSubmit(
                AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token),sideid!!, gsonObject!!)
            callApi.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    customProgress.hideProgress()
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            val intent = Intent()
                            setResult(ApplicationConstant.INTENT_CHECKCOMPONENT, intent)
                            finish()
                        }
                    }
                    else
                        Toast.makeText(this@CheckInputNoteActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })

        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }
}