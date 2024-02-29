package com.movie.common.extension

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackBar(text: CharSequence, duration: Int = Snackbar.LENGTH_SHORT, init: Snackbar.() -> Unit = {}): Snackbar {
    val snack = Snackbar.make(this, text, duration)
    snack.init()
    snack.show()
    return snack
}

fun <T> Activity.extra(key: String): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        @Suppress("UNCHECKED_CAST")
        intent?.extras?.get(key) as T
    }
}
