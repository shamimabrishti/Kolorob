package demo.kolorob.kolorobdemoversion.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import demo.kolorob.kolorobdemoversion.R;

/**
 * Created by Mazharul.Islam1 on 5/10/2016.
 */
public class DisplayAllJobList extends BaseAdapter
{
    Activity context;
    String title[];
    String salary_range[];
    long remaing_date[];
    String address[];
    String contract_number[];
    LinearLayout titlePart,salaryPart,addressPart,numberpart;
    int height,width;


    public DisplayAllJobList(Activity context, String[] title,String[] salary_range,long[] remaining_date, String[] address,String[] contract_number) {
        super();
        this.context = context;
        this.title = title;
        this.salary_range = salary_range;
        this.remaing_date = remaining_date;
        this.address =address;
        this.contract_number=contract_number;




    }

    public int getCount() {
        // TODO Auto-generated method stub
        return title.length;
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView title;
        TextView  salary_range;
        TextView remaining_date;
        TextView address;
        TextView contact_number;


    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.display_all_job_list, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.salary_range = (TextView) convertView.findViewById(R.id.salary_range);
            holder.remaining_date = (TextView) convertView.findViewById(R.id.remaining_date);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.contact_number = (TextView) convertView.findViewById(R.id.contact_number);


            convertView.setTag(holder);
        }

        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        titlePart=(LinearLayout)convertView.findViewById(R.id.titlePart);
        salaryPart=(LinearLayout)convertView.findViewById(R.id.salaryPart);
        addressPart=(LinearLayout)convertView.findViewById(R.id.addressPart);
        numberpart=(LinearLayout)convertView.findViewById(R.id.numberPart);
        DisplayMetrics displayMetrics =  context.getResources().getDisplayMetrics();
        height= displayMetrics.heightPixels;
        width=displayMetrics.widthPixels;

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) titlePart.getLayoutParams();

        params.width = width/3;
        titlePart.setLayoutParams(params);


        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) salaryPart.getLayoutParams();


        params1.width = width/3;
        salaryPart.setLayoutParams(params1);

        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) addressPart.getLayoutParams();

        params2.width = width/3;
        addressPart.setLayoutParams(params2);



        holder.title.setText("" +title[position]);
        holder.title.setTextSize(26);
        holder.salary_range.setText("স্যালারি : " + salary_range[position]);
        holder.remaining_date.setText("সময় বাকি আছেঃ " + remaing_date[position]);
        holder.address.setText("ঠিকানাঃ " + address[position]);
        holder.contact_number.setText("যোগাযোগের নম্বরঃ " + contract_number[position]);






        return convertView;
    }

}

