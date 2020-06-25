package com.wecompli.screeen.checktaptosign

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Base64OutputStream
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.koushikdutta.async.http.AsyncHttpPost
import com.koushikdutta.async.http.body.MultipartFormDataBody
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.R
import com.wecompli.apiresponsemodel.checkelementdetails.ElementDetailsRow
import com.wecompli.network.ApiInterface
import com.wecompli.network.NetworkUtility
import com.wecompli.network.Retrofit
import com.wecompli.utils.ApplicationConstant
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.concurrent.TimeUnit

class CheckTapToSignActivity:AppCompatActivity() {
    var tapToSignViewBind:CheckTapToSignViewBind?=null
    var taptoSignOnClick:CheckTaptoSignOnClick?=null
    var elementDetailsRow: ElementDetailsRow?=null
    var checkdate:String?=null
    var checkcomponent:String?=null
    var sideid:String?=null
    var sessionid:String?=null
   public var imagesignAvaliable:Boolean?=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_check_tap_to_sign,null)
        setContentView(view)
        tapToSignViewBind= CheckTapToSignViewBind(this,view)
        taptoSignOnClick= CheckTaptoSignOnClick(this,tapToSignViewBind!!)
        val intent=intent
        elementDetailsRow=intent.getSerializableExtra("elementdetails")as ElementDetailsRow
        checkcomponent=intent.getStringExtra("componet")
        checkdate=intent.getStringExtra("date")
        sideid=intent.getStringExtra("sideid")
        sessionid=intent.getStringExtra("session")
        tapToSignViewBind!!.tv_general_task_name!!.setText(elementDetailsRow!!.checkName)
    }
    fun isWriteStoragePermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED) {
                taptosign()
                return true
            } else {
                // Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)
                return false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            //Log.v(TAG,"Permission is granted2");
            taptosign()
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
          when(requestCode){
              2->{
                  if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                      taptosign()
                  }
              }
          }
    }
    private fun taptosign() {
        tapToSignViewBind!!.img_sign!!.setDrawingCacheEnabled(true)
        val bmap = tapToSignViewBind!!.img_sign!!.getDrawingCache()
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/req_images")
        myDir.mkdirs()
       // val generator = Random()
      //  var n = 10000
      //  n = generator.nextInt(n)
        val fname = "Image_sign.jpg"
        val file = File(myDir, fname)
        if (file.exists())
            file.delete()
        try {
            val out = FileOutputStream(file)
            // bmap.setHasAlpha(true);
            bmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
      // callApiforCheckSubmitusingimage(file)
        //submitimageusingmultipart(file)
        submitfalutusingmultipartBulider(file)
    }

    public fun callApiforCheckSubmitusingimage(file: File) {
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("check_id", elementDetailsRow!!.id)
            paramObject.put("season_id", sessionid)
            paramObject.put("check_type_id", elementDetailsRow!!.checkTypeId)
            paramObject.put("check_type_values_id", elementDetailsRow!!.checkTypeValue!!.get(0).id)
            paramObject.put("check_process_type", PreferenceConstent.category_purpose)
            paramObject.put("check_date",checkdate)
            paramObject.put("process_remark",tapToSignViewBind!!.et_input!!.text.toString())
            paramObject.put("process_status","Y")
            paramObject.put("process_file",convertImageFileToBase64(file))

            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callApi= apiInterface.callcomponetCheckSubmit(
                AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token),sideid!!, gsonObject!!)
            callApi.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    customProgress.hideProgress()
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            val intent = Intent()
                            setResult(ApplicationConstant.INTENT_CHECKCOMPONENT, intent)
                            finish()
                        }
                    }
                    else
                        Toast.makeText(this@CheckTapToSignActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })

        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }
    fun convertImageFileToBase64(imageFile: File): String {
        return FileInputStream(imageFile).use { inputStream ->
            ByteArrayOutputStream().use { outputStream ->
                Base64OutputStream(outputStream, Base64.DEFAULT).use { base64FilterStream ->
                    inputStream.copyTo(base64FilterStream)
                    base64FilterStream.runCatching {  } // This line is required, see comments
                    outputStream.toString()
                }
            }
        }
    }

    fun submitfalutusingmultipartBulider(file: File){
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("check_id" ,elementDetailsRow!!.id)
        builder.addFormDataPart("season_id", sessionid)
        builder.addFormDataPart("check_type_id", elementDetailsRow!!.checkTypeId)
        builder.addFormDataPart("check_type_values_id",elementDetailsRow!!.checkTypeValue!!.get(0).id)
        builder.addFormDataPart("check_process_type" ,PreferenceConstent.category_purpose)
        builder.addFormDataPart("check_date", checkdate)
        builder.addFormDataPart("process_remark", tapToSignViewBind!!.et_input!!.text.toString())
        builder.addFormDataPart("process_status",PreferenceConstent.process_status)
        builder.addFormDataPart("process_file[]", file.name, okhttp3.RequestBody.create(MediaType.parse("image/jpeg"), file))
        val requestBody = builder.build()
        var request: Request? = null
        request = Request.Builder()
            .addHeader("Authorization", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token))
            .addHeader("site_id",sideid)
            .addHeader("Content-Type","application/json")
            .url(NetworkUtility.BASE_URL + NetworkUtility.COMPONENET_CHECK_SUBMIT)
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
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        val intent = Intent()
                        setResult(ApplicationConstant.INTENT_CHECKCOMPONENT, intent)
                        finish()
                    }
                }
                else
                    Toast.makeText(this@CheckTapToSignActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()

            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                customProgress.hideProgress()
            }
        })

    }

    fun submitimageusingmultipart(file: File){
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {

            val  requestFile :RequestBody= RequestBody.create(MediaType.parse("multipart/form-data"), file);
// MultipartBody.Part is used to send also the actual file name
            val fbody =MultipartBody.Part.createFormData("process_file", file.getName(), requestFile);
           // val fbody: RequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            val check_id:RequestBody = RequestBody.create(MediaType.parse("text/plain"),elementDetailsRow!!.id)
            val sessionid:RequestBody = RequestBody.create(MediaType.parse("text/plain"),sessionid)
            val checktype:RequestBody = RequestBody.create(MediaType.parse("text/plain"),elementDetailsRow!!.checkTypeId)
            val checktypevalueid:RequestBody = RequestBody.create(MediaType.parse("text/plain"),elementDetailsRow!!.checkTypeValue!!.get(0).id)
            val checksprocess:RequestBody = RequestBody.create(MediaType.parse("text/plain"),PreferenceConstent.category_purpose)
            val checkdate:RequestBody = RequestBody.create(MediaType.parse("text/plain"),checkdate)
            val checkremark:RequestBody = RequestBody.create(MediaType.parse("text/plain"),tapToSignViewBind!!.et_input!!.text.toString())
            val process_status:RequestBody = RequestBody.create(MediaType.parse("text/plain"),"Y")
            val authtoken:RequestBody = RequestBody.create(MediaType.parse("text/plain"), AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token))
            val side_id:RequestBody = RequestBody.create(MediaType.parse("text/plain"), sideid)
            val callApi= apiInterface.callApifaultsubmitusingImsage(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token),sideid!!,
                check_id,sessionid,checktype,checktypevalueid ,checksprocess,checkdate,checkremark,process_status,fbody)

            callApi.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    customProgress.hideProgress()
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            val intent = Intent()
                            setResult(ApplicationConstant.INTENT_CHECKCOMPONENT, intent)
                            finish()
                        }
                    }
                    else
                        Toast.makeText(this@CheckTapToSignActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })


        }catch (e :Exception){
            e.printStackTrace()
        }
    }

}