package com.bintangfajarianto.submission1.widgets

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bintangfajarianto.submission1.R
import com.bintangfajarianto.submission1.data.model.HomeWidgetItems
import com.bintangfajarianto.submission1.data.model.RemoteItem

internal class RemoteViewsFactory(private val mContext: Context)
    : RemoteViewsService.RemoteViewsFactory {

    private lateinit var remoteItems: ArrayList<RemoteItem>

    override fun onCreate() {}

    override fun onDataSetChanged() {
        remoteItems = HomeWidgetItems.remoteItems
    }

    override fun onDestroy() {
        remoteItems.clear()
    }

    override fun getCount(): Int = remoteItems.size

    override fun getViewAt(p0: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.story_widget_item)

        rv.setImageViewBitmap(R.id.widget_image, remoteItems[p0].bitmap)
        rv.setTextViewText(R.id.widget_name, remoteItems[p0].name)

        val extras = bundleOf(
            StoryWidget.EXTRA_NAME to remoteItems[p0].name
        )
        val fillInIntent = Intent().apply {
            putExtras(extras)
        }

        rv.setOnClickFillInIntent(R.id.widget_image, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun hasStableIds(): Boolean = true

}