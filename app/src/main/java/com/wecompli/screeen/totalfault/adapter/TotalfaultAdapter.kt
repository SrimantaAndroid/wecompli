package com.wecompli.screeen.totalfault.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R
import com.wecompli.screeen.totalfault.TotalFaultActivity
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.widget.LinearLayout
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.wecompli.apiresponsemodel.faultapi.CheckRow
import com.wecompli.utils.customviews.CustomTypefaceSpan
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface




class TotalfaultAdapter(
    val totalFaultActivity: TotalFaultActivity,
    val falultrow: List<CheckRow>,
    val onItemClickInterface: OnItemClickInterface
) : RecyclerView.Adapter<TotalfaultAdapter.ViewHolder>() {
    var  deviceResolution:DeviceResolution?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View=LayoutInflater.from(totalFaultActivity).inflate(R.layout.total_fault_item_layout,parent,false)
        deviceResolution= DeviceResolution(totalFaultActivity)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return falultrow.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_faultname.typeface=deviceResolution!!.getgothmbold(totalFaultActivity)
        holder.tv_dateval.typeface=deviceResolution!!.getgothmlight(totalFaultActivity)
        holder.tvdate.typeface=deviceResolution!!.getgothmbold(totalFaultActivity)
        if(falultrow.get(position).status.equals("ACTIVE")){
            holder.imgfaulttypeimagr.setBackgroundResource(R.drawable.circle_shape_green)
        }else
            holder.imgfaulttypeimagr.setBackgroundResource(R.drawable.circle_shape_red)

        val front1:Typeface=deviceResolution!!.getgothmbold(totalFaultActivity)
        val front2:Typeface=deviceResolution!!.getgothmlight(totalFaultActivity)

        holder.tv_faultname.text=falultrow.get(position).categoryName

        val firstWord = totalFaultActivity.resources.getString(R.string.Details)
        val secondWord = falultrow.get(position).checkNote
        val SS = SpannableStringBuilder(firstWord+": "+ secondWord)
     //   SS.setSpan(CustomTypefaceSpan("", front1), 0, firstWord.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
     //   SS.setSpan(CustomTypefaceSpan("", front2), firstWord.length+1, secondWord!!.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        holder.tvfaultdetails.setText(SS)

        val remark = totalFaultActivity.resources.getString(R.string.remark)
        val fault_description = falultrow.get(position).faultDescription
        val remarks = SpannableStringBuilder(remark+": "+ fault_description)
       // remarks.setSpan(CustomTypefaceSpan("", front1), 0, remark.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
//        remarks.setSpan(CustomTypefaceSpan("", front2), remark.length+1, fault_description!!.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        holder.tv_remarks.setText(remarks)

        val status = totalFaultActivity.resources.getString(R.string.status)
        if (falultrow.get(position).timeline.isNullOrEmpty()==false) {
            val statusval = falultrow.get(position).timeline!!.get(0).repairMessage

            val SS1 = SpannableStringBuilder(status + ": " + statusval)
         //   SS1.setSpan(CustomTypefaceSpan("", front1), 0, status.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
          //  SS1.setSpan(CustomTypefaceSpan("", front2), status.length + 1, statusval!!.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            holder.tv_status.setText(SS1)
        }else{
            val statusval = "Parts Ordered"

            val SS1 = SpannableStringBuilder(status + ": " + statusval)
           // SS1.setSpan(CustomTypefaceSpan("", front1), 0, status.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
           // SS1.setSpan(CustomTypefaceSpan("", front2), status.length + 1, statusval!!.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            holder.tv_status.setText(SS1)
        }

        val str:String = falultrow.get(position).createdAt!!
        val splited = str.split(" ")
        holder.tv_dateval.text=splited[0]

        /*  val styledString = SpannableString("myLogin logout")
          styledString.setSpan(StyleSpan(deviceResolution!!.getgothmbold(totalFaultActivity)), 0, 7, 0)
          styledString.setSpan(StyleSpan(Typeface.ITALIC), 8, 14, 0)*/

        if(falultrow.get(position).faultFiles!!.size>0){
            for (i in 0 until falultrow.get(position).faultFiles!!.size){
                if(i==0){

                    var requestOptions = RequestOptions()
                    requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(5))
                    Glide.with(totalFaultActivity).load(falultrow.get(position).faultFiles!![0]).apply(requestOptions).into(holder.img_fault1)
                  /*  var chefBitmap: Bitmap =Glide.with(totalFaultActivity)
                        .asBitmap()
                        .load(falultrow.get(position).faultFiles!![0])
                        .submit()
                        .get();
                    totalFaultActivity.runOnUiThread(Runnable {
                        val RBD = RoundedBitmapDrawableFactory.create(totalFaultActivity.getResources(), chefBitmap)
                        RBD.setCornerRadius(totalFaultActivity.resources.getDimension(R.dimen._7sdp))
                        RBD.setAntiAlias(true)
                        holder.img_fault1.setImageDrawable(RBD)
                    })*/

                }
                else if(i==1){
                    var requestOptions = RequestOptions()
                    requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(4))
                    Glide.with(totalFaultActivity).load(falultrow.get(position).faultFiles!![1]).apply(requestOptions).into(holder.img_fault2)

                    /* var chefBitmap: Bitmap =Glide.with(totalFaultActivity)
                         .asBitmap()
                         .load(falultrow.get(position).faultFiles!![1])
                         .submit()
                         .get();
                     totalFaultActivity.runOnUiThread(Runnable {
                         val RBD = RoundedBitmapDrawableFactory.create(totalFaultActivity.getResources(), chefBitmap)
                         RBD.setCornerRadius(totalFaultActivity.resources.getDimension(R.dimen._7sdp))
                         RBD.setAntiAlias(true)
                         holder.img_fault2.setImageDrawable(RBD)
                     })*/
                }

                else if(i==2){
                    var requestOptions = RequestOptions()
                    requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(4))
                    Glide.with(totalFaultActivity).load(falultrow.get(position).faultFiles!![2]).apply(requestOptions).into(holder.img_fault3)

                    /* var chefBitmap: Bitmap =Glide.with(totalFaultActivity)
                         .asBitmap()
                         .load(falultrow.get(position).faultFiles!![2])
                         .submit()
                         .get();
                     totalFaultActivity.runOnUiThread(Runnable {
                         val RBD = RoundedBitmapDrawableFactory.create(totalFaultActivity.getResources(), chefBitmap)
                         RBD.setCornerRadius(totalFaultActivity.resources.getDimension(R.dimen._7sdp))
                         RBD.setAntiAlias(true)
                         holder.img_fault3.setImageDrawable(RBD)
                     })*/
                }

                else if(i==3){
                    var requestOptions = RequestOptions()
                    requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(4))
                    Glide.with(totalFaultActivity).load(falultrow.get(position).faultFiles!![3]).apply(requestOptions).into(holder.img_fault4)

                    /*var chefBitmap: Bitmap =Glide.with(totalFaultActivity)
                        .asBitmap()
                        .load(falultrow.get(position).faultFiles!![3])
                        .submit()
                        .get();
                    totalFaultActivity.runOnUiThread(Runnable {
                        val RBD = RoundedBitmapDrawableFactory.create(totalFaultActivity.getResources(), chefBitmap)
                        RBD.setCornerRadius(totalFaultActivity.resources.getDimension(R.dimen._7sdp))
                        RBD.setAntiAlias(true)
                        holder.img_fault4.setImageDrawable(RBD)
                    })*/
                }


            }
        }

