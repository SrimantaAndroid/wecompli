package com.wecompli.screeen.bodymap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wecompli.R
import java.util.*

class ActivityBodyMap:AppCompatActivity() {
    var bodyMapViewBind:BodyMapViewBind?=null
    var bodyMapOnClick:BodyMapOnClick?=null
    var bodyitemlist = ArrayList<String>()
    var str_body = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View =LayoutInflater.from(this).inflate(R.layout.activity_body_layout,null)
        setContentView(view)
        bodyMapViewBind= BodyMapViewBind(this,view!!)
        bodyMapOnClick=BodyMapOnClick(this,bodyMapViewBind!!)
    }

    fun addselectebodymaplist(textView: TextView) {
        if (!bodyitemlist.contains(textView.text.toString())) {
            bodyitemlist.add(textView.text.toString())
            textView.setTextColor(resources.getColor(R.color.body_mapselectcolor))
        } else {
            textView.setTextColor(resources.getColor(R.color.body_maptextcolor))
            bodyitemlist.remove(textView.text.toString())
        }
    }
}