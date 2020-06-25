package com.wecompli.apiresponsemodel.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class ModuleFunctionList (
    @SerializedName("id") var id: String? = null,
    @SerializedName("function_name") var functionName: String? = null,
    @SerializedName("function_display_name") var functionDisplayName: String? = null,
    @SerializedName("function_description") var functionDescription: String? = null,
    @SerializedName("is_display_in_menu") var isDisplayInMenu: String? = null
)