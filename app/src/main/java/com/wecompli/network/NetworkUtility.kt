package com.wecompli.network

class NetworkUtility {
    companion object{
       // val BASE_URL = "http://rest.wecompli.io/api/mobile/"
        val BASE_URL ="https://wecompli.io/rest/api/mobile/"
        const val  LOG_IN = "user/login"
        const val TODAYSEASON="today/season/list"
        const val REGENERATE_TOKEN="token/genarate"
        const val COMPONENTCHECKLIST="category/list"
        const val COMPONENT_CHECK_ELEMENTDE_DETAILS="checks/list"
        const val COMPONENET_CHECK_SUBMIT="checking/process"
        const val FAULT_LIST="checks/fault/list"
        const val FAULTCREATE="checks/fault/create"
        const val FAULTDETAILS="checks/fault/details"
        const val FAULSTATUSLIST="fault/status/list"
        const val FAULTSTATUSMESSAGE_CHANGE="checks/fault/status/change"
        const val FAULTREPAIR="fault/repair"
        const val FAULTBYSCAN="checks/fault/details/byscan"
        const val DOCUMENTSUBMIT="document/create"
        const val SERVERTYLEVEL="site/severity/level"
        const val LOCATIONLIST="location/list"
        const val CREATEINCIDENTREPORT="incident/create"

    }
}