package lilee.hd.anotterredditapptwo.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import lilee.hd.anotterredditapptwo.viewmodel.SubredditRepository;
import lilee.hd.anotterredditapptwo.viewmodel.SubredditViewModel;
import lilee.hd.anotterredditapptwo.viewmodel.SubredditViewModelFactory;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class HomeFragment extends Fragment implements PostViewAdapter.PostClickListener {
    public static final String TAG = "HomeFragment";

    @BindView(R.id.home_list_view)
    RecyclerView postView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.connection_info)
    TextView connectionInfo;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    String mQuery = "";
    private PostViewAdapter adapter;
    private LiveData<Post> currentpost;
    private ArrayList<Children> postsList = new ArrayList<>();
    private PostViewModel mPostViewModel;
    private SubredditViewModel viewModel;
    private String mSearchResult;
    private boolean mIsRefreshing = false;
    private String sort = "new";
    private ArrayList<String> mNames = new ArrayList<>();

    private HomeFragment homeFragment;
    private DetailFragment detailFragment;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        checkConnection();
        refreshingUI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null && isAdded()){
            mPostViewModel = ViewModelProviders.of(getActivity()).get(PostViewModel.class);
        }
    }

    private void initViewModel() {
        mPostViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        mPostViewModel.initHome();
        mPostViewModel.getDefaultFeed().observe(this, feed -> {
            List<Children> childrenList = feed.getData().getChildren();
            postsList.addAll(childrenList);
            adapter.notifyDataSetChanged();
        });
    }

    private String getImplode(List<String> names) {
        mNames = (ArrayList<String>) names;
        mQuery = TextUtils.join("+", mNames);
        return mQuery;
    }

    private String initStringData() {
        SubredditRepository repository = SubredditRepository.setInstance(getContext());
        SubredditViewModelFactory factory = new SubredditViewModelFactory(repository);
        viewModel = ViewModelProviders.of(getActivity(), factory).get(SubredditViewModel.class);
        viewModel.getnames().observe(getActivity(), strings -> {
            mQuery = getImplode(strings);
        });
        Log.d(TAG, "initStringData: " + "\n" + "-----------" + mQuery);
        return mQuery;
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
        }
    }

    // Click
    @Override
    public void onPostClick(PostViewModel model, int position) {
        Children post = postsList.get(position);
        mPostViewModel.sendData(post.getData());
        swapFragment();
        Log.d(TAG, "onPostClick: " + post.getData().getTitle());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    private void swapFragment() {
        if (isAdded()){
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, DetailFragment.newInstance())
                    .addToBackStack("home")
                    .commit();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    //  Navigation
    private void refreshingUI() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
//            postsList.clear();
//            initViewModel();
//            initPostView();
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }


    private void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager != null ? manager.getActiveNetworkInfo() : null;
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            refreshingUI();
            if (mQuery.isEmpty()){
                initViewModel();
            }else {
                initStringData();
            }

            initPostView();
        } else {
            connectionInfo.setVisibility(View.VISIBLE);
            connectionInfo.setText(getText(R.string.no_connected));
            Toast.makeText(getContext(), "No connection", Toast.LENGTH_SHORT).show();
        }
    }


}
