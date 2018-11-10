package com.application.android.imageloader.ui.Feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.android.imageloader.R;
import com.application.android.imageloader.data.model.Feed;
import com.application.android.imageloader.download.ImageLoader;
import com.application.android.imageloader.util.cache.BitmapLruCache;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The {@link RecyclerView.Adapter} that renders and populates each photo
 * in the photos list.
 */
public class PhotosRecyclerAdapter extends RecyclerView.Adapter<PhotosRecyclerAdapter.ViewHolder> {

    private List<Feed> photos;

    PhotosRecyclerAdapter(List<Feed> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        Feed photo = photos.get(position);
        viewHolder.feedTitleTextView.setText(photo.getUser().getName());
        ImageLoader.getInstance(BitmapLruCache.getInstance()).
                downloadImage(photo.getUrl().getRegular(), viewHolder.feedImageView);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    void clear() {
        int size = getItemCount();
        photos.clear();
        notifyItemRangeRemoved(0, size);
    }

    void addAll(List<Feed> photos) {
        int prevSize = getItemCount();
        this.photos.addAll(photos);
        notifyItemRangeInserted(prevSize, photos.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.feedTitleTextView)
        TextView feedTitleTextView;

        @BindView(R.id.feedImageView)
        ImageView feedImageView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
