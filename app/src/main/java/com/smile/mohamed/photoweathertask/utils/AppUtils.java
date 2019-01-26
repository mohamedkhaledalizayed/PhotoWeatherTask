package com.smile.mohamed.photoweathertask.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.annotation.ColorRes;
import android.support.v7.app.AlertDialog;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.smile.mohamed.photoweathertask.R;

public class AppUtils {

    private static void showToast(Context context, String text, @ColorRes int textColor,
                                  @ColorRes int backgroundColor, int icon, Typeface font) {
        final StyleableToast.Builder builder = new StyleableToast
                .Builder(context)
                .text(text)
                .textColor(context.getResources().getColor(textColor))
                .backgroundColor(context.getResources().getColor(backgroundColor));
        if (font != null) {
            builder.typeface(font);
        }
        if (icon != 0) {
            builder.icon(icon);
        }
        builder.build().show();
    }

    public static void showInfoToast(Context context, String text) {
        showToast(context, text, android.R.color.white, R.color.info_toast_color, 0, null);
    }

    public static void showErrorToast(Context context, String text) {
        showToast(context, text, android.R.color.white, R.color.fail_toast_color, 0, null);
    }

    public static void showSuccessToast(Context context, String text) {
        showToast(context, text, android.R.color.white, R.color.success_toast_color, 0, null);
    }
    public static void showAlert(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.title);
        builder.setMessage(R.string.enable_gps);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

}
