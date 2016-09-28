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
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import demo.kolorob.kolorobdemoversion.R;
import demo.kolorob.kolorobdemoversion.adapters.DefaultAdapter;
import demo.kolorob.kolorobdemoversion.database.Entertainment.EntertainmetTypeTable;
import demo.kolorob.kolorobdemoversion.fragment.MapFragmentRouteOSM;
import demo.kolorob.kolorobdemoversion.helpers.Helpes;
import demo.kolorob.kolorobdemoversion.model.Entertainment.EntertainmentServiceProviderItemNew;
import demo.kolorob.kolorobdemoversion.model.Entertainment.EntertainmentTypeItem;
import demo.kolorob.kolorobdemoversion.utils.AlertMessage;
import demo.kolorob.kolorobdemoversion.utils.AppConstants;
import demo.kolorob.kolorobdemoversion.utils.AppUtils;
import demo.kolorob.kolorobdemoversion.utils.SharedPreferencesHelper;

/**
 * Created by arafat on 28/05/2016.
 */

public class DetailsInfoActivityEntertainmentNew extends AppCompatActivity {
    Dialog dialog;
    LinearLayout upperHand,upperText,left_way,middle_phone,right_email,bottom_bar,linearLayout;
    ImageView left_image,middle_image,right_image,email_btn;
    TextView address_text,phone_text,email_text;
    int width,height;
    String datevalue,datevaluebn;

    TextView ups_text,headerx;
    ListView courseListView,listView;
    String username="kolorobapp";
    String password="2Jm!4jFe3WgBZKEN";
    Context con;
    String[] key;
    String[] value;
    int increment=0;
    EntertainmentServiceProviderItemNew entertainmentServiceProviderItemNew;
    ArrayList<EntertainmentTypeItem> entertainmentTypeItems;

    private TextView ratingText;
    private ImageView distance_left,feedback,top_logo,cross;
    RadioGroup feedRadio;
    RadioButton rb1;
    String status="",phone_num="";
    String result_concate="";

    EditText feedback_comment;

    RatingBar ratingBar;
    ListView alldata;
    long dateval;
    TextView toastMessage;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_info_activity_entertainment_new);
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        con = this;

        Log.d("ccc","######"+width);

        Intent intent = getIntent();


        if (null != intent) {
            entertainmentServiceProviderItemNew = (EntertainmentServiceProviderItemNew) intent.getSerializableExtra(AppConstants.KEY_DETAILS_ENT);

        }




        courseListView = (ListView) findViewById(R.id.courseListView);
        listView = (ListView) findViewById(R.id.listView5);
        linearLayout = (LinearLayout) findViewById(R.id.lll);
        upperHand = (LinearLayout) findViewById(R.id.upper_part);
        upperText = (LinearLayout) findViewById(R.id.upperText);
        left_way = (LinearLayout) findViewById(R.id.left_go_process);
        middle_phone = (LinearLayout) findViewById(R.id.middle_phone);
        right_email = (LinearLayout) findViewById(R.id.right_email);
        left_image = (ImageView) findViewById(R.id.distance_left);
        bottom_bar = (LinearLayout) findViewById(R.id.bottom_bar);
        middle_image = (ImageView) findViewById(R.id.phone_middl);
        right_image = (ImageView) findViewById(R.id.right_side_email);
        address_text = (TextView) findViewById(R.id.address_text);
        phone_text = (TextView) findViewById(R.id.phone_text);
        email_text = (TextView) findViewById(R.id.email_text);

        ratingText=(TextView)findViewById(R.id.ratingText);

        // headerx=(TextView)findViewById(R.id.headerx);
        alldata=(ListView)findViewById(R.id.allData);

        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) alldata
                .getLayoutParams();

        mlp.setMargins(width/100,0,width/990,width/8);

        // close_button=(ImageView)findViewById(R.id.close_button);

        top_logo=(ImageView)findViewById(R.id.top_logo);
        cross=(ImageView)findViewById(R.id.close_buttonc);
