package franklinvasquez.ufps.platzigram.viem;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import franklinvasquez.ufps.platzigram.R;
import franklinvasquez.ufps.platzigram.viem.fragment.HomeFragment;
import franklinvasquez.ufps.platzigram.viem.fragment.ProfileFragment;
import franklinvasquez.ufps.platzigram.viem.fragment.SearchFragment;

public class ContainerActivity extends AppCompatActivity {

    HomeFragment homeFragment;
    ProfileFragment profileFragment;
    SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        BottomBar bottombar = (BottomBar) findViewById(R.id.bottombar);
        bottombar.setDefaultTab(R.id.tab_home);
        bottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                        switch (tabId){
                            case R.id.tab_home:
                                HomeFragment homefragment = new HomeFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, homefragment)
                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                        .addToBackStack(null).commit();
                                break;
                            case R.id.tab_profile:
                                ProfileFragment profilefragment = new ProfileFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, profilefragment)
                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                        .addToBackStack(null).commit();
                                break;
                            case R.id.tab_search:
                                SearchFragment searchfragment = new SearchFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, searchfragment)
                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                        .addToBackStack(null).commit();
                                break;
                        }
            }
        });
    }
}
