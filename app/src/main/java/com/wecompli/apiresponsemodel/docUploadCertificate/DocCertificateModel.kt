package com.wecompli.apiresponsemodel.docUploadCertificate

import android.graphics.Bitmap
import java.io.File

data class DocCertificateModel(var imagepath:String, var imagebitmap: Bitmap,var file:File) {
}