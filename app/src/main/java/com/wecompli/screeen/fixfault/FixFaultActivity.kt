package com.wecompli.screeen.fixfault

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.zxing.integration.android.IntentIntegrator
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.R
import com.wecompli.apiresponsemodel.faultdetails.FaultDetailsByScanModel
import com.wecompli.network.ApiInterface
import com.wecompli.network.NetworkUtility
import com.wecompli.network.Retrofit
import com.wecompli.screeen.totalfault.TotalFaultActivity
import com.wecompli.utils.ApplicationConstant
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class FixFaultActivity:AppCompatActivity() {
   public var fixFaultViewBind:FixFaultViewBind?=null
    var fixFaultOnClick:FixFaultOnClick?=null
    var image: String?=null
    var REQUEST_CAMERA = 111
    var SELECT_FILE = 112
    var worktype=""
    var faultrepairimage:File?=null
    var managersign:File?=null
    var engineeirsign:File?=null
    var selectenginnersign:Boolean?=false
    var selectmanagertimage:Boolean?=false
    var selectfaultimage:Boolean?=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view=LayoutInflater.from(this).inflate(R.layout.fix_fault_layout,null)
        setContentView(view)
        fixFaultViewBind= FixFaultViewBind(this,view)
        fixFaultOnClick= FixFaultOnClick(this,fixFaultViewBind!!)
        setvalues()
    }
   
    private fun setvalues() {
        fixFaultViewBind!!.tv_Company!!.setText(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.UserCompany))
        fixFaultViewBind!!.tv_site!!.setText(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.UserSite))
        fixFaultViewBind!!.tv_bcomp_name!!.setText(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.Category_name))
        fixFaultViewBind!!.tv_bcomp_element_name!!.setText(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.CheckName))

        fixFaultViewBind!!.et_part_cost.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (!fixFaultViewBind!!.et_labourcharge.getText().toString().equals(""))
                    fixFaultViewBind!!.tv_total_costval.setText(
                        (Integer.parseInt(fixFaultViewBind!!.et_labourcharge.getText().toString()) + Integer.parseInt(
                            fixFaultViewBind!!.et_part_cost.getText().toString()
                        )).toString()
                    )

            }
        })
        fixFaultViewBind!!.et_labourcharge.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (!fixFaultViewBind!!.et_part_cost.getText().toString().equals(""))
                    fixFaultViewBind!!.tv_total_costval.setText(
                        (Integer.parseInt(fixFaultViewBind!!.et_labourcharge.getText().toString()) + Integer.parseInt(
                            fixFaultViewBind!!.et_part_cost.getText().toString()
                        )).toString()
                    )

            }
        })

    }

    fun showPictureDialog() {
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
            image = "gallery"
            alertDialog.dismiss()
            checkpermession()
            //activity.chooseFromgallery()
        }
        btn_camera.setOnClickListener {
            alertDialog.dismiss()
            image = "camera"
            checkpermession()
            //activity.chooseimagrfromcamera()
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
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data)
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data!!)

            else if (result != null) run {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()

                } else {
                    // val obj = JSONObject(result.getContents())
                    // Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show()
                    //println("Scanned: " + result.getContents())
                    val separated = result.getContents().split("_")
                   // if (AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.faultid).equals(separated.get(separated.size-1)))
                        callApiforfaultDetails(separated.get(separated.size-1))
                  //  else
                     //   Alert.showalert(this,"QR Code doesn't match. Try another")
                }
            }

        }else if (requestCode==155){
            Toast.makeText(this, "from list", Toast.LENGTH_SHORT).show()

        }

    }

    private fun callApiforfaultDetails(contents: String) {
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
           // paramObject.put("id", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.faultid))
            paramObject.put("id", contents)
            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callApi= apiInterface.callfaultdetailsbyscan(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token),AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.site_id), gsonObject!!)
            callApi.enqueue(object : Callback<FaultDetailsByScanModel> {
                override fun onResponse(call: Call<FaultDetailsByScanModel>, response: Response<FaultDetailsByScanModel>) {
                    customProgress.hideProgress()
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            AppSheardPreference(this@FixFaultActivity).setvalue_in_preference(PreferenceConstent.faultid,response.body()!!.row.id)
                            AppSheardPreference(this@FixFaultActivity).setvalue_in_preference(PreferenceConstent.Category_name,response.body()!!.row.category_name)
                            AppSheardPreference(this@FixFaultActivity).setvalue_in_preference(PreferenceConstent.CheckName,response.body()!!.row.check_name)
                            setvalues()

                            // elementDetailsAdapter!!.notifyItemRemoved(listposition)
                            // elementDetailsAdapter!!.notifyDataSetChanged()
                        }
                    }
                    else
                        Toast.makeText(this@FixFaultActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                }
                override fun onFailure(call: Call<FaultDetailsByScanModel>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })

        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    private fun onSelectFromGalleryResult(data: Intent?) {
        var bm: Bitmap? = null
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, data.data)
                val bytes = ByteArrayOutputStream()
                bm!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)

                val destination = File(Environment.getExternalStorageDirectory(), "fault_image"+ ".jpg")
                // val destination = File(Environment.getExternalStorageDirectory(), System.currentTimeMillis().toString() + ".jpg")
                val fo: FileOutputStream
                try {
                    destination.createNewFile()
                    fo = FileOutputStream(destination)
                    fo.write(bytes.toByteArray())
                    fo.close()

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                faultrepairimage=destination
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        selectfaultimage=true
        fixFaultViewBind!!.img_phato!!.setImageBitmap(bm)
    }

    private fun onCaptureImageResult(data: Intent) {
        val thumbnail = data.extras!!.get("data") as Bitmap?
        val bytes = ByteArrayOutputStream()
        thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val destination = File(Environment.getExternalStorageDirectory(), "fault_image"+ ".jpg")
        val fo: FileOutputStream
        try {
            destination.createNewFile()
            fo = FileOutputStream(destination)
            fo.write(bytes.toByteArray())
            fo.close()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        selectfaultimage=true
        faultrepairimage=destination
        fixFaultViewBind!!.img_phato!!.setImageBitmap(thumbnail)

    }

    fun callApiforfaultSubmit(){
       // val c = Calendar.getInstance()
       // val df = SimpleDateFormat("dd/MM/yyyy")
      // var formattedDate = df.format(c.time)
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("checks_process_fault_id" ,AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.faultid))
        builder.addFormDataPart("work_type",worktype )
        builder.addFormDataPart("next_service_date", fixFaultViewBind!!.tv_select_date.text.toString())
        builder.addFormDataPart("remarks",fixFaultViewBind!!.et_servicing_description!!.text.toString())
        builder.addFormDataPart("time_spent" , fixFaultViewBind!!.et_hour.text.toString()+":"+fixFaultViewBind!!.et_min.text.toString())
        builder.addFormDataPart("labour_charge", fixFaultViewBind!!.et_labourcharge.text.toString())
        builder.addFormDataPart("part_cost", fixFaultViewBind!!.et_part_cost.text.toString())
        builder.addFormDataPart("name_of_engineer", fixFaultViewBind!!.et_name_of_enginner.text.toString())
        builder.addFormDataPart("company_name", fixFaultViewBind!!.et_name_of_company.text.toString())
        builder.addFormDataPart("repair_date", fixFaultViewBind!!.tv_date.text.toString())
        builder.addFormDataPart("manager_name",fixFaultViewBind!!.et_managername.text.toString())
        builder.addFormDataPart("notify_who",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.SelectedEmail))
       // for (i in imagearraylist.indices) {
        builder.addFormDataPart("repair_images[]",faultrepairimage!!.name , okhttp3.RequestBody.create(MediaType.parse("image/jpeg"),faultrepairimage ))
      //  }
        //builder.addFormDataPart("fault_image", imagearraylist.get(0).name, okhttp3.RequestBody.create(MediaType.parse("image/jpeg"), imagearraylist.get(0)))
        builder.addFormDataPart("engineer_signature", engineeirsign!!.name, okhttp3.RequestBody.create(MediaType.parse("image/jpeg"),engineeirsign))
        builder.addFormDataPart("manager_signature", managersign!!.name, okhttp3.RequestBody.create(MediaType.parse("image/jpeg"), managersign))

        val requestBody = builder.build()
        var request: Request? = null
        request = Request.Builder()
            .addHeader("Authorization", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token))
            .addHeader("site_id",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.UserSite))
            .addHeader("Content-Type","application/json")
            .url(NetworkUtility.BASE_URL + NetworkUtility.FAULTREPAIR)
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
                          // val check_process_log_id:String=response_obj.getInt("check_process_log_id").toString()
                        AppSheardPreference(this@FixFaultActivity).setvalue_in_preference(PreferenceConstent.SelectedEmail,"")
                        val foultlist=Intent(this@FixFaultActivity,TotalFaultActivity::class.java)
                        foultlist.putExtra("componet",AppSheardPreference(this@FixFaultActivity).getvalue_in_preference(PreferenceConstent.component_totalfault))
                        foultlist.putExtra("date",AppSheardPreference(this@FixFaultActivity).getvalue_in_preference(PreferenceConstent.date_totalfault))
                        foultlist.putExtra("sideid",AppSheardPreference(this@FixFaultActivity).getvalue_in_preference(PreferenceConstent.siteidtotalfault))
                        foultlist.putExtra("companyid",AppSheardPreference(this@FixFaultActivity).getvalue_in_preference(PreferenceConstent.companyidtotalfault))
                        //foultlist.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                       // foultlist.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(foultlist)
                        //callApiforfaultcreate(check_process_log_id);
                       // val intent = Intent()
                        //setResult(ApplicationConstant.INTENT_CHECKCOMPONENT, intent)
                        finish()
                    }else{
                        Toast.makeText(this@FixFaultActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                    }
                }
                catch (e: Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                customProgress.hideProgress()
            }
        })
    }

}