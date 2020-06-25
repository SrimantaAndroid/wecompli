package com.wecompli.apiresponsemodel.checkelementdetails

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CheckTypeValue (@SerializedName("id") val  id:String?=null,
@SerializedName("value") val  value:String):Serializable{
}