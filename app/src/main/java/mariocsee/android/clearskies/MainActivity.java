package mariocsee.android.clearskies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.List;

import mariocsee.android.clearskies.adapter.CityRecyclerAdapter;
import mariocsee.android.clearskies.data.City;
import mariocsee.android.clearskies.fragments.AddCityFragment;
import mariocsee.android.clearskies.fragments.OnAddCityAnswer;
import mariocsee.android.clearskies.touch.CityTouchHelperCallback;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnAddCityAnswer {

    public static final String KEY_CITY = "KEY_CITY";
    public static final String CITY_NAME = "CITY_NAME";
    public static final int REQUEST_NEW_WEATHER = 101;
    private CityRecyclerAdapter cityRecyclerAdapter;
    private RecyclerView recyclerCity;
    private AutoCompleteTextView autocompleteCity;

    private CityRecyclerAdapter cityAdapter;

    public static final String KEY_MSG = "KEY_MSG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
    }

    private void setupUI() {
        setupToolbarDrawer();
        setupNavView();
        setupFloatingActionButton();
        setupRecyclerView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_add_city) {
            showAddCityDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            Toast.makeText(this, "Clear Skies was made by Mario See for AIT Mobile Software Development course", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_add_city) {
            showAddCityDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupToolbarDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupNavView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCityDialog();
            }
        });
    }

    private void setupRecyclerView() {
        recyclerCity = (RecyclerView) findViewById(
                R.id.recyclerCity);
        recyclerCity.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(this);
        recyclerCity.setLayoutManager(mLayoutManager);

        List<City> citiesList;
        citiesList = City.listAll(City.class);
        cityRecyclerAdapter = new CityRecyclerAdapter(citiesList, this);

        ItemTouchHelper.Callback callback = new CityTouchHelperCallback(cityRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerCity);

        recyclerCity.setAdapter(cityRecyclerAdapter);
    }

    private void showAddCityDialog() {
        AddCityFragment addCityFragment = new AddCityFragment();
        addCityFragment.setCancelable(false);

        Bundle bundle = new Bundle();
        bundle.putString(KEY_MSG,"You have opened the add city dialog");
        addCityFragment.setArguments(bundle);

        addCityFragment.show(getSupportFragmentManager(),
                "AddCityFragment");
    }

    public void showWeatherPagerActivity(String city) {
        Intent newIntent = new Intent(MainActivity.this, ViewWeatherActivity.class);
        newIntent.putExtra(CITY_NAME, city);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(newIntent);
    }

    @Override
    public void onPositiveSelected() {
            Toast.makeText(this, "New city added", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onNegativeSelected() {

    }

    public void saveCity(City city) {
        cityRecyclerAdapter.addCity(city);
    }

}