//        school_logo_default=(ImageView)findViewById(R.id.service_logo);





        distance_left = (ImageView) findViewById(R.id.distance_left);
        email_btn = (ImageView) findViewById(R.id.right_side_email);
        feedback = (ImageView) findViewById(R.id.feedback);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        if(width<=400)
            ratingBar = new RatingBar(this, null, android.R.attr.ratingBarStyleSmall);
        setRatingBar();



        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) upperHand.getLayoutParams();
        upperHand.setLayoutParams(params2);


        LinearLayout.LayoutParams params_upperText = (LinearLayout.LayoutParams) upperText.getLayoutParams();


        //  params_upperText.setMargins(width/3,0,0,0);
        upperText.setLayoutParams(params_upperText);

        LinearLayout.LayoutParams params_left_way = (LinearLayout.LayoutParams) left_way.getLayoutParams();
        int lett_img = params_left_way.height = (height * 3) / 24;
        int right_img = params_left_way.width = width / 3;
        left_way.setLayoutParams(params_left_way);


        top_logo.getLayoutParams().height = width / 8;
        top_logo.getLayoutParams().width = width / 8;

        middle_image.getLayoutParams().height = width / 8;
        middle_image.getLayoutParams().width = width / 8;

        cross.getLayoutParams().height = width / 13;
        cross.getLayoutParams().width = width / 13;

        right_image.getLayoutParams().height = width / 8;
        right_image.getLayoutParams().width = width / 8;

        left_image.getLayoutParams().height = width / 8;
        left_image.getLayoutParams().width = width / 8;


        SharedPreferences settings = DetailsInfoActivityEntertainmentNew.this.getSharedPreferences("prefs", 0);

        LinearLayout.LayoutParams params_middle_phone = (LinearLayout.LayoutParams) middle_phone.getLayoutParams();
        int vx = params_middle_phone.height = (height * 3) / 24;
        params_middle_phone.width = width / 3;
        middle_phone.setLayoutParams(params_middle_phone);


        Date date2 = new Date(settings.getLong("time", 0));
        Date today=new Date();
        long diffInMillisec = today.getTime() - date2.getTime();

        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
        if (diffInDays==0) datevalue=" (Updated today)";
        else
        {
            dateval=diffInDays;
            if (dateval>30) datevalue=" ( Old information )";
            else
                datevalue=" ( Information of" + datevaluebn + " days ago)";
        }
        LayoutInflater inflater = getLayoutInflater();

        View toastView = inflater.inflate(R.layout.toast_view,null);
     toast = new Toast(this);
        // Set the Toast custom layout
        toast.setView(toastView);


        //   View toastView = toast.getView(); //This'll return the default View of the Toast.

        /* And now you can get the TextView of the default View of the Toast. */



        toastMessage = (TextView) toastView.findViewById(R.id.toasts);
        toastMessage.setTextSize(25);
        toastMessage.setText(datevalue);


        toastMessage.setTextColor(getResources().getColor(R.color.orange));
        //  toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.kolorob_logo, 0, 0, 0);
        // toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setCompoundDrawablePadding(26);
        //  toastView.setBackgroundColor(getResources().getColor(R.color.orange));
        toast.show();


        LinearLayout.LayoutParams params_right_email = (LinearLayout.LayoutParams) right_email.getLayoutParams();
        int vc = params_right_email.height = (height * 3) / 24;
        params_right_email.width = width / 3;
        right_email.setLayoutParams(params_right_email);

        ups_text = (TextView) findViewById(R.id.ups_text);
        ups_text.setTextSize(27);
        ratingText.setTextSize(23);


        LinearLayout.LayoutParams feedbacks = (LinearLayout.LayoutParams) feedback.getLayoutParams();
        feedbacks.height = width / 8;
        feedbacks.width = width / 8;
        feedback.setLayoutParams(feedbacks);
        feedbacks.setMargins(0, 0, width / 30, 0);
        Log.d("width", "====" + width);

        EntertainmetTypeTable entertainmetTypeTable=new EntertainmetTypeTable(DetailsInfoActivityEntertainmentNew.this);
        entertainmentTypeItems=entertainmetTypeTable.getEntTypeItem(entertainmentServiceProviderItemNew.getNodeId());
        result_concate ="";

        key = new String[600];

        value = new String[600];


        //   other_detailsEnt.setVisibility(View.VISIBLE);


        CheckConcate("Road", entertainmentServiceProviderItemNew.getRoad());
        CheckConcate("Line", entertainmentServiceProviderItemNew.getLine());
        CheckConcate("Avenue", entertainmentServiceProviderItemNew.getAvenue());
        CheckConcate("Block", entertainmentServiceProviderItemNew.getBlock());
        CheckConcate("Address", entertainmentServiceProviderItemNew.getAddress());

        CheckConcate("House Name", entertainmentServiceProviderItemNew.getHouse_name());
        CheckConcate("House Number", entertainmentServiceProviderItemNew.getHouse_no());
        CheckConcate("Floor ", entertainmentServiceProviderItemNew.getFloor());
        CheckConcate("Closest Landmark", entertainmentServiceProviderItemNew.getLandmark());

        CheckConcate("Opening Time",  entertainmentServiceProviderItemNew.getOpeningtime());
        if(!entertainmentServiceProviderItemNew.getBreaktime().equals("null")&&!entertainmentServiceProviderItemNew.getBreaktime().equals(""))
            breakTimeProcessing("Break Time", entertainmentServiceProviderItemNew.getBreaktime());
        CheckConcate("Closing Time", entertainmentServiceProviderItemNew.getClosingtime());
        CheckConcate("Closed on", entertainmentServiceProviderItemNew.getOff_day());

        ups_text.setText(entertainmentServiceProviderItemNew.getNodeName());

        // detailsEntertainment.setText(result_concate);
        for (EntertainmentTypeItem entertainmentTypeItem : entertainmentTypeItems) {
            CheckConcate("Center Type", entertainmentTypeItem.getType());
            CheckConcate("Service Type", entertainmentTypeItem.getSub_type());
            if(entertainmentTypeItem.getRecreation_price().equals("")||entertainmentTypeItem.getRecreation_price().equals("null"))
                CheckConcate("Service Cost", "70 BDT");
            else
                CheckConcate("Service Cost", entertainmentTypeItem.getRecreation_price()+" BDT");
            CheckConcate("Additional Information ", entertainmentTypeItem.getRecreation_remarks());



        }


        DefaultAdapter defaultAdapter= new DefaultAdapter(this,key,value,increment);
        alldata.setAdapter(defaultAdapter);




        //   other_detailsEnt.setText(result_concate);


        right_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entertainmentServiceProviderItemNew.getNodeWebsite().equals("null")||entertainmentServiceProviderItemNew.getNodeWebsite().equals("")) {
                    AlertMessage.showMessage(con, "Not possible to e-mail",
                            "Email-id not found");
                }
                else{
                    Helpes.sendEmail(DetailsInfoActivityEntertainmentNew.this, entertainmentServiceProviderItemNew.getNodeEmail());
                }
            }
        });

        middle_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent1 = new Intent(Intent.ACTION_CALL);
                if (!entertainmentServiceProviderItemNew.getNodeContact().equals("null")&&!entertainmentServiceProviderItemNew.getNodeContact().equals("")) {
                    callIntent1.setData(Uri.parse("tel:" + entertainmentServiceProviderItemNew.getNodeContact()));
                    if (checkPermission())
                        startActivity(callIntent1);
                    else {

                        Toast.makeText(getApplicationContext(),
                                "Sorry, Phone call is not possible now. ", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {

                    AlertMessage.showMessage(con,  "Sorry"," Phone call is not possible now. ");

                }
            }
        });



