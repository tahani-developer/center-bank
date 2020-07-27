package com.falconssoft.centerbank;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;

public class LocaleAppUtils {

    private static Locale locale;
    public static String language;

    public static void setLocale(Locale localeIn) {
        locale = localeIn;
        Log.e("locale", locale.getCountry());
        if (locale != null) {
            Locale.setDefault(locale);
        }
    }

    public static void setConfigChange(Context ctx) {
        if (locale != null) {
            Locale.setDefault(locale);

            Configuration configuration = ctx.getResources().getConfiguration();
            DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
            configuration.locale = locale;

            ctx.getResources().updateConfiguration(configuration, displayMetrics);
        }
    }

    public static void changeLayot(Context context){


        if (language.equals("ar")) {
            LocaleAppUtils.setLocale(new Locale("ar"));
            LocaleAppUtils.setConfigChange(context);
        } else {
            LocaleAppUtils.setLocale(new Locale("en"));
            LocaleAppUtils.setConfigChange(context);
        }

    }

}
