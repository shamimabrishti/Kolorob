package demo.kolorob.kolorobdemoversion.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import demo.kolorob.kolorobdemoversion.BuildConfig;
import demo.kolorob.kolorobdemoversion.R;
import demo.kolorob.kolorobdemoversion.database.AreaTable;
import demo.kolorob.kolorobdemoversion.database.CityCorporationTable;
import demo.kolorob.kolorobdemoversion.database.DistrictTable;
import demo.kolorob.kolorobdemoversion.database.StoredAreaTable;
import demo.kolorob.kolorobdemoversion.database.WardTable;
import demo.kolorob.kolorobdemoversion.interfaces.VolleyApiCallback;
import demo.kolorob.kolorobdemoversion.model.Area;
import demo.kolorob.kolorobdemoversion.model.CityCorporation;
import demo.kolorob.kolorobdemoversion.model.District;
import demo.kolorob.kolorobdemoversion.model.Ward;
import demo.kolorob.kolorobdemoversion.utils.AppConstants;
import demo.kolorob.kolorobdemoversion.utils.AppUtils;
import demo.kolorob.kolorobdemoversion.utils.SharedPreferencesHelper;
import demo.kolorob.kolorobdemoversion.utils.ToastMessageDisplay;

import static demo.kolorob.kolorobdemoversion.parser.VolleyApiParser.getRequest;


public class SplashActivityNew extends AppCompatActivity {

    Context context;
    final private int NUMBER_OF_TASKS = 4;
    private Integer counter = new Integer(0);

    RelativeLayout dataload;
    String app_ver = "";
    long install = 0, install2 = 0;
    File filesDir;
    public final static int PERM_REQUEST_CODE_DRAW_OVERLAYS = 1234;
    boolean firstRun = false, firstRunUpdate = false;
    public int height, width;
    boolean registered = false;
    JSONObject areaData;

    public void setCounter(int counter){
        this.counter = counter;
    }

    public Integer getCounter(){
        return counter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashnew);

