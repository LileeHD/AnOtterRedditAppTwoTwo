package lilee.hd.anotterredditapptwo.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import lilee.hd.anotterredditapptwo.R;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.back_btn)
    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.search_container, new SubredditFragment())
                .replace(R.id.search_container, new SearchFragment())
                .commit();
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
    }


}
