package com.wecompli.screeen.docmanagment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wecompli.R
import com.wecompli.apiresponsemodel.docUploadCertificate.DocCertificateModel
import com.wecompli.screeen.docmanagment.DocManagmentActivity
import com.wecompli.utils.onitemclickinterface.OnItemClickInterface

class ImageCertificateAdapter(
    val docManagmentActivity: DocManagmentActivity,
   var docImagelist: ArrayList<DocCertificateModel>,
    var onItemClickInterface: OnItemClickInterface
): RecyclerView.Adapter<ImageCertificateAdapter.DocumentItemView>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentItemView {
        val view = LayoutInflater.from(docManagmentActivity).inflate(R.layout.task_complete_image_list, null)
        return DocumentItemView(view)
    }

    override fun getItemCount(): Int {
      return docImagelist.size
    }

    override fun onBindViewHolder(holder: DocumentItemView, position: Int) {
        holder.tv_phatoname.setText("Document " + (position + 1).toString())
        holder.tv_phatoname.setOnClickListener(View.OnClickListener {
            onItemClickInterface.OnItemClick(position)
        })
        holder.tv_phatodelete.setOnClickListener(View.OnClickListener { // imagelist.remove(i);
            if (docManagmentActivity.docImagelist!!.size > 0) docManagmentActivity.docImagelist!!.remove(docManagmentActivity.docImagelist.get(position))
            notifyDataSetChanged()
        })
    }
    class DocumentItemView( itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tv_phatoname: TextView
        var tv_phatodelete: ImageView

        init {
            tv_phatoname = itemView.findViewById(R.id.tv_phatoname)
            tv_phatodelete = itemView.findViewById(R.id.tv_phatodelete)
        }
    }

}