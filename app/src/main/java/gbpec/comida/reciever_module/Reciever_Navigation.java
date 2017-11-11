package gbpec.comida.reciever_module;

import gbpec.comida.R;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import gbpec.comida.SessionManager;
import gbpec.comida.SplashScreen;
import gbpec.comida.donor_module.Change_Password;
import gbpec.comida.donor_module.Donor_Profile;
import gbpec.comida.donor_module.Edit_Profilr;


public class Reciever_Navigation extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener, Reciever_Home.OnFragmentInteractionListener, Receiver_Profile.OnFragmentInteractionListener,Edit_Profilr_Ngo.OnFragmentInteractionListener,Change_Password_Ngo.OnFragmentInteractionListener{
    Fragment fragment = null;
    Class fragmentClass = null;
    SessionManager sessionManager;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever__navigation);
        sessionManager = new SessionManager(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentClass = Reciever_Home.class;
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
                startActivity(logout);
                Toast.makeText(getApplicationContext(), "Loging Out..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_home:
                Intent home= new Intent(this, SplashScreen.class);
                startActivity(home);
                break;
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
