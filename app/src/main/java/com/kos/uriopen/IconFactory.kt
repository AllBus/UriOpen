package com.kos.uriopen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

sealed interface IconSource : Parcelable {

    interface DrawableSource : IconSource {
        fun getDrawable(context: Context): Drawable?
    }

    @Parcelize
    data class Url internal constructor(
        val url: String,
        val defaultIcon: DrawableSource? = null,
    ) : IconSource {
        @IgnoredOnParcel
        var transformations: List<List<Bitmap>>? = null
    }

    @Parcelize
    data class Image internal constructor(
        val bitmap: Bitmap,
    ) : DrawableSource {

        override fun getDrawable(context: Context) = BitmapDrawable(context.resources, bitmap)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Image

            return bitmap.sameAs(other.bitmap)
        }

        override fun hashCode(): Int {
            return bitmap.hashCode()
        }
    }

    @Parcelize
    data class Res internal constructor(
        @DrawableRes val res: Int,
        val color: ColorSource? = null,
    ) : DrawableSource {

        override fun getDrawable(context: Context) = null
    }
}

data class CombineIcon(
    val icon: IconSource.Res,
    val background: IconSource.Res,
)

object IconFactory {

    fun simple(
        url: String,
        defaultIcon: IconSource.DrawableSource? = null,
        vararg transformations: List<Bitmap>,
    ) = IconSource.Url(url, defaultIcon)
        .apply {
            this.transformations = transformations.toList()
        }

    fun simple(bitmap: Bitmap) = IconSource.Image(bitmap)

    fun simple(@DrawableRes res: Int, color: ColorSource? = null) = IconSource.Res(res, color)

}





