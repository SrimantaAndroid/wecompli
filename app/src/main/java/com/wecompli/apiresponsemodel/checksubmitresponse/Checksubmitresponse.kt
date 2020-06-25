package com.wecompli.apiresponsemodel.checksubmitresponse

import com.google.gson.annotations.SerializedName

data class Checksubmitresponse(@SerializedName("status") val status:Boolean,
@SerializedName("message") val message:String,
@SerializedName("fileUploadMessage") val fileUploadMessage:String) {
}