package com.lz.myreceiver;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else if ("lz.intent.action.SEND".equals(action) && type != null) {
            handleSendText(intent);
        } else {
            // Handle other intents, such as being started from the home screen
        }
    }

    /*Caution: Take extra care to check the incoming data,
    you never know what some other application may send you.
    For example, the wrong MIME type might be set,
    or the image being sent might be extremely large.
    Also, remember to process binary data
    in a separate thread rather than the main ("UI") thread.*/

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            Log.e("result", sharedText);
            Toast.makeText(MainActivity.this, sharedText, Toast.LENGTH_SHORT).show();
        }
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String username = extras.getString("username");
            if (username != null) {
                Toast.makeText(MainActivity.this, username, Toast.LENGTH_SHORT).show();
            }
        }
    }

    void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
            Log.e("result", imageUri.toString());
        }
    }

    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
            Log.e("imageUris", imageUris.toString());
        }
    }
}
