package gbpec.comida.reciever_module;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gbpec.comida.R;
import gbpec.comida.SessionManager;
import gbpec.comida.Settings_activity;
import gbpec.comida.SplashScreen;

public class Reciever_Navigation extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener, Reciever_Home.OnFragmentInteractionListener, Receiver_Profile.OnFragmentInteractionListener,Edit_Profilr_Ngo.OnFragmentInteractionListener,Change_Password_Ngo.OnFragmentInteractionListener,Books.OnFragmentInteractionListener,Clothes.OnFragmentInteractionListener,Home.OnFragmentInteractionListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    Fragment fragment = null;
    Class fragmentClass = null;
    SessionManager sessionManager;
    Bundle bundle;
    String ngo_name="NGO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever__navigation);
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user1 = sessionManager.getUserDetails();
        ngo_name = user1.get(SessionManager.USER_NAME);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       fragmentClass = Home.class;
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        NetworkImageView nav_img = (NetworkImageView) hView.findViewById(R.id.user_image);
        TextView Name =(TextView)hView.findViewById(R.id.user_name);
        Name.setText(ngo_name);

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
    public boolean onNavigationItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_donor_profile:
                //Toast.makeText(getApplicationContext(), "Profile.", Toast.LENGTH_SHORT).show();
                fragmentClass = Receiver_Profile.class;


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
            case R.id.nav_logout: sessionManager.logoutUser();
                Intent logout= new Intent(this, SplashScreen.class);
                FirebaseMessaging.getInstance().unsubscribeFromTopic("reciever");
                startActivity(logout);
                Toast.makeText(getApplicationContext(), "Loging Out..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_home:
                Intent home= new Intent(this, SplashScreen.class);
                startActivity(home);
                break;
            case R.id.nav_setting:
                Intent setting= new Intent(this, Settings_activity.class);
                //  FirebaseMessaging.getInstance().unsubscribeFromTopic("donor");
                startActivity(setting);
         /*   case R.id.nav_setting:
                Intent setting= new Intent(this, Settings_activity.class);
                startActivity(setting);
                break; */
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
