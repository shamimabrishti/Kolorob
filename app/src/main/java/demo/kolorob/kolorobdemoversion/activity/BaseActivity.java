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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import demo.kolorob.kolorobdemoversion.adapters.Comment_layout_adapter;
import demo.kolorob.kolorobdemoversion.adapters.DefaultAdapter;
import demo.kolorob.kolorobdemoversion.database.CommentTable;
import demo.kolorob.kolorobdemoversion.database.SubCategoryTableNew;
import demo.kolorob.kolorobdemoversion.fragment.MapFragmentRouteOSM;
import demo.kolorob.kolorobdemoversion.helpers.Helpes;
import demo.kolorob.kolorobdemoversion.model.CommentItem;
import demo.kolorob.kolorobdemoversion.model.CommonModel;
import demo.kolorob.kolorobdemoversion.model.Entertainment.EntertainmentNewDBModel;
import demo.kolorob.kolorobdemoversion.model.SubCategoryItemNew;
import demo.kolorob.kolorobdemoversion.utils.AlertMessage;
import demo.kolorob.kolorobdemoversion.utils.AppConstants;
import demo.kolorob.kolorobdemoversion.utils.AppUtils;
import demo.kolorob.kolorobdemoversion.utils.SharedPreferencesHelper;
import demo.kolorob.kolorobdemoversion.utils.ToastMessageDisplay;


public abstract class BaseActivity <ModelType> extends AppCompatActivity{

    Dialog dialog;
    LinearLayout upperHand, service_center_name, left_way, middle_phone, right_email, bottom_bar;
    ImageView route_icon, phone_icon, email_icon, comment_icon, email_btn;
    ArrayList <CommentItem> commentItems;
    ArrayList <SubCategoryItemNew> subCategoryItemNews = new ArrayList<>();
    int inc;
    int width, height;
    String datevalue,datevaluebn;

    TextView service_name;
    String username="kolorobapp";
    String password="2Jm!4jFe3WgBZKEN";
    Context con;

    int increment = 0;

    TextView ratingText;
    ImageView routing_icon,feedback;
    RadioGroup feedRadio;
    RadioButton rb1;
    String status="",phone_num="",uname="";
    String result_concate = "";
    //EditText feedback_comment;
    RatingBar ratingBar;
    ListView service_data, contact_data;

    String[] key = new String[600];
    String[] value = new String[600];






