package com.wecompli.apiresponsemodel.faultdetails

data class FaultDetailsByScanModel(val status:Boolean,val message:String,val row:FaultDetailsRow) {
}