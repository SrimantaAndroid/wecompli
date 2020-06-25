package com.wecompli.apiresponsemodel.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class SiteList (
    @SerializedName("id") var id: String? = null,
    @SerializedName("site_name") var siteName: String? = null,
    @SerializedName("site_logo_path") var siteLogoPath: String? = null,
    @SerializedName("site_URL") var siteURL: String? = null,
    @SerializedName("site_email") var siteEmail: String? = null,
    @SerializedName("site_contact_no") var siteContactNo: Any? = null,
    @SerializedName("status") var status: String? = null)