    protected void onCreateCustom(Bundle savedInstanceState, Context context, ModelType modelType, CommonModel commonModel, String appConstant) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_info_activity_entertainment_new);
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        con = this;
        Intent intent = getIntent();


        if (null != intent) {

            modelType = (ModelType) intent.getSerializableExtra(appConstant);

        }


        upperHand = (LinearLayout) findViewById(R.id.upper_part); //service center name and icon set will be here
        service_center_name = (LinearLayout) findViewById(R.id.upperText);// service center name will be here
        left_way = (LinearLayout) findViewById(R.id.left_go_process);
        middle_phone = (LinearLayout) findViewById(R.id.middle_phone);
        right_email = (LinearLayout) findViewById(R.id.right_email);
        bottom_bar = (LinearLayout) findViewById(R.id.bottom_bar);
        phone_icon = (ImageView) findViewById(R.id.phone_middl);
        email_icon = (ImageView) findViewById(R.id.right_side_email);
        route_icon = (ImageView) findViewById(R.id.distance_left);
        ratingText=(TextView)findViewById(R.id.ratingText);
        service_data=(ListView)findViewById(R.id.allData); //service_data will hold the main information of a service center
        //contact_data = (ListView)findViewById(R.id.contactData);

        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) service_data
                .getLayoutParams();
        mlp.setMargins(width/100,0,width/990,width/8); //set margin in main info block

        routing_icon = (ImageView) findViewById(R.id.distance_left); //routing icon
        email_btn = (ImageView) findViewById(R.id.right_side_email);  //email icon
        feedback = (ImageView) findViewById(R.id.feedback);          //feedback icon
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        /*if(width<=400)
            ratingBar = new RatingBar(this, null, android.R.attr.ratingBarStyleSmall);// if ratingBar's height less than 400 then style will be different
        */
        setRatingBar(commonModel);



        LinearLayout.LayoutParams params_service_center_name = (LinearLayout.LayoutParams) service_center_name.getLayoutParams();
        service_center_name.setLayoutParams(params_service_center_name);

        SharedPreferences settings = context.getSharedPreferences("prefs", 0);

        LinearLayout.LayoutParams feedbacks = (LinearLayout.LayoutParams) feedback.getLayoutParams();
        feedbacks.height = width / 8;
        feedbacks.width = width / 8;
        feedback.setLayoutParams(feedbacks);


        phone_icon.getLayoutParams().height=width/8;
        phone_icon.getLayoutParams().width=width/8;
        email_icon.getLayoutParams().height = width/8;
        email_icon.getLayoutParams().width = width/8;
        route_icon.getLayoutParams().height =  width/8;
        route_icon.getLayoutParams().width =  width/8;




        // Last date of updating data will be displayed here via toast message
        Date date2 = new Date(settings.getLong("time", 0));
        Date today=new Date();
        long diffInMillisec = today.getTime() - date2.getTime();
        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
        if (diffInDays==0) datevalue=" আজকের তথ্য";
        else
        {
            datevaluebn=English_to_bengali_number_conversion(String.valueOf(diffInDays));
            datevalue=""+ datevaluebn + " দিন আগের তথ্য";
        }
        ToastMessageDisplay.setText(this,datevalue);
        ToastMessageDisplay.showText(this);

        //set properties of service center name
        service_name = (TextView) findViewById(R.id.ups_text);
        service_name.setTextSize(27);
        service_name.setText(commonModel.getNameBn());
        ratingText.setTextSize(23);



        CheckConcate("প্রতিষ্ঠানের  ধরণ",  entertainmentServiceProviderItemNew.getEnttype());
        if(!entertainmentServiceProviderItemNew.getEnttype().equals(getReferences(entertainmentServiceProviderItemNew))){
            CheckConcate("বিশেষত্ব", getReferences(entertainmentServiceProviderItemNew));
        }
        if(entertainmentServiceProviderItemNew.getServicetype().equals(true)) CheckConcate("প্রবেশ মূল্য",  "প্রযোজ্য");


        CheckConcate("\n", "\n");
        CheckConcate("ঠিকানা", concatenateAddress(entertainmentServiceProviderItemNew.getHouseno(), entertainmentServiceProviderItemNew.getRoad(), entertainmentServiceProviderItemNew.getBlock(), entertainmentServiceProviderItemNew.getAreabn()));
        String ward = entertainmentServiceProviderItemNew.getWard();
        if(ward.contains("_")){
            String[] wardSplitted = ward.split("_");
            if(wardSplitted[1].equals("dakshinkhan")){
                ward = "দক্ষিণখান";
            }
            else{
                ward = English_to_bengali_number_conversion(wardSplitted[1]);
            }
        }
        else{
            ward = English_to_bengali_number_conversion(ward);
        }

        CheckConcate("ওয়ার্ড", ward);
        CheckConcate("পুলিশ স্টেশন", entertainmentServiceProviderItemNew.getPolicestation());


        CheckConcate("যোগাযোগ", English_to_bengali_number_conversion(entertainmentServiceProviderItemNew.getNode_contact()));

        CheckConcate("ইমেইল", entertainmentServiceProviderItemNew.getNode_email());

        timeProcessing("খোলার সময়", entertainmentServiceProviderItemNew.getOpeningtime());
        timeProcessing("বন্ধের সময়", entertainmentServiceProviderItemNew.getClosetime());

        CheckConcate("সাপ্তাহিক বন্ধ", entertainmentServiceProviderItemNew.getOffday());


        CheckConcate("অন্যান্য তথ্য ", entertainmentServiceProviderItemNew.getOtherinfo());

        //checkConcate method will check null data and concat

        // Default Adapter will show the details info of a service
        DefaultAdapter defaultAdapter= new DefaultAdapter(this,key,value,increment);
        service_data.setAdapter(defaultAdapter);


        email_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entertainmentServiceProviderItemNew.getNode_email().equals("null")||entertainmentServiceProviderItemNew.getNode_email().equals("")) {
                    AlertMessage.showMessage(con, "ই মেইল করা সম্ভব হচ্ছে না",
                            "ই মেইল আই ডি পাওয়া যায়নি");
                }
                else{
                    //Helpes method will be used to send Email
                    Helpes.sendEmail(DetailsInfoActivityEntertainmentNew.this, entertainmentServiceProviderItemNew.getNode_email());
                }
            }
        });


        comment_icon = (ImageView)findViewById(R.id.comments); //this icon will be used to show comment_icon
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width/8, width/8);
        lp.setMargins(width/26, 0, 0, 0);
        comment_icon.setLayoutParams(lp);
        CommentTable commentTable = new CommentTable(DetailsInfoActivityEntertainmentNew.this);


        commentItems=commentTable.getAllFinancialSubCategoriesInfo(String.valueOf(entertainmentServiceProviderItemNew.getEntid()));
        int size= commentItems.size();
        String[] phone = new String[size];
        String[] date = new String[size];
        final String[] comment = new String[size];
        final String[] rating = new String[size];



        for (CommentItem commentItem:commentItems)
        {
            if(!commentItem.getRating().equals(""))
            {
                phone[inc]= commentItem.getUser_name();
                if(commentItem.getComment().equals(""))date[inc]="কমেন্ট করা হয় নি ";
                else {date[inc]= commentItem.getComment();}
                comment[inc]= English_to_bengali_number_conversion(commentItem.getDate());
                rating[inc]= commentItem.getRating();
                inc++;
            }

        }



        final Comment_layout_adapter comment_layout_adapter = new Comment_layout_adapter(this,phone,date,comment,rating);


        comment_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(SharedPreferencesHelper.getifcommentedalready(DetailsInfoActivityEntertainmentNew.this, String.valueOf(entertainmentServiceProviderItemNew.getEntid()),uname).equals("yes")||inc>0) {
                if (SharedPreferencesHelper.getifcommentedalready(DetailsInfoActivityEntertainmentNew.this, String.valueOf(entertainmentServiceProviderItemNew.getEntid()), uname).equals("yes")&&inc==0) {
                    AlertMessage.showMessage(con, "দুঃখিত",
                            "কমেন্ট দেখতে দয়া করে তথ্য আপডেট করুন");

                } else {
                    if (SharedPreferencesHelper.getifcommentedalready(DetailsInfoActivityEntertainmentNew.this, String.valueOf(entertainmentServiceProviderItemNew.getEntid()), uname).equals("yes") ) {
                        ToastMessageDisplay.setText(con,
                                "আপনার করা কমেন্ট দেখতে দয়া করে তথ্য আপডেট করুন");
                        ToastMessageDisplay.showText(con);
                    }
                    LayoutInflater layoutInflater = LayoutInflater.from(DetailsInfoActivityEntertainmentNew.this);
                    final View promptView = layoutInflater.inflate(R.layout.comment_popup, null);
                    final Dialog alertDialog = new Dialog(DetailsInfoActivityEntertainmentNew.this);
                    alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    alertDialog.setContentView(promptView);
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.show();

                    final ListView listView = (ListView) promptView.findViewById(R.id.comment_list);
                    final ImageView close_icon = (ImageView) promptView.findViewById(R.id.closex);
                    final TextView review = (TextView) promptView.findViewById(R.id.review);
                    final ImageView ratingbarz = (ImageView) promptView.findViewById(R.id.ratingBarz);

                    try {
                        int ratings = Integer.parseInt(entertainmentServiceProviderItemNew.getRatings());
                        if (ratings == 1) {
                            ratingbarz.setBackgroundResource(R.drawable.one);
                        } else if (ratings == 2)
                            ratingbarz.setBackgroundResource(R.drawable.two);

                        else if (ratings == 3)
                            ratingbarz.setBackgroundResource(R.drawable.three);

                        else if (ratings == 4)
                            ratingbarz.setBackgroundResource(R.drawable.four);

                        else if (ratings == 5)
                            ratingbarz.setBackgroundResource(R.drawable.five);
                    } catch (Exception e) {

                    }


                    review.setText(English_to_bengali_number_conversion(Integer.toString(inc)) + " রিভিউ");
                    Double screenSize = AppUtils.ScreenSize(DetailsInfoActivityEntertainmentNew.this);
                    //Check ScreenSize
                    if (screenSize > 6.5) {
                        review.setTextSize(20);
                    } else {
                        review.setTextSize(16);
                    }


                    listView.setAdapter(comment_layout_adapter);
                    alertDialog.getWindow().setLayout((width * 5) / 6, (height * 2) / 3);

                    close_icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });


                    alertDialog.setCancelable(false);
                    alertDialog.show();


                }
            }
            else if(inc==0)  //if inc= o means no one commented
            {
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
                String text="এই সেবা সম্পর্কে কেউ এখনো মন্তব্য করেনি "+"\n"+"আপনি কি আপনার মতামত জানাতে চান?";
                textAsk.setText(text);
                alertDialog.getWindow().setLayout((width*5)/6, WindowManager.LayoutParams.WRAP_CONTENT);

                if(SharedPreferencesHelper.isTabletDevice(DetailsInfoActivityEntertainmentNew.this))
                    textAsk.setTextSize(23);
                else
                    textAsk.setTextSize(17);
                //  alertDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    alertDialog.cancel();
                    String  register = SharedPreferencesHelper.getNumber(DetailsInfoActivityEntertainmentNew.this);
                    phone_num=register;
                    //if no number is set it will request to register
                    if (register.equals("")) {
                        requestToRegister();
                    } else {

                        feedBackAlert();
                    }

                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.setCancelable(false);
                alertDialog.show();

            }




            }
        });






        phone_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent1 = new Intent(Intent.ACTION_CALL);
                if (!entertainmentServiceProviderItemNew.getNode_contact().equals("null")&&!entertainmentServiceProviderItemNew.getNode_contact().equals("")) {
                    Log.d("Entertainment Parsing","......."+entertainmentServiceProviderItemNew.getNode_contact());
                    callIntent1.setData(Uri.parse("tel:" + entertainmentServiceProviderItemNew.getNode_contact()));
                    if (checkPermission())
                        startActivity(callIntent1);
                    else {
                        AlertMessage.showMessage(con, "ফোনে কল দেয়া সম্ভব হচ্ছে না",
                                "ফোন নম্বর পাওয়া যায়নি");

                    }
                } else {

                    AlertMessage.showMessage(con, "ফোনে কল দেয়া সম্ভব হচ্ছে না",
                            "ফোন নম্বর পাওয়া যায়নি");

                }
            }
        });


        routing_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppUtils.isNetConnected(getApplicationContext())  && AppUtils.displayGpsStatus(getApplicationContext())) {


                    String lat = entertainmentServiceProviderItemNew.getLat();
                    // double latitude = Double.parseDouble(lat);
                    String lon = entertainmentServiceProviderItemNew.getLon();
                    // double longitude = Double.parseDouble(lon);
                    String name= entertainmentServiceProviderItemNew.getNamebn();
                    String node=String.valueOf(entertainmentServiceProviderItemNew.getEntid());
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

                        // implementFragment();
                        //username and password are present, do your stuff
                    }

                    Intent intentJ = new Intent(DetailsInfoActivityEntertainmentNew.this,MapFragmentRouteOSM.class);
                    startActivity(intentJ);

                }
                else if(!AppUtils.displayGpsStatus(getApplicationContext())){

                    AppUtils.showMessage(con, "জিপিএস বন্ধ করা রয়েছে!",
                            "আপনি কি আপনার মোবাইলের জিপিএস টি চালু করতে চান?");
                }

                else
                {
                    AlertMessage.showMessage(con, "দুঃখিত আপনার ইন্টারনেট সংযোগটি সচল নয়।",
                            "দিকনির্দেশনা দেখতে চাইলে অনুগ্রহপূর্বক ইন্টারনেট সংযোগটি চালু করুন।  ");
                }
            }
        });

    }


    public void verifyRegistration(View v, Context context, CommonModel commonModel) {

        String  register = SharedPreferencesHelper.getNumber(context);
        phone_num = register;
        if (register.equals("")) {
            requestToRegister(context);
        }
        else {
            feedBackAlert(context, commonModel);
        }
    }

    public void feedBackAlert(final Context context, final CommonModel commonModel) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View promptView = layoutInflater.inflate(R.layout.give_feedback_dialogue, null);
        final Dialog alertDialog = new Dialog(context);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(promptView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();


        final Button submit = (Button) promptView.findViewById(R.id.submit);
        final Button close_icon = (Button) promptView.findViewById(R.id.btnclose);
        close_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            feedRadio=(RadioGroup)promptView.findViewById(R.id.feedRadio);
            int selected = feedRadio.getCheckedRadioButtonId();
            rb1 = (RadioButton)promptView.findViewById(selected);
            status = rb1.getText().toString();

            if(AppUtils.isNetConnected(getApplicationContext()))
            {
                sendReviewToServer(context, commonModel);
                alertDialog.cancel();
            }
            else {
                ToastMessageDisplay.setText(context,"দয়া করে ইন্টারনেট চালু করুন।");
                ToastMessageDisplay.showText(context);
            }

            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    public void sendReviewToServer(final Context context, final CommonModel commonModel) {
        String  uname2 = SharedPreferencesHelper.getUname(context);
        uname=uname2.replace(' ','+');;
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


        Log.d("status ","======"+status);
        String url = "http://kolorob.net/kolorob-new-demo/api/sp_rating2/" + commonModel.getId() + "?" + "phone=" + phone_num + "&name=" + uname + "&rating=" + rating + "&username=" + username + "&password=" + password + "";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Response is true or false
                    try {
                        if (response.equals("true")) {
                            SharedPreferencesHelper.setifcommentedalready(context, String.valueOf(commonModel.getId()),uname,"yes");
                            AlertMessage.showMessage(context, "মতামতটি গ্রহন করা হয়েছে",
                                    "মতামত প্রদান করার জন্য আপনাকে ধন্যবাদ");
                        }
                        else
                            AlertMessage.showMessage(context, "মতামতটি গ্রহন করা হয় নি",
                                    "অনুগ্রহ পূর্বক পুনরায় চেস্টা করুন।");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void setRatingBar(CommonModel commonModel) {
        try {
            if(commonModel.getRatings()!=null)
                ratingBar.setRating(Float.parseFloat(commonModel.getRatings()));
            else
                ratingBar.setRating(0.0f);
        }
        catch (Exception e)
        {

        }


    }

    public void requestToRegister(final Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.verify_reg_dialog, null);


        final Dialog alertDialog = new Dialog(context);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(promptView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();


        final ImageView yes = (ImageView) promptView.findViewById(R.id.yes);
        final ImageView no = (ImageView) promptView.findViewById(R.id.no);
        final TextView textAsk=(TextView)promptView.findViewById(R.id.textAsk);
        String text="  মতামত দেয়ার আগে আপনাকে"+"\n"+"       রেজিস্ট্রেশন করতে হবে"+"\n"+"আপনি কি রেজিস্ট্রেশন করতে চান?";
        textAsk.setText(text);
        if(SharedPreferencesHelper.isTabletDevice(context))
            textAsk.setTextSize(23);
        else
            textAsk.setTextSize(17);
        alertDialog.getWindow().setLayout((width*5)/6, WindowManager.LayoutParams.WRAP_CONTENT);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                Intent intentPhoneRegistration = new Intent(context, PhoneRegActivity.class);

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
            else if(english_number.charAt(i) == '-')
                concatResult = concatResult + "-";
            else if(english_number.charAt(i)== '+'){
                concatResult = concatResult + "+";
            }
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

            //Toast.makeText(DetailsInfoActivityEntertainmentNew.this, "Hour: " + hour, Toast.LENGTH_LONG).show();

            if (hour ==0 && times==0)
                timeInBengali = "রাত ১২";
            else if (hour > 0 && hour < 4)
                timeInBengali = "রাত " + English_to_bengali_number_conversion(String.valueOf(hour));
            else if (hour > 3 && hour < 6)
                timeInBengali = "ভোর " + English_to_bengali_number_conversion(String.valueOf(hour));
            else if (hour >= 6 && hour < 12)
                timeInBengali = "সকাল " + English_to_bengali_number_conversion(String.valueOf(hour));
            else if (hour == 12)
                timeInBengali = "দুপুর  " + English_to_bengali_number_conversion(String.valueOf(hour));
            else if (hour > 12 && hour < 16)
                timeInBengali = "দুপুর  " + English_to_bengali_number_conversion(String.valueOf(hour - 12));
            else if (hour > 15 && hour < 18)
                timeInBengali = "বিকাল " + English_to_bengali_number_conversion(String.valueOf(hour - 12));
            else if (hour > 17 && hour < 20)
                timeInBengali = "সন্ধ্যা " + English_to_bengali_number_conversion(String.valueOf(hour - 12));
            else if (hour > 19 && hour < 24)
                timeInBengali = "রাত " + English_to_bengali_number_conversion(String.valueOf(hour - 12));
            if (times != 0)
                timeInBengali = timeInBengali + " টা " + English_to_bengali_number_conversion(String.valueOf(times)) + " মিনিট";
            else
                timeInBengali = timeInBengali + "টা";
        }
        catch (Exception e)
        {
        }

        return timeInBengali;

    }
    // check null and concat
    private void CheckConcate(String value1, String value2) {
        if (!value2.equals("null") && !value2.equals("")&& !value2.equals(" টাকা")) {
            key[increment] = value1;
            value[increment] = value2 + "\n";
            increment++;
        }
    }

    /*private void CheckConcateContact(String key, String value) {
        if (!value.equals("null") && !value.equals("")&& !value.equals(" টাকা")) {
            keyContact[incrementContact] = key;
            valueContact[incrementContact] = value + "\n";
            incrementContact++;
        }
    }*/

    private boolean checkValue(String value){
        return !value.equals("null") && !value.equals("");
    }

    private String concatenateAddress(String house, String block, String road, String areaBn){
        String address = "";

        if(checkValue(house)){
            address += " বাড়ির নাম্বার : " + English_to_bengali_number_conversion(house) + ",";
        }
        if(checkValue(road)){
            address += " রাস্তা : " + English_to_bengali_number_conversion(road) + ",";
        }
        if(checkValue(block)){
            address += " ব্লক : " + English_to_bengali_number_conversion(block) + ",";
        }
        if(checkValue(areaBn)){
            address += " " + areaBn + ",";
        }


        char[] addressArray = address.toCharArray();
        addressArray[addressArray.length-1] = ' ';

        return String.valueOf(addressArray);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }

    }

    private String getReferences(CommonModel commonModel){
        String ref;
        StringBuilder result = new StringBuilder();

        setSubcategories(commonModel.getCategoryId());

        String refid = commonModel.getRefNum();
        result.delete(0, result.length());
        String[] references = refid.split(",");
        for (int k = 0; k < references.length; k++) {
            for (int i = 0; i < subCategoryItemNews.size(); i++) {
                int value = subCategoryItemNews.get(i).getRefId();
                if (value == Integer.parseInt(references[k])) {
                    result.append(subCategoryItemNews.get(i).getRefLabelBn());
                    result.append(",");
                }
            }
        }
        try {

            result.setLength(result.length() - 1);
            ref = String.valueOf(result);
        }catch (StringIndexOutOfBoundsException  e)
        {
            ref = "পাওয়া যায় নি";
        }

        return ref;

    }



    public void setSubcategories(int id) {

        SubCategoryTableNew subCategoryTableNew = new SubCategoryTableNew(this);
        subCategoryItemNews = subCategoryTableNew.getAllSubCategories(id);

        subCategoryItemNews = subCategoryTableNew.getAllData();
        subCategoryItemNews = subCategoryTableNew.getAllSubCategories(id);

    }




}
/*
* This is to notify to whoever works on this project; app structure is fully developed by junior developers; so it does not follow optimised
* coding pattern fully. Moreover; Code structure got changed due to new feature integration/New UX etc and
* same code been rewritten or tried to hold new component in lieu of completely write new code every time. So please don't curse us
* if you get lost in our messy coding!THis is the best we could manage without any expert support. We suggest you debug the app and go through each and every function one by one. This will help!
*
* Thanks! :)
*
* */




