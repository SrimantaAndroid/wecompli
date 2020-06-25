package com.wecompli.apiresponsemodel.checksummery

import com.google.gson.annotations.SerializedName

data class CheckRow(@SerializedName("id")  val id:String,
@SerializedName("category_name") val categoryName:String,
@SerializedName("category_note") val categoryNote:String,
@SerializedName("total_checks_count") val totalChecksCount:String,
@SerializedName("checks_ids") val checksIds: String,
                    @SerializedName("last_checked_at") val last_checked_at: String,
@SerializedName("checks_count") val checksCount: String) {
}