package com.wecompli.utils.customalert

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.screeen.checktaptosign.CheckTapToSignActivity
import com.wecompli.utils.sheardpreference.PreferenceConstent
import com.wecompli.utils.utilsview.DrawingView

class TapToSignDialog(val checkTapToSignActivity: CheckTapToSignActivity) :AppCompatDialog(checkTapToSignActivity), View.OnClickListener {
    var deviceResolution:DeviceResolution?=null
    var   tv_done: TextView?=null
    var   tv_signhere:TextView?=null
     var tv_cancel:TextView?=null
     var tv_claer:TextView?=null
     var signDrawView: DrawingView?=null
    override fun onClick(p0: View?) {
        when (p0!!.getId()) {
            R.id.tv_done -> {
                //    signDrawView.clear();
                checkTapToSignActivity.imagesignAvaliable=true
                val bitmap = signDrawView!!.bitmap
                checkTapToSignActivity.tapToSignViewBind!!.tv_tap_to_sign!!.setText("")
                   checkTapToSignActivity.tapToSignViewBind!!.img_sign!!.setImageBitmap(bitmap)
                    //  if (bitmap!=null || !bitmap.equals("")) {

                dismiss()
            }
            R.id.tv_signhere -> {
            }
            R.id.tv_cancel -> {
                dismiss()
                checkTapToSignActivity.imagesignAvaliable = true
            }
            R.id.tv_claer -> signDrawView!!.clear()
        }//  Bitmap bmp=signDrawView.getBitmap();
        //  Bitmap bmp=signDrawView.getBitmap();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = checkTapToSignActivity.getLayoutInflater().inflate(R.layout.tap_to_sign_dialog_layout, null)
        setContentView(view)
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        deviceResolution = DeviceResolution(checkTapToSignActivity)
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
        tv_done!!.setTypeface(deviceResolution!!.getbebas(checkTapToSignActivity))
        tv_signhere!!.setTypeface(deviceResolution!!.getbebas(checkTapToSignActivity))
        tv_cancel!!.setTypeface(deviceResolution!!.getbebas(checkTapToSignActivity))
        tv_claer!!.setTypeface(deviceResolution!!.getbebas(checkTapToSignActivity))

        //  signDrawView.setImageBitmap();


    }

}