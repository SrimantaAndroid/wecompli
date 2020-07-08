package com.wecompli.screeen.notifywho

import android.content.Intent
import android.view.View
import com.wecompli.R
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent

class NotifyWhoOnClick(
   val  notifyWhoActivity: NotifyWhoActivity,
   val notifyWhoViewBind: NotifyWhoViewBind
): View.OnClickListener {

    init {
        notifyWhoViewBind.rl_back_notifywho!!.setOnClickListener(this)
        notifyWhoViewBind.tv_notifysubmit!!.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.rl_back_notifywho->{
                notifyWhoActivity.finish()
            }
            R.id.tv_notifysubmit->{

                var email=""
                var selectedemail=""
                for (i in 0 until notifyWhoActivity.emillist.size){
                    if (notifyWhoActivity.emillist.get(i).ischeck){
                        selectedemail=selectedemail+","+notifyWhoActivity.emillist.get(i).name
                        email=email+"###"+notifyWhoActivity.emillist.get(i).email
                    }
                }
                if(!email.equals(""))
                    AppSheardPreference(notifyWhoActivity).setvalue_in_preference(PreferenceConstent.SelectedEmail,email.substring(1,3))
                    else
                    AppSheardPreference(notifyWhoActivity).setvalue_in_preference(PreferenceConstent.SelectedEmail,email)
             //   notifyWhoActivity.finish()

                // }
                val intent = Intent()
                intent.putExtra("emaillist", selectedemail)
               // intent.putExtra("emailname", emailname)
                notifyWhoActivity.setResult(2, intent)
                notifyWhoActivity.finish()

               /* if (!email.equals("")){

                }else
                    Alert.showalert(notifyWhoActivity,notifyWhoActivity.resources.getString(R.string.pleaseeneteremail))*/
            }
        }
    }
}