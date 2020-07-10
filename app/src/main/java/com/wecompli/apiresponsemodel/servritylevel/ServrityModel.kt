package com.wecompli.apiresponsemodel.servritylevel

import com.google.gson.annotations.SerializedName

data  class ServrityModel(val status:String,val message:String,@SerializedName("row")val row: Row) {
}