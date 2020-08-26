package com.wecompli.utils.customalert

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.screeen.adhocfault.AdHocFaultActivity
import com.wecompli.screeen.checkelementdetails.CheckElementDetailsActivity
import com.wecompli.screeen.checkminorfail.CheckMinorfailActivity
import com.wecompli.screeen.faultdetails.FaultDetailsActivity
import com.wecompli.screeen.home.HomeActivity


class Alert {
     companion object {
         fun showalert(activity: Activity, message: String) {
             //  var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity)
             val alertDialog = Dialog(activity, R.style.Transparent)
             /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
             alertDialog.setMessage(message)*/
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View = LayoutInflater.from(activity).inflate(R.layout.alert_layout, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_ok: Button = view.findViewById(R.id.btn_ok)
             btn_ok.typeface = deviceResolution.getgothmbold(activity)
             tv_message.typeface = deviceResolution.getgothmlight(activity)
             btn_ok.setOnClickListener {
                 alertDialog.dismiss()
             }
             tv_message.setText(message)
             alertDialog.show()
             /*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                 DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
             alertDialog.show()*/
         }


         fun showalertToGoHomePage(activity: Activity, message: String) {
             //  var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity)
             val alertDialog = Dialog(activity, R.style.Transparent)
             /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
             alertDialog.setMessage(message)*/
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View = LayoutInflater.from(activity).inflate(R.layout.alert_layout, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_ok: Button = view.findViewById(R.id.btn_ok)
             btn_ok.typeface = deviceResolution.getgothmbold(activity)
             tv_message.typeface = deviceResolution.getgothmlight(activity)
             btn_ok.setOnClickListener {
                 alertDialog.dismiss()
                 val intte=Intent(activity,HomeActivity::class.java);
                 intte.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                 activity.startActivity(intte)
             }
             tv_message.setText(message)
             alertDialog.show()
             /*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                 DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
             alertDialog.show()*/
         }
         fun showalertforallchecksubmit(checkElementDetailsActivity: CheckElementDetailsActivity, message: String) {
             //  var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(checkElementDetailsActivity)
             val alertDialog = Dialog(checkElementDetailsActivity, R.style.Transparent)
             /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
             alertDialog.setMessage(message)*/
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View = LayoutInflater.from(checkElementDetailsActivity).inflate(R.layout.alert_layout_all_checks, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_ok: Button = view.findViewById(R.id.btn_ok)
             btn_ok.typeface = deviceResolution.getbebas(checkElementDetailsActivity)
             tv_message.typeface = deviceResolution.getgothmlight(checkElementDetailsActivity)
             btn_ok.setOnClickListener {
                 alertDialog.dismiss()
                 val intent=Intent()
                 intent.putExtra("componet",checkElementDetailsActivity.checkcomponent)
                 intent.putExtra("date",checkElementDetailsActivity.checkdate)
                 intent.putExtra("sideid",checkElementDetailsActivity.sideid)
                 checkElementDetailsActivity.setResult(Activity.RESULT_OK,intent)
                 checkElementDetailsActivity.finish()
             }
             tv_message.setText(message)
             alertDialog.show()
             /*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                 DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
             alertDialog.show()*/
         }

         fun showyesnoalert(activity: HomeActivity, message: String) {
             // var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity)
             val alertDialog = Dialog(activity, R.style.Transparent)
             /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
             alertDialog.setMessage(message)*/
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View =
                 LayoutInflater.from(activity).inflate(R.layout.alert_layout_yesno, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_ok: Button = view.findViewById(R.id.btn_ok)
             val btn_no: Button = view.findViewById(R.id.btn_no)
             btn_ok.typeface = deviceResolution.getgothmbold(activity)
             btn_no.typeface = deviceResolution.getgothmbold(activity)
             tv_message.typeface = deviceResolution.getgothmlight(activity)
             btn_ok.setOnClickListener {
                 alertDialog.dismiss()
                 activity.homeOnClick!!.logoutyes()
                 // activity.alertyesfuncation();
                // activity.calllogoutdeleteusertoken()
             }
             btn_no.setOnClickListener {
                 alertDialog.dismiss()

             }
             tv_message.setText(message)
             alertDialog.show()
             /*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                 DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
             alertDialog.show()*/
         }

         fun showclosedfaultAlert(activity: FaultDetailsActivity, message: String) {
             // var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity)
             val alertDialog = Dialog(activity, R.style.Transparent)
             /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
             alertDialog.setMessage(message)*/
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View =
                 LayoutInflater.from(activity).inflate(R.layout.alert_layout_yesno, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_ok: Button = view.findViewById(R.id.btn_ok)
             val btn_no: Button = view.findViewById(R.id.btn_no)
             btn_ok.typeface = deviceResolution.getgothmbold(activity)
             btn_no.typeface = deviceResolution.getgothmbold(activity)
             tv_message.typeface = deviceResolution.getgothmlight(activity)
             btn_ok.setOnClickListener {
                 alertDialog.dismiss()
                 activity.faulrDetailsOnClick!!.callApiforemovefault()
                 // activity.alertyesfuncation();
                 // activity.calllogoutdeleteusertoken()
             }
             btn_no.setOnClickListener {
                 alertDialog.dismiss()

             }
             tv_message.setText(message)
             alertDialog.show()
             /*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                 DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
             alertDialog.show()*/
         }

         fun showalertforImageSelectio(activity: CheckMinorfailActivity) {
             // var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity)
             val alertDialog = Dialog(activity, R.style.Transparent)
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View = LayoutInflater.from(activity).inflate(R.layout.alert_custom_imageselection, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_gallery: Button = view.findViewById(R.id.btn_gallery)
             val btn_camera:Button=view.findViewById(R.id.btn_camera)
             val btn_cancel:Button=view.findViewById(R.id.btn_cancel)
             btn_camera.typeface=deviceResolution.getgothmbold(activity)
             btn_cancel.typeface=deviceResolution.getgothmbold(activity)
             btn_gallery.typeface = deviceResolution.getgothmbold(activity)
             tv_message.typeface = deviceResolution.getgothmbold(activity)
             btn_gallery.setOnClickListener {
                 alertDialog.dismiss()
                 activity.chooseFromgallery()
             }
             btn_camera.setOnClickListener {
                 alertDialog.dismiss()
                 activity.chooseimagrfromcamera()
             }
             btn_cancel.setOnClickListener {
                alertDialog.dismiss()
             }
             alertDialog.show()

         }


         fun showalertforImageSelectionAdhocfault(activity: AdHocFaultActivity) {
             // var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity)
             val alertDialog = Dialog(activity, R.style.Transparent)
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View = LayoutInflater.from(activity).inflate(R.layout.alert_custom_imageselection, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_gallery: Button = view.findViewById(R.id.btn_gallery)
             val btn_camera:Button=view.findViewById(R.id.btn_camera)
             val btn_cancel:Button=view.findViewById(R.id.btn_cancel)
             btn_camera.typeface=deviceResolution.getgothmbold(activity)
             btn_cancel.typeface=deviceResolution.getgothmbold(activity)
             btn_gallery.typeface = deviceResolution.getgothmbold(activity)
             tv_message.typeface = deviceResolution.getgothmbold(activity)
             btn_gallery.setOnClickListener {
                 alertDialog.dismiss()
                 activity.chooseFromgallery()
             }
             btn_camera.setOnClickListener {
                 alertDialog.dismiss()
                 activity.chooseimagrfromcamera()
             }
             btn_cancel.setOnClickListener {
                 alertDialog.dismiss()
             }
             alertDialog.show()

         }

         /*  fun showalertinternerservererror(activity: HomeActivity, message: String) {
               // var deviceResolution:DeviceResolution?=null
               var deviceResolution = DeviceResolution(activity)
               val alertDialog = Dialog(activity, R.style.Transparent)
               *//*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
             alertDialog.setMessage(message)*//*
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View = LayoutInflater.from(activity).inflate(R.layout.alertlayout, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_ok: Button = view.findViewById(R.id.btn_ok)
             btn_ok.typeface = deviceResolution.getMavenProRegular(activity)
             tv_message.typeface = deviceResolution.getMavenProRegular(activity)
             btn_ok.setOnClickListener {
                 alertDialog.dismiss()
                 activity.finish()
             }
             tv_message.setText(message)
             alertDialog.show()
             *//*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                 DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
             alertDialog.show()*//*
         }


         fun showalertRateusapp(activity: Context, message: String) {
             val APP_PNAME = getApplicationContext().getPackageName();// Package Name
             // var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity as Activity?)
             val alertDialog = Dialog(activity, R.style.Transparent)
             *//*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
             alertDialog.setMessage(message)*//*
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View =
                 LayoutInflater.from(activity).inflate(R.layout.alert_rating_layout, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btnrateus: Button = view.findViewById(R.id.btnrateus)
             val btn_remindermelater: Button = view.findViewById(R.id.btn_remindermelater)
             val btn_nothanks: Button = view.findViewById(R.id.btn_nothanks)
             btnrateus.typeface = deviceResolution.getMavenProRegular(activity)
             btn_remindermelater.typeface = deviceResolution.getMavenProRegular(activity)
             btn_nothanks.typeface = deviceResolution.getMavenProRegular(activity)
             tv_message.typeface = deviceResolution.getMavenProRegular(activity)
             btnrateus.setOnClickListener {
                 alertDialog.dismiss()
                 activity.startActivity(
                     Intent(
                         Intent.ACTION_VIEW,
                         Uri.parse("market://details?id=$APP_PNAME")
                     )
                 )
                 // activity.finish()
             }
             btn_remindermelater.setOnClickListener {
                 alertDialog.dismiss()
             }
             btn_nothanks.setOnClickListener {
                 alertDialog.dismiss()
                 AppPreferenceHalper.write("dontshowagain", true)
             }
             tv_message.setText(message)
             alertDialog.show()
             *//*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                 DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
             alertDialog.show()*//*
         }


         fun showalertforPaymentSucess(activity: PlaceOrderActivity, message: String, payuResponse: String) {
             // var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity)
             val alertDialog = Dialog(activity, R.style.Transparent)
             *//*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
        alertDialog.setMessage(message)*//*
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View =
                 LayoutInflater.from(activity).inflate(R.layout.alertlayout, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_ok: Button = view.findViewById(R.id.btn_ok)
           //  val btn_no: Button = view.findViewById(R.id.btn_no)
             btn_ok.typeface = deviceResolution.getMavenProRegular(activity)
           //  btn_no.typeface = deviceResolution.getMavenProRegular(activity)
             tv_message.typeface = deviceResolution.getMavenProRegular(activity)
             btn_ok.setOnClickListener {
                 alertDialog.dismiss()
                 // activity.alertyesfuncation();
                 activity.callapiforpaymentsucesstranscation(payuResponse)
             }

             tv_message.setText(message)
             alertDialog.show()
             *//*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        alertDialog.show()*//*
         }*/
     }
}