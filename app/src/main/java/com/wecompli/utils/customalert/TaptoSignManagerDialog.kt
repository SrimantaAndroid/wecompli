package com.wecompli.utils.customalert

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.screeen.fixfault.FixFaultActivity
import com.wecompli.utils.sheardpreference.PreferenceConstent
import com.wecompli.utils.utilsview.DrawingView
import java.io.File
import java.io.FileOutputStream

class TaptoSignManagerDialog(val fixFaultActivity: FixFaultActivity?) : AppCompatDialog(fixFaultActivity), View.OnClickListener{
    var deviceResolution:DeviceResolution?=null
    internal var tv_done: TextView?=null
    internal var tv_signhere:TextView?=null
    internal var tv_cancel:TextView?=null
    internal var tv_claer:TextView?=null
    internal var signDrawView: DrawingView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = fixFaultActivity!!.getLayoutInflater().inflate(R.layout.tap_to_sign_dialog_layout, null)
        setContentView(view)
        setCanceledOnTouchOutside(true)
        setCancelable(true)

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
                    // servicingOnClick.taptosignmanager(bitmap)
              //  if (PreferenceConstent.signdraw == true) {
                    //  if (bitmap!=null || !bitmap.equals("")) {
                    PreferenceConstent.signdraw = false
                    fixFaultActivity!!.fixFaultViewBind!!.tv_manager_signaturehere.setText("")
                fixFaultActivity.fixFaultViewBind!!.tv_manager_signaturehere.setBackgroundResource(R.color.text_color)

                fixFaultActivity!!.fixFaultViewBind!!.img_manager_sign.setImageBitmap(bitmap)
                fixFaultActivity!!.selectmanagertimage=true
                val root = Environment.getExternalStorageDirectory().toString()
                val myDir = File("$root/req_images")
                myDir.mkdirs()
                // val generator = Random()
                //  var n = 10000
                //  n = generator.nextInt(n)
                val fname = "Manager_sign.jpg"
                val file = File(myDir, fname)
                if (file.exists())
                    file.delete()
                try {
                    val out = FileOutputStream(file)
                    // bmap.setHasAlpha(true);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                    out.flush()
                    out.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                fixFaultActivity.managersign=file
                //}
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