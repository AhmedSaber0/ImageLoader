package com.application.android.imageloader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.application.android.imageloader.ui.Feed.PhotosFragment;
import com.application.android.imageloader.util.BaseActivity;
import com.application.android.imageloader.util.NetworkHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

public class FeedActivity extends BaseActivity {

    @BindView(R.id.fragmentPlaceHolder)
    FrameLayout fragmentPlaceholder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.offlineModeTextView)
    TextView offlineModeTextView;
    BroadcastReceiver connectivityBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!NetworkHelper.getInstance().isNetworkAvailable(context)) {
                offlineModeTextView.setVisibility(View.VISIBLE);
            } else {
                offlineModeTextView.setVisibility(View.GONE);
            }
        }
    };
    private IntentFilter connectivityIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);
        setupToolbar();
        showFragment(PhotosFragment.class);
        connectivityIntentFilter = new IntentFilter(CONNECTIVITY_ACTION);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(connectivityBroadcastReceiver, connectivityIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(connectivityBroadcastReceiver);
        super.onPause();
    }
}
