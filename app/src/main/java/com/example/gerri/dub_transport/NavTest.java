package com.example.gerri.dub_transport;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.gerri.dub_transport.Dub_Bus.DubBusHomeActivity;
import com.example.gerri.dub_transport.Luas.LuasHomeActivity;

public class NavTest extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_test);

        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar));
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        //selectItem(menuItem.getItemId());
                        Intent intent = null;
                        switch (menuItem.getItemId()) {
                            case R.id.home:
                                intent = new Intent(NavTest.this, MainActivity.class);
                                break;
                            case R.id.settings:
                                intent = new Intent(NavTest.this, LuasHomeActivity.class); //cant be getApplicationContext, as it crashes on my dad's lollipop S5 for some reason
                                break;
                            case R.id.logout:
                                intent = new Intent(NavTest.this, DubBusHomeActivity.class);
                                break;

                            default:
                                mDrawerLayout.closeDrawers();
                        }


                        startActivity(intent);
                        mDrawerLayout.closeDrawers();



                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectItem(int position) {
        Intent intent = null;
        switch(position) {
            case 0:
                intent = new Intent(this, MainActivity.class);
                break;
            case 1:
                intent = new Intent(this, LuasHomeActivity.class);
                break;

            case 2:
                intent = new Intent(this, DubBusHomeActivity.class);
                break;

            default :
                intent = new Intent(this, MainActivity.class); // Activity_0 as default
                break;
        }

        startActivity(intent);
    }
}