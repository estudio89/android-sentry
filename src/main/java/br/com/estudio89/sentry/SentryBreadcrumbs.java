package br.com.estudio89.sentry;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SentryBreadcrumbs {
    private static SentryBreadcrumbs instance;
    private JSONArray breadcrumbs = new JSONArray();
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public SentryBreadcrumbs() {

    }

    public static SentryBreadcrumbs getInstance() {
        if (instance == null) {
            instance = new SentryBreadcrumbs();
        }
        return instance;
    }

    public void addBreadcrumb(Object caller, String message) {
        try {
            Date date = new Date();
            message = fmt.format(date) + " | " + caller.getClass().getSimpleName() + " | " + message;
            breadcrumbs.put(message);

            if (breadcrumbs.length() > 30) {
                remove(0, breadcrumbs);
            }
        } catch (Exception e) {
            Sentry.captureException(e, Sentry.SentryEventBuilder.SentryEventLevel.WARNING);
        }
    }

    public JSONArray getBreadcrumbs() {
        return breadcrumbs;
    }

    public static JSONArray remove(final int idx, final JSONArray from) {
        final List<String> objs = asList(from);
        objs.remove(idx);

        final JSONArray ja = new JSONArray();
        for (final String obj : objs) {
            ja.put(obj);
        }

        return ja;
    }

    public static List<String> asList(final JSONArray ja) {
        final int len = ja.length();
        final ArrayList<String> result = new ArrayList<String>(len);
        for (int i = 0; i < len; i++) {
            final String obj = ja.optString(i);
            if (obj != null) {
                result.add(obj);
            }
        }
        return result;
    }

}
