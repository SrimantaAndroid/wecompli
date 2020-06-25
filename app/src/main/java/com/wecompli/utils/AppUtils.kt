package com.wecompli.utils

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object AppUtils {
    fun isConnectingToInternet(context: Context): Boolean {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun checkdate(selectdate: String): Boolean {
        val c = Calendar.getInstance()
        val df = SimpleDateFormat("dd/MM/yyyy")
        val today = df.format(c.time)
        var datesucess = false
        try {
            //  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            val date1 = df.parse(today)
            val date2 = df.parse(selectdate)
            if (date2!!.compareTo(date1) == 0 || date2.compareTo(date1) < 0) {
                datesucess = true
                return datesucess
            } else
                datesucess = false
            return datesucess

        } catch (e1: ParseException) {
            e1.printStackTrace()
        }

        return datesucess

    }

    fun customView(v: View, backgroundColor: Int) {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadii = floatArrayOf(8f, 8f, 8f, 8f, 0f, 0f, 0f, 0f)
        shape.setColor(backgroundColor)
        // shape.setStroke(3, borderColor);
        v.background = shape


    }
}
