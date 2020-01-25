package lilee.hd.anotterredditapptwo.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import lilee.hd.anotterredditapptwo.R;
import lilee.hd.anotterredditapptwo.adapter.PostViewAdapter;
import lilee.hd.anotterredditapptwo.model.Children;
import lilee.hd.anotterredditapptwo.model.Post;
import lilee.hd.anotterredditapptwo.viewmodel.PostViewModel;
import lilee.hd.anotterredditapptwo.viewmodel.SubredditViewModel;

public class SubredditFragment extends Fragment implements PostViewAdapter.PostClickListener {
    private static final String TAG = "SubredditFragment";
    @BindView(R.id.list_view_otter)
    RecyclerView postView;
    @BindView(R.id.swipe_layout_otter)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.connection_info_otter)
    TextView connectionInfo;
    @BindView(R.id.progressbar_otter)
    ProgressBar progressBar;

    private Snackbar snackbar;
    private PostViewAdapter adapter;
    private Post currentpost;
    private ArrayList<Children> postsList = new ArrayList<>();
    private SubredditViewModel viewModel;
    private boolean mIsRefreshing = false;
    private String sort = "new";

    
    public SubredditFragment() {
    }
    public static SubredditFragment newInstance() {
        return new SubredditFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otter_feed, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        viewModel = ViewModelProviders.of(getActivity()).get(SubredditViewModel.class);
//        initViewModel();
//        initPostView();
    }

    private void initViewModel() {
//            viewModel.initUserFeed();
//            viewModel.needHelp().observe(this, feed -> {
//                List<Children> childrenList = feed.getData().getChildren();
//                postsList.addAll(childrenList);
//                adapter.notifyDataSetChanged();
//                Log.d(TAG, "initViewModel: " + postsList.size());
//            });
    }

    private void initPostView() {
        if (adapter == null) {
            DividerItemDecoration dividerItemDecoration =
                    new DividerItemDecoration(Objects.requireNonNull(getContext()),
                    DividerItemDecoration.VERTICAL);
            adapter = new PostViewAdapter(getContext(), postsList, this);
            postView.addItemDecoration(dividerItemDecoration);
            postView.setItemAnimator(new DefaultItemAnimator());
            postView.setAdapter(adapter);
            postView.setLayoutManager(new LinearLayoutManager(getContext()));
            postView.setHasFixedSize(true);
            mSwipeRefreshLayout.setOnRefreshListener(() -> {
                adapter.notifyDataSetChanged();
            });
            Log.d(TAG, "initPostView: ");
        }
    }

    // Click
    @Override
    public void onPostClick(PostViewModel model, int position) {
        Children post = postsList.get(position);
        model.sendData(post.getData());
        swapFragment();
        Log.d(TAG, "onPostClick: " + position);
    }

    private void swapFragment() {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
                .replace(R.id.fragment_container, DetailFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

}
