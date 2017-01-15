package com.crucifix.software.coffeeshop;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yarolegovich.lovelydialog.LovelyStandardDialog;

public class BaseActivity extends AppCompatActivity {

    protected final static String LOG_TAG = "KEKHANE";
    protected final static String EXTRA_ORDERED_BEVERAGE_KEYS = "EXTRA_ORDERED_BEVERAGE_KEYS";

    protected KekhaneApplication mKekhaneApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mKekhaneApplication = (KekhaneApplication) getApplicationContext();

        if (online()) {
            // INTENTIONALLY LEFT BLANK
        } else {
            displayDialog(getResources().getString(R.string.info_wifi_title), getResources().getString(R.string.info_wifi_message), true);
        }
    }

    /**
     * Does our device have access to the interweb?
     *
     * @return boolean
     */
    protected boolean online() {
        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    protected void displayDialog(final String dialogTitle, final String dialogMessage, final boolean finish) {
        final LovelyStandardDialog lovelyStandardDialog = new LovelyStandardDialog(this);

        lovelyStandardDialog
                .setCancelable(false)
                .setTopColorRes(R.color.colorAccent)
                .setIcon(R.drawable.ic_info_outline_white_48dp)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if (finish) {
                            BaseActivity.this.finishAffinity();
                        } else {
                            lovelyStandardDialog.dismiss();
                        }
                    }
                })
                .show();
    }

}
