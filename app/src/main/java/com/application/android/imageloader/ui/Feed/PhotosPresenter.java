package com.application.android.imageloader.ui.Feed;

import android.content.Context;

import com.application.android.imageloader.R;
import com.application.android.imageloader.data.DataRepository;
import com.application.android.imageloader.data.model.Feed;
import com.application.android.imageloader.data.source.DataSource;
import com.application.android.imageloader.util.mvp.BasePresenter;

import java.util.List;

public class PhotosPresenter extends BasePresenter<PhotosContract.View> implements
        PhotosContract.Presenter {

    private DataRepository dataRepository;

    PhotosPresenter(PhotosContract.View view, DataRepository dataRepository) {
        this.view = view;
        this.dataRepository = dataRepository;
    }

    /**
     * load photos
     *
     * @param context FeedActivity context
     * @param page    used for pagination
     */
    @Override
    public void getPhotos(final Context context, int page) {
        if (view == null) {
            return;
        }

        view.setProgressBar(true);

        dataRepository.getPhotos(context, page, new DataSource.GetPhotosCallback() {
            @Override
            public void onSuccess(List<Feed> photos) {
                if (view != null) {
                    view.showPhotos(photos);
                    view.setProgressBar(false);
                    view.shouldShowPlaceholderText();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (view != null) {
                    view.setProgressBar(false);
                    view.showToastMessage(context.getString(R.string.error_msg));
                    view.shouldShowPlaceholderText();
                }
            }

            @Override
            public void onNetworkFailure() {
                if (view != null) {
                    view.setProgressBar(false);
                    view.showToastMessage(context.getString(R.string.network_failure_msg));
                    view.shouldShowPlaceholderText();
                }
            }
        });
    }
}
