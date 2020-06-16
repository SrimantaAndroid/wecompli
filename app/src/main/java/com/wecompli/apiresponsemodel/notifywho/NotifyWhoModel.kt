package com.wecompli.apiresponsemodel.notifywho

import java.io.Serializable

data class NotifyWhoModel(val name:String,val email:String,var ischeck:Boolean):Serializable {
}