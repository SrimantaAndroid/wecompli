package com.wecompli.screeen.accidentreport

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.R
import com.wecompli.apiresponsemodel.notifywho.NotifyWhoModel
import com.wecompli.apiresponsemodel.seasonlist.SeasonListApiresponse
import com.wecompli.apiresponsemodel.servritylevel.ServrityModel
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class AccidentReportActivity :AppCompatActivity(){
    var accidentReportViewBind:AccidentReportViewBind?=null
    var accidentReportOnclick:AccidentReportOnclick?=null
    var servityvalue:String=""
    var usertppe:String=""
    var image: String?=null
    var REQUEST_CAMERA = 111
    var SELECT_FILE = 112
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
        callservaritylevelApi()
        setvalue()
    }

    private fun callservaritylevelApi() {
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("site_id", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.site_id))

            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject

            val calltodayseasonApi = apiInterface.callApiforSERVRITYLABEL("application/json",
                AppSheardPreference(this!!).getvalue_in_preference(PreferenceConstent.loginuser_token),gsonObject)
            calltodayseasonApi.enqueue(object : Callback<ServrityModel> {
                override fun onResponse(call: Call<ServrityModel>, response: Response<ServrityModel>) {
                    customProgress.hideProgress()
                    if (response.code() == 200) {
                       /* var str_response = response.body()!!.string()
                        val json_contact:JSONObject = JSONObject(str_response)
                        val row=json_contact.getJSONObject("row")*/

                        if(response.body()!!.row.field_type.equals("majorminor")){
                           accidentReportViewBind!!.rg_servety_level!!.visibility=View.VISIBLE
                       }else{
                           accidentReportViewBind!!.seek_servertylevel!!.visibility=View.VISIBLE
                       }

                        //  showcomponentValue(response!!.body()!!.row!!.get(i).seasonName.toString())
                        // }
                    } else if (response.code() == 401) {


                    }
                }

                override fun onFailure(call: Call<ServrityModel>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun setvalue() {
        accidentReportViewBind!!.tv_company!!.setText(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.UserCompany))
        accidentReportViewBind!!.tv_site!!.setText(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.UserSite))
    }
    fun cretefileforsignbehalf(bitmap: Bitmap) {
        accidentReportViewBind!!.img_signbehlf!!.setDrawingCacheEnabled(true)
        val bmap = accidentReportViewBind!!.img_signbehlf!!.getDrawingCache()
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/wecompli/incident_images")
        myDir.mkdirs()
      /*  val generator = Random()
        var n = 10000
        n = generator.nextInt(n)*/
        val fname = "image_behalfsign.jpg"
        signed_employment_person = File(myDir, fname)
        if (signed_employment_person!!.exists())
            signed_employment_person!!.delete()
        try {
            val out = FileOutputStream(signed_employment_person)
            // bmap.setHasAlpha(true);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }



    }

    fun cretefileforwitness1sign(bitmap: Bitmap) {
        accidentReportViewBind!!.img_witeness1!!.isDrawingCacheEnabled = true
        val bmap = accidentReportViewBind!!.img_witeness1!!.drawingCache
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/wecompli/incident_images")
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
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun cretefileforwitness2sign(bitmap: Bitmap) {
        accidentReportViewBind!!.imgwiteness2!!.isDrawingCacheEnabled = true
        val bmap = accidentReportViewBind!!.imgwiteness2!!.drawingCache
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/wecompli/incident_images")
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
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    fun cretefileforsignfromcompleted(bitmap: Bitmap) {
        accidentReportViewBind!!.img_sign_completedby!!.isDrawingCacheEnabled = true
        val bmap = accidentReportViewBind!!.img_sign_completedby!!.drawingCache
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/wecompli/incident_images")
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
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun cretefileforsignfromcompletedinjuredperson(bitmap: Bitmap) {
        accidentReportViewBind!!.img_injuredperson!!.isDrawingCacheEnabled = true
        val bmap = accidentReportViewBind!!.img_injuredperson!!.drawingCache
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/wecompli/incident_images")
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
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
         if (requestCode == 500) {
            if (data != null) {
                val body = data.getStringExtra("body")
                if (!body.equals(""))
                accidentReportViewBind!!.tv_bodymaptext!!.text = body.substring(1, body.length)
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data)
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data!!)
        }

    }
    fun showalertforImageSelectio() {
        // var deviceResolution:DeviceResolution?=null
        var deviceResolution = DeviceResolution(this)
        val alertDialog = Dialog(this, R.style.Transparent)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view: View = LayoutInflater.from(this).inflate(R.layout.alert_custom_imageselection, null)
        alertDialog.setContentView(view)
        alertDialog.setCancelable(false)
        val tv_message: TextView = view.findViewById(R.id.tv_message)
        val btn_gallery: Button = view.findViewById(R.id.btn_gallery)
        val btn_camera: Button =view.findViewById(R.id.btn_camera)
        val btn_cancel: Button =view.findViewById(R.id.btn_cancel)
        btn_camera.typeface=deviceResolution.getgothmbold(this)
        btn_cancel.typeface=deviceResolution.getgothmbold(this)
        btn_gallery.typeface = deviceResolution.getgothmbold(this)
        tv_message.typeface = deviceResolution.getgothmbold(this)
        btn_gallery.setOnClickListener {
            alertDialog.dismiss()
            image = "gallery"
            checkpermession()
        }
        btn_camera.setOnClickListener {
            alertDialog.dismiss()
            image = "camera"
            checkpermession()
        }
        btn_cancel.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()

    }
    fun checkpermession(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        } else {
            if (image == "gallery")
                galleryIntent()
            else if (image == "camera")
                takePhotoFromCamera()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 0) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                if (image == "gallery")
                    galleryIntent()
                else if (image == "camera")
                    takePhotoFromCamera()
            }
        }
    }
    private fun galleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE)
    }
    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CAMERA)
    }
    private fun onCaptureImageResult(data: Intent) {
        val thumbnail = data.extras!!.get("data") as Bitmap?
        /* val bytes = ByteArrayOutputStream()
         thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
         val destination = File(Environment.getExternalStorageDirectory(), "fault_image"+ ".jpg")
         val fo: FileOutputStream
 */
        try {
            // thumbnail = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, data.data)
            /*  val bytes = ByteArrayOutputStream()
              bm!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
              val destination = File(Environment.getExternalStorageDirectory(), "fault_image"+ ".jpg")*/
            // val destination = File(Environment.getExternalStorageDirectory(), System.currentTimeMillis().toString() + ".jpg")

            val root = Environment.getExternalStorageDirectory().toString()
            val myDir = File("$root/wecompli/fault_images")
            myDir.mkdirs()
            /* val generator = Random()
              var n = 100
              n = generator.nextInt(n)*/
            val fname = "accident_image.jpg"
            val file = File(myDir, fname)
            val fo: FileOutputStream
            if (file.exists())
                file.delete()
            try {
                /* destination.createNewFile()
                 fo = FileOutputStream(destination)
                 fo.write(bytes.toByteArray())
                 fo.close()*/
                val out = FileOutputStream(file)
                thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
                accidentReportViewBind!!.img_select!!.setImageBitmap(thumbnail)
                //addimagelistview(file.absolutePath,thumbnail,file)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun onSelectFromGalleryResult(data: Intent?) {
        var bm: Bitmap? = null
        if (data != null) {
            try {
                bm =
                    MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, data.data)
                /*  val bytes = ByteArrayOutputStream()
                  bm!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                  val destination = File(Environment.getExternalStorageDirectory(), "fault_image"+ ".jpg")*/
                // val destination = File(Environment.getExternalStorageDirectory(), System.currentTimeMillis().toString() + ".jpg")

                val root = Environment.getExternalStorageDirectory().toString()
                val myDir = File("$root/wecompli/fault_images")
                myDir.mkdirs()
                /* val generator = Random()
                  var n = 100
                  n = generator.nextInt(n)*/
                val fname = "accident_image.jpg"
                val file = File(myDir, fname)
                val fo: FileOutputStream
                if (file.exists())
                    file.delete()
                try {
                    /* destination.createNewFile()
                     fo = FileOutputStream(destination)
                     fo.write(bytes.toByteArray())
                     fo.close()*/
                    val out = FileOutputStream(file)
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, out)
                    out.flush()
                    out.close()
                    accidentReportViewBind!!.img_select!!.setImageBitmap(bm)
                    //addimagelistview(file.absolutePath,bm,file)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
}