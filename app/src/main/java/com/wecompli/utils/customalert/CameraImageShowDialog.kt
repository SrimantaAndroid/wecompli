package com.wecompli.utils.customalert

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.apiresponsemodel.checkelementdetails.ElementDetailsRow
import com.wecompli.screeen.checkelementdetails.CheckElementDetailsActivity

class CameraImageShowDialog(context: Context?) : AppCompatDialog(context),View.OnClickListener {
    override fun onClick(p0: View?) {
    when(p0!!.id){
        R.id.btn_retake->{
            dismiss()
            context!!.checkpermessionopencamera()

        }
        R.id.btn_submit_phato->{
            dismiss()
            context!!.callApiforCheckSubmitusingimage()

        }
        R.id.rl_cross_cameraphato->{
            dismiss()
        }
      }
    }

    var context: CheckElementDetailsActivity?=null
    var elementdetailsrow: ElementDetailsRow?=null
    var thumbnail: Bitmap?=null
    internal lateinit var rl_cross_cameraphato: RelativeLayout
    internal lateinit var tv_elementname: TextView
    internal lateinit var tv_e_details:TextView
    internal lateinit var btn_submit_phato: Button
    internal lateinit var btn_retake:Button
    internal lateinit var img_preview: ImageView
    internal lateinit var deviceResolution: DeviceResolution
    constructor(context: CheckElementDetailsActivity, elementdetailsrow: ElementDetailsRow?, thumbnail: Bitmap) : this(context){
        this.context=context
        this.elementdetailsrow=elementdetailsrow
        this.thumbnail=thumbnail
        deviceResolution=DeviceResolution(context)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = context!!.getLayoutInflater().inflate(R.layout.image_preview_layout, null)
        setContentView(view)
        val wmlp = window!!.getAttributes()
        val manager = context!!.getSystemService(Activity.WINDOW_SERVICE) as WindowManager
        val window = window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        initview(view)
    }
    private fun initview(view: View) {
        img_preview = view.findViewById(R.id.img_preview)
        tv_elementname = view.findViewById(R.id.tv_elementname)
        rl_cross_cameraphato = view.findViewById(R.id.rl_cross_cameraphato)
        btn_retake = view.findViewById(R.id.btn_retake)
        btn_submit_phato = view.findViewById(R.id.btn_submit_phato)
        tv_e_details = view.findViewById(R.id.tv_e_details)
        btn_retake.setTypeface(deviceResolution.getbebas(context))
        btn_submit_phato.setTypeface(deviceResolution.getbebas(context))
        tv_elementname.setTypeface(deviceResolution.getbebas(context))
        btn_retake.setTypeface(deviceResolution.getbebas(context))
        tv_e_details.setTypeface(deviceResolution.getgothmlight(context))

        setonclic()


    }

    private fun setonclic() {
        img_preview.setImageBitmap(thumbnail)
        tv_elementname.setText(elementdetailsrow!!.checkName)

        btn_retake.setOnClickListener(this)
        btn_submit_phato.setOnClickListener(this)
        rl_cross_cameraphato.setOnClickListener(this)
    }

}