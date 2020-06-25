package com.wecompli.apiresponsemodel.seasonlist

import com.google.gson.annotations.SerializedName
import com.wecompli.apiresponsemodel.notifywho.NotifyWhoModel

data class SeasonListApiresponse(@SerializedName("status") val status:Boolean?=null,
@SerializedName("message")val  message :String?=null,
@SerializedName("row") val row: List<Row> ?= null,
 @SerializedName("notify_emails") val notify_emails:List<NotifyWhoModel>){

}
