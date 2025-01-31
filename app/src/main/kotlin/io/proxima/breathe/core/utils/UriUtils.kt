package io.proxima.breathe.core.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.annotation.RawRes

fun Context.uriFromResource(
    @RawRes resId: Int,
    scheme: String = ContentResolver.SCHEME_ANDROID_RESOURCE
): Uri = Uri.Builder()
    .scheme(scheme)
    .authority(packageName)
    .appendPath("$resId")
    .build()