package com.lz.mysender;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSendCustomClick(View view) {
        try {
            Intent intent = new Intent();
            intent.setAction("lz.intent.action.SEND");
            intent.putExtra(Intent.EXTRA_TEXT, "username");
            //在代码中进行定义的时候Data属性和Type属性会进行相互覆盖：
            //intent.setType("text/plain");
            intent.setDataAndType(Uri.parse("http://mysender.lz.com"), "text/plain");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onSendCustom2Click(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra("username", "username");
            String packageName = "com.lz.myreceiver";
            String className = "com.lz.myreceiver.MainActivity";
            ComponentName componentName = new ComponentName(packageName, className);
            intent.setComponent(componentName);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onSendTextClick(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "send_to"));
    }

    public void onSendBinaryClick(View view) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.EMPTY);
        shareIntent.setType("image/jpeg");
        //shareIntent.setType("*/*");
        startActivity(Intent.createChooser(shareIntent, "send_to"));
    }

    public void onSendMultiplePiecesClick(View view) {
        ArrayList<Uri> imageUris = new ArrayList<Uri>();
        imageUris.add(Uri.EMPTY); // Add your image URIs here
        imageUris.add(Uri.EMPTY);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "Share images to.."));
    }

    private void startActivityCreateChooser() {
        // Start an activity if it's safe
        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        Intent intent = new Intent(Intent.ACTION_VIEW, location);

        // Always use string resources for UI text.
        // This says something like "Share this photo with"
        String title = getResources().getString(R.string.app_name);
        // Create intent to show chooser
        Intent chooser = Intent.createChooser(intent, title);

        // Verify the intent will resolve to at least one activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    /**
     * 验证是否存在处理 Intent 的应用
     *
     * @param intent
     * @return
     */
    private boolean verify(Intent intent) {
        // Verify it resolves
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;
        return isIntentSafe;
    }
}
