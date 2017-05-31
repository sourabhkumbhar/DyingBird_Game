package sourabh.dyingbird;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import sourabh.dyingbird.DyingBirdGame;

public class AndroidLauncher extends AndroidApplication implements AdHandler {

	private static final String TAG = "AndroidLauncher";
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;
	protected AdView adView;
	InterstitialAd mInterstitialAd;



	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
				case SHOW_ADS:
					adView.setVisibility(View.VISIBLE);
					break;
				case HIDE_ADS:
					adView.setVisibility(View.GONE);
					break;
			}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		initialize(new DyingBirdGame(), config);


		RelativeLayout layout = new RelativeLayout(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new DyingBirdGame(this), config);
		layout.addView(gameView);



		loadInterstitial();




		adView = new AdView(this);
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				int visiblity = adView.getVisibility();
				adView.setVisibility(AdView.GONE);
				adView.setVisibility(visiblity);
				Log.i(TAG, "Ad Loaded...");
			}

			@Override
			public void onAdClosed() {
				super.onAdClosed();
				loadInterstitial();
			}

			@Override
			public void onAdOpened() {
				super.onAdOpened();
			}

			@Override
			public void onAdFailedToLoad(int i) {
				super.onAdFailedToLoad(i);
				Log.i(TAG, " onAdFailedToLoad..."+i);


			}
		});
		adView.setAdSize(AdSize.SMART_BANNER);
		//http://www.google.com/admob
		adView.setAdUnitId(getString(R.string.ad_unit_bottom_banner));

		AdRequest adRequestBanner = new AdRequest.Builder().build();
		//run once before uncommenting the following line. Get TEST device ID from the logcat logs.
		//builder.addTestDevice("INSERT TEST DEVICE ID HERE");
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT

		);

		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,1);

		layout.addView(adView, adParams);
		adView.loadAd(adRequestBanner);

		setContentView(layout);
	}

	private void showInterstitial() {
		if (mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
			loadInterstitial();
		}
	}

	void loadInterstitial(){
		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_interstitial));

		AdRequest adRequestInterstitial = new AdRequest.Builder()
				.build();
		// Load ads into Interstitial Ads
		mInterstitialAd.loadAd(adRequestInterstitial);

		mInterstitialAd.setAdListener(new AdListener() {
			public void onAdLoaded() {
//				showInterstitial();

				Log.d("AndroidLauncher","onAdLoaded");
			}

			@Override
			public void onAdFailedToLoad(int i) {
				super.onAdFailedToLoad(i);
				Log.d("AndroidLauncher","onAdFailedToLoad "+i);

			}
		});
	}

	void loadBanner(){



	}


	@Override
	public void showAds(boolean show) {

	}

	@Override
	public void showInterstitialAd() {

		runOnUiThread(new Runnable() {
			@Override public void run() {
				showInterstitial();
			}
		});
	}

//	@Override
//	public void showAds(boolean show) {
//		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
//	}



}
