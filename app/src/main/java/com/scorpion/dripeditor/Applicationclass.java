package com.scorpion.dripeditor;

import android.app.Application;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;

public class Applicationclass extends Application {

	private static Applicationclass instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		AudienceNetworkAds.initialize(this);
		AdSettings.addTestDevice("9990e78a-441e-4f6d-b00a-b198255cf579");
		FBInterstitial.getInstance().loadFBInterstitial(this);

	}

}
