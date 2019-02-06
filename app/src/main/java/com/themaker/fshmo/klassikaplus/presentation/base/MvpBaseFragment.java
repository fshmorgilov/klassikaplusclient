package com.themaker.fshmo.klassikaplus.presentation.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.arellomobile.mvp.MvpView;
import com.themaker.fshmo.klassikaplus.R;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class MvpBaseFragment extends MvpAppCompatFragment implements BackButtonListener, MvpView {

    protected View rootView;
    protected int orientation;


    private Toast toast;
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected Unbinder unbinder;

    protected void addSub(Disposable subscription) {
        compositeDisposable.add(subscription);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null && !compositeDisposable.isDisposed())
            compositeDisposable.dispose();
    }

    public MvpBaseFragment() {
    }

    protected abstract int setLayoutRes();

    /**
     * Вызываеться после бинда вьюшек
     */
    protected void onPostCreateView() {
        // nothing
    }

    /**
     * Для настройки тулбара
     */
    @CallSuper
    protected void configureToolbar(@NonNull final Toolbar toolbar) {
        if (showBackButton()) {
            toolbar.setNavigationIcon(R.drawable.ic_sharp_arrow_back_24px);
            toolbar.setNavigationOnClickListener(view -> onBackPressed());
        } else {
            toolbar.setNavigationIcon(null);
            toolbar.setOnMenuItemClickListener(null);
        }
    }

    /**
     * Переопределить чтобы проказать кнопку назад в тулбаре
     */
    protected boolean showBackButton() {
        return false;
    }

    protected boolean orientationPortrait() {
        orientation = this.getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(setLayoutRes(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setupUI(rootView);
        onPostCreateView();
        return rootView;
    }


    protected void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                hideSoftKeyboard(getActivity());
                return false;
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    protected void hideSoftKeyboard(Activity activity) {
        if (activity == null) return;
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void showToast(String message) {
        if (toast != null) toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }

    protected void showToast(int message) {
        if (toast != null) toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }

    protected void showDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show();
    }

    protected void showDialog(Context context, String title, String message, DialogInterface.OnClickListener lis) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, lis)
                .create()
                .show();
    }


}