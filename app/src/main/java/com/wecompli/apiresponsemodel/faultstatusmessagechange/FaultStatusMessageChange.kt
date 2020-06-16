package com.wecompli.apiresponsemodel.faultstatusmessagechange


data class FaultStatusMessageChange(val status:Boolean,val message:String,val timeLine:List<Timeline>) {
}