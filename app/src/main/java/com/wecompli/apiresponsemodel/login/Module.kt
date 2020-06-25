package com.wecompli.apiresponsemodel.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class Module (
    @SerializedName("id") var id: String? = null,
    @SerializedName("module_controller_name") var moduleControllerName: String? = null,
    @SerializedName("module_display_name") var moduleDisplayName: String? = null,
    @SerializedName("module_function_list") var moduleFunctionList: List<ModuleFunctionList>? = null
)