package com.wecompli.apiresponsemodel.seasonlist

import com.google.gson.annotations.SerializedName

data class Row(@SerializedName("id") val id:String?=null,
@SerializedName("season_name") val seasonName:String?=null) {
}