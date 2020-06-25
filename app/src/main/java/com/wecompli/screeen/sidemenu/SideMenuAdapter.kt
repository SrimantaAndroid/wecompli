package com.wecompli.screeen.sidemenu

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.apiresponsemodel.login.Menu
import com.wecompli.screeen.home.HomeActivity
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface

class SideMenuAdapter(
    val activity: HomeActivity,
    val menu: List<Menu>,
    val onItemClickInterface: OnItemClickInterface?
) : RecyclerView.Adapter<SideMenuAdapter.MenuViewHoldwr>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHoldwr {
        val view=LayoutInflater.from(activity).inflate(R.layout.side_menu_item,parent,false)
        return MenuViewHoldwr(view)
    }

    override fun getItemCount(): Int {
        return menu.size
    }

    override fun onBindViewHolder(holder: MenuViewHoldwr, position: Int) {
        val deviceResolution=DeviceResolution(activity)
        holder.itemname.text=menu.get(position).menuTitle
        holder.itemname.typeface=deviceResolution.getbebas(activity)
        var imageLoader: ImageLoader
        imageLoader = ImageLoader.getInstance() // Get singleton instance
        imageLoader.init(ImageLoaderConfiguration.createDefault(activity))
        imageLoader.loadImage(menu.get(position).menuIcon.toString(),
            object : SimpleImageLoadingListener() {
                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                    holder!!.img_menuitem!!.setImageBitmap(loadedImage)
                }
            })

        holder.ll_menuitem.setOnClickListener {
            onItemClickInterface!!.OnItemClick(position)
        }
    }


    class MenuViewHoldwr(itemView: View) : RecyclerView.ViewHolder(itemView){

        var itemname=itemView.findViewById<TextView>(R.id.itemname)
        var ll_menuitem=itemView.findViewById<LinearLayout>(R.id.ll_menuitem)
        val img_menuitem=itemView.findViewById<ImageView>(R.id.img_menuitem)
    }
}