package com.application.android.imageloader.ui.Feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.android.imageloader.R;
import com.application.android.imageloader.data.DataRepository;
import com.application.android.imageloader.data.model.Feed;
import com.application.android.imageloader.data.service.ApiClient;
import com.application.android.imageloader.data.service.ApiService;
import com.application.android.imageloader.ui.Injection;
import com.application.android.imageloader.util.EndlessRecyclerViewScrollListener;
import com.application.android.imageloader.util.mvp.BaseView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The {@link Fragment} that receives photo data from its {@link PhotosContract.Presenter} and
 * renders a list of photos and also handles user actions, such as clicks on photos,
 * and passes it to its {@link PhotosContract.Presenter}.
 */
public class PhotosFragment extends BaseView implements PhotosContract.View {

    public static final int STARTING_PAGE_INDEX = 1;
    @BindView(R.id.photosRecyclerView)
    RecyclerView photosRecyclerView;
    @BindView(R.id.noPhotosAvailablePlaceholder)
    TextView noPhotosAvailablePlaceholder;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private PhotosRecyclerAdapter recyclerAdapter;
    private List<Feed> photos;
    private EndlessRecyclerViewScrollListener endlessScrollListener;
    private PhotosContract.Presenter presenter;
    private boolean shouldRefreshPhotos;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photos = new ArrayList<>();
        if (getActivity() != null) {
            ApiService apiService = ApiClient.getRetrofitInstance(getActivity()).create(ApiService.class);
            DataRepository dataRepository = Injection.provideDataRepository(apiService);
            presenter = new PhotosPresenter(this, dataRepository);
            setRetainInstance(true);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerAdapter = new PhotosRecyclerAdapter(photos);
        photosRecyclerView.setAdapter(recyclerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        photosRecyclerView.setLayoutManager(linearLayoutManager);

        endlessScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager,
                STARTING_PAGE_INDEX) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getPhotos(page);
            }
        };

        photosRecyclerView.addOnScrollListener(endlessScrollListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPhotos();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary);

        getPhotos(STARTING_PAGE_INDEX);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showPhotos(List<Feed> photos) {
        if (shouldRefreshPhotos) {
            recyclerAdapter.clear();
            endlessScrollListener.resetState();
            shouldRefreshPhotos = false;
        }
        recyclerAdapter.addAll(photos);
    }

    @Override
    public void shouldShowPlaceholderText() {
        if (photos.isEmpty()) {
            noPhotosAvailablePlaceholder.setVisibility(View.VISIBLE);
        } else {
            noPhotosAvailablePlaceholder.setVisibility(View.GONE);
        }
    }

    private void getPhotos(int page) {
        presenter.getPhotos(getActivity(), page);
    }

    private void refreshPhotos() {
        shouldRefreshPhotos = true;
        getPhotos(STARTING_PAGE_INDEX);
    }

    @Override
    public void setProgressBar(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

}