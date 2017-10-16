package gbpec.comida;

/**
 * Created by Mohit Chauhan on 10/14/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.HashMap;

import gbpec.comida.donor_module.Donor_NavigationMainActivity;

public class SplashScreen extends Activity {

    // Splash screen timer
    SessionManager session;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        session = new SessionManager(getApplicationContext());

        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
            if(session.isLoggedIn()) {
                    HashMap<String, String> user = session.getUserDetails();
                    String username = user.get(SessionManager.KEY_NAME);

                    String type = user.get(SessionManager.KEY_ID);
                    if (type.equals("business")) {
                        Intent myIntent = new Intent(SplashScreen.this, Donor_NavigationMainActivity.class);
                        myIntent.putExtra("user", username);
                        startActivity(myIntent);
                       overridePendingTransition(R.anim.slide_in,R.anim.slide_out);


                    }
                }
                else {
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

              }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
