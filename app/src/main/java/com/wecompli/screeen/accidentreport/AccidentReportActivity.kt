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
import android.widget.Toast
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
import com.wecompli.network.NetworkUtility
import com.wecompli.network.Retrofit
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit

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
   // var filewitness1:File?=null
    //var filewitness2:File?=null



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

    fun callAccidentreportsubmitApi(){

        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("company_id",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.Selectedcompany_id))
        builder.addFormDataPart("site_id" ,AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.site_id))
        builder.addFormDataPart("severity_level", servityvalue)
        builder.addFormDataPart("organisation_name",accidentReportViewBind!!.et_nameof_organization!!.text.toString())
        builder.addFormDataPart("organisation_address" , accidentReportViewBind!!.et_organizationaddress!!.text.toString())
        builder.addFormDataPart("organisation_postcode", accidentReportViewBind!!.et_postcode!!.text.toString())
        builder.addFormDataPart("organisation_telephone", accidentReportViewBind!!.et_telephone!!.text.toString())
        builder.addFormDataPart("injured_person_name", accidentReportViewBind!!.et_fullnameofperson_injured!!.text.toString())

        builder.addFormDataPart("injured_person_telephone", accidentReportViewBind!!.et_telephone_person!!.text.toString())
        builder.addFormDataPart("injured_person_postcode", accidentReportViewBind!!.et_postcode_person!!.text.toString())

        builder.addFormDataPart("injured_person_date_of_birth", accidentReportViewBind!!.tv_date_of_birth!!.text.toString())
        builder.addFormDataPart("accident_injuries_image", "injuries_image"+".jpg", okhttp3.RequestBody.create(
            MediaType.parse("image/jpeg"), signed_employment_person))
        //paramObject.put("accident_injuries_image", accidentReportViewBind!!.et_personhomeaddress!!.text.toString())
        builder.addFormDataPart("user_type", usertppe)
        builder.addFormDataPart("user_type_other", accidentReportViewBind!!.et_detailsofother!!.text.toString())
        builder.addFormDataPart("date_of_occurrence", accidentReportViewBind!!.tv_dateofoccurenceet_postcode_person!!.text.toString())
        builder.addFormDataPart("time_of_occurrence", accidentReportViewBind!!.tv_timeof_occurence!!.text.toString())
        builder.addFormDataPart("location", accidentReportViewBind!!.tv_select_location!!.text.toString())
        builder.addFormDataPart("full_description_incident_circumstances", accidentReportViewBind!!.et_fulldescrtpttion!!.text.toString())
        builder.addFormDataPart("effected_body_parts", accidentReportViewBind!!.tv_bodymaptext!!.text.toString())
        builder.addFormDataPart("full_description_injuries_suffered", accidentReportViewBind!!.et_fulldes_inj!!.text.toString())
        builder.addFormDataPart("injured_person_employment_nature", "")
        builder.addFormDataPart("injured_person_employment_duty_status", "")
        builder.addFormDataPart("injured_person_employment_duty_work_status", "")
        builder.addFormDataPart("injured_person_employment_off_duty_time", accidentReportViewBind!!.et_off_duty!!.text.toString())
        builder.addFormDataPart("employment_person_name", accidentReportViewBind!!.et_prientname!!.text.toString())
        builder.addFormDataPart("employment_person_position", accidentReportViewBind!!.et_position!!.text.toString())
        builder.addFormDataPart("employment_signed_date", accidentReportViewBind!!.tv_datepatient!!.text.toString())
        // paramObject.put("effected_body_parts", accidentReportViewBind!!.tv_bodymaptext!!.text.toString())
        builder.addFormDataPart("witness_1st_statement", accidentReportViewBind!!.et_statementdetails!!.text.toString())
        builder.addFormDataPart("witness_1st_signed", "witness1"+".jpg", okhttp3.RequestBody.create(
            MediaType.parse("image/jpeg"), witness1))
        //paramObject.put("witness_1st_signed", accidentReportViewBind!!.tv_bodymaptext!!.text.toString())
        builder.addFormDataPart("witness_1st_home_address", accidentReportViewBind!!.et_address_wite!!.text.toString())
        builder.addFormDataPart("witness_1st_postcode", accidentReportViewBind!!.et_postcodewe1!!.text.toString())

        builder.addFormDataPart("witness_2nd_statement", accidentReportViewBind!!.et_statementdetails2!!.text.toString())
        builder.addFormDataPart("witness_2nd_signed", "witness2"+".jpg", okhttp3.RequestBody.create(
            MediaType.parse("image/jpeg"), witness2))
        //  paramObject.put("witness_2nd_signed", accidentReportViewBind!!.et_date!!.text.toString())
        builder.addFormDataPart("witness_2nd_home_address", accidentReportViewBind!!.et_address_wite2!!.text.toString())
        builder.addFormDataPart("witness_2nd_postcode", accidentReportViewBind!!.et_postcodewe2!!.text.toString())
        builder.addFormDataPart("witness_2nd_person_name", accidentReportViewBind!!.patient2!!.text.toString())
        builder.addFormDataPart("witness_2nd_signed_date", accidentReportViewBind!!.tv_date2!!.text.toString())
        builder.addFormDataPart("form_completed_person_name", accidentReportViewBind!!.et_complientby!!.text.toString())
        builder.addFormDataPart("form_completed_person_signed", "formcompleted"+".jpg", okhttp3.RequestBody.create(
            MediaType.parse("image/jpeg"), form_completed_person_signed))
        builder.addFormDataPart("form_completed_injured_person_signed", "formcompleted_inj_perso"+".jpg", okhttp3.RequestBody.create(
            MediaType.parse("image/jpeg"), form_completed_injured_person_signed))
        //paramObject.put("form_completed_person_signed", accidentReportViewBind!!.et_postcodewe2!!.text.toString())
        //paramObject.put("form_completed_injured_person_signed", accidentReportViewBind!!.patient2!!.text.toString())
        builder.addFormDataPart("form_completed_injured_person_name", accidentReportViewBind!!.et_injuredperson!!.text.toString())
        builder.addFormDataPart("information_share", accidentReportViewBind!!.et_injuredperson!!.text.toString())
        builder.addFormDataPart("fault_mail", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.notifyemail))
        builder.addFormDataPart("notify_who",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.SelectedEmail))
        //for (i in docImagelist.indices) {
         //   builder.addFormDataPart("document_file[]", "doc_image_"+i.toString()+".jpg", okhttp3.RequestBody.create(
             //   MediaType.parse("image/jpeg"), docImagelist.get(i).file))
      //  }
        //builder.addFormDataPart("fault_image", imagearraylist.get(0).name, okhttp3.RequestBody.create(MediaType.parse("image/jpeg"), imagearraylist.get(0)))

        val requestBody = builder.build()
        var request: Request? = null
        request = Request.Builder()
            .addHeader("Authorization", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token))
            .addHeader("site_id",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.site_id))
            .addHeader("Content-Type","application/json")
            .url(NetworkUtility.BASE_URL + NetworkUtility.CREATEINCIDENTREPORT)
            .post(requestBody)
            .build()

        val client = okhttp3.OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()

        val call = client.newCall(request)
        call.enqueue(object :okhttp3.Callback{
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {

                // System.out.println("respppdoc"+response.body()!!.string())
                try {
                    var resStr :String=response.body()!!.string()
                    var response_obj= JSONObject(resStr)
                    val message=response_obj.getString("message")
                    customProgress.hideProgress()
                    if (response_obj.getBoolean("status")){
                        //{"status":true,"message":"Document added"}
                        //   val check_process_log_id:String=response_obj.getInt("check_process_log_id").toString()
                        //callApiforfaultcreate(check_process_log_id);
                        AppSheardPreference(this@AccidentReportActivity).setvalue_in_preference(PreferenceConstent.SelectedEmail,"")
                        // val intent = Intent()
                        // setResult(ApplicationConstant.INTENT_CHECKCOMPONENT, intent)
                        runOnUiThread {
                            Alert.showalertToGoHomePage(this@AccidentReportActivity,message)
                        }
                    }else{
                        //Toast.makeText(this@DocManagmentActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                        Toast.makeText(this@AccidentReportActivity, message, Toast.LENGTH_LONG).show()
                    }
                }
                catch (e: java.lang.Exception){
                    e.printStackTrace()
                  //  Toast.makeText(this@AccidentReportActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                customProgress.hideProgress()
            }
        })

       /* val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("company_id", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.selected_company_id))
            paramObject.put("site_id", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.site_id))
            paramObject.put("severity_level", servityvalue)
            paramObject.put("organisation_name", accidentReportViewBind!!.et_nameof_organization!!.text.toString())
            paramObject.put("organisation_address", accidentReportViewBind!!.et_organizationaddress!!.text.toString())
            paramObject.put("organisation_postcode", accidentReportViewBind!!.et_postcode!!.text.toString())
            paramObject.put("injured_person_name", accidentReportViewBind!!.et_fullnameofperson_injured!!.text.toString())
            paramObject.put("injured_person_address", accidentReportViewBind!!.et_personhomeaddress!!.text.toString())
            paramObject.put("injured_person_postcode", accidentReportViewBind!!.et_postcode_person!!.text.toString())
            paramObject.put("injured_person_telephone", accidentReportViewBind!!.et_telephone_person!!.text.toString())
            paramObject.put("injured_person_date_of_birth", accidentReportViewBind!!.tv_date_of_birth!!.text.toString())
            //paramObject.put("accident_injuries_image", accidentReportViewBind!!.et_personhomeaddress!!.text.toString())
            paramObject.put("user_type", usertppe)
            paramObject.put("user_type_other", accidentReportViewBind!!.et_detailsofother!!.text.toString())
            paramObject.put("date_of_occurrence", accidentReportViewBind!!.tv_dateofoccurenceet_postcode_person!!.text.toString())
            paramObject.put("time_of_occurrence", accidentReportViewBind!!.tv_timeof_occurence!!.text.toString())
            paramObject.put("location", accidentReportViewBind!!.tv_select_location!!.text.toString())
            paramObject.put("full_description_incident_circumstances", accidentReportViewBind!!.et_fulldescrtpttion!!.text.toString())
            paramObject.put("effected_body_parts", accidentReportViewBind!!.tv_bodymaptext!!.text.toString())
            paramObject.put("full_description_injuries_suffered", accidentReportViewBind!!.et_fulldes_inj!!.text.toString())
            paramObject.put("injured_person_employment_nature", "")
            paramObject.put("injured_person_employment_duty_status", "")
            paramObject.put("injured_person_employment_duty_work_status", "")
            paramObject.put("injured_person_employment_off_duty_time", accidentReportViewBind!!.et_off_duty!!.text.toString())
            paramObject.put("employment_person_name", accidentReportViewBind!!.et_prientname!!.text.toString())
            paramObject.put("employment_person_position", accidentReportViewBind!!.et_position!!.text.toString())
            paramObject.put("employment_signed_date", accidentReportViewBind!!.tv_datepatient!!.text.toString())
           // paramObject.put("effected_body_parts", accidentReportViewBind!!.tv_bodymaptext!!.text.toString())
            paramObject.put("witness_1st_statement", accidentReportViewBind!!.et_statementdetails!!.text.toString())
            //paramObject.put("witness_1st_signed", accidentReportViewBind!!.tv_bodymaptext!!.text.toString())
            paramObject.put("witness_1st_home_address", accidentReportViewBind!!.et_address_wite!!.text.toString())
            paramObject.put("witness_1st_postcode", accidentReportViewBind!!.et_postcodewe1!!.text.toString())

            paramObject.put("witness_2nd_statement", accidentReportViewBind!!.et_statementdetails2!!.text.toString())
          //  paramObject.put("witness_2nd_signed", accidentReportViewBind!!.et_date!!.text.toString())
            paramObject.put("witness_2nd_home_address", accidentReportViewBind!!.et_address_wite2!!.text.toString())
            paramObject.put("witness_2nd_postcode", accidentReportViewBind!!.et_postcodewe2!!.text.toString())
            paramObject.put("witness_2nd_person_name", accidentReportViewBind!!.patient2!!.text.toString())
            paramObject.put("witness_2nd_signed_date", accidentReportViewBind!!.tv_date2!!.text.toString())
            paramObject.put("form_completed_person_name", accidentReportViewBind!!.et_complientby!!.text.toString())
            //paramObject.put("form_completed_person_signed", accidentReportViewBind!!.et_postcodewe2!!.text.toString())
            //paramObject.put("form_completed_injured_person_signed", accidentReportViewBind!!.patient2!!.text.toString())
            paramObject.put("form_completed_injured_person_name", accidentReportViewBind!!.et_injuredperson!!.text.toString())
            paramObject.put("information_share", accidentReportViewBind!!.et_injuredperson!!.text.toString())
            paramObject.put("fault_mail", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.notifyemail))

            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject
            val callApiaccidentreport = apiInterface.calllCreateIncidentapi("application/json",
                AppSheardPreference(this!!).getvalue_in_preference(PreferenceConstent.loginuser_token),gsonObject)
            callApiaccidentreport.enqueue(object :Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    customProgress.hideProgress()
                    if (response.isSuccessful){

                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                }
            } )

        }catch (e : java.lang.Exception){
            e.printStackTrace()
        }*/
    }
}