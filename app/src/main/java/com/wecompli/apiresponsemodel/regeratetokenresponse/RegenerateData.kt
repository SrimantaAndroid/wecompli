package com.wecompli.apiresponsemodel.regeratetokenresponse

import com.google.gson.annotations.SerializedName
import com.wecompli.apiresponsemodel.login.Menu
import com.wecompli.apiresponsemodel.login.Module
import com.wecompli.apiresponsemodel.login.SiteList

data class RegenerateData(@SerializedName("user_id") var userId: String? = null,
                          @SerializedName("full_name") var fullName: String? = null,
                          @SerializedName("email") var email: String? = null,
                          @SerializedName("company_id") var companyId: String? = null,
                          @SerializedName("company_name") var companyName: String? = null,
                          @SerializedName("company_logo") var companyLogo: String? = null,
                          @SerializedName("user_type") var userType: String? = null,
                          @SerializedName("user_profile_picture_path") var userProfilePicturePath: String? = null,
                          @SerializedName("created_at") var createdAt: String? = null,
                          @SerializedName("token") var token: String? = null) {
}