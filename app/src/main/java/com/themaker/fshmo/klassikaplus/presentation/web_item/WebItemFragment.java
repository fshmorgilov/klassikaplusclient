package com.themaker.fshmo.klassikaplus.presentation.web_item;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import com.themaker.fshmo.klassikaplus.R;
import com.themaker.fshmo.klassikaplus.data.domain.Item;
import com.themaker.fshmo.klassikaplus.presentation.base.MvpBaseFragment;
import com.themaker.fshmo.klassikaplus.presentation.common.State;
import com.themaker.fshmo.klassikaplus.presentation.root.MainNavigationCallback;

public class WebItemFragment extends MvpBaseFragment {

    private static final String TAG = WebItemFragment.class.getName();
    private static final String KEY_ID = "BundleKey";

    private Item item;
    private MainNavigationCallback mainMenuCallback = (MainNavigationCallback) getActivity();

    @BindView(R.id.web_item_webview) WebView webView;
    @BindView(R.id.web_item_error) TextView error;
    @BindView((R.id.web_item_toolbar)) Toolbar toolbar;

    public static WebItemFragment newInstance(@NonNull Item item) {
        WebItemFragment fragment = new WebItemFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_ID, item);
        fragment.setArguments(bundle);
        Log.i(TAG, "newInstance: fragment created for item: " + item.getName());
        return fragment;
    }

    @Override
    protected void onPostCreateView() {
        super.onPostCreateView();
        setupWebView();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

    }

    private void setupWebView() {
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true); // TODO: 2/8/2019 перепилить
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        showState(State.Loading);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.item = (Item) bundle.getSerializable(KEY_ID);
            Log.d(TAG, "onPostCreateView: item: " + item.getName());
            if (item != null && item.getPageAlias() != null) {
                webView.loadUrl(item.getPageAlias());
                showState(State.HasData);
                Log.i(TAG, "onPostCreateView: item loaded: " + item.getPageAlias());
            } else {
                showError("item is null");
            }
        } else {
            showError("bundle is null");
        }
    }

    private void showError(String message) {
        Log.e(TAG, "onPostCreateView: no data provided for webView. Reason: " + message);
        showState(State.NoData);
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.web_item;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void showState(@NonNull State state) {
        Log.i(TAG, "showState: calling state " + state.toString());
        switch (state) {
            case HasData:
                webView.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
                break;
            case NoData:
                webView.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                break;
            case Loading:
                webView.setVisibility(View.GONE);
                error.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
                break;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mainMenuCallback.showMainNavigation();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
