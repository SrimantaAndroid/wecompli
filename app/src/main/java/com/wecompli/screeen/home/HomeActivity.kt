package com.wecompli.screeen.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import com.wecompli.R
import com.wecompli.apiresponsemodel.home.LoginUserDetailsModel
import com.wecompli.screeen.fragment.dashboard.DashBordFragment
import com.wecompli.screeen.sidemenu.SideMenuAdapter
import com.wecompli.screeen.startcheck.StartCheckFragment
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import org.jsoup.Jsoup
import java.io.IOException

class HomeActivity:AppCompatActivity(){
    var homeViewBind:HomeViewBind?=null
    var homeOnClick:HomeOnClick?=null
     public  var userData:LoginUserDetailsModel?=null
    internal var currentVersion: String?=""
    internal var latestVersion:String? = null
    var sideMenuAdapter:SideMenuAdapter?=null
    internal var dialog: Dialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_home,null)
        homeViewBind= HomeViewBind(this,view)
        homeOnClick= HomeOnClick(this,homeViewBind!!)
        setContentView(view)
        getuserdataafterlogin()
        setvalueofuser()
       // showsidemenulist()
        getCurrentVersion()
        showStartCheckFragment()

    }
    private fun getCurrentVersion() {
        val pm = this.packageManager
        var pInfo: PackageInfo? = null

        try {
            pInfo = pm.getPackageInfo(this.packageName, 0)
        } catch (e1: PackageManager.NameNotFoundException) {
            // TODO Auto-generated catch block
            e1.printStackTrace()
        }

        currentVersion = pInfo!!.versionName
        GetVersionCode().execute()

    }

    internal inner class GetVersionCode : AsyncTask<Void, String, String>() {

        override fun doInBackground(vararg voids: Void): String? {

            var newVersion: String? = null

            try {
                val document =
                    Jsoup.connect("https://play.google.com/store/apps/details?id=" + this@HomeActivity.packageName + "&hl=en")
                        //  Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "com.app.astrolab"  + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                if (document != null) {
                    val element = document!!.getElementsContainingOwnText("Current Version")
                    for (ele in element) {
                        if (ele.siblingElements() != null) {
                            val sibElemets = ele.siblingElements()
                            for (sibElemet in sibElemets) {
                                newVersion = sibElemet.text()
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return newVersion

        }


        override fun onPostExecute(onlineVersion: String?) {

            super.onPostExecute(onlineVersion)

            if (onlineVersion != null && !onlineVersion.isEmpty()) {

                if (java.lang.Float.valueOf(currentVersion!!) < java.lang.Float.valueOf(onlineVersion)) {
                    //show anything
                    showUpdateDialog()
                }

            }

            // Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

        }
    }

    private fun showUpdateDialog() {
      val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.new_version_avalible))
     builder.setPositiveButton(resources.getText(R.string.update)) { dialog, which ->
    /* startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                    ("market://details?id=com.wecompli")));*/
    val appPackageName = packageName // getPackageName() from Context or Activity object
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
    } catch (anfe:android.content.ActivityNotFoundException) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
    }

    dialog.dismiss()
     }

        builder.setNegativeButton(resources.getString(R.string.cancel), object: DialogInterface.OnClickListener {
    override fun onClick(dialog: DialogInterface, which:Int) {
     dialog.dismiss()
     }
    })

      builder.setCancelable(true)
      dialog = builder.show()
      dialog!!.setCancelable(false)
    }

    private fun showStartCheckFragment() {
        if (homeViewBind!!.drawer_layout!!.isDrawerOpen(GravityCompat.START))
            homeViewBind!!.drawer_layout!!.closeDrawer(Gravity.LEFT)
        homeViewBind!!.tv_header_text!!.setText(resources.getText(R.string.startcheck))
        val chechkFragment = StartCheckFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.content_frame, chechkFragment)
        ft.commit()
    }

    private fun showdashboardFragment() {
        if (homeViewBind!!.drawer_layout!!.isDrawerOpen(GravityCompat.START))
            homeViewBind!!.drawer_layout!!.closeDrawer(Gravity.LEFT)
        homeViewBind!!.tv_header_text!!.setText(resources.getText(R.string.dashboard))
        val chechkFragment = DashBordFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.content_frame, chechkFragment)
        ft.commit()
    }

    private fun showsidemenulist() {
        val layoutManager = LinearLayoutManager(this)
        homeViewBind!!.rec_sidemenu!!.layoutManager=layoutManager
        sideMenuAdapter= SideMenuAdapter(this,userData!!.menu, object : OnItemClickInterface {
            override fun OnItemClick(position: Int) {
               // if(position==0)
                  //  showdashboardFragment()
             //   else
                Toast.makeText(this@HomeActivity,userData!!.menu.get(position).menuTitle+" "+"Under Development",Toast.LENGTH_LONG).show()

            }
        })
        homeViewBind!!.rec_sidemenu!!.adapter=sideMenuAdapter
    }

    private fun setvalueofuser() {
        homeViewBind!!.tv_user_name!!.text=userData!!.full_name
        homeViewBind!!.tv_user_type!!.text=userData!!.user_type
        homeViewBind!!.tv_user_email!!.text=userData!!.email
       // AppSheardPreference(this).setvalue_in_preference(PreferenceConstent.site_id,userData!!.site_id)
        loadprofileimage()
    }

    private fun getuserdataafterlogin() {
        val gson = Gson()
        val json = AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loginuser_data)
        userData = gson.fromJson<LoginUserDetailsModel>(json, LoginUserDetailsModel::class.java!!)
    }

    private fun loadprofileimage() {

        val options = DisplayImageOptions.Builder()
            /* .showImageForEmptyUri(R.drawable.ic_empty)
                 .showImageOnFail(R.drawable.ic_error)*/
            .resetViewBeforeLoading(true)
            .cacheOnDisk(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .considerExifParams(true)
            .displayer(FadeInBitmapDisplayer(300))
            .build()

        var imageLoader: ImageLoader
        imageLoader = ImageLoader.getInstance() // Get singleton instance
        imageLoader.init(ImageLoaderConfiguration.createDefault(this))
        imageLoader.loadImage(userData!!.user_profile_picture_path, options,
            object : SimpleImageLoadingListener() {
                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                    homeViewBind!!.cirecularimageview!!.setImageBitmap(loadedImage)
                }
            })
    }
}
