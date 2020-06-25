package com.wecompli.apiresponsemodel.faultapi

import com.google.gson.annotations.SerializedName
import java.util.*

data class CheckRow(@SerializedName("id") val id:String?=null,
@SerializedName("category_name") val categoryName:String?=null,
@SerializedName("check_name") val checkName:String?=null,
@SerializedName("check_note")val checkNote:String?=null,
@SerializedName("fault_description") val faultDescription:String?=null,
@SerializedName("created_at") val createdAt:String?=null,
@SerializedName("check_type_value") val checkTypeValue:String?=null,
@SerializedName("check_design_css") val checkDesignCss:String?=null,
@SerializedName("company_id") val companyId:String?=null,
@SerializedName("site_id") val siteId:String?=null,
@SerializedName("fault_files") val faultFiles:List<String>?=null,
@SerializedName("status") val status:String?=null,
@SerializedName("timeline") val timeline: List<Timeline> ? = null){
}