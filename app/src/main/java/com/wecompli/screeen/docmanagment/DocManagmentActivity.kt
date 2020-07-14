package com.wecompli.screeen.docmanagment

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.R
import com.wecompli.apiresponsemodel.docUploadCertificate.DocCertificateModel
import com.wecompli.network.NetworkUtility
import com.wecompli.screeen.docmanagment.adapter.ImageCertificateAdapter
import com.wecompli.utils.ApplicationConstant
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import com.wecompli.utils.taskcompletionzoomimage.TaskCompleteZoomImageDialog
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class DocManagmentActivity:AppCompatActivity() {
    var docManagmentViewBind:DocManagmentViewBind?=null
    var docmangonclick:DocManagntOnClick?=null
    var image: String?=null
    var REQUEST_CAMERA = 111
    var SELECT_FILE = 112
    var sehedulename: String? = ""
    var scheduleid:kotlin.String? = ""
    var emailis:kotlin.String? = ""
    var imageCertificateAdapter:ImageCertificateAdapter?=null
    var docImagelist= ArrayList<DocCertificateModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_doc_managment,null)
        setContentView(view)
        docManagmentViewBind= DocManagmentViewBind(this,view)
        docmangonclick= DocManagntOnClick(this,docManagmentViewBind!!)
        setvalue()
        setAdapterforimagecertificate()
    }

    private fun setAdapterforimagecertificate() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        docManagmentViewBind!!.rec_imaglist!!.setLayoutManager(layoutManager)
        imageCertificateAdapter= ImageCertificateAdapter(this,docImagelist!!,object :OnItemClickInterface{
            override fun OnItemClick(position: Int) {
                TaskCompleteZoomImageDialog(this@DocManagmentActivity,docImagelist.get(position).imagepath,docImagelist.get(position).imagebitmap).show();
            }
        })
        docManagmentViewBind!!.rec_imaglist!!.adapter=imageCertificateAdapter

    }

    private fun setvalue() {
        docManagmentViewBind!!.tv_Select_company!!.setText(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.UserCompany))
        docManagmentViewBind!!.tv_select_site!!.setText(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.UserSite))
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
         if (requestCode == 1) {
            if (data != null) {
                sehedulename = data.getStringExtra("result")
                scheduleid = data.getStringExtra("ids")
                if (!sehedulename.equals(""))
                   docManagmentViewBind!!.tv_select_week!!.setText(sehedulename!!.substring(0, sehedulename!!.length - 1))
            }
        }
       else  if (requestCode == 2) {
            if (data != null) {
                emailis = data.getStringExtra("emaillist")
                docManagmentViewBind!!.tv_notify_who!!.setText(emailis)
                //val emailname = data.getStringExtra("emailname")
                //documentViewBind.tv_notify_who.setText(emailname.substring(0, emailname.length - 1))
            }
        }
        else if (resultCode == Activity.RESULT_OK) {
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
            val myDir = File("$root/wecompli/document")
            myDir.mkdirs()
            /* val generator = Random()
              var n = 100
              n = generator.nextInt(n)*/
            val fname = "doc_image.jpg"
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
                if (docImagelist.size<4)
                    addimagelistview(file.absolutePath,thumbnail,file)
                else
                    Alert.showalert(this,"You can not select more then 4 images")

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
                val myDir = File("$root/wecompli/document")
                myDir.mkdirs()
                /* val generator = Random()
                  var n = 100
                  n = generator.nextInt(n)*/
                val fname = "doc_image_"+docImagelist.size+".jpg"
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

                    if (docImagelist.size<4)
                     addimagelistview(file.absolutePath,bm,file)
                    else
                        Alert.showalert(this,"You can not select more then 4 images")

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

    private fun addimagelistview(absolutePath: String, bm: Bitmap?, file: File) {
       var docCertificateModel= DocCertificateModel(absolutePath,bm!!,file)
        docImagelist.add(docCertificateModel)
        imageCertificateAdapter!!.notifyDataSetChanged()

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

    public fun callApifordocSubmit(){
        if (!docManagmentViewBind!!.tv_Select_company!!.getText().toString().equals("")) {
            if (!docManagmentViewBind!!.tv_select_site!!.getText().toString().equals("")) {
                if (!docManagmentViewBind!!.et_document_name!!.getText().toString().equals("")) {
                    if (!docManagmentViewBind!!.tv_start_date!!.getText().toString().equals("")) {
                        if (!docManagmentViewBind!!.tv_end_date!!.getText().toString().equals("")) {
                            if (!docManagmentViewBind!!.tv_select_week!!.getText().toString().equals("")) {
                                if (docImagelist.size>0)
                                     Apicall()
                                // if(!documentViewBind.tv_notify_who.getText().toString().equals("")){
                                //}
                                 else
                                   Alert.showalert(this,"Please Choose Image");
                            } else Alert.showalert(this, this.getResources().getString(R.string.notify_me_expairy))
                        } else Alert.showalert(this, this.getResources().getString(R.string.enter_end_date)
                        )
                    } else Alert.showalert(this, this.getResources().getString(R.string.enter_start_date))
                } else Alert.showalert(this, this.getResources().getString(R.string.enter_doc_name))
            } else Alert.showalert(this, this.getResources().getString(R.string.select_site_))
        } else Alert.showalert(this, this.getResources().getString(R.string.select_company))
    }

    private fun Apicall() {
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("company_id",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.Selectedcompany_id))
        builder.addFormDataPart("site_id" ,AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.site_id))
        builder.addFormDataPart("document_name", docManagmentViewBind!!.et_document_name!!.text.toString())
        builder.addFormDataPart("affected_date",docManagmentViewBind!!.tv_start_date!!.text.toString())
        builder.addFormDataPart("expire_date" , docManagmentViewBind!!.tv_end_date!!.text.toString())
        builder.addFormDataPart("notify_about_expiry", scheduleid)
        builder.addFormDataPart("status_id","1")
        builder.addFormDataPart("notify_who",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.SelectedEmail))
        for (i in docImagelist.indices) {
            builder.addFormDataPart("document_file[]", "doc_image_"+i.toString()+".jpg", okhttp3.RequestBody.create(MediaType.parse("image/jpeg"), docImagelist.get(i).file))
        }
        //builder.addFormDataPart("fault_image", imagearraylist.get(0).name, okhttp3.RequestBody.create(MediaType.parse("image/jpeg"), imagearraylist.get(0)))

        val requestBody = builder.build()
        var request: Request? = null
        request = Request.Builder()
            .addHeader("Authorization", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token))
            .addHeader("site_id",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.site_id))
            .addHeader("Content-Type","application/json")
            .url(NetworkUtility.BASE_URL + NetworkUtility.DOCUMENTSUBMIT)
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
               // System.out.println("respppdoc"+response.body()!!.string())
                try {
                    var response_obj :JSONObject= JSONObject(response.body()!!.string())
                    val message=response_obj.getString("message")
                    if (response_obj.getBoolean("status")){
                        //{"status":true,"message":"Document added"}
                        //   val check_process_log_id:String=response_obj.getInt("check_process_log_id").toString()
                        //callApiforfaultcreate(check_process_log_id);
                        AppSheardPreference(this@DocManagmentActivity).setvalue_in_preference(PreferenceConstent.SelectedEmail,"")
                       // val intent = Intent()
                       // setResult(ApplicationConstant.INTENT_CHECKCOMPONENT, intent)
                        runOnUiThread {
                            docmangonclick!!.clearallfeild()
                            Alert.showalert(this@DocManagmentActivity,message)
                        }
                    }else{
                        //Toast.makeText(this@DocManagmentActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                        Toast.makeText(this@DocManagmentActivity, message, Toast.LENGTH_LONG).show()
                    }
                }
                catch (e: Exception){
                    e.printStackTrace()
                    Toast.makeText(this@DocManagmentActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                customProgress.hideProgress()
            }
        })
    }
}