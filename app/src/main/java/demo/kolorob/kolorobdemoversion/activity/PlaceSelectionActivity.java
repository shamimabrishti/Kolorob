package demo.kolorob.kolorobdemoversion.activity;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import demo.kolorob.kolorobdemoversion.R;
import demo.kolorob.kolorobdemoversion.interfaces.VolleyApiCallback;
import demo.kolorob.kolorobdemoversion.utils.AlertMessage;
import demo.kolorob.kolorobdemoversion.utils.AppConstants;
import demo.kolorob.kolorobdemoversion.utils.SharedPreferencesHelper;

import static demo.kolorob.kolorobdemoversion.parser.VolleyApiParser.getRequest;


public class PlaceSelectionActivity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {
    ImageButton img;
    Toolbar toolbar;
    private String comment = "";
    String usernames = "kolorobapp";
    String password = "2Jm!4jFe3WgBZKEN";
    NotificationManager manager;
    private Notification myNotication;
    Boolean doubleBackToExitPressedOnce;
    Toast t = null;
    Intent i;
    Float  ratings;
    Boolean click=false;
    float[][] mirpur10Coords = {
            {42, 267},
            {80, 420},
            {109, 412},
            {121, 431},
            {440, 363},
            {439, 282},
            {439, 278},
            {427, 237},
            {419, 224},
            {410, 214},
            {401, 212},
            {391, 208},
            {377, 197},
            {370, 195},
            {343, 201},
            {314, 204},
            {313, 220},
            {312, 222},
            {297, 223},
            {42, 267}
    };
    float[][] mirpur11Coords = {
            {110, 412},
            {120, 433},
            {269, 405},
            {275, 416},
            {274, 424},
            {283, 444},
            {292, 443},
            {294, 465},
            {287, 486},
            {260, 483},
            {267, 502},
            {296, 519},
            {235, 625},
            {222, 619},
            {201, 630},
            {168, 636},
            {144, 637},
            {129, 641},
            {81, 639},
            {66, 624},
            {60, 596},
            {60, 593},
            {119, 575},
            {80, 421}
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     *  ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_selection_activity);


        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Get Display Metrics
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        final int height = metrics.heightPixels;
        final int width = metrics.widthPixels;

        //   int relativeWidthOfImage = (int)(width * 0.1);
        final int coordsHeight = 800;
        final int coordsWidth = 480;
        final String comment = "";
        String app_ver = "";
        NotificationManager manager;



        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        FrameLayout holder = (FrameLayout) findViewById(R.id.holder);
        holder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                float x = event.getX();
                float y = event.getY();
                // Hack to deal with issue in original image source
                y = y - 37;
                x = x * ((float) coordsWidth / (float) width);
                y = y * ((float) coordsHeight / (float) height);
                boolean mirpur10Hit = isPointInPolygon(x, y, mirpur10Coords);
                boolean mirpur11Hit = isPointInPolygon(x, y, mirpur11Coords);
                boolean anyHit = false;
                if (t != null)
                    t.cancel();
//                if (y < ((float)height) / 2.0) {
                if (mirpur10Hit) {
                    if(click==false)
                    {
                        Intent intent = new Intent(PlaceSelectionActivity.this, PlaceDetailsActivityNewLayout.class);
                        intent.putExtra(AppConstants.KEY_PLACE, 1);
                        startActivity(intent);
                        click=true;
                    }

                    Log.d("BAUNIABHAD", "********" );
                    t = Toast.makeText(getApplicationContext(), "প্যারিস রোড", Toast.LENGTH_SHORT);
                    anyHit = true;
                }
                else if (mirpur11Hit) {

                    if(click==false)
                    {
                        Intent intent = new Intent(PlaceSelectionActivity.this, PlaceDetailsActivityNewLayout.class);
                        intent.putExtra(AppConstants.KEY_PLACE, 2);
                        startActivity(intent);
                        click=true;
                    }

                    Log.d("PARIS ROAD", "********" );
                    t = Toast.makeText(getApplicationContext(), "বাউনিয়াবাদ", Toast.LENGTH_SHORT);
                    anyHit = true;

                }
                if (anyHit)
                    t.show();
                return true;
            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        else
//           toolbar = (Toolbar) findViewById(R.id.toolbars);

