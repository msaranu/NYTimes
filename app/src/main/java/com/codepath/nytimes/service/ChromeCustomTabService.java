package com.codepath.nytimes.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.view.View;

import com.codepath.nytimes.R;

/**
 * Created by Saranu on 3/19/17.
 */

public class ChromeCustomTabService {
    public static ChromeCustomTabService getInstance(){
        return new ChromeCustomTabService();
    }
    public void launchChromeCustomTab(View v, String url, int color){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(color);

        Bitmap bitmap = BitmapFactory.decodeResource(v.getContext().getResources(), R.drawable.ic_share_alt_1);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, url);
        int requestCode = 100;

        PendingIntent pendingIntent = PendingIntent.getActivity(v.getContext(),
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Map the bitmap, text, and pending intent to this icon
        // Set tint to be true so it matches the toolbar color
        builder.setActionButton(bitmap, "Share Link", pendingIntent, true);

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(v.getContext(), Uri.parse(url));
    }
}
