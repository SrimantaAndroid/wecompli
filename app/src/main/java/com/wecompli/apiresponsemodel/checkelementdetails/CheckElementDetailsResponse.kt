package com.wecompli.apiresponsemodel.checkelementdetails

import com.google.gson.annotations.SerializedName

data class CheckElementDetailsResponse(
    @SerializedName("status")  val status:Boolean?=false,
    @SerializedName("message") val message:String?=null,
    @SerializedName("row") val elementrow:List<ElementDetailsRow> ? = null) {
}