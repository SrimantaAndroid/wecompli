package com.wecompli.apiresponsemodel.checksummery

import com.google.gson.annotations.SerializedName

class CheckSummeryResponse(@SerializedName("status")  val status:Boolean,
@SerializedName("message") val message: String,
@SerializedName("row") val row: List<CheckRow> ,
@SerializedName("fault_count") val  faultCount: Integer) {
}