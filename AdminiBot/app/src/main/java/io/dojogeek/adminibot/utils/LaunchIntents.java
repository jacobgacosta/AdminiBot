package io.dojogeek.adminibot.utils;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import io.dojogeek.adminibot.exceptions.ArgumentException;

public class LaunchIntents {

    public static void launchIntentClearTop(Context context, Class className) {
        Intent intent = new Intent(context, className);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void launchIntentWithExtraValues(Context context, Class className,
                                                   Map<String, Serializable> flagValues) throws ArgumentException {

        checkRequiredValue(context, "context");
        checkRequiredValue(flagValues, "flagValues");

        Intent intent = new Intent(context, className);

        Set<Map.Entry<String, Serializable>> entrySet = flagValues.entrySet();

        for (Map.Entry<String, Serializable> entry : entrySet) {

            String key = entry.getKey();
            Serializable value = entry.getValue();

            intent.putExtra(key, value);
        }

        context.startActivity(intent);
    }

    private static void checkRequiredValue(Object object, String fieldName) throws ArgumentException {

        if (object == null) {
            throw new ArgumentException("The " + fieldName + "is required.");
        }

    }

}
