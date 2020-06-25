package com.wecompli.screeen.checkelementdetails

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.View
import com.wecompli.R

class CheckElementDetailsOnClick(
    val checkElementDetailsActivity: CheckElementDetailsActivity,
    val checkElementDetatDetailsViewBind: CheckElementDetatDetailsViewBind) : View.OnClickListener {
    init {
        checkElementDetatDetailsViewBind.img_back_header.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.img_back_header->{
              //  checkElementDetailsActivity.finish()
                val intent=Intent()
                intent.putExtra("componet",checkElementDetailsActivity.checkcomponent)
                intent.putExtra("date",checkElementDetailsActivity.checkdate)
                intent.putExtra("sideid",checkElementDetailsActivity.sideid)
                checkElementDetailsActivity.setResult(RESULT_OK,intent)
                checkElementDetailsActivity.finish()
            }
        }

    }
}