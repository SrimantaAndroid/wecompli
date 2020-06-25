package com.wecompli.apiresponsemodel.home

import com.wecompli.apiresponsemodel.login.Menu
import com.wecompli.apiresponsemodel.login.SiteList

data class LoginUserDetailsModel(val user_id:String,val full_name :String,val email:String,val user_profile_picture_path:String,
                                 val token:String,val company_name:String,val company_id:String, var site_id:String,val company_logo:String,val user_type:String,val menu:List<Menu>,val site_list:List<SiteList>) {
}