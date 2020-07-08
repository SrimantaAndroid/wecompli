package com.wecompli.screeen.accidentreport

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wecompli.R
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import java.io.File
import java.io.FileOutputStream
import java.util.*

class AccidentReportActivity :AppCompatActivity(){
    var accidentReportViewBind:AccidentReportViewBind?=null
    var accidentReportOnclick:AccidentReportOnclick?=null
    var servityvalue:String=""
    var usertppe:String=""
    var REQUEST_CAMERA = 111
    var SELECT_FILE:Int = 112
    var position:Int = 0
    var destination: File? = null
    var witness1:java.io.File? = null
    var witness2:java.io.File? = null
    var signed_employment_person:java.io.File? = null
    var form_completed_person_signed:java.io.File? = null
    var form_completed_injured_person_signed:java.io.File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_accident_report,null)
        setContentView(view)
        accidentReportViewBind= AccidentReportViewBind(this,view)
        accidentReportOnclick=AccidentReportOnclick(this,accidentReportViewBind!!)
        setvalue()
    }
    private fun setvalue() {
        accidentReportViewBind!!.tv_company!!.setText(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.UserCompany))
        accidentReportViewBind!!.tv_site!!.setText(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.UserSite))
    }
    fun cretefileforsignbehalf() {
        accidentReportViewBind!!.img_signbehlf!!.setDrawingCacheEnabled(true)
        val bmap = accidentReportViewBind!!.img_signbehlf!!.getDrawingCache()
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/incident_images")
        myDir.mkdirs()
      /*  val generator = Random()
        var n = 10000
        n = generator.nextInt(n)*/
        val fname = "image_behalfsign.jpg"
        signed_employment_person = File(myDir, fname)
        if (signed_employment_person!!.exists()) signed_employment_person!!.delete()
        try {
            val out = FileOutputStream(signed_employment_person)
            // bmap.setHasAlpha(true);
            bmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun cretefileforwitness1sign() {
        accidentReportViewBind!!.img_witeness1!!.isDrawingCacheEnabled = true
        val bmap = accidentReportViewBind!!.img_witeness1!!.drawingCache
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/req_images")
        myDir.mkdirs()
      /*  val generator = Random()
        var n = 10000
        n = generator.nextInt(n)*/
        val fname = "image_witeness.jpg"
        witness1 = File(myDir, fname)
        if (witness1!!.exists()) witness1!!.delete()
        try {
            val out = FileOutputStream(witness1)
            // bmap.setHasAlpha(true);
            bmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun cretefileforwitness2sign() {
        accidentReportViewBind!!.imgwiteness2!!.isDrawingCacheEnabled = true
        val bmap = accidentReportViewBind!!.imgwiteness2!!.drawingCache
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/req_images")
        myDir.mkdirs()
       /* val generator = Random()
        var n = 10000
        n = generator.nextInt(n)*/
        val fname = "image_witness2.jpg"
        witness2 = File(myDir, fname)
        if (witness2!!.exists()) witness2!!.delete()
        try {
            val out = FileOutputStream(witness2)
            // bmap.setHasAlpha(true);
            bmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    fun cretefileforsignfromcompleted() {
        accidentReportViewBind!!.img_sign_completedby!!.isDrawingCacheEnabled = true
        val bmap = accidentReportViewBind!!.img_sign_completedby!!.drawingCache
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/req_images")
        myDir.mkdirs()
       /* val generator = Random()
        var n = 10000
        n = generator.nextInt(n)*/
        val fname = "image_completed.jpg"
        form_completed_person_signed = File(myDir, fname)
        if (form_completed_person_signed!!.exists()) form_completed_person_signed!!.delete()
        try {
            val out =
                FileOutputStream(form_completed_person_signed)
            // bmap.setHasAlpha(true);
            bmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun cretefileforsignfromcompletedinjuredperson() {
        accidentReportViewBind!!.img_injuredperson!!.isDrawingCacheEnabled = true
        val bmap = accidentReportViewBind!!.img_injuredperson!!.drawingCache
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/req_images")
        myDir.mkdirs()
       /* val generator = Random()
        var n = 10000
        n = generator.nextInt(n)*/
        val fname = "image_sinbywitness.jpg"
        form_completed_injured_person_signed = File(myDir, fname)
        if (form_completed_injured_person_signed!!.exists()) form_completed_injured_person_signed!!.delete()
        try {
            val out =
                FileOutputStream(form_completed_injured_person_signed)
            // bmap.setHasAlpha(true);
            bmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}