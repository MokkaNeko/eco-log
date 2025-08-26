package com.mokaneko.recycle2.utils.helper

import android.app.Activity
import android.widget.ImageView
import android.widget.TextView
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.databinding.CustomTopNavBinding

object TopNavHelper {
    fun setupTopNav(
        activity: Activity,
        rootView: CustomTopNavBinding,
        title: String,
        onBackPressed: (() -> Unit)? = null
    ) {
        val titleView = rootView.navTitle
        val backButton = rootView.navBack

        titleView.text = title
        backButton.setOnClickListener {
            onBackPressed?.invoke() ?: activity.finish()
        }
    }
}