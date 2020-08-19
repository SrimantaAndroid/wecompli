package com.wecompli.screeen.checkelementdetails

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.zxing.integration.android.IntentIntegrator
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.R
import com.wecompli.apiresponsemodel.checkelementdetails.CheckElementDetailsResponse
import com.wecompli.apiresponsemodel.checkelementdetails.ElementDetailsRow
import com.wecompli.apiresponsemodel.checkelementdetails.SelectedSiteSessionForCheck
import com.wecompli.network.ApiInterface
import com.wecompli.network.NetworkUtility
import com.wecompli.network.Retrofit
import com.wecompli.screeen.checkelementdetails.adapter.CheckElementDetailsAdapter
import com.wecompli.screeen.checkinputnote.CheckInputNoteActivity
import com.wecompli.screeen.checkminorfail.CheckMinorfailActivity
import com.wecompli.screeen.checktaptosign.CheckTapToSignActivity
import com.wecompli.screeen.checkteaprature.CheckTempuratureActivity
import com.wecompli.utils.ApplicationConstant
import com.wecompli.utils.customalert.Alert
import com.wecompli.utils.customalert.CameraImageShowDialog
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
import java.io.*


class CheckElementDetailsActivity: AppCompatActivity() {
    var checkElementDetatDetailsViewBind:CheckElementDetatDetailsViewBind?=null
    public var checkElementDetailsOnClick:CheckElementDetailsOnClick?=null
    var elementDetailsAdapter:CheckElementDetailsAdapter?=null
     var rowlist= ArrayList<ElementDetailsRow>()
    var selectedSiteSessionForCheck:SelectedSiteSessionForCheck?=null
    var checkdate:String?=null
    var checkcomponent:String?=null
    var sideid:String?=null
    internal var thumbnail: Bitmap?=null
     lateinit var destination: File
     lateinit var byteArray: ByteArray
    var elementdetailsrow: ElementDetailsRow?=null
     var selectedposition:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_check_element_details,null)
        setContentView(view)

        checkElementDetatDetailsViewBind= CheckElementDetatDetailsViewBind(this,view)
        checkElementDetailsOnClick=CheckElementDetailsOnClick(this,checkElementDetatDetailsViewBind!!)
        val intent=intent
        checkcomponent=intent.getStringExtra("componet")
        checkdate=intent.getStringExtra("date")
        sideid=intent.getStringExtra("sideid")
        selectedSiteSessionForCheck=intent!!.getSerializableExtra(ApplicationConstant.INTENT_COMPONENETDETAILS) as SelectedSiteSessionForCheck
        setvalues(selectedSiteSessionForCheck!!)
         setupAdapter()
        callApiforelementdetails(selectedSiteSessionForCheck!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            getcameraoutputfile(data)
        }
        else if(requestCode==ApplicationConstant.INTENT_CHECKCOMPONENT){
          //  callApiforelementdetails(selectedSiteSessionForCheck!!)
            rowlist.removeAt(selectedposition)
            elementDetailsAdapter!!.notifyDataSetChanged()
            if(rowlist.size==0)
                Alert.showalertforallchecksubmit(this@CheckElementDetailsActivity,"All Checks Done.")

            //setupAdapter()
        } else if (result != null) run {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()

            } else {
               // val obj = JSONObject(result.getContents())
               // Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show()
                //println("Scanned: " + result.getContents())
                val separated = result.getContents().split("_")
                if (elementdetailsrow!!.id.equals(separated.get(separated.size-1)))
                callApiforsubmitScan(result.getContents())
                else
                    Alert.showalert(this,"QR Code doesn't match. Try another")
            }
        }
    }

    private fun getcameraoutputfile(data: Intent?) {
        thumbnail = data!!.getExtras()!!.get("data") as Bitmap?
        val stream = ByteArrayOutputStream()
        thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        byteArray = stream.toByteArray()
        val tempUri: Uri = getImageUri(applicationContext!!, thumbnail!!)
         destination = File(getRealPathFromURI(tempUri))
        /*val bytes = ByteArrayOutputStream()
        thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/wecompli/minorfault")
        myDir.mkdirs()
        val fname ="camerafault_image.jpg"
        destination = File(myDir, fname)
        val fo: FileOutputStream
        if (destination.exists())
            destination.delete()
        *//*destination = File(Environment.getExternalStorageDirectory(), "wc_cam_check" + ".jpg")
        val fo: FileOutputStream*//*
        try {
            val out = FileOutputStream(destination)
            thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
           // destination=file
           *//* destination.createNewFile()
            fo = FileOutputStream(destination)
            fo.write(bytes.toByteArray())
            fo.close()*//*

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }*/
       CameraImageShowDialog(this,elementdetailsrow, thumbnail!!).show()
    }
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }
    fun getRealPathFromURI(uri: Uri?): String? {
        var path = ""
        if (contentResolver != null) {
            val cursor: Cursor? = contentResolver.query(uri!!, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val idx: Int =
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }
    private fun callApiforelementdetails(selectedSiteSessionForCheck: SelectedSiteSessionForCheck) {

        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this,"Please Wait..",false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("company_id", selectedSiteSessionForCheck.selectedCompay)
            paramObject.put("site_id", selectedSiteSessionForCheck.selected_site)
            paramObject.put("category_id", selectedSiteSessionForCheck.selected_category)
            paramObject.put("season_id", selectedSiteSessionForCheck.selected_session)
            paramObject.put("check_date", selectedSiteSessionForCheck.checkdate)
            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callApi=apiInterface.callcomponetChecklelementdetails( AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token),selectedSiteSessionForCheck.selected_site, gsonObject!!)
            callApi.enqueue(object : Callback<CheckElementDetailsResponse>{
                override fun onResponse(call: Call<CheckElementDetailsResponse>, response: Response<CheckElementDetailsResponse>) {
                    customProgress.hideProgress()
                    if (response.code()==200){
                        rowlist.clear()
                       // rowlist= response.body()!!.elementrow!!
                        for ( i in  0 until response.body()!!.elementrow!!.size){
                            var elementDetailsRow:ElementDetailsRow=response.body()!!.elementrow!!.get(i)
                            rowlist!!.add(elementDetailsRow)
                        }
                       // setupAdapter()
                       elementDetailsAdapter!!.notifyDataSetChanged()


                    }else if (response.code()==401){

                    }
                }

                override fun onFailure(call: Call<CheckElementDetailsResponse>, t: Throwable) {
                    customProgress.hideProgress()

                }
            })


        }catch (e:Exception){
            e.printStackTrace()
            customProgress.hideProgress()

        }


    }

    private fun setupAdapter() {
        elementDetailsAdapter= CheckElementDetailsAdapter(this,rowlist!!)
        val layoutmanager=LinearLayoutManager(this)
        layoutmanager.orientation=LinearLayoutManager.VERTICAL
        checkElementDetatDetailsViewBind!!.recview_checkdetails.layoutManager=layoutmanager
        checkElementDetatDetailsViewBind!!.recview_checkdetails.adapter=elementDetailsAdapter
    }

    private fun setvalues(selectedSiteSessionForCheck: SelectedSiteSessionForCheck) {
        checkElementDetatDetailsViewBind!!.tv_checksummery.text=selectedSiteSessionForCheck.selectedcategoryname
    }

    public fun callApiforCheckSubmitusingimage(){
       /* val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("check_id", elementdetailsrow!!.id)
            paramObject.put("season_id", selectedSiteSessionForCheck!!.selected_session)
            paramObject.put("check_type_id", elementdetailsrow!!.checkTypeId)
            paramObject.put("check_type_values_id", elementdetailsrow!!.checkTypeValue!!.get(0).id)
            paramObject.put("check_process_type", PreferenceConstent.category_purpose)
            paramObject.put("check_date",selectedSiteSessionForCheck!!.checkdate)
            paramObject.put("process_remark","This is a test check")
            paramObject.put("process_status",PreferenceConstent.process_status)
            paramObject.put("process_file[]",destination)
            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callApi= apiInterface.callcomponetCheckSubmit(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token),selectedSiteSessionForCheck!!.selected_site, gsonObject!!)
            callApi.enqueue(object :Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    customProgress.hideProgress()
                    if (response.isSuccessful){
                        if (response.code()==200){
                            // elementDetailsAdapter!!.notifyItemRemoved(listposition)
                            callApiforelementdetails(selectedSiteSessionForCheck!!)
                            // elementDetailsAdapter!!.notifyDataSetChanged()
                        }
                        // if (response.body()!!.status){

                    }
                    else
                        Toast.makeText(this@CheckElementDetailsActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })

        }catch (e:Exception){
            e.printStackTrace()
        }*/

        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("check_id" ,elementdetailsrow!!.id)
        builder.addFormDataPart("season_id", selectedSiteSessionForCheck!!.selected_session)
        builder.addFormDataPart("check_type_id", elementdetailsrow!!.checkTypeId)
        builder.addFormDataPart("check_type_values_id",elementdetailsrow!!.checkTypeValue!!.get(0).id)
        builder.addFormDataPart("check_process_type" ,PreferenceConstent.category_purpose)
        builder.addFormDataPart("check_date", selectedSiteSessionForCheck!!.checkdate)
        builder.addFormDataPart("process_remark", "")
        builder.addFormDataPart("process_status",PreferenceConstent.process_status)
        builder.addFormDataPart("checks_process_log_entry_date",AppSheardPreference(this!!).getvalue_in_preference(PreferenceConstent.chk_selectiondate))
        builder.addFormDataPart("process_file[]", destination.name, okhttp3.RequestBody.create(MediaType.parse("image/jpeg"), destination))
        val requestBody = builder.build()
        var request: Request? = null
        request = Request.Builder()
            .addHeader("Authorization", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token))
            .addHeader("site_id",sideid)
            .addHeader("Content-Type","application/json")
            .url(NetworkUtility.BASE_URL + NetworkUtility.COMPONENET_CHECK_SUBMIT)
            .post(requestBody)
            .build()
        val client = okhttp3.OkHttpClient.Builder().build()
        val call = client.newCall(request)
        call.enqueue(object :okhttp3.Callback{
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                customProgress.hideProgress()
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        runOnUiThread {
                            callApiforelementdetails(selectedSiteSessionForCheck!!)
                        }

                       /* rowlist.removeAt(selectedposition)
                        elementDetailsAdapter!!.notifyDataSetChanged()
                        if(rowlist.size==0)
                            Alert.showalertforallchecksubmit(this@CheckElementDetailsActivity,"All Checks Done.")
                      */ // setupAdapter()
                    }
                }
                else
                    Toast.makeText(this@CheckElementDetailsActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()

            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                customProgress.hideProgress()
            }
        })
    }
    fun callApiforsubmitScan(contents: String) {
        //Toast.makeText(this, elementDetailsRow.checkTypeValue!!.get(position).value, Toast.LENGTH_LONG).show()
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("check_id", elementdetailsrow!!.id)
            paramObject.put("season_id", selectedSiteSessionForCheck!!.selected_session)
            paramObject.put("check_type_id", elementdetailsrow!!.checkTypeId)
            paramObject.put("check_type_values_id", elementdetailsrow!!.checkTypeValue!!.get(0).id)
            paramObject.put("check_process_type", PreferenceConstent.category_purpose)
            paramObject.put("check_date",selectedSiteSessionForCheck!!.checkdate)
            paramObject.put("process_remark",contents)
            paramObject.put("process_status",PreferenceConstent.process_status)
            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callApi= apiInterface.callcomponetCheckSubmit(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token),selectedSiteSessionForCheck!!.selected_site, gsonObject!!)
            callApi.enqueue(object :Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    customProgress.hideProgress()
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            // elementDetailsAdapter!!.notifyItemRemoved(listposition)
                            runOnUiThread {
                                callApiforelementdetails(selectedSiteSessionForCheck!!)
                            }

                            // elementDetailsAdapter!!.notifyDataSetChanged()
                        }
                    }
                    else
                        Toast.makeText(this@CheckElementDetailsActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })

        }catch (e:Exception){
            e.printStackTrace()
        }

    }
    fun callApiforsubmitcheck(elementDetailsRow: ElementDetailsRow, position: Int, listposition: Int) {
        //Toast.makeText(this, elementDetailsRow.checkTypeValue!!.get(position).value, Toast.LENGTH_LONG).show()
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface = Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        try {
            val paramObject = JSONObject()
            paramObject.put("check_id", elementDetailsRow.id)
            paramObject.put("season_id", selectedSiteSessionForCheck!!.selected_session)
            paramObject.put("check_type_id", elementDetailsRow.checkTypeId)
            paramObject.put("check_type_values_id", elementDetailsRow.checkTypeValue!!.get(position).id)
            paramObject.put("check_process_type", PreferenceConstent.category_purpose)
            paramObject.put("check_date",selectedSiteSessionForCheck!!.checkdate)
            paramObject.put("process_remark","Fault ")
            paramObject.put("process_status",PreferenceConstent.process_status)
            var obj: JSONObject = paramObject
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callApi= apiInterface.callcomponetCheckSubmit(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_token),selectedSiteSessionForCheck!!.selected_site, gsonObject!!)
            callApi.enqueue(object :Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    customProgress.hideProgress()
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            rowlist!!.removeAt(listposition)
                            // setupAdapter()
                             //elementDetailsAdapter!!.notifyItemRemoved(listposition)
                            //callApiforelementdetails(selectedSiteSessionForCheck!!)
                             elementDetailsAdapter!!.notifyDataSetChanged()
                            if(rowlist.size==0)
                                Alert.showalertforallchecksubmit(this@CheckElementDetailsActivity,"All Checks Done.")
                        }
                    }
                    else
                        Toast.makeText(this@CheckElementDetailsActivity, "Try later. Something Wrong.", Toast.LENGTH_LONG).show()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })

        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    fun startscan(elementdetailsrowsc: ElementDetailsRow, position: Int) {
        selectedposition=position
        elementdetailsrow=elementdetailsrowsc
        IntentIntegrator(this).initiateScan()
        val integrator = IntentIntegrator(this)
        integrator.setOrientationLocked(true)
        integrator.initiateScan()

    }

    fun openInputNote(elementdetailsrow: ElementDetailsRow, position: Int) {
        selectedposition=position
        val intent=Intent(this,CheckInputNoteActivity::class.java)
        intent.putExtra("elementdetails",elementdetailsrow as Serializable)
        intent.putExtra("componet",checkcomponent)
        intent.putExtra("date",checkdate)
        intent.putExtra("sideid",sideid)
        intent.putExtra("session",selectedSiteSessionForCheck!!.selected_session)
        intent.putExtra("selectedcategory",selectedSiteSessionForCheck!!.selectedcategoryname)
        this.startActivityForResult(intent,ApplicationConstant.INTENT_CHECKCOMPONENT)
        

    }

    fun openTempture(elementdetailsrow: ElementDetailsRow, position: Int) {
        selectedposition=position
        val intent=Intent(this,CheckTempuratureActivity::class.java)
        intent.putExtra("componet",checkcomponent)
        intent.putExtra("date",checkdate)
        intent.putExtra("sideid",sideid)
        intent.putExtra("session",selectedSiteSessionForCheck!!.selected_session)
        intent.putExtra("elementdetails",elementdetailsrow as Serializable)
        intent.putExtra("selectedcategory",selectedSiteSessionForCheck!!.selectedcategoryname)
        this.startActivityForResult(intent,ApplicationConstant.INTENT_CHECKCOMPONENT)

    }

    fun openTapToSign(elementdetailsrow: ElementDetailsRow, position: Int) {
        selectedposition=position
        val intent=Intent(this,CheckTapToSignActivity::class.java)
        intent.putExtra("elementdetails",elementdetailsrow as Serializable)
        intent.putExtra("componet",checkcomponent)
        intent.putExtra("date",checkdate)
        intent.putExtra("sideid",sideid)
        intent.putExtra("session",selectedSiteSessionForCheck!!.selected_session)
        this.startActivityForResult(intent,ApplicationConstant.INTENT_CHECKCOMPONENT)
    }

    fun openfailandMinorFail(elementdetailsrow: ElementDetailsRow, position: Int, positioninlist: Int){
        selectedposition=positioninlist
        val intent=Intent(this,CheckMinorfailActivity::class.java)
        intent.putExtra("elementdetails",elementdetailsrow as Serializable)
        intent.putExtra("componet",checkcomponent)
        intent.putExtra("date",checkdate)
        intent.putExtra("sideid",sideid)
        intent.putExtra("session",selectedSiteSessionForCheck!!.selected_session)
        this.startActivityForResult(intent,ApplicationConstant.INTENT_CHECKCOMPONENT)
    }

    public fun opeenCamera(elementdetailsrowdeta: ElementDetailsRow, position: Int) {
        selectedposition=position
        elementdetailsrow=elementdetailsrowdeta
        checkpermessionopencamera()
    }

   public fun checkpermessionopencamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        } else {
            opencamera()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 0) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                opencamera()
            }
        }
    }

    private fun opencamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // file = Uri.fromFile(getOutputMediaFile());
        //  intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100)
    }
}