/*
        val ImageBit = BitmapFactory.decodeResource(totalFaultActivity.getResources(), R.drawable.intro1)
        holder.img_fault1.setImageBitmap(ImageBit)
        holder.img_fault2.setImageBitmap(ImageBit)
        val RBD = RoundedBitmapDrawableFactory.create(totalFaultActivity.getResources(), ImageBit)
        RBD.setCornerRadius(totalFaultActivity.resources.getDimension(R.dimen._7sdp))
        RBD.setAntiAlias(true)
        holder.img_fault1.setImageDrawable(RBD)
        holder.img_fault2.setImageDrawable(RBD)

        val ImageBit1 = BitmapFactory.decodeResource(totalFaultActivity.getResources(), R.drawable.intro4)
        holder.img_fault3.setImageBitmap(ImageBit1)
        holder.img_fault4.setImageBitmap(ImageBit1)
        val RBD1 = RoundedBitmapDrawableFactory.create(totalFaultActivity.getResources(), ImageBit)
        RBD1.setCornerRadius(totalFaultActivity.resources.getDimension(R.dimen._7sdp))
        RBD1.setAntiAlias(true)
        holder.img_fault3.setImageDrawable(RBD1)
        holder.img_fault4.setImageDrawable(RBD1)
*/

        holder.llmainfaultitem.setOnClickListener {
            onItemClickInterface.OnItemClick(position)
        }

    }

    class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var tv_faultname=itemView.findViewById<TextView>(R.id.tv_faultname)
    var imgfaulttypeimagr=itemView.findViewById<ImageView>(R.id.imgfaulttypeimagr)
        var tvfaultdetails=itemView.findViewById<TextView>(R.id.tvfaultdetails)
        var tv_remarks=itemView.findViewById<TextView>(R.id.tv_remarks)
        var tv_status=itemView.findViewById<TextView>(R.id.tv_status)
        var tvdate=itemView.findViewById<TextView>(R.id.tvdate)
        var tv_dateval=itemView.findViewById<TextView>(R.id.tv_dateval)
        var img_fault1=itemView.findViewById<ImageView>(R.id.img_fault1)
        var img_fault2=itemView.findViewById<ImageView>(R.id.img_fault2)
        var img_fault3=itemView.findViewById<ImageView>(R.id.img_fault3)
        var img_fault4=itemView.findViewById<ImageView>(R.id.img_fault4)
        var llmainfaultitem=itemView.findViewById<LinearLayout>(R.id.llmainfaultitem)

    }
}