package com.kos.uriopen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Utils {

    public static @NonNull
    String getClipboard(@NonNull Context context) {
        final ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        String copiedText = "";

        if (clipboardManager != null) {
            ClipData clipData = clipboardManager.getPrimaryClip();

            if (clipData != null && clipData.getItemCount() > 0) {
                final CharSequence clipText = clipData.getItemAt(0).getText();

                copiedText = clipText != null ? clipText.toString() : "";
            }
        }

        return copiedText;
    }

    public static void setClipboard(@NonNull Context context, @Nullable String label, String text) {
        final ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(label, text);
        if (clipboardManager != null)
            clipboardManager.setPrimaryClip(clipData);
    }
}
