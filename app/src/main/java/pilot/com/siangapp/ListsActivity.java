package pilot.com.siangapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ListsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.genre_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setPrompt("Select your favorite Planet!");
        final List<String> items = new ArrayList<String>();

        final ListAdapter Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        final ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(Adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            ListAdapter innerAdapter;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    items.clear();

                    adapter.notifyDataSetChanged();
                    listview.setAdapter(Adapter);
                } else if (position == 1) {
                    items.clear();
                    adapter.notifyDataSetChanged();
                    items.add("Football");
                    items.add("Cricket Bat and Ball");
                    items.add("Volleyball (x3)");
                    items.add("Hockey Stick (x4)");
                    items.add("Lawn Tennis Rackets (x4)");
                    items.add("Pool Table");
                    items.add("Table Tennis Bats and Ball");

                    adapter.notifyDataSetChanged();
                    listview.setAdapter(Adapter);

                } else if (position == 2) {

                    items.clear();
                    adapter.notifyDataSetChanged();

                    items.add("Keyboard+Stand+Charger");
                    items.add("AUX Cables (x5)");
                    items.add("Speakers (x2)");
                    items.add("Mixer");
                    items.add("Speaker Amps (x2)");
                    items.add("Guitar Amps (x2)");
                    items.add("Bass Guitar");
                    items.add("Table");
                    items.add("Flute");
                    items.add("Drum Set and DrumSticks");



                        adapter.notifyDataSetChanged();
                        listview.setAdapter(Adapter);




                } else if (position == 3) {

                    items.clear();
                    adapter.notifyDataSetChanged();
                    items.add("30A ESC");
                    items.add("Arduino Board");
                    items.add("Belt(Belt Drive)");
                    items.add("BO Motors (x2)");
                    items.add("Bosh Drill Set");
                    items.add("Brushless Motor(x2)");
                    items.add("Buzzer");
                    items.add("Caster Wheels (x2)");
                    items.add("DC Motor Holders");
                    items.add("DC Motors (x22)");
                    items.add("DPDT Switches (x11)");
                    items.add("Glue Gun (x2)");
                    items.add("Glue Sticks (x3)");
                    items.add("Hack Saw");
                    items.add("IR LEDs");
                    items.add("L298N Motor Driver (x3)");
                    items.add("Line Sensor (x3)");
                    items.add("Lipo Battery(2200mAh)");
                    items.add("Lipo Battery Charger");
                    items.add("Multimeter");
                    items.add("Nose Pliers (x2)");
                    items.add("Nut and Bolts(Big Size)");
                    items.add("Rack and Pinion (x2)");
                    items.add("Screw Driver");
                    items.add("Servo-9g (x3)");
                    items.add("Servo Big");
                    items.add("Small Screw Extenders");
                    items.add("Small Screws");
                    items.add("Small Moisture Sensor (x2)");
                    items.add("Solder (x2)");
                    items.add("Solder Paste");
                    items.add("Solder Wire");
                    items.add("Telescope");
                    items.add("Try Square");
                    items.add("Wheels (x10)");
                    items.add("Wires");

                    adapter.notifyDataSetChanged();
                    listview.setAdapter(Adapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_developers) {
            Intent i = new Intent(getApplicationContext(),DeveloperActivity.class);
            startActivity(i);finish();
            return true;
        }
        else if(id == R.id.action_account){
            Intent i = new Intent(getApplicationContext(),Account.class);
            startActivity(i);finish();
            return true;
        }
        else if(id==R.id.action_utility){
            Intent i = new Intent(getApplicationContext(),UtilityActivity.class);
            startActivity(i);finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_complaints) {

            Intent i = new Intent(getApplicationContext(), ComplaintActivity.class);
            startActivity(i);finish();

        } else if (id == R.id.nav_feedback) {
            Intent i = new Intent(getApplicationContext(), FeedbackActivity.class);
            startActivity(i);finish();

        } else if (id == R.id.nav_issue) {
            Intent i = new Intent(getApplicationContext(), BookActivity.class);
            startActivity(i);finish();

        } else if (id == R.id.nav_map) {
            Intent i = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(i);finish();

        } else if (id == R.id.nav_list) {
            Intent i = new Intent(getApplicationContext(), ListsActivity.class);
            startActivity(i);finish();

        } else if (id == R.id.nav_hmc) {
            Intent i = new Intent(getApplicationContext(), HmcActivity.class);
            startActivity(i);finish();

        } else if (id == R.id.nav_search) {
            Intent i = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(i);finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
