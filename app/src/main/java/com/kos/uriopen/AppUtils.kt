package com.kos.uriopen

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import java.io.File

private const val FILE_PROVIDER_FORMAT = "%s.fileprovider"

fun Context.startActionDialIntent(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL)
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        .setData(Uri.parse("tel:$phoneNumber"))

    startActivity(intent)
}

fun Context.startActionDialIntent(@StringRes stringId: Int) {
    startActionDialIntent(getString(stringId))
}

fun Context.startActionViewIntent(urlString: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    startActivity(intent)
}

fun Context.startActionViewWithComponentNameIntent(urlString: String, packageName: String, activityInfoName: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString)).apply {
        addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or
                Intent.FLAG_ACTIVITY_CLEAR_TOP
        )
        component = ComponentName(packageName, activityInfoName)
    }

    startActivity(intent)
}

fun Context.startActionPdfSendIntent(file: File) {
    val intent = Intent(Intent.ACTION_SEND)
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    if (file.exists()) {
        val uri = FileProvider.getUriForFile(
            this,
            String.format(FILE_PROVIDER_FORMAT, applicationContext.packageName),
            file
        )
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_STREAM, uri)

        startActivity(Intent.createChooser(intent, getString(R.string.send)))
    }
}

fun Context.openShareDialog(text: String) {
    ShareCompat.IntentBuilder(this)
        .setText(text)
        .setType("text/plain")
        .startChooser()
}

fun Context.showApplicationSettings() {
    val uri = Uri.fromParts("package", packageName, null)
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        .setData(uri)

    startActivity(intent)
}

fun Context.copyToClipboard(label: String? = null, text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText(label, text))
}

fun Context.safeStartActivity(intent: Intent): Boolean {
    return intent.resolveActivity(packageManager) != null && try {
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}
