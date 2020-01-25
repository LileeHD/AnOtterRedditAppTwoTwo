package lilee.hd.anotterredditapptwo.adapter;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.card.MaterialCardView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lilee.hd.anotterredditapptwo.R;
import lilee.hd.anotterredditapptwo.model.Children;
import lilee.hd.anotterredditapptwo.util.GlideApp;
import lilee.hd.anotterredditapptwo.viewmodel.PostViewModel;

public class PostViewAdapter extends RecyclerView.Adapter<PostViewAdapter.PostViewHolder> {
//    https://gist.github.com/ZkHaider/9bf0e1d7b8a2736fd676

    public static final int DEFAULT_ADAPTER = 1;
    public static final int USER_ADAPTER = 2;
    private static final String TAG = "PostViewAdapter";
    private Context mContext;
    private Children post;
    private ArrayList<Children> posts;
    private PostClickListener mListener;
    private PostViewModel postViewModel;
    private int originalHeight = 0;
    private boolean mIsViewExpanded = false;


    public PostViewAdapter(Context context, ArrayList<Children> posts, PostClickListener listener) {
        this.mContext = context;
        this.posts = posts;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView rootView = (MaterialCardView) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.post_item, parent, false);
        postViewModel = new PostViewModel();
        return new PostViewHolder(rootView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        post = posts.get(position);
        holder.postTitle.setText(post.getData().getTitle());
        holder.rSubredditName.setText(post.getData().getSubredditR());
        holder.postAuthor.setText(post.getData().getAuthor());
        if (post.getData().getImageUrl() == null) {
            holder.postThumbnail.setVisibility(View.GONE);
        } else {
            imageLoader(holder);
        }
    }

    private void imageLoader(PostViewHolder holder) {
        RequestOptions defaultOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(null);
        GlideApp.with(mContext)
                .setDefaultRequestOptions(defaultOptions)
                .load(post.getData().getImageUrl())
                .into(holder.postThumbnail);
    }


    @Override
    public int getItemCount() {
        if (posts != null && posts.size() > 0) {
            return posts.size();
        } else {
            return 0;
        }
    }

    public interface PostClickListener {
        void onPostClick(PostViewModel postViewModel, int position);
    }

    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.post_cardview)
        MaterialCardView cardView;
        @BindView(R.id.post_bottom_bar)
        LinearLayout bottomBar;
        //        top bar
        @BindView(R.id.post_subreddit)
        TextView rSubredditName;
        @BindView(R.id.post_author)
        TextView postAuthor;
        @BindView(R.id.date_updated)
        TextView dateUpdate;
        //        body
        @BindView(R.id.post_image)
        ImageView postThumbnail;
        @BindView(R.id.post_title_view)
        TextView postTitle;
        PostClickListener listener;

        PostViewHolder(@NonNull View itemView, PostClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onPostClick(postViewModel, getAdapterPosition());

        }
    }
}
