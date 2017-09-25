package demo.kolorob.kolorobdemoversion.activity;

import android.content.Intent;
import android.os.Bundle;
import demo.kolorob.kolorobdemoversion.R;
import demo.kolorob.kolorobdemoversion.model.Entertainment.EntertainmentNewDBModel;
import demo.kolorob.kolorobdemoversion.utils.AppConstants;

/**
 * Created by arafat on 28/05/2016.
 */

public class DetailsInfoActivityEntertainmentNew extends BaseActivity <EntertainmentNewDBModel>{

    EntertainmentNewDBModel entertainment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_info_activity_entertainment_new);
        context = this;
        Intent intent = getIntent();
        if (null != intent) {
            entertainment = (EntertainmentNewDBModel) intent.getSerializableExtra(AppConstants.KEY_DETAILS_ENT);
        }

        viewBaseLayout(entertainment.getCommonModel());
        displayUniqueProperties();
        displayCommonProperties(entertainment.getCommonModel());
    }

    public void displayUniqueProperties(){

        CheckConcate("প্রতিষ্ঠানের  ধরণ", entertainment.getEntType());
        if(!entertainment.getEntType().equals(getReferences(entertainment.getCommonModel()))){
            CheckConcate("বিশেষত্ব", getReferences(entertainment.getCommonModel()));
        }
        if(entertainment.getEntryFee().equals(true)) CheckConcate("প্রবেশ মূল্য",  "প্রযোজ্য");

    }
}