//        feedback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent feedIntent = new Intent(DetailsInfoActivityEducation.this,FeedBackActivityNew.class);
//                feedIntent.putExtra("id",educationServiceProviderItem.getIdentifierId());
//                feedIntent.putExtra("categoryId","1");
//                Log.d(">>>>","Button is clicked1 " +educationServiceProviderItem.getIdentifierId());
//
//                startActivity(feedIntent);
//
//            }
//        });


//            right_image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(educationServiceProviderItem.getEmailAddress().equals(""))
//                    {
//                        AlertMessage.showMessage(con, "ই মেইল করা সম্ভব হচ্ছে না",
//                                "ই মেইল আই ডি পাওয়া যায়নি");
//                    }
//                }
//            });
//


        // phermacy.setText(lat);


//        }

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        distance_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppUtils.isNetConnected(getApplicationContext())  && AppUtils.displayGpsStatus(getApplicationContext())) {


                    String lat = entertainmentServiceProviderItemNew.getLatitude().toString();
                    // double latitude = Double.parseDouble(lat);
                    String lon = entertainmentServiceProviderItemNew.getLongitude().toString();
                    // double longitude = Double.parseDouble(lon);
                    String name= entertainmentServiceProviderItemNew.getNodeNameBn().toString();
                    String node=String.valueOf(entertainmentServiceProviderItemNew.getNodeId());
                    boolean fromornot=true;
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("Latitude", lat);
                    editor.putString("Longitude", lon);
                    editor.putString("Name", name);
                    editor.putBoolean("Value", fromornot);
                    editor.putString("nValue", node);
                    editor.commit();


                    String Longitude = pref.getString("Longitude", null);
                    String Latitude = pref.getString("Latitude", null);

                    if (Latitude != null && Longitude != null) {
                        Double Lon = Double.parseDouble(Longitude);
                        Double Lat = Double.parseDouble(Latitude);
                        // implementFragment();
                        //username and password are present, do your stuff
                    }

                    Intent intentJ = new Intent(DetailsInfoActivityEntertainmentNew.this,MapFragmentRouteOSM.class);
                    startActivity(intentJ);

                }
                else if(!AppUtils.displayGpsStatus(getApplicationContext())){

                    AppUtils.showMessage(con, "GPS is off!",
                            "Do you want to activate GPS?");

                }

                else
                {


                    AlertMessage.showMessage(con, "Sorry।",
                            "Please activate your internet to see route");




                }


            }
        });

    }
