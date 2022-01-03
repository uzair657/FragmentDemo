package com.uxi.fragmentsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.uxi.fragmentsdemo.Adapter.PagerAdapter;
import com.uxi.fragmentsdemo.Controller.LoginActivity;
import com.uxi.fragmentsdemo.Controller.ProfileActivity;
import com.uxi.fragmentsdemo.Controller.ThemeActivity;
import com.uxi.fragmentsdemo.Fragments.DashboardFragment;
import com.uxi.fragmentsdemo.Fragments.NewOrderFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.uxi.fragmentsdemo.KotlinTheme.ThemesKotlin;

public class HomeActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    //=================Drawer Variables=======================
    public static DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    //========================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUpToolBar();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Toast.makeText(HomeActivity.this, "Item = "+item.toString(), Toast.LENGTH_SHORT).show();
                switch (item.toString()){
                    case "Theme":
                        //Toast.makeText(HomeActivity.this, "Item = "+item, Toast.LENGTH_SHORT).show();
                        Intent theme = new Intent(HomeActivity.this, ThemeActivity.class);
                        startActivity(theme);
                        break;
                    case "Profile":
                        //Toast.makeText(HomeActivity.this, "Item = "+item, Toast.LENGTH_SHORT).show();
                        Intent profile = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(profile);
                        break;
                    case "Kotlin Themes":
                        Intent KotlinTheme = new Intent(HomeActivity.this, ThemesKotlin.class);
                        startActivity(KotlinTheme);
                        break;
                    case "Login":
                        Intent Login = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(Login);


                }

                drawerLayout.closeDrawers();
                return false;
            }
        });


        //===========================Add Fragments================================
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DashboardFragment());
        adapter.addFragment(new NewOrderFragment());

        viewPager = findViewById(R.id.viewPage);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);

        tabLayout =  findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Dashboard").setIcon(R.drawable.ic_dashboard);
        tabLayout.getTabAt(1).setText("New Order").setIcon(R.drawable.ic_dashboard);

    }
    private void setUpToolBar() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}