package com.wecompli.apiresponsemodel.faultapi

import com.google.gson.annotations.SerializedName

data class Timeline(@SerializedName("repairDatetime") val repairDatetime:String?=null,
@SerializedName("repairMessage") val repairMessage:String?=null,
@SerializedName("repairRemark") val repairRemark:String?=null) {
}