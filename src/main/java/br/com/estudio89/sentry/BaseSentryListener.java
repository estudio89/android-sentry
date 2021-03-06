package br.com.estudio89.sentry;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;



public class BaseSentryListener extends Sentry.SentryEventCaptureListener {
	private Context context;
	public BaseSentryListener(Context context) {
		this.context = context;
	}

	@Override
	public Sentry.SentryEventBuilder beforeCapture(Sentry.SentryEventBuilder builder) {
		String version = "";
		JSONObject tags = builder.getTags();
		JSONObject extra = builder.getExtra();
		try {
			PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			version = pInfo.versionName;
			tags.put("version", version);
			tags.put("device_model", android.os.Build.MODEL);
			tags.put("manufacturer", android.os.Build.MANUFACTURER);
			tags.put("android_version", android.os.Build.VERSION.RELEASE);
			extra.put("breadcrumbs", SentryBreadcrumbs.getInstance().getBreadcrumbs());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder;
	}

}
