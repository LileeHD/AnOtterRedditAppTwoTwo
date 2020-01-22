package lilee.hd.anotterredditapptwo.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import lilee.hd.anotterredditapptwo.detail.DetailFragment;
import lilee.hd.anotterredditapptwo.model.Children;
import lilee.hd.anotterredditapptwo.model.Post;
import lilee.hd.anotterredditapptwo.viewmodel.PostViewModel;
import lilee.hd.anotterredditapptwo.viewmodel.SubredditRepository;
import lilee.hd.anotterredditapptwo.viewmodel.SubredditViewModel;
import lilee.hd.anotterredditapptwo.viewmodel.SubredditViewModelFactory;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class HomeFragment extends Fragment implements PostViewAdapter.PostClickListener {
    private static final String TAG = "HomeFragment";
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.ic_back)
    ImageButton refreshBtn;
    @BindView(R.id.home_list_view)
    RecyclerView postView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.connection_info)
    TextView connectionInfo;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.search_frag)
    Button sendUsertoSearch;
    private PostViewAdapter adapter;
    private LiveData<Post> currentpost;
    private ArrayList<Children> postsList = new ArrayList<>();
    private PostViewModel mPostViewModel;
    private SubredditViewModel viewModel;
    private String mSearchResult;
    private boolean mIsRefreshing = false;
    private String sort = "new";
    private ArrayList<String> mNames = new ArrayList<>();
    String mQuery ="";


    public HomeFragment() {
    }
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initUserViewModel();
        checkConnection();
        refreshingUI();
        Log.d(TAG, "onCreateView: PHARAH");
        return view;
    }

    private String getImplode(List<String> names){
        mNames= (ArrayList<String>) names;
        mQuery = TextUtils.join("+", mNames);
        Log.d(TAG, "getImplode: " + mQuery);
        return mQuery;
    }

//    private void initViewModel() {
//        mPostViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
//        mPostViewModel.initHome();
//        mPostViewModel.getFeedRepository().observe(this, feed -> {
//            List<Children> childrenList = feed.getData().getChildren();
//            postsList.addAll(childrenList);
//            adapter.notifyDataSetChanged();
//            Log.d(TAG, "initViewModel: "+ postsList.size());
//        });
//    }

    private void initUserViewModel() {
        SubredditRepository repository = SubredditRepository.setInstance(getContext());
        SubredditViewModelFactory factory = new SubredditViewModelFactory(repository);
        viewModel = ViewModelProviders.of(getActivity(), factory).get(SubredditViewModel.class);
        viewModel.getnames().observe(getActivity(), strings -> {
            mQuery = getImplode(strings);
            Log.d(TAG, "onViewCreated: " + mQuery);
        });
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
            Log.d(TAG, "initPostView: ");
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

    private void swapFragment() {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, DetailFragment.newInstance())
                .addToBackStack("detail")
                .commit();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    //  Navigation
    private void refreshingUI() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            postsList.clear();

            initPostView();
            mSwipeRefreshLayout.setRefreshing(false);
        });
        refreshBtn.setOnClickListener(v -> {
            postsList.clear();
            postView.scrollToPosition(1);

            initPostView();
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                mSwipeRefreshLayout.setRefreshing(true);

                initPostView();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager != null ? manager.getActiveNetworkInfo() : null;
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {

            initPostView();
        } else {
            connectionInfo.setVisibility(View.VISIBLE);
            connectionInfo.setText(getText(R.string.no_connected));
            mSwipeRefreshLayout.setVisibility(View.GONE);
        }
    }

}
