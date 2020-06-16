package com.wecompli.utils.customalert

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.screeen.fixfault.FixFaultActivity
import com.wecompli.utils.sheardpreference.PreferenceConstent
import com.wecompli.utils.utilsview.DrawingView

class TaptoSignEngineerDialog(val fixFaultActivity: FixFaultActivity?) : AppCompatDialog(fixFaultActivity),View.OnClickListener {
    var deviceResolution:DeviceResolution?=null
    internal var tv_done: TextView?=null
    internal var tv_signhere:TextView?=null
    internal var tv_cancel:TextView?=null
    internal var tv_claer:TextView?=null
    internal var signDrawView: DrawingView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = fixFaultActivity!!.getLayoutInflater().inflate(R.layout.tap_to_sign_dialog_layout, null)
        setContentView(view)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
          deviceResolution= DeviceResolution(fixFaultActivity)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        deviceResolution = DeviceResolution(fixFaultActivity)
        val wmlp = window!!.getAttributes()

        wmlp.width = deviceResolution!!.getWidth(0.9)
        wmlp.height = deviceResolution!!.getWidth(1.0)
        window!!.setAttributes(wmlp)
        initView(view)
    }

    private fun initView(view: View) {
        tv_done = view.findViewById(R.id.tv_done)
        tv_signhere = view.findViewById(R.id.tv_signhere)
        tv_cancel = view.findViewById(R.id.tv_cancel)
        tv_claer = view.findViewById(R.id.tv_claer)
        signDrawView = view.findViewById(R.id.sign)
        tv_done!!.setOnClickListener(this)
        tv_signhere!!.setOnClickListener(this)
        tv_cancel!!.setOnClickListener(this)
        tv_claer!!.setOnClickListener(this)

        //settypeface
        tv_done!!.setTypeface(deviceResolution!!.getbebas(fixFaultActivity))
        tv_signhere!!.setTypeface(deviceResolution!!.getbebas(fixFaultActivity))
        tv_cancel!!.setTypeface(deviceResolution!!.getbebas(fixFaultActivity))
        tv_claer!!.setTypeface(deviceResolution!!.getbebas(fixFaultActivity))

        //  signDrawView.setImageBitmap();


    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.tv_done->{
                val bitmap = signDrawView!!.bitmap
                //servicingOnClick.taptosignengineer(bitmap)
               // if (PreferenceConstent.signdraw == true) {
                    //  if (bitmap!=null || !bitmap.equals("")) {
                    fixFaultActivity!!.fixFaultViewBind!!.tv_engineer_signaturehere.setText("")
                fixFaultActivity.fixFaultViewBind!!.tv_engineer_signaturehere.setBackgroundResource(R.color.text_color)
                    fixFaultActivity!!.fixFaultViewBind!!.img_engineer_sign.setImageBitmap(bitmap)
                    PreferenceConstent.signdraw = false
               // }
                dismiss()
            }
            R.id.tv_cancel->{
                dismiss()
            }
            R.id.tv_claer->{
                signDrawView!!.clear()
            }

        }

    }
}