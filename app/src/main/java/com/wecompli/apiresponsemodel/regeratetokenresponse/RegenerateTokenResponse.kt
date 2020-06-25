package com.wecompli.apiresponsemodel.regeratetokenresponse

import com.google.gson.annotations.SerializedName

data class RegenerateTokenResponse(@SerializedName("status") val  status:Boolean?=false,
@SerializedName("data") val data:RegenerateData?=null,
@SerializedName("message") val message:String?=null) {
}