1. Adicionar jar ao projeto

2. Para enviar junto às exceções o username do usuário logado, criar classe que extende BaseSentryListener e implementá-la da seguinte forma:

	public class SentryListener extends BaseSentryListener {

		@Override
		public SentryEventBuilder beforeCapture(SentryEventBuilder builder) {
			JSONObject tags = builder.getTags();
			SyncConfig syncConfig = SyncConfig.getInstance();
			try {
				tags.put("username", syncConfig.getUsername());
				tags.put("token", syncConfig.getAuthToken());
			} catch (JSONException e) {
			}
			return super.beforeCapture(builder);
		}

	}

3. No método onCreate da application, inicializar plugin:

		// Sentry
	    Sentry.setCaptureListener(new SentryListener());
		Sentry.init(this.getApplicationContext(), "https://app.getsentry.com","https://a5daef2f5c824f7dbb706db435f33413:d3b37fb0dacf41eda0724b0e0a9ebbd4@app.getsentry.com/25072");