package com.wecompli.apiresponsemodel.checkelementdetails

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ElementDetailsRow(@SerializedName("id") val id:String,
@SerializedName("check_name") val checkName:String?=null,
@SerializedName("check_note") val checkNote:String?=null,
@SerializedName("check_type_id") val checkTypeId:String?=null,
@SerializedName("check_type_name") val checkTypeName:String?=null,
@SerializedName("check_type_icon") val checkTypeIcon:String?=null,
@SerializedName("has_qrcode") val hasQrcode:String?=null,
@SerializedName("qrcode") val qrcode:String?=null,
@SerializedName("check_type_value") val  checkTypeValue:List<CheckTypeValue>? = null,
@SerializedName("status_id") val statusId:String?=null,
@SerializedName("status") val  status:String?=null):Serializable {
}