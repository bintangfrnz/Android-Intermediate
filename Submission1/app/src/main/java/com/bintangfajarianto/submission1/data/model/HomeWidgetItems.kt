package com.bintangfajarianto.submission1.data.model

import android.graphics.Bitmap

object HomeWidgetItems {
    lateinit var remoteItems: ArrayList<RemoteItem>
}

data class RemoteItem (
    var name: String,
    var bitmap: Bitmap
)