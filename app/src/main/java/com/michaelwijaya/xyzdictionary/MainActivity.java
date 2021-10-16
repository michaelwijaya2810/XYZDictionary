package com.michaelwijaya.xyzdictionary;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container, ExploreFragment.class, null)
                .commit();

        TabLayout tabLayout = findViewById(R.id.tabs_layout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, ExploreFragment.class, null)
                            .setReorderingAllowed(true)
                            .commit();
                }else if(tab.getPosition() == 1){
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, FavoriteFragment.class, null)
                            .setReorderingAllowed(true)
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}