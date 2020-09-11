package com.wecompli.screeen.adhocfault

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
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.R
import com.wecompli.network.ApiInterface
import com.wecompli.network.NetworkUtility
import com.wecompli.network.Retrofit
import com.wecompli.utils.ApplicationConstant
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class AdHocFaultActivity:AppCompatActivity() {
    var adhocFaultViewBind:AdhocFaultViewBind?=null
    var adHocFaultOnClick:AdHocFaultOnClick?=null
    var REQUEST_CAMERA = 111
    var SELECT_FILE = 112
    var position:Int = 0
    var image: String?=null
    var imageView: ImageView?=null
    internal var Imagesellposition: Int = 0
    internal var imagearraylist = ArrayList<File>()
    internal var imagearraylistpath = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = LayoutInflater.from(this).inflate(R.layout.activity_adhoc_fault,null)
        adhocFaultViewBind= AdhocFaultViewBind(this,view)
        adHocFaultOnClick=AdHocFaultOnClick(this,adhocFaultViewBind!!)
        setContentView(view)


    }
    fun chooseFromgallery() {
        image = "gallery"
        checkpermession()
    }

    fun chooseimagrfromcamera() {
        image = "camera"
        checkpermession()
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
            val myDir = File("$root/wecompli/adhocfault")
            myDir.mkdirs()
            /* val generator = Random()
              var n = 100
              n = generator.nextInt(n)*/
            val fname =Imagesellposition.toString()+"adhocfault_image.jpg"
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

            adhocFaultViewBind!!.tv_add_hoc_image1!!.setVisibility(View.INVISIBLE)
            adhocFaultViewBind!!.img_img1!!.setVisibility(View.INVISIBLE)
        }
        if (Imagesellposition == 2) {
            adhocFaultViewBind!!.tv_add_hocimage2!!.setVisibility(View.INVISIBLE)
            adhocFaultViewBind!!.img_img2!!.setVisibility(View.INVISIBLE)
        }
        if (Imagesellposition == 3) {
            adhocFaultViewBind!!.tv_add_hocimage3!!.setVisibility(View.INVISIBLE)
            adhocFaultViewBind!!.img_img3!!.setVisibility(View.INVISIBLE)
        }
        if (Imagesellposition == 4) {
            adhocFaultViewBind!!.tv_add_hoc_image4!!.setVisibility(View.INVISIBLE)
            adhocFaultViewBind!!.img_img4!!.setVisibility(View.INVISIBLE)
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
                val myDir = File("$root/wecompli/adhocfault")
                myDir.mkdirs()
                /* val generator = Random()
                  var n = 100
                  n = generator.nextInt(n)*/
                val fname =Imagesellposition.toString()+"adhoc_image.jpg"
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

            adhocFaultViewBind!!.tv_add_hoc_image1!!.setVisibility(View.INVISIBLE)
            adhocFaultViewBind!!.img_img1!!.setVisibility(View.INVISIBLE)
        }
        if (Imagesellposition == 2) {
            adhocFaultViewBind!!.tv_add_hocimage2!!.setVisibility(View.INVISIBLE)
            adhocFaultViewBind!!.img_img2!!.setVisibility(View.INVISIBLE)
        }
        if (Imagesellposition == 3) {
            adhocFaultViewBind!!.tv_add_hocimage3!!.setVisibility(View.INVISIBLE)
            adhocFaultViewBind!!.img_img3!!.setVisibility(View.INVISIBLE)
        }
        if (Imagesellposition == 4) {
            adhocFaultViewBind!!.tv_add_hoc_image4!!.setVisibility(View.INVISIBLE)
            adhocFaultViewBind!!.img_img4!!.setVisibility(View.INVISIBLE)
        }
        imageView!!.setImageBitmap(bm)
    }
    public fun callApiforAdhocSubmitusingimage() {
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            builder.addFormDataPart("site_id" ,AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.site_id)!!)
            builder.addFormDataPart("check_process_type", PreferenceConstent.category_purpose)
            builder.addFormDataPart("fault_description", adhocFaultViewBind!!.et_adhocfault_note!!.text.toString())
            builder.addFormDataPart("status_id","1")
            builder.addFormDataPart("fault_entry_date",AppSheardPreference(this!!).getvalue_in_preference(PreferenceConstent.chk_selectiondate))
            builder.addFormDataPart("notify_who",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.SelectedEmail))

            for (i in imagearraylist.indices) {
                builder.addFormDataPart("fault_image[]", imagearraylist.get(i).name, okhttp3.RequestBody.create(
                    MediaType.parse("image/jpeg"), imagearraylist.get(i)))
            }
            //builder.addFormDataPart("fault_image", imagearraylist.get(0).name, okhttp3.RequestBody.create(MediaType.parse("image/jpeg"), imagearraylist.get(0)))

            val requestBody = builder.build()
            var request: Request? = null
            request = Request.Builder()
                .addHeader("Authorization", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token))
                .addHeader("site_id",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.site_id))
                .addHeader("Content-Type","application/json")
                .url(NetworkUtility.BASE_URL + NetworkUtility.ADHOPFAULT)
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
                        if(response.isSuccessful) {
                            var resStr: String = response.body()!!.string()
                            var response_obj = JSONObject(resStr)
                            //val response_obj = JSONObject(response.body()!!.string())
                            if (response_obj.getBoolean("status")) {
                                // val check_process_log_id:String=response_obj.getInt("check_process_log_id").toString()
                                //callApiforfaultcreate(check_process_log_id);
                                runOnUiThread {
                                    Alert.showalert(this@AdHocFaultActivity,response_obj.getString("message"))
                                    clearview()
                                }
                                AppSheardPreference(this@AdHocFaultActivity).setvalue_in_preference(PreferenceConstent.SelectedEmail, "")

                            } else {
                                Toast.makeText(
                                    this@AdHocFaultActivity,
                                    response_obj.getString("message"),
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        }
                    }
                    catch (e: java.lang.Exception){
                        e.printStackTrace()
                        Toast.makeText(this@AdHocFaultActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()

                    }
                }

                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    customProgress.hideProgress()
                }
            })
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun clearview() {
        adhocFaultViewBind!!.adhoc_img_1!!.setImageResource(0)
        adhocFaultViewBind!!.add_hoc_img_2!!.setImageResource(0)
        adhocFaultViewBind!!.adhoc_img_3!!.setImageResource(0)
        adhocFaultViewBind!!.adhoc_img_4!!.setImageResource(0)
        adhocFaultViewBind!!.et_adhocfault_note!!.setText("")
        adhocFaultViewBind!!.tv_add_hoc_image1!!.setVisibility(View.VISIBLE)
        adhocFaultViewBind!!.img_img1!!.setVisibility(View.VISIBLE)
        adhocFaultViewBind!!.tv_add_hocimage2!!.setVisibility(View.VISIBLE)
        adhocFaultViewBind!!.img_img2!!.setVisibility(View.VISIBLE)
        adhocFaultViewBind!!.tv_add_hocimage3!!.setVisibility(View.VISIBLE)
        adhocFaultViewBind!!.img_img3!!.setVisibility(View.VISIBLE)
        adhocFaultViewBind!!.tv_add_hoc_image4!!.setVisibility(View.VISIBLE)
        adhocFaultViewBind!!.img_img4!!.setVisibility(View.VISIBLE)
    }
}