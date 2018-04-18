package gbpec.comida.donor_module;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import gbpec.comida.R;
import gbpec.comida.SessionManager;
import gbpec.comida.Settings_activity;
import gbpec.comida.SplashScreen;

public class Donor_NavigationMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Donor_Profile.OnFragmentInteractionListener,Edit_Profilr.OnFragmentInteractionListener,Change_Password.OnFragmentInteractionListener,History_Module.OnFragmentInteractionListener{
    DrawerLayout drawer;
    Fragment fragment = null;
    Class fragmentClass = null;
    SessionManager sessionManager;
    String user;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor__navigation_main);
        sessionManager = new SessionManager(getApplicationContext());


        //TextView t=(TextView)findViewById(R.id.name);
        //t.setText(user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //   bundle = new Bundle();
      //  bundle.putString("username", user);
      ;
       fragmentClass = Donor_Home_Activity.class;
        try
        {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        HashMap<String, String> user = sessionManager.getUserDetails();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        NetworkImageView nav_img = (NetworkImageView) hView.findViewById(R.id.user_image);
        TextView Name =(TextView)hView.findViewById(R.id.user_name);
        Name.setText(user.get(sessionManager.USER_NAME));
        String url1= user.get(sessionManager.USER_IMG);
        /*if(url1.equals(null)) {
            ImageLoader imageLoader = CustomVolleyRequest.getInstance(getApplicationContext())
                    .getImageLoader();
            imageLoader.get(url1, ImageLoader.getImageListener(nav_img,
                    R.drawable.logo, android.R.drawable
                            .ic_dialog_alert));
            nav_img.setImageUrl(url1, imageLoader);
        }*///
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.donor__navigation_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
         switch (item.getItemId()){
             //PROFILE
             case R.id.nav_donor_profile:
                 //Toast.makeText(getApplicationContext(), "Profile.", Toast.LENGTH_SHORT).show();
                 fragmentClass = Donor_Profile.class;


                 try {
                     fragment = (Fragment) fragmentClass.newInstance();
                     fragment.setArguments(bundle);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                 // fragment.setArguments(bundle);
                 FragmentManager fragmentManager = getSupportFragmentManager();
                 fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                 break;
             case R.id.nav_logout:
                 sessionManager.logoutUser();
                 Intent logout= new Intent(this, SplashScreen.class);
                 FirebaseMessaging.getInstance().unsubscribeFromTopic("donor");
                 startActivity(logout);
                 Toast.makeText(getApplicationContext(), "Loging Out..", Toast.LENGTH_SHORT).show();
                 break;
             case R.id.nav_setting:
                 Intent setting= new Intent(this, Settings_activity.class);
               //  FirebaseMessaging.getInstance().unsubscribeFromTopic("donor");
                 startActivity(setting);

             case R.id.nav_home:
                 fragmentClass = Donor_Home_Activity.class;
                 try
                 {
                     fragment = (Fragment) fragmentClass.newInstance();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }


                 FragmentManager fragmentManagerhome = getSupportFragmentManager();
                 fragmentManagerhome.beginTransaction().replace(R.id.flContent, fragment).commit();
                 break;
             case R.id.nav_Donation_history:
                                  fragmentClass = History_Module.class;


                                 try {
                                      fragment = (Fragment) fragmentClass.newInstance();
                                      fragment.setArguments(bundle);
                                  } catch (Exception e) {
                                      e.printStackTrace();
                                  }
                                  // fragment.setArguments(bundle);
                                         fragmentManager = getSupportFragmentManager();
                                  fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                                  break;
         }
          //if(item.equals("Profile")){

          //}


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



  /*  @Override
    public void onFragmentInteraction(Uri uri) {

    } */
}
