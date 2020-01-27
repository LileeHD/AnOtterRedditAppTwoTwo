package lilee.hd.anotterredditapptwo.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import lilee.hd.anotterredditapptwo.R;
import lilee.hd.anotterredditapptwo.adapter.PostViewAdapter;
import lilee.hd.anotterredditapptwo.model.Post;
import lilee.hd.anotterredditapptwo.util.GlideApp;
import lilee.hd.anotterredditapptwo.viewmodel.PostViewModel;
import lilee.hd.anotterredditapptwo.viewmodel.SubredditViewModel;

public class DetailFragment extends Fragment {
    public static final String TAG = "DetailFragment";
    @BindView(R.id.post_subreddit_detail)
    TextView subNameView;
    @BindView(R.id.post_author_detail)
    TextView authorView;
    @BindView(R.id.post_thumbnail_detail)
    ImageView postImg;
    @BindView(R.id.post_title_view_detail)
    TextView titleView;
    @BindView(R.id.post_text_detail)
    TextView postBodyView;
    private SubredditViewModel viewModel;
    private PostViewModel mPostViewModel;
    private PostViewAdapter adapter;
    private Post mPost;
    private String subreddit;
    private String author;
    private String imgUrl;
    private String title;
    private String body;

    public DetailFragment() {
    }

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);
        ButterKnife.bind(this, view);
        setRetainInstance(true);
        bindViews();
        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    private Post getPrefs(Post post) {
        mPost = post;
        Context context = getActivity();
        if (context != null) {
            SharedPreferences prefs;
            prefs = context.getSharedPreferences("PREF", Context.MODE_PRIVATE);
            subreddit= prefs.getString("subreddit",mPost.getSubredditR());
            author= prefs.getString("author", mPost.getAuthor());
            imgUrl = prefs.getString("image", mPost.getImageUrl());
            title = prefs.getString("title", mPost.getTitle());
            body= prefs.getString("body", mPost.getBody());
        }
        Log.d(TAG, "getPrefs: "+ subreddit);
       return mPost;
    }

    private void bindViews() {
        Post post = new Post();
        mPost= getPrefs(post);
        if (mPost != null && isAdded()) {
            subNameView.setText(subreddit);
            authorView.setText(author);
            titleView.setText(title);
            postBodyView.setText(body);

            if (imgUrl.isEmpty()) {
                postImg.setImageResource(R.drawable.theotter);
            } else {
                RequestOptions defaultOptions = new RequestOptions()
                        .error(null);
                try {
                    GlideApp.with(requireActivity())
                            .setDefaultRequestOptions(defaultOptions)
                            .load(imgUrl)
                            .into(postImg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (body==null) {
                postBodyView.setVisibility(View.GONE);
            } else {
                postBodyView.setText(body);
            }
            Log.d(TAG, "bindViews: " + imgUrl);
        }

    }

}
