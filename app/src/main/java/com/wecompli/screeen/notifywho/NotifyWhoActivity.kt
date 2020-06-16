package com.wecompli.screeen.notifywho

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.wecompli.R
import com.wecompli.apiresponsemodel.faultdetails.NotifyEmail
import com.wecompli.apiresponsemodel.notifywho.NotifyWhoModel
import com.wecompli.screeen.notifywho.adapter.NotiFyWhoAdapter
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import org.json.JSONArray
import com.google.gson.reflect.TypeToken



class NotifyWhoActivity:AppCompatActivity() {
    var notifyWhoViewBind:NotifyWhoViewBind?=null
    var notifyWhoOnClick:NotifyWhoOnClick?=null
    var emillist=ArrayList<NotifyWhoModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_notify_who,null)
        setContentView(view)
        notifyWhoViewBind= NotifyWhoViewBind(this,view)
        notifyWhoOnClick=NotifyWhoOnClick(this,notifyWhoViewBind!!)
        setnotifyemilAdapter()
    }

    private fun setnotifyemilAdapter() {

      //  val jsonArray = JSONArray(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.notifyemil_list))
       // val strArr = arrayOfNulls<String>(jsonArray.length())
        val emailstring=AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.notifyemil_list)
       // val intArray = emailstring.split(",")
        val geson=Gson()
        val type = object : TypeToken<ArrayList<NotifyWhoModel>>() {}.type
        emillist =geson.fromJson<ArrayList<NotifyWhoModel>>(emailstring,type)

        if (emillist.size>0) {
          //  for (i in 0 until listoeemail.size) {
                // strArr[i] = jsonArray.getString(i)
             //   val notifyEmail = NotifyEmail(intArray.get(i), false)
               // emillist.add(notifyEmail)
           // }


            val notifyListAdapter = NotiFyWhoAdapter(this, emillist)
            val linLayoutManager = LinearLayoutManager(this)
            notifyWhoViewBind!!.rv_notifywho!!.setLayoutManager(linLayoutManager)
            notifyWhoViewBind!!.rv_notifywho!!.setAdapter(notifyListAdapter)
        }
    }
}