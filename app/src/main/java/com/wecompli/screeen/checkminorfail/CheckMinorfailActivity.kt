package com.wecompli.screeen.checkminorfail

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.koushikdutta.async.http.AsyncHttpClient
import com.koushikdutta.async.http.AsyncHttpPost
import com.koushikdutta.async.http.AsyncHttpResponse
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
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit

class CheckMinorfailActivity:AppCompatActivity() {
    var checkMinorFailViewBind:CheckMinorFailViewBind?=null
    var checkMinorOnClick:CheckMinorOnClick?=null
     var checkdate:String?=null
     var checkcomponent:String?=null
     var sideid:String?=null
     var sessionid:String?=null
     var elementDetailsRow: ElementDetailsRow?=null
     var imageView: ImageView?=null
     var REQUEST_CAMERA = 111
     var SELECT_FILE = 112
     var position:Int = 0
     var image: String?=null
    internal var Imagesellposition: Int = 0
    internal var imagearraylist = ArrayList<File>()
    internal var imagearraylistpath = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_check_fail_minorfail,null)
        setContentView(view)
        checkMinorFailViewBind= CheckMinorFailViewBind(this,view)
        checkMinorOnClick=CheckMinorOnClick(this,checkMinorFailViewBind!!)
        elementDetailsRow=intent.getSerializableExtra("elementdetails")as ElementDetailsRow
        checkcomponent=intent.getStringExtra("componet")
        checkdate=intent.getStringExtra("date")
        sideid=intent.getStringExtra("sideid")
        sessionid=intent.getStringExtra("session")
        setvaluefortask()
    }

    fun chooseFromgallery() {
        image = "gallery"
        checkpermession()
    }

    fun chooseimagrfromcamera() {
        image = "camera"
        checkpermession()
    }
    fun  setvaluefortask(){
        checkMinorFailViewBind!!.tv_taskname!!.setText(elementDetailsRow!!.checkName)
        checkMinorFailViewBind!!.tv_task_name!!.setText(elementDetailsRow!!.checkTypeName)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data)
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data!!)
        }
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
            val myDir = File("$root/wecompli/minorfault")
            myDir.mkdirs()
            /* val generator = Random()
              var n = 100
              n = generator.nextInt(n)*/
            val fname =Imagesellposition.toString()+"fault_image.jpg"
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
                imagearraylist.add(file)
                imagearraylistpath.add(file.path)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

       /* try {
            destination.createNewFile()
            fo = FileOutputStream(destination)
            fo.write(bytes.toByteArray())
            fo.close()
            imagearraylist.add(destination)
            imagearraylistpath.add(destination.path)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }*/

        if (Imagesellposition == 1) {

            checkMinorFailViewBind!!.tv_addimage1!!.setVisibility(View.INVISIBLE)
            checkMinorFailViewBind!!.img1!!.setVisibility(View.INVISIBLE)
        }
        if (Imagesellposition == 2) {
            checkMinorFailViewBind!!.tv_addimage2!!.setVisibility(View.INVISIBLE)
            checkMinorFailViewBind!!.img2!!.setVisibility(View.INVISIBLE)
        }
        if (Imagesellposition == 3) {
            checkMinorFailViewBind!!.tv_addimage3!!.setVisibility(View.INVISIBLE)
            checkMinorFailViewBind!!.img3!!.setVisibility(View.INVISIBLE)
        }
        if (Imagesellposition == 4) {
            checkMinorFailViewBind!!.tv_addimage4!!.setVisibility(View.INVISIBLE)
            checkMinorFailViewBind!!.img4!!.setVisibility(View.INVISIBLE)
        }

      //  val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), destination)
        // MultipartBody.Part is used to send also the actual file name
       // val body = MultipartBody.Part.createFormData("image", destination.name, requestFile)


        imageView!!.setImageBitmap(thumbnail)

    }

    private fun onSelectFromGalleryResult(data: Intent?) {
        var bm: Bitmap? = null
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, data.data)
              /*  val bytes = ByteArrayOutputStream()
                bm!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                val destination = File(Environment.getExternalStorageDirectory(), "fault_image"+ ".jpg")*/
               // val destination = File(Environment.getExternalStorageDirectory(), System.currentTimeMillis().toString() + ".jpg")

                val root = Environment.getExternalStorageDirectory().toString()
                val myDir = File("$root/wecompli/minorfault")
                myDir.mkdirs()
                /* val generator = Random()
                  var n = 100
                  n = generator.nextInt(n)*/
                val fname =Imagesellposition.toString()+"fault_image.jpg"
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
                    imagearraylist.add(file)
                    imagearraylistpath.add(file.path)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        if (Imagesellposition == 1) {

            checkMinorFailViewBind!!.tv_addimage1!!.setVisibility(View.INVISIBLE)
            checkMinorFailViewBind!!.img1!!.setVisibility(View.INVISIBLE)
        }
        if (Imagesellposition == 2) {
            checkMinorFailViewBind!!.tv_addimage2!!.setVisibility(View.INVISIBLE)
            checkMinorFailViewBind!!.img2!!.setVisibility(View.INVISIBLE)
        }
        if (Imagesellposition == 3) {
            checkMinorFailViewBind!!.tv_addimage3!!.setVisibility(View.INVISIBLE)
            checkMinorFailViewBind!!.img3!!.setVisibility(View.INVISIBLE)
        }
        if (Imagesellposition == 4) {
            checkMinorFailViewBind!!.tv_addimage4!!.setVisibility(View.INVISIBLE)
            checkMinorFailViewBind!!.img4!!.setVisibility(View.INVISIBLE)
        }
        imageView!!.setImageBitmap(bm)
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

    public fun callApiforCheckSubmitusingimage() {
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("check_id", elementDetailsRow!!.id)
            paramObject.put("season_id", sessionid)
            paramObject.put("check_type_id", elementDetailsRow!!.checkTypeId)
            if(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.failselection).equals("fail")) {
                paramObject.put("check_type_values_id", elementDetailsRow!!.checkTypeValue!!.get(1).id)
                paramObject.put("process_status","N")
            }
            else {
                paramObject.put("check_type_values_id", elementDetailsRow!!.checkTypeValue!!.get(2).id)
                paramObject.put("process_status","Y")
            }

            paramObject.put("check_process_type", "checks")
            paramObject.put("check_date",checkdate)
            paramObject.put("process_remark",checkMinorFailViewBind!!.et_fault!!.text.toString())

            //for ( i in 0 until imagearraylist.size) {
              //  paramObject.put("process_file[]", imagearraylist.get(i))
           // }

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
                            try {
                                val response_obj = JSONObject(response.body()!!.string())
                                if (response_obj.getBoolean("status")){
                                    val check_process_log_id:String=response_obj.getInt("check_process_log_id").toString()
                                    submitfalutusingmultipartBulider(check_process_log_id);
                                    /* val intent = Intent()
                                     setResult(ApplicationConstant.INTENT_CHECKCOMPONENT, intent)
                                     finish()*/
                                }else{
                                    Toast.makeText(this@CheckMinorfailActivity, response_obj.getString("message"), Toast.LENGTH_LONG).show()
                                }
                            }
                            catch (e:Exception){
                                e.printStackTrace()
                                Toast.makeText(this@CheckMinorfailActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()

                            }
                        }
                    }
                    else
                        Toast.makeText(this@CheckMinorfailActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })

        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }

    fun submitimageusingasyntask(){
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val post = AsyncHttpPost(NetworkUtility.BASE_URL + NetworkUtility.COMPONENET_CHECK_SUBMIT)
            post.addHeader("Authorization", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token))
            post.addHeader("site_id",sideid)
            post.addHeader("Content-Type","application/json")

        val body = MultipartFormDataBody()
        body.addStringPart("check_id", elementDetailsRow!!.id)
        body.addStringPart("season_id", sessionid)
        body.addStringPart("check_type_id", elementDetailsRow!!.checkTypeId)
        body.addStringPart("check_type_values_id", elementDetailsRow!!.checkTypeValue!!.get(0).id)
        body.addStringPart("check_process_type", PreferenceConstent.category_purpose)
        body.addStringPart("check_date", checkdate)
        body.addStringPart("process_remark", checkMinorFailViewBind!!.et_fault!!.text.toString())
        body.addStringPart("process_status", PreferenceConstent.process_status)
        for (i in imagearraylist.indices) {
            body.addFilePart("process_file[]", imagearraylist.get(i))
        }
        post.body = body

        AsyncHttpClient.getDefaultInstance().executeString(post,object :AsyncHttpClient.StringCallback(){
            override fun onCompleted(ex: Exception?, source: AsyncHttpResponse?, result: String?) {
                customProgress.hideProgress()
                if (ex != null) {
                    ex.printStackTrace()
                    return
                }
                try {
                    val response_obj = JSONObject(result)
                    if (response_obj.getBoolean("status")){
                        val intent = Intent()
                        setResult(ApplicationConstant.INTENT_CHECKCOMPONENT, intent)
                        finish()
                    }else{
                        Toast.makeText(this@CheckMinorfailActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                    }
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
            }

        })
    }

    fun submitfalutusingmultipartBulider(checkProcessLogId: String){

        /*paramObject.put("site_id",sideid!!)
        paramObject.put("check_id", elementDetailsRow!!.id)
        paramObject.put("check_process_fault_id",checkProcessLogId)
        paramObject.put("season_id", sessionid)
        paramObject.put("check_type_id", elementDetailsRow!!.checkTypeId)
        paramObject.put("check_type_values_id", elementDetailsRow!!.checkTypeValue!!.get(0).id)
        paramObject.put("check_process_type", PreferenceConstent.category_purpose)
        paramObject.put("fault_description",checkMinorFailViewBind!!.et_fault!!.text.toString())
        paramObject.put("status_id","1")
        builder.addFormDataPart("fault_image", imagearraylist.get(0).name, okhttp3.RequestBody.create(MediaType.parse("image/jpeg"), imagearraylist.get(0)))*/

        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("site_id" ,sideid!!)
        builder.addFormDataPart("check_id", elementDetailsRow!!.id)
        builder.addFormDataPart("check_process_fault_id", checkProcessLogId)
        builder.addFormDataPart("season_id",sessionid)
        builder.addFormDataPart("check_type_id" , elementDetailsRow!!.checkTypeId)
        builder.addFormDataPart("check_type_values_id", elementDetailsRow!!.checkTypeValue!!.get(0).id)
        builder.addFormDataPart("check_process_type", PreferenceConstent.category_purpose)
        builder.addFormDataPart("fault_description", checkMinorFailViewBind!!.et_fault!!.text.toString())
        builder.addFormDataPart("status_id","1")
        builder.addFormDataPart("notify_who",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.SelectedEmail))
         for (i in imagearraylist.indices) {
         builder.addFormDataPart("fault_image[]", imagearraylist.get(i).name, okhttp3.RequestBody.create(MediaType.parse("image/jpeg"), imagearraylist.get(i)))
        }
        //builder.addFormDataPart("fault_image", imagearraylist.get(0).name, okhttp3.RequestBody.create(MediaType.parse("image/jpeg"), imagearraylist.get(0)))

        val requestBody = builder.build()
        var request: Request? = null
        request = Request.Builder()
            .addHeader("Authorization", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token))
            .addHeader("site_id",sideid)
            .addHeader("Content-Type","application/json")
            .url(NetworkUtility.BASE_URL + NetworkUtility.FAULTCREATE)
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
                customProgress.hideProgress()
                try {
                    var resStr :String=response.body()!!.string()
                    var response_obj= JSONObject(resStr)
                    //val response_obj = JSONObject(response.body()!!.string())
                    if (response_obj.getBoolean("status")){
                     //   val check_process_log_id:String=response_obj.getInt("check_process_log_id").toString()
                        //callApiforfaultcreate(check_process_log_id);
                        AppSheardPreference(this@CheckMinorfailActivity).setvalue_in_preference(PreferenceConstent.SelectedEmail,"")
                        val intent = Intent()
                         setResult(ApplicationConstant.INTENT_CHECKCOMPONENT, intent)
                         finish()
                    }else{
                        Toast.makeText(this@CheckMinorfailActivity, response_obj.getString("message"), Toast.LENGTH_LONG).show()

                    }
                }
                catch (e:Exception){
                    e.printStackTrace()
                    Toast.makeText(this@CheckMinorfailActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                customProgress.hideProgress()
            }
        })

    }

    /*fun submitfalutusingmultipartBulider(){
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("check_id" ,elementDetailsRow!!.id)
        builder.addFormDataPart("season_id", sessionid)
        builder.addFormDataPart("check_type_id", elementDetailsRow!!.checkTypeId)
        builder.addFormDataPart("check_type_values_id",elementDetailsRow!!.checkTypeValue!!.get(0).id)
        builder.addFormDataPart("check_process_type" ,PreferenceConstent.category_purpose)
        builder.addFormDataPart("check_date", checkdate)
        builder.addFormDataPart("process_remark", checkMinorFailViewBind!!.et_fault!!.text.toString())
        builder.addFormDataPart("process_status","N")
       // for (i in imagearraylist.indices) {
         //   builder.addFormDataPart("fault_image", imagearraylist.get(i).name, okhttp3.RequestBody.create(MediaType.parse("image/jpeg"), imagearraylist.get(i)))
       // }
        builder.addFormDataPart("fault_image", imagearraylist.get(0).name, okhttp3.RequestBody.create(MediaType.parse("image/jpeg"), imagearraylist.get(0)))

        val requestBody = builder.build()
        var request: Request? = null
        request = Request.Builder()
            .addHeader("Authorization", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token))
            .addHeader("site_id",sideid)
            .addHeader("Content-Type","multipart/form-data")
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
                customProgress.hideProgress()
                try {
                    val response_obj = JSONObject(response.body()!!.string())
                    if (response_obj.getBoolean("status")){
                        val check_process_log_id:String=response_obj.getInt("check_process_log_id").toString()
                      callApiforfaultcreate(check_process_log_id);
                       *//* val intent = Intent()
                        setResult(ApplicationConstant.INTENT_CHECKCOMPONENT, intent)
                        finish()*//*
                    }else{
                        Toast.makeText(this@CheckMinorfailActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                    }
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                customProgress.hideProgress()
            }
        })

    }*/

    private fun callApiforfaultcreate(checkProcessLogId: String) {

        val customProgress1: CustomProgressDialog = CustomProgressDialog().getInstance()
     //   customProgress1.showProgress(this@CheckMinorfailActivity, "Please Wait..", false)
        val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("site_id",sideid!!)
            paramObject.put("check_id", elementDetailsRow!!.id)
            paramObject.put("check_process_fault_id",checkProcessLogId)
            paramObject.put("season_id", sessionid)
            paramObject.put("check_type_id", elementDetailsRow!!.checkTypeId)
            paramObject.put("check_type_values_id", elementDetailsRow!!.checkTypeValue!!.get(0).id)
            paramObject.put("check_process_type", PreferenceConstent.category_purpose)
            paramObject.put("fault_description",checkMinorFailViewBind!!.et_fault!!.text.toString())
            paramObject.put("status_id","1")
         //   builder.addFormDataPart("fault_image", imagearraylist.get(0).name, okhttp3.RequestBody.create(MediaType.parse("image/jpeg"), imagearraylist.get(0)))

            // paramObject.put("process_status","N")

            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callApi= apiInterface.callcreatefaultApi(
                AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token),sideid!!, gsonObject!!)
            callApi.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                  //  customProgress1.hideProgress()
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            val intent = Intent()
                            setResult(ApplicationConstant.INTENT_CHECKCOMPONENT, intent)
                            finish()
                        }
                    }
                    else
                        Toast.makeText(this@CheckMinorfailActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                  //  customProgress1.hideProgress()
                }
            })

        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }

}