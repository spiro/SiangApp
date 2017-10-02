package pilot.com.siangapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;

import pilot.com.siangapp.TouchImageView.TouchImageView;

import static android.R.attr.src;
import static pilot.com.siangapp.R.drawable.map_mi;
import static pilot.com.siangapp.R.id.home;
import static pilot.com.siangapp.R.id.imageView2;
import static pilot.com.siangapp.R.menu.main;


public class MapActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{


    ImageView img;
    //ScaleGestureDetector scaleGDetector;

   // float scale=1f;
    //Matrix matrix = new Matrix();
    //Float scale = 1f;
    //ScaleGestureDetector SGD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        img = (ImageView) findViewById(R.id.imageView2);

      //  SGD = new ScaleGestureDetector(this, new ScaleListener());
        //TouchImageView view = (TouchImageView) findViewById(R.id.imageView2);
        //view.setScaleType(TouchImageView.ScaleType.FIT_CENTER);
        //view.setOnTouchListener((View.OnTouchListener) this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);



       // img = (TouchImageView) findViewById(R.id.imageView2);
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
        getMenuInflater().inflate(main, menu);
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
