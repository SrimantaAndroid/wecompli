package com.wecompli.apiresponsemodel.faultapi

import com.google.gson.annotations.SerializedName

data class FaultApiResponse(@SerializedName("status")val status :Boolean?=false,
@SerializedName("message")val message:String?=null,
@SerializedName("row")val row:List<CheckRow> ?= null) {
}