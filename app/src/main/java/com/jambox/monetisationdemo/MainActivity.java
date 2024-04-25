package com.jambox.monetisationdemo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jambox.monetisation.JamboxAdsHelper;
import com.jambox.monetisation.OnJamboxAdInitializeListener;
import com.jambox.monetisation.OnRewardedAdListener;
import com.jambox.monetisation.WebviewObject;

public class MainActivity extends AppCompatActivity {

    private WebviewObject webview = null;
    private AppOpenManager appOpenManager;
    private Context context;
    private String interstitialId = "0ee55073fd46cb13";
    private String rewardedId = "7d64a59befe5cef9";
    private String bannerId = "ba924c1fc44d29ac";
    private String appOpenId = "fce5b3d0bbba9df0";
    private String nativeId = "8d9bec8b94279ed6";

    private String h5ClientId = "9285717016";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = this;
        //Waiting for the SDK to initialize before showing the UI
        findViewById(R.id.main).setVisibility(View.GONE);

        //Ad initialization
        JamboxAdsHelper.InitializeAds(this, interstitialId, rewardedId, bannerId, new OnJamboxAdInitializeListener()
                {
                    @Override
                    public void OnJamboxAdsInitialized()
                    {
                        //Showing the UI after initialization
                        findViewById(R.id.main).setVisibility(View.VISIBLE);

                        //Initializing native
                        JamboxAdsHelper.InitializeNativeAd(nativeId);

                        //Initializing App Open Ad
                        JamboxAdsHelper.InitializeAppOpenAds(appOpenId);
                        //App open ads will be managed in separate class
                        appOpenManager = new AppOpenManager(context);
                    }
                });

        //All the button functions are set here
        SetButtonListeners();
    }

    void SetButtonListeners()
    {
        //Starting Webview
        Button startBtn = findViewById(R.id.btn_start);
        startBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StartWebview();
            }
        });

        //Closing webview on back button pressed
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed()
            {
                CloseWebview();
            }
        });

        //Rewarded Ads
        Button rw_btn = findViewById(R.id.btn_rw);
        rw_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ShowRW();
            }
        });

        //Interstitial Ads
        Button is_btn = findViewById(R.id.btn_is);
        is_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ShowIS();
            }
        });

        //Show Banner Ads
        Button banner_show = findViewById(R.id.btn_banner_show);
        banner_show.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ShowBanner();
            }
        });

        //Hiding Banner Ads
        Button banner_hide = findViewById(R.id.btn_banner_hide);
        banner_hide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                HideBanner();
            }
        });

        //Show Small Native Ads
        Button native_btn_small = findViewById(R.id.btn_native_small);
        native_btn_small.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Enabling the layout to show the native ads
                findViewById(R.id.native_ad_small).setVisibility(View.VISIBLE);
                JamboxAdsHelper.ShowNativeAd(findViewById(R.id.native_ad_small), JamboxAdsHelper.NativeAdTemplate.SMALL);
            }
        });

        //Show Medium Native Ads
        Button native_btn_medium = findViewById(R.id.btn_native_medium);
        native_btn_medium.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                findViewById(R.id.native_ad_small).setVisibility(View.GONE);
                JamboxAdsHelper.ShowNativeAd(findViewById(R.id.native_ad_medium), JamboxAdsHelper.NativeAdTemplate.MEDIUM);
            }
        });

        //Hiding native ads
        Button native_btn_hide = findViewById(R.id.btn_native_hide);
        native_btn_hide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((FrameLayout)findViewById(R.id.native_ad_small)).removeAllViews();
                ((FrameLayout)findViewById(R.id.native_ad_medium)).removeAllViews();
                JamboxAdsHelper.HideNativeAd();
            }
        });
    }

    void StartWebview()
    {
        if (webview != null)
            return;

        webview = new WebviewObject(this, h5ClientId);
        webview.StartWebview();
    }

    void CloseWebview()
    {
        if (webview == null)
            return;

        webview.CloseWebview();
        webview = null;
    }

    void ShowBanner()
    {
        JamboxAdsHelper.ShowBannerAd(JamboxAdsHelper.BannerPosition.BOTTOM);
    }

    void HideBanner()
    {
        JamboxAdsHelper.HideBannerAd();
    }

    void ShowRW()
    {
        JamboxAdsHelper.ShowRewarded(new OnRewardedAdListener()
        {
            @Override
            public void OnAdDisplayFailed() { }
            @Override
            public void OnAdDisplayed() { }
            @Override
            public void OnAdCompleted()
            {
                System.out.println("User Rewarded");
            }
            @Override
            public void OnAdHidden() { }
        });
    }

    void ShowIS()
    {
        JamboxAdsHelper.ShowRewarded(null);
    }

}