        // toolbar.setBackgroundResource(android.R.color.transparent);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.menu_icon);
        ab.setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //  getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(toggle);
        //toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        try {
            app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            SharedPreferencesHelper.setVersion(PlaceSelectionActivity.this,app_ver);
        } catch (PackageManager.NameNotFoundException e) {
            // Log.e(tag, e.getMessage());


        }

        checkVersion(Double.parseDouble(app_ver));


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    public void checkVersion(final double current_version) {
        getRequest(PlaceSelectionActivity.this, "http://kolorob.net/app_version.json", new VolleyApiCallback() {
                    @Override
                    public void onResponse(int status, String apiContent) {
                        Log.d(">>>", "Start Json Parsing " + apiContent);
                        try {
                            JSONObject jo = new JSONObject(apiContent);
                            Log.d(">>>", "JsonObject: " + current_version);
                            Double remote_version = jo.getDouble("version");

                            if (remote_version > current_version) {
                                Toast.makeText(PlaceSelectionActivity.this, "কলরবের নতুন ভার্সন পাওয়া যাচ্ছে",
                                        Toast.LENGTH_LONG).show();
                                generateNotification();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
    }


    public void generateNotification() {
        String url = "https://play.google.com/store/apps/details?id=demo.kolorob.kolorobdemoversion&hl=en";


        i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));



      //  startActivity(i);
        // i.setData(Uri.parse("package:demo.kolorob.kolorobdemoversion"));

        //Intent intent = new Intent(this,NotificationView.class);

        manager.cancel(11);
        PendingIntent pendingIntent = PendingIntent.getActivity(PlaceSelectionActivity.this, 0, i, 0);

        Notification.Builder builder = new Notification.Builder(PlaceSelectionActivity.this);

        builder.setAutoCancel(true);
        builder.setTicker("কলরবের নতুন ভার্সন পাওয়া যাচ্ছে");
        builder.setContentTitle("কলরব ভার্সন");
        //  builder.setContentText("To update click here.");
        builder.setSmallIcon(R.drawable.kolorob_logo_first_page);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        //  builder.setSubText("Click here to update");   //API level 16
        builder.setNumber(100);
        //   builder.build();

        builder.setContentTitle("কলরব আপডেট করুন").setContentText("কলরবের নতুন ভার্সন পাওয়া যাচ্ছে")
                .setSmallIcon(R.drawable.notify_logo_1).getNotification();

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);
    }

    @Override
    public void onBackPressed() {

        help();

//        if (doubleBackToExitPressedOnce) {
//            new AlertDialog.Builder(this)
//                    .setTitle("Close")
//                    .setMessage("Are you sure you want to close Kolorob")
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                    {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                        }
//
//                    })
//                    .setNegativeButton("No", null)
//                    .show();
//        }
//searchmain.setVisibility(View.GONE);
//        placemain.setVisibility(View.VISIBLE);
//        this.doubleBackToExitPressedOnce = true;
//
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
    }

    public void help() {
        LayoutInflater layoutInflater = LayoutInflater.from(PlaceSelectionActivity.this);
        View promptView = layoutInflater.inflate(R.layout.app_feedback_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlaceSelectionActivity.this);
        final AlertDialog alert = alertDialogBuilder.create();

        alertDialogBuilder.setView(promptView);
        final RatingBar ratingBar = (RatingBar) promptView.findViewById(R.id.ratingBar);
        final EditText submit_btn = (EditText) promptView.findViewById(R.id.submit_btn);
        final Button btnSubmit = (Button) promptView.findViewById(R.id.btnSubmit);
        final Button btnclose = (Button) promptView.findViewById(R.id.btnclose);

//       final EditText userfeedback = (EditText) promptView.findViewById(R.id.edittext);
//        final Button submit= (Button)promptView.findViewById(R.id.submit_btn);
//        final Button button= (Button)promptView.findViewById(R.id.phone_call);
//        final ImageView imageView7= (ImageView)promptView.findViewById(R.id.imageView7);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = SharedPreferencesHelper.getUser(PlaceSelectionActivity.this);
                String testUser = SharedPreferencesHelper.getFeedback(PlaceSelectionActivity.this);


                ratings = ratingBar.getRating();

                sendDataToserver(ratings, comment);
                alert.cancel();
                finish();
                //   back();


            }

        });
