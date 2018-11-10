package com.application.android.imageloader.ui.Feed;

import android.content.Context;

import com.application.android.imageloader.data.model.Feed;
import com.application.android.imageloader.util.mvp.IBasePresenter;
import com.application.android.imageloader.util.mvp.IBaseView;

import java.util.List;

public interface PhotosContract {

    interface View extends IBaseView {

        void showPhotos(List<Feed> photos);

        void shouldShowPlaceholderText();
    }

    interface Presenter extends IBasePresenter<View> {

        void getPhotos(Context context, int page);
    }

}
