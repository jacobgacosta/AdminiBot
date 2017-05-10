package io.dojogeek.adminibot.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

public class ResourceProvider {

    public static String getStringFromName(Context context, String nameResource) {

        Resources resources = context.getResources();

        int stringId = resources.getIdentifier(nameResource, "string", context.getPackageName());

        return resources.getString(stringId);
    }

    public static Drawable getDrawableFromName(Context context, String nameResource) {

        Resources resources = context.getResources();

        int drawableId = resources.getIdentifier(nameResource, "drawable", context.getPackageName());

        return ResourcesCompat.getDrawable(resources, drawableId, null);
    }
}
