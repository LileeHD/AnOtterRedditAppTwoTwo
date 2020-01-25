package lilee.hd.anotterredditapptwo.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import lilee.hd.anotterredditapptwo.R;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    public static final String QUERY_EXTRA = "query";
    @BindView(R.id.searchBtn)
    Button searchBtn;
    private Context mContext = HomeActivity.this;
    private HomeFragment currentHomeFragment;
    private DetailFragment detailFragment;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new DetailFragment(), "detail")
                .replace(R.id.fragment_container, new HomeFragment(), "home")
                .commit();
        setSearchButton();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        currentHomeFragment = (HomeFragment) getSupportFragmentManager()
                .getFragment(outState, HomeFragment.TAG);
    }

    private void setSearchButton() {
        searchBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        });
    }

    private void switchFrag(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new HomeFragment(), "home")
                .addToBackStack("home")
                .commit();
        setSearchButton();
    }

    private void instantiateFragments(Bundle inState) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (inState != null) {
//            currentHomeFragment = (HomeFragment) manager.put;
        } else {
            currentHomeFragment = new HomeFragment();
//            transaction.add(R.id.fragment_container, homeFragment, HomeFragment.TAG);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