//        prebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                PlaceDetailsActivityNewLayout.this.onBackPressed();
//
//            }
//        });

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // setup a dialog window
        alertDialogBuilder.setCancelable(false);

        // create an alert dialog
        AlertDialog alerts = alertDialogBuilder.create();
        alerts.show();
    }


    public boolean isPointInPolygon(float x, float y, float[][] coords) {


        int j = coords.length - 1;
        boolean oddNodes = false;

        for (int i = 0; i < coords.length; i++) {
            if ((coords[i][1] < y && coords[j][1] >= y) || (coords[j][1] < y && coords[i][1] >= y)) {
                if (coords[i][0] +
                        (y - coords[i][1]) / (coords[j][1] - coords[i][1]) * (coords[j][0] - coords[i][0]) < x) {
                    oddNodes = !oddNodes;
                }
            }
            j = i;
        }

        return oddNodes;
    }


    public void sendDataToserver(Float rating, String comment) {
        String username = SharedPreferencesHelper.getUser(PlaceSelectionActivity.this);
        SharedPreferencesHelper.setFeedback(PlaceSelectionActivity.this, username);
        String phone = SharedPreferencesHelper.getNumber(PlaceSelectionActivity.this);


        if (phone.equals("")) {
            AlertMessage.showMessage(PlaceSelectionActivity.this, "ফোন নম্বরটি নিবন্ধন করা হয়নি",
                    "অনুগ্রহ পূর্বক ফোন নম্বরটি নিবন্ধন করুন");
        } else {
            String url = "http://kolorob.net/demo/api/app_rating?phone=" + phone + "&review=" + comment + "&rating=" + rating + "&username=" + this.usernames + "&password=" + this.password;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(PlaceSelectionActivity.this, response, Toast.LENGTH_SHORT).show();

                            try {

                                Toast.makeText(PlaceSelectionActivity.this, response.toString(), Toast.LENGTH_LONG).show();

//                                if(response.toString().trim().equalsIgnoreCase("true"))
//                                {
//
//                                    AlertMessage.showMessage(PlaceSelectionActivity.this, "মন্তব্যটি পাঠানো হয়ছে",
//                                            "মন্তব্য করার জন্য আপনাকে ধন্যবাদ");
//                                }
//                                else
//                                    AlertMessage.showMessage(PlaceSelectionActivity.this, "মন্তব্য পাঠানো সফল হয়নি",
//                                            "মন্তব্য করার জন্য আপনাকে ধন্যবাদ");


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PlaceSelectionActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();

                    return params;
                }

            };

// Adding request to request queue

            RequestQueue requestQueue = Volley.newRequestQueue(PlaceSelectionActivity.this);
            requestQueue.add(stringRequest);


        }


    }




    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.phone_reg) {
            // Handle the camera action
            Intent em = new Intent(this, PhoneRegActivity.class);
            startActivity(em);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        } else if (id == R.id.emergency_info) {

            //  Toast.makeText(con,"emergency",Toast.LENGTH_LONG).show();
            Intent em = new Intent(this, NewEmergency.class);
            startActivity(em);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
//        else if (id == R.id.info_change) {
//
//            Intent em = new Intent(this, Information_UpdateActivity.class);
//            startActivity(em);
//            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//
//        }
//         else if (id == R.id.local_representative) {
//
//           // Toast.makeText(con,"It will be added in next version.",Toast.LENGTH_LONG).show();
//            AlertMessage.showMessage(con, "Representative", "It will be added in next version.");
//
//        } else if (id == R.id.adv_info) {
//          //  Toast.makeText(con,"It will be added in next version.",Toast.LENGTH_LONG).show();
//
//            AlertMessage.showMessage(con,"Advertisement","It will be added in next version.");
//        } else if (id == R.id.adv) {
//            AlertMessage.showMessage(con,"Ads Information","It will be added in next version.");
//        }

//        else if (id == R.id.nav_share) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    public void back() {

        new AlertDialog.Builder(this)
                .setTitle("বন্ধ করুন")
                .setMessage("আপনি কি কলরব বন্ধ করতে চান?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();

        this.doubleBackToExitPressedOnce = true;


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onClick(View view) {


    }

    @Override
    protected void onResume() {
        click=false;
        super.onResume();
    }
}