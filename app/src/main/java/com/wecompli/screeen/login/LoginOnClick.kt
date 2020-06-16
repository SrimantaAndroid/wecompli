package com.wecompli.screeen.login

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.R
import com.wecompli.apiresponsemodel.home.LoginUserDetailsModel
import com.wecompli.apiresponsemodel.login.LoginResponse
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.screeen.forgotpassword.ForGotPasswordActivity
import com.wecompli.screeen.home.HomeActivity
import com.wecompli.screeen.intropages.InterPagesActivity
import com.wecompli.screeen.login.adapter.LanguageAdapter
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import kotlinx.android.synthetic.main.activity_login.view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginOnClick:View.OnClickListener {
    lateinit var loginViewBind: LoginViewBind
    lateinit var loginActivity: LoginActivity

    constructor( loginViewBind: LoginViewBind, loginAcitytiv: LoginActivity){
        this.loginViewBind=loginViewBind
        this.loginActivity=loginAcitytiv
        setonclicklistner()
    }

    private fun setonclicklistner() {
        loginViewBind.btn_login.setOnClickListener(this)
        loginViewBind.chk_remember.setOnClickListener (this)
        loginViewBind.tv_forgotpassword.setOnClickListener(this)
        loginViewBind.tv_language.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_login->{
                if(checkblank()){
                  saveusernameandpassword()
                    if(ConnectionDetector.isConnectingToInternet(loginActivity))
                     callApiforLogin()
                    else
                        Alert.showalert(loginActivity,loginActivity.resources.getString(R.string.nointerner_connection))
                }
            }
            R.id.tv_forgotpassword->{
                val forgotintent=Intent(loginActivity,ForGotPasswordActivity::class.java)
                loginActivity.startActivity(forgotintent!!)
            }
            R.id.tv_language->{
                showlanguagedropdown()
            }

        }

    }

    private fun saveusernameandpassword() {
        if (loginViewBind.chk_remember.isChecked) {
            AppSheardPreference(loginActivity).setvalue_in_preference(PreferenceConstent.iS_Checked_login, "1")
            AppSheardPreference(loginActivity).setvalue_in_preference(PreferenceConstent.username_key, loginViewBind.username.text.toString())
            AppSheardPreference(loginActivity).setvalue_in_preference(PreferenceConstent.password_key, loginViewBind.et_pass.text.toString())

        } else {
            AppSheardPreference(loginActivity).setvalue_in_preference(PreferenceConstent.iS_Checked_login, "0")
            AppSheardPreference(loginActivity).setvalue_in_preference(PreferenceConstent.username_key, "")
            AppSheardPreference(loginActivity).setvalue_in_preference(PreferenceConstent.password_key, "")

        }
    }

    private fun callApiforLogin() {

        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(loginActivity,"Please Wait..",false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)

        try {
            val paramObject = JSONObject()
            paramObject.put("user_email",loginViewBind.username.text.toString())
            paramObject.put("password",loginViewBind.et_pass.text.toString())
            paramObject.put("identification_id_token",loginActivity.IMEINumber)
            paramObject.put("device_model",loginActivity.getDeviceName())
            paramObject.put("device_os",loginActivity.softwareVersion)

            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val loginApiCall = apiInterface.callLogInApi(gsonObject)

            loginApiCall.enqueue(object : Callback<LoginResponse>{
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    customProgress.hideProgress()
                    if (response.isSuccessful){
                        if (response.body()!!.status!!){
                            AppSheardPreference(loginActivity).setvalue_in_preference(PreferenceConstent.iS_LOGIN,"1")
                            val useradta= response!!.body()!!.data!!
                            val loginuserdata= LoginUserDetailsModel(
                                useradta.userId!!, useradta.fullName!!,useradta.email!!,useradta.userProfilePicturePath!!,useradta.token!!,useradta.companyName!!,useradta.companyId!!,",",
                                useradta.companyLogo!!,useradta.userType!!,useradta.menu!!,useradta.siteList!!)

                            AppSheardPreference(loginActivity).setvalue_in_preference(PreferenceConstent.loginuser_id, useradta.userId!!)
                            val gson = Gson()
                            val json = gson.toJson(loginuserdata)
                            AppSheardPreference(loginActivity).setvalue_in_preference(PreferenceConstent.loginuser_name, useradta.fullName!!)
                            AppSheardPreference(loginActivity).setvalue_in_preference(PreferenceConstent.loginuser_token,useradta.token!!)
                            AppSheardPreference(loginActivity).setvalue_in_preference(PreferenceConstent.loginuser_data, json)
                            AppSheardPreference(loginActivity).setvalue_in_preference(PreferenceConstent.UserCompany,useradta!!.companyName!!)

                            val homeintent=Intent(loginActivity,HomeActivity::class.java)
                            loginActivity.startActivity(homeintent)
                            loginActivity.finish()
                        }else{
                            Alert.showalert(loginActivity!!, response.body()!!.message!!)
                        }
                    }else
                        Alert.showalert(loginActivity!!, loginActivity.resources.getString(R.string.wrongmessage))
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })

        }catch (e:Exception){
            e.printStackTrace()
        }
        //  val loginApiCall = apiInterface.callLogInApi("trugym3@gmail.com","123456",loginActivity.IMEINumber,loginActivity.getDeviceName(),loginActivity.softwareVersion.toString())
    }

    private fun showlanguagedropdown() {
        val laanguagelist = ArrayList<String>()
        val languagecode = ArrayList<String>()
        laanguagelist.add(loginActivity.getResources().getString(R.string.english))
        languagecode.add(loginActivity.getResources().getString(R.string.english_code))
        laanguagelist.add(loginActivity.getResources().getString(R.string.german))
        languagecode.add(loginActivity.getResources().getString(R.string.german_code))
        laanguagelist.add(loginActivity.getResources().getString(R.string.dutch))
        languagecode.add(loginActivity.getResources().getString(R.string.dutch_code))
        laanguagelist.add(loginActivity.getResources().getString(R.string.spnish))
        languagecode.add(loginActivity.getResources().getString(R.string.spanish_code))

        var languageAdapter = LanguageAdapter(loginActivity, laanguagelist, object : OnItemClickInterface {
                override fun OnItemClick(position: Int) {
                    loginActivity.changelanguage(languagecode[position])
                    AppSheardPreference(loginActivity).setvalue_in_preference(PreferenceConstent.selectedlanagage,
                    languagecode[position])
                }
            })
        val popupview = LayoutInflater.from(loginActivity).inflate(R.layout.languagepopup_layout, null)
        val popupWindow = PopupWindow(popupview, loginActivity.getResources().getDimension(R.dimen._93sdp).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT, true)
        val recyclerView_language:RecyclerView= popupview!!.findViewById(R.id.rec_languagelist)
        val layoutManager = LinearLayoutManager(loginActivity)
        recyclerView_language.setLayoutManager(layoutManager)
        recyclerView_language.setAdapter(languageAdapter)
        popupWindow.showAsDropDown(loginViewBind.tv_language)
    }


    private fun checkblank(): Boolean {
        if (loginViewBind.username.text.toString().trim().equals("")){
            loginViewBind.username.setHintTextColor(Color.RED)
            loginViewBind.username.requestFocus()
            return false
        }
        if (loginViewBind.et_pass.text.toString().trim().equals("")){
            loginViewBind.et_pass.requestFocus()
            loginViewBind.et_pass.setHintTextColor(Color.RED)
            return false
        }
        return true
    }



}