package com.bintangfajarianto.submission1.widgets

import android.content.Intent
import android.widget.RemoteViewsService

class StoryWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        RemoteViewsFactory(this.applicationContext)
}