        context = this;

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        dataload = (RelativeLayout) findViewById(R.id.splash);
        SharedPreferences settings = getSharedPreferences("prefs", 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Snackbar snackbar = Snackbar.make(dataload, "Please allow all the permission so that app works smoothly", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        String state = Environment.getExternalStorageState();
        registered = settings.getBoolean("IFREGISTERED", false);

        // Make sure it's available
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            filesDir = getExternalFilesDir(null);
        } else {
            // Load another directory, probably local memory
            filesDir = getFilesDir();
        }


        try {
            app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            SharedPreferencesHelper.setVersion(SplashActivityNew.this, app_ver);
        }
        catch (PackageManager.NameNotFoundException e) {
            Log.e("Exception message", e.getMessage());
        }


        try /*
        to fix issue with backward date device time in some version*/ {

            app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;


            Float currentVersion = Float.parseFloat(app_ver);
            Float previousVersion = Float.parseFloat(SharedPreferencesHelper.getVersion(SplashActivityNew.this));
            //Float previousVersion=Float.parseFloat("2.03");
            //Toast.makeText(SplashActivityNew.this, "previous: " + previousVersion +" Current: " + currentVersion, Toast.LENGTH_LONG).show();

            firstRun = settings.getBoolean("firstRunUp", false);
            if (isInstallFromUpdate() && currentVersion >= Float.parseFloat("2.22")) {
                firstRunUpdate = settings.getBoolean("update_first_run", true);
            }

            Log.e("", "First run: " + firstRun + " First run update: " + firstRunUpdate);

            //Toast.makeText(SplashActivityNew.this, "firstRun: " + (firstRun==false) + "first run update: " + firstRunUpdate + "firstInstall: " + isFirstInstall() + "fromUpdate: " + isInstallFromUpdate(), Toast.LENGTH_LONG).show();


            if (!firstRun || firstRunUpdate) {
                Log.e("", "First run if: " + firstRun + " First run update: " + firstRunUpdate);
                getRequest(SplashActivityNew.this, "http://kolorob.net/kolorob-new-live/api/getAreaList?", new VolleyApiCallback() {
                    @Override
                    public void onResponse(int status, String apiContent) {


                        if (status == AppConstants.SUCCESS_CODE) {

                            try {

                                areaData = new JSONObject(apiContent);
                                Log.d("Data", "********" + areaData);

                                if (areaData.length() == 0) {
                                    ToastMessageDisplay.setText(SplashActivityNew.this, getString(R.string.try_later));
                                    ToastMessageDisplay.showText(context);

                                } else {

                                    if(areaData.has(AppConstants.DISTRICT_API)){
                                        new SaveDistrictTask(SplashActivityNew.this).execute(areaData.getJSONArray(AppConstants.DISTRICT_API));
                                    }

                                    if (areaData.has(AppConstants.CC_API)) {
                                        new SaveCityCorporationTask(SplashActivityNew.this).execute(areaData.getJSONArray(AppConstants.CC_API));
                                    }
                                    if (areaData.has(AppConstants.WARD_API)) {
                                        new SaveWardTask(SplashActivityNew.this).execute(areaData.getJSONArray(AppConstants.WARD_API));
                                    }
                                    if (areaData.has(AppConstants.AREA_API)) {
                                        new SaveAreaTask(SplashActivityNew.this).execute(areaData.getJSONArray(AppConstants.AREA_API));
                                }

                                    Log.e("counter: ", "Splash: " + counter);

                                    /*if (counter < NUMBER_OF_TASKS) {
                                        ToastMessageDisplay.setText(SplashActivityNew.this, getString(R.string.try_later));
                                        ToastMessageDisplay.showText(context);
                                    }*/
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });


            }

            else{
                Log.e("", "First run else: " + firstRun + " First run update: " + firstRunUpdate);
            }
            if (currentVersion > previousVersion) {
                //SharedPreferences.Editor editor = settings.edit();

                if (previousVersion.floatValue() < Float.parseFloat("2.03")) {
                    long check = settings.getLong("timefirstinstall", Long.valueOf(2));
                    if (check != 2) {
                        settings.edit().putString("timesfirstinstall", String.valueOf(check)).apply();
                    } else {
                        install2 = System.currentTimeMillis();
                        settings.edit().putString("timesfirstinstall", String.valueOf(install)).apply();
                    }
                } else if (previousVersion.floatValue() == Float.parseFloat("2.03")) {
                    String check2 = settings.getString("timefirstinstall", "2");
                    if (!check2.equals("2")) {
                        settings.edit().putString("timesfirstinstall", check2).apply();
                    }
                }
                /*if (firstRun == false) {
                    editor.putBoolean("new_categories_on_update", true).apply();
                    editor.putBoolean("new_areas_on_update", true).apply();
                }*/
            }
        } catch (Exception e) {
            Log.e("Exception message", e.getMessage());
        }


        if (!firstRun || firstRunUpdate)//if running for first time
        {
            //SharedPreferences.Editor editor = settings.edit();


            if (!AppUtils.isNetConnected(getApplicationContext())) {


                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View promptView = layoutInflater.inflate(R.layout.default_alert, null);


                final Dialog alertDialog = new Dialog(this);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setContentView(promptView);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();


                final TextView header = (TextView) promptView.findViewById(R.id.headers);
                final TextView bodys = (TextView) promptView.findViewById(R.id.body);
                final ImageView okay = (ImageView) promptView.findViewById(R.id.okay);

                header.setText(R.string.no_internet);
                header.setTextColor(getResources().getColor(R.color.Black));

                if (!firstRun)
                    bodys.setText(R.string.first_run_no_internet);
                else if (firstRunUpdate)
                    bodys.setText(R.string.update_no_internet);

                bodys.setTextColor(getResources().getColor(R.color.Black));
                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                        SharedPreferences settings = getSharedPreferences("prefs", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("firstRunUp", false);
                        if (isInstallFromUpdate()) {
                            editor.putBoolean("update_first_run", true);
                        }
                        editor.apply();
                        finish();
                    }
                });

                alertDialog.setCancelable(false);

                WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
                lp.dimAmount = 0.0f; // Dim level. 0.0 - no dim, 1.0 - completely opaque
                alertDialog.getWindow().setAttributes(lp);

                alertDialog.getWindow().setLayout((width * 5) / 6, WindowManager.LayoutParams.WRAP_CONTENT);

            }

            else {
                install = System.currentTimeMillis();
                settings.edit().putLong("time", System.currentTimeMillis()).apply();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent;

                        if (BuildConfig.DEBUG) {
                            System.gc();
                        }
                                /* start the activity */
                        if (!firstRun || firstRunUpdate) {
                            if (registered)
                                intent = new Intent(SplashActivityNew.this, DataLoadingActivity.class);
                            else
                                intent = new Intent(SplashActivityNew.this, PhoneRegActivity.class);

                            startActivity(intent);
                            stopService(intent);

                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            finish();
                        }


                    }
                }, 2000);

            }
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (BuildConfig.DEBUG) {
                        System.gc();
                    }


                                /* start the activity */

                    if (registered && (new StoredAreaTable(SplashActivityNew.this).getAllData().size() > 0))
                        startActivity(new Intent(SplashActivityNew.this, PlaceDetailsActivityNewLayout.class));

                    else
                        startActivity(new Intent(SplashActivityNew.this, DataLoadingActivity.class));


                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    finish();
                }
            }, 3000);


        }


    }

    public boolean isFirstInstall() {
        try {
            long firstInstallTime = this.getPackageManager().getPackageInfo(getPackageName(), 0).firstInstallTime;
            long lastUpdateTime = this.getPackageManager().getPackageInfo(getPackageName(), 0).lastUpdateTime;
            return firstInstallTime == lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean isInstallFromUpdate() {
        try {
            long firstInstallTime = this.getPackageManager().getPackageInfo(getPackageName(), 0).firstInstallTime;
            long lastUpdateTime = this.getPackageManager().getPackageInfo(getPackageName(), 0).lastUpdateTime;
            return firstInstallTime != lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void permissionToDrawOverlays() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, PERM_REQUEST_CODE_DRAW_OVERLAYS);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERM_REQUEST_CODE_DRAW_OVERLAYS) {
            if (android.os.Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
                if (!Settings.canDrawOverlays(this)) {
                    // ADD UI FOR USER TO KNOW THAT UI for SYSTEM_ALERT_WINDOW permission was not granted earlier...
                }
            }
        }
    }

    private void checkPermissions() {
        List<String> permissions = new ArrayList<>();
        String message = null;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.GET_ACCOUNTS);
            message += "\n access to read sms.";
            //requestReadPhoneStatePermission();
        }
        if (!permissions.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            String[] params = permissions.toArray(new String[permissions.size()]);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 175;
                requestPermissions(params, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
        } // else: We already have permissions, so handle as normal
    }


    private static abstract class GenericSaveTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

        WeakReference<SplashActivityNew> activityReference;


        GenericSaveTask(SplashActivityNew activity) {
            activityReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPostExecute(Result result) {

            SplashActivityNew activity = activityReference.get();

            if (activity == null) return;

            if (((Long) result).longValue() == 0.0 && activity.getCounter() < activity.NUMBER_OF_TASKS) { // Means the task is successful
                activity.setCounter(activity.getCounter()+1);
            }
        }

    }


    private static class SaveDistrictTask extends GenericSaveTask<JSONArray, Integer, Long> {

        SaveDistrictTask(SplashActivityNew activity){
            super(activity);
        }

        @Override
        protected Long doInBackground(JSONArray... jsonArray) {

            SplashActivityNew activity = activityReference.get();
            JSONArray districtArray = jsonArray[0];
            DistrictTable districtTable = new DistrictTable(activity.context);
            districtTable.dropTable();

            for (int i = 0; i < districtArray.length(); i++) {

                try {
                    JSONObject jsonObject = districtArray.getJSONObject(i);
                    districtTable.insertItem(new District().parse(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return new Long(-1);
                }
            }
            return new Long(0);
        }
    }


    private static class SaveCityCorporationTask extends GenericSaveTask<JSONArray, Integer, Long> {

        SaveCityCorporationTask(SplashActivityNew activity){
            super(activity);
        }

        @Override
        protected Long doInBackground(JSONArray... jsonArray) {

            SplashActivityNew activity = activityReference.get();
            JSONArray ccArray = jsonArray[0];
            CityCorporationTable cityCorporationTable = new CityCorporationTable(activity.context);
            cityCorporationTable.dropTable();

            for (int i = 0; i < ccArray.length(); i++) {

                try {
                    JSONObject jsonObject = ccArray.getJSONObject(i);
                    cityCorporationTable.insertItem(new CityCorporation().parse(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return new Long(-1);
                }
            }
            return new Long(0);
        }
    }

    private static class SaveWardTask extends GenericSaveTask<JSONArray, Integer, Long> {

        SaveWardTask(SplashActivityNew activity){
            super(activity);
        }

        @Override
        protected Long doInBackground(JSONArray... jsonArray) {

            SplashActivityNew activity = activityReference.get();
            JSONArray wardArray = jsonArray[0];
            WardTable wardTable = new WardTable(activity.context);
            wardTable.dropTable();

            for (int i = 0; i < wardArray.length(); i++) {

                try {
                    JSONObject jsonObject = wardArray.getJSONObject(i);
                    wardTable.insertItem(new Ward().parse(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return new Long(-1);
                }
            }
            return new Long(0);
        }
    }

    private static class SaveAreaTask extends GenericSaveTask<JSONArray, Integer, Long> {

        SaveAreaTask(SplashActivityNew activity){
            super(activity);
        }

        @Override
        protected Long doInBackground(JSONArray... jsonArray) {

            SplashActivityNew activity = activityReference.get();
            JSONArray areaArray = jsonArray[0];
            AreaTable areaTable = new AreaTable(activity.context);
            areaTable.dropTable();

            for (int i = 0; i < areaArray.length(); i++) {

                try {
                    JSONObject jsonObject = areaArray.getJSONObject(i);
                    areaTable.insertItem(new Area().parse(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return new Long(-1);
                }
            }
            return new Long(0);
        }
    }
}