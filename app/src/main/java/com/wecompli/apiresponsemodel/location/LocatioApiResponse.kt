package com.wecompli.apiresponsemodel.location

data class LocatioApiResponse(val status:Boolean, val message:String, val row:List<LocationRow>) {
}