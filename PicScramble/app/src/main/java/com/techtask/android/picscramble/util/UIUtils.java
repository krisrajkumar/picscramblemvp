package com.techtask.android.picscramble.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Holds the ui utils.
 */
public class UIUtils {
    public static void displaySnackBar(View view, int msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static ProgressDialog createCircularProgress(Context context, String msg) {
        ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.setCancelable(false);
        progressBar.setMessage(msg);
        progressBar.setProgress(ProgressDialog.STYLE_SPINNER);
        return progressBar;
    }
}
