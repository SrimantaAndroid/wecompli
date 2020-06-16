package com.wecompli.apiresponsemodel.faultdetails

data class FaultDetailsRow(val id:String,val company_id:String,val site_id:String,val category_name:String,val check_name:String,
                           val check_note:String,val fault_description:String,val created_at:String,val check_type_value:String,val check_design_css:String,
                           val status:String,val timeline:List<Timeline>,val fault_files:List<String>) {
}