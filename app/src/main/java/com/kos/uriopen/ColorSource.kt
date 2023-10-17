package com.kos.uriopen

import android.content.Context
import android.graphics.Color
import android.os.Parcelable
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import kotlinx.parcelize.Parcelize

private const val HEX_PREFIX = '#'

sealed class ColorSource : Parcelable {

    @Parcelize
    data class Simple(
        @ColorInt val color: Int,
    ) : ColorSource()

    @Parcelize
    data class Res(
        @ColorRes val res: Int,
    ) : ColorSource()

    @Parcelize
    data class Attr(
        @AttrRes val attrRes: Int
    ) : ColorSource()

    companion object Factory {

        private const val defaultColorHex = "#000000"

        @Throws
        fun fromHex(hex: String): ColorSource {
            val hexValue = when {
                hex.contains(HEX_PREFIX) -> hex
                else -> "$HEX_PREFIX$hex"
            }
            return Simple(Color.parseColor(hexValue))
        }

        fun fromHexSafely(hex: String, onParseFailHex: String = defaultColorHex): ColorSource {
            return try {
                fromHex(hex)
            } catch (e: Exception) {
                try {
                    fromHex(onParseFailHex)
                } catch (e: Exception) {
                    fromHex(defaultColorHex)
                }
            }
        }

        fun fromColorInt(@ColorInt color: Int) = Simple(color)

        fun fromRes(@ColorRes res: Int) = Res(res)

        fun fromAttr(@AttrRes attrRes: Int) = Attr(attrRes)
    }

    fun getColorInt(context: Context) = when (this) {
        is Simple -> color
        is Res -> ContextCompat.getColor(context, res)
        is Attr -> 1
    }

    fun getColorHex(context: Context): String {
        return "#" + Integer.toHexString(getColorInt(context) and 0x00ffffff)
    }
}
