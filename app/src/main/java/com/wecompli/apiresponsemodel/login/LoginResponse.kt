package com.wecompli.apiresponsemodel.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class LoginResponse (
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("data") var data: Data? = null,
    @SerializedName("message") var message: String? = null

)