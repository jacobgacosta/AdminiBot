package io.dojogeek.adminibot.utils;

import android.content.Context;
import android.content.Intent;

public class LaunchIntents {

    public static void launchIntentClearTop(Context context, Class className) {
        Intent intent = new Intent(context, className);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

}
