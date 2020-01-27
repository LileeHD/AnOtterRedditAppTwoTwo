package lilee.hd.anotterredditapptwo.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import lilee.hd.anotterredditapptwo.R;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    public static final String QUERY_EXTRA = "query";
    @BindView(R.id.searchBtn)
    Button searchBtn;
    @BindView(R.id.adView)
    AdView mAdview;
    private Context mContext = HomeActivity.this;
    private Fragment currentFragment;
    private DetailFragment detailFragment;
    private String query;
//    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MobileAds.initialize(this, initializationStatus -> {
        });
        ButterKnife.bind(this);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        switchFrag();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (currentFragment instanceof DetailFragment){
            currentFragment= getSupportFragmentManager().getFragment(outState, DetailFragment.TAG);
        }
        if (currentFragment instanceof HomeFragment){
            currentFragment= getSupportFragmentManager().getFragment(outState, HomeFragment.TAG);
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSearchButton() {
        searchBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        });
    }

    private void switchFrag(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new DetailFragment(), "detail")
                .replace(R.id.fragment_container, new HomeFragment(), "home")
                .commit();
        setSearchButton();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
