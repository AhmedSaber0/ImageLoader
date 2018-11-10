package com.application.android.imageloader.util.mvp;


public interface IBasePresenter<ViewT> {

    void onViewActive(ViewT view);

    void onViewInactive();
}
