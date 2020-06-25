package com.wecompli.apiresponsemodel.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class Menu (
    @SerializedName("menu_id") var menuId: String,
    @SerializedName("menu_title") var menuTitle: String,
    @SerializedName("menu_icon") var menuIcon: Any? = null,
    @SerializedName("menu_path") var menuPath: String)
