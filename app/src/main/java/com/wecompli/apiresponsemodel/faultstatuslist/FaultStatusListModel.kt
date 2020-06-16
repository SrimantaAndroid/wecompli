package com.wecompli.apiresponsemodel.faultstatuslist

data class FaultStatusListModel(val status:Boolean,val message:String,val row:List<FaultStatusRow>){
}