//


    private void breakTimeProcessing(String value1, String value2) {
        if (!value2.equals("null") || !value2.equals(", ")) {
            String timeInBengali = "";

            try {
                value2 = value2 + ",";

                String[] breakTIme = value2.split(",");


                String[] realTIme = breakTIme[0].split("-");


                value2 = timeConverter(realTIme[0]) + " to " + timeConverter(realTIme[1]);
                CheckConcate(value1, value2);
            }
            catch (Exception e)
            {

            }
        }
    }

    public void verifyRegistration(View v) {

        String  register = SharedPreferencesHelper.getNumber(DetailsInfoActivityEntertainmentNew.this);
        phone_num=register;
        Log.d("Phone_num","------"+phone_num);

        if (register.equals("")) {
            requestToRegister();
        } else {

            feedBackAlert();
            //  sendReviewToServer();
        }


    }

    public void feedBackAlert() {

        LayoutInflater layoutInflater = LayoutInflater.from(DetailsInfoActivityEntertainmentNew.this);
        final View promptView = layoutInflater.inflate(R.layout.give_feedback_dialogue, null);
        final Dialog alertDialog = new Dialog(DetailsInfoActivityEntertainmentNew.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(promptView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();


        final Button submit = (Button) promptView.findViewById(R.id.submit);

        final Button close = (Button) promptView.findViewById(R.id.btnclose);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback_comment=(EditText)promptView.findViewById(R.id.feedback_comment);
                feedRadio=(RadioGroup)promptView.findViewById(R.id.feedRadio);
                int selected = feedRadio.getCheckedRadioButtonId();
                rb1 = (RadioButton)promptView.findViewById(selected);
                status = rb1.getText().toString();
                //  declareRadiobutton();
               // sendReviewToServer();
                toastMessage.setText("This is dummy feedback. This wont be submitted to server.Thanks!");


                toastMessage.setTextColor(getResources().getColor(R.color.orange));

                //  toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.kolorob_logo, 0, 0, 0);
                // toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

                toastMessage.setGravity(Gravity.CENTER);
                toastMessage.setCompoundDrawablePadding(26);
                //  toastView.setBackgroundColor(getResources().getColor(R.color.orange));
                toast.show();
                alertDialog.cancel();

            }
        });
        alertDialog.setCancelable(false);


        alertDialog.show();
    }


    public void sendReviewToServer() {
        int rating;
        if(status.equals(getString(R.string.feedback1)))
            rating= 1;
        else if(status.equals(getString(R.string.feedback2)))
            rating=  2;
        else if(status.equals(getString(R.string.feedback3)))
            rating= 3;
        else if(status.equals(getString(R.string.feedback4)))
            rating=  4;
        else
            rating= 5;

        String comment="";
        comment=feedback_comment.getText().toString();
        Log.d("status ","======"+status);
        String url = "http://kolorob.net/demo/api/sp_rating/"+entertainmentServiceProviderItemNew.getNodeId()+"?"+"phone=" +phone_num +"&review=" +comment.replace(' ','+')+ "&rating="+rating+"&username="+username+"&password="+password+"";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("========", "status " + response);
                        try {


                            if (response.equals("true")) {
                                AlertMessage.showMessage(DetailsInfoActivityEntertainmentNew.this, "মতামতটি গ্রহন করা হয়েছে",
                                        "মতামত প্রদান করার জন্য আপনাকে ধন্যবাদ");
                            }
                            else
                                AlertMessage.showMessage(DetailsInfoActivityEntertainmentNew.this, "মতামতটি গ্রহন করা হয় নি",
                                        "অনুগ্রহ পূর্বক পুনরায় চেস্টা করুন।");


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailsInfoActivityEntertainmentNew.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(DetailsInfoActivityEntertainmentNew.this);
        requestQueue.add(stringRequest);
    }

    public void setRatingBar()
    {
//        getRequest(DetailsInfoActivityEntertainmentNew.this, "http://kolorob.net/demo/api/get_sp_rating/entertainment?username=kolorobapp&password=2Jm!4jFe3WgBZKEN", new VolleyApiCallback() {
//                    @Override
//                    public void onResponse(int status, String apiContent) {
//                        if (status == AppConstants.SUCCESS_CODE) {
//                            try {
//                                JSONArray jo = new JSONArray(apiContent);
//                                int size= jo.length();
//                                for(int i=0;i<size;i++)
//                                {
//                                    JSONObject ratingH=jo.getJSONObject(i);
//                                    String id= ratingH.getString("id");
//                                    if(id.equals(entertainmentServiceProviderItemNew.getNodeId()))
//                                    {
//
//                                        rating=Float.parseFloat(ratingH.getString("avg"));

        try {
            float f= Float.parseFloat(entertainmentServiceProviderItemNew.getRating());

            ratingBar.setRating(f);
            ratingBar.setIsIndicator(true);


        }
        catch (Exception e)
        {

        }

//                                        break;
//
//                                    }
//
//
//                                }
//
//
//
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//        );
    }

    public void requestToRegister() {
        LayoutInflater layoutInflater = LayoutInflater.from(DetailsInfoActivityEntertainmentNew.this);
        View promptView = layoutInflater.inflate(R.layout.verify_reg_dialog, null);


        final Dialog alertDialog = new Dialog(DetailsInfoActivityEntertainmentNew.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(promptView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();


        final ImageView yes = (ImageView) promptView.findViewById(R.id.yes);
        final ImageView no = (ImageView) promptView.findViewById(R.id.no);
        final TextView textAsk=(TextView)promptView.findViewById(R.id.textAsk);
        String text="    You need to    "+"\n"+"     Register first    "+"\n"+"   Do you want to?    ";
        textAsk.setText(text);
        if(SharedPreferencesHelper.isTabletDevice(DetailsInfoActivityEntertainmentNew.this))
            textAsk.setTextSize(23);
        else
            textAsk.setTextSize(17);
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                Intent intentPhoneRegistration = new Intent(DetailsInfoActivityEntertainmentNew.this, PhoneRegActivity.class);

                startActivity(intentPhoneRegistration);

            }
        });


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();

            }
        });
        //   setup a dialog window
        alertDialog.setCancelable(false);


        alertDialog.show();
    }


    private void timeProcessing(String value1, String value2) {
        if (!value2.equals("null") || value2.equals("")) {

            String GetTime = timeConverter(value2);
            CheckConcate(value1, GetTime);

        }
    }



    private String English_to_bengali_number_conversion(String english_number) {
        if(english_number.equals("null")||english_number.equals(""))
            return english_number;
        int v = english_number.length();
        String concatResult = "";
        for (int i = 0; i < v; i++) {
            if (english_number.charAt(i) == '1')
                concatResult = concatResult + "১";
            else if (english_number.charAt(i) == '2')
                concatResult = concatResult + "২";
            else if (english_number.charAt(i) == '3')
                concatResult = concatResult + "৩";
            else if (english_number.charAt(i) == '4')
                concatResult = concatResult + "৪";
            else if (english_number.charAt(i) == '5')
                concatResult = concatResult + "৫";
            else if (english_number.charAt(i) == '6')
                concatResult = concatResult + "৬";
            else if (english_number.charAt(i) == '7')
                concatResult = concatResult + "৭";
            else if (english_number.charAt(i) == '8')
                concatResult = concatResult + "৮";
            else if (english_number.charAt(i) == '9')
                concatResult = concatResult + "৯";
            else if (english_number.charAt(i) == '0')
                concatResult = concatResult + "০";
            else if (english_number.charAt(i) == '.')
                concatResult = concatResult + ".";
            else if(english_number.charAt(i) == '/')
                concatResult = concatResult + "/";
            else {
                return english_number;
            }

        }
        return concatResult;
    }

    private String timeConverter(String time) {


        String timeInBengali = "";

        try
        {

            String[] separated = time.split(":");


            int hour = Integer.valueOf(separated[0]);
            int times = Integer.valueOf(separated[1]);

            if (hour ==0 && times==0)
                timeInBengali = "12 AM";
            else if (hour >= 6 && hour < 12)
                timeInBengali = String.valueOf(hour)+" AM";
            else if (hour == 12)
                timeInBengali = String.valueOf(hour)+" Noon";
            else if (hour > 12 && hour < 16)
                timeInBengali = String.valueOf(hour - 12)+" PM (Noon)";
            else if (hour > 15 && hour < 18)
                timeInBengali = String.valueOf(hour - 12) + " PM (Afternoon)";
            else if (hour > 17 && hour < 20)
                timeInBengali = String.valueOf(hour - 12)+" PM (Evening)";
            else if (hour > 20)
                timeInBengali = String.valueOf(hour - 12)+" PM(Night)";
            if (times != 0)
                timeInBengali = timeInBengali + " O clock and " + String.valueOf(times) + " Minutes";
            else
                timeInBengali = timeInBengali + " ";
        }
        catch (Exception e)
        {

        }

        return timeInBengali;

    }
    //    public Boolean RegisteredOrNot()
//    {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        //  editor.putString("registered", lat);
//        registered = pref.getString("registered", null);
//        phone_num = pref.getString("phone",null);
//        // editor.commit();
//        //  if(registered.equals("yes"))
//        return true;
//        //  else
//        //   return true;
//
//
//
//
    private void CheckConcate(String value1,String value2){

        if(value1.equals("Email")||value1.equals("Web site"))
        {
            key[increment] = value1;
            value[increment] = value2;
        }
        else {
            if (!value2.equals("null") && !value2.equals("")) {
                if(value2.equals(" BDT"))
                {
                    key[increment] = value1;
                    value[increment] = "120 BDT";
                }
                else {
                    {
                        key[increment] = value1;
                        value[increment] = AppUtils.Check_Capitalization(value2);
                    }

                }
                increment++;

            }
        }
    }







    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;

        }

    }


}