package com.movie.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment

private const val FEATURES = "com.movie.features"

fun Context.intentTo(addressableActivity: AddressableActivity): Intent {
    return Intent(Intent.ACTION_VIEW)
            .setClassName(this, addressableActivity.className)
}
fun Context.startFeature(
    addressableActivity: AddressableActivity,
    @AnimRes enterResId: Int = android.R.anim.fade_in,
    @AnimRes exitResId: Int = android.R.anim.fade_out,
    options: Bundle? = null,
    body: Intent.() -> Unit) {

    val intent = intentTo(addressableActivity)
    intent.body()

    if (options == null) {
        val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, enterResId, exitResId)
        ActivityCompat.startActivity(this, intent, optionsCompat.toBundle())
    } else {
        ActivityCompat.startActivity(this, intent, options)
    }
}

interface AddressableActivity {
    val className: String
}

object Activities {
    object MovieCategoryActivity : AddressableActivity {
        override val className = "$FEATURES.main.MovieCategoryActivity"
    }
    object MovieActivity : AddressableActivity {
        override val className = "$FEATURES.main.MovieActivity"
    }
    object MovieActivityDetail : AddressableActivity {
        override val className = "$FEATURES.main.MovieActivityDetail"
    }
    object MovieActivityTrailer : AddressableActivity {
        override val className = "$FEATURES.main.MovieActivityTrailer"
    }
    object MovieActivityVideo : AddressableActivity {
        override val className = "$FEATURES.main.MovieActivityVideo"
    }
    object MovieActivityReview : AddressableActivity {
        override val className = "$FEATURES.main.MovieActivityReview"
    }
}