package demo.kolorob.kolorobdemoversion.model.Entertainment;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.Serializable;

/**
 * Created by arafat on 1/5/2016.
 */
public class EntertainmentServiceProviderItemNew implements Serializable {
    private String nodeId;
    private int entSubCategoryId;
    private String nodeName;
    private String nodeNameBn;
    public static JSONObject contact_info;


    private String nodeDesignation;
    private String nodeContact;
    private String nodeEmail;
    private String nodeAdditional;
    private String nodeWebsite;
    private String nodeFacebook;
    private String nodeRegisteredWith;
    private String nodeRegistrationNumber;


    private String nodeType;
    private String area;
    private String address;
    private String latitude;
    private String longitude;
    private int categoryId;
    private String openingtime;
    private String closingtime;
    private String breaktime;
    private String breaktime2;
    private String road;
    private String block;
    private String landmark;
    private String floor;
    private String house_name;
    private String house_no;
    private String line;
    private String avenue;
    private String post_office;
    private String police_station;
    private String city;
    private String off_day;





    public EntertainmentServiceProviderItemNew(
            String nodeId,
            int entSubCategoryId,
            String nodeName,
            String nodeNameBn,

            String nodeDesignation,
            String nodeContact,
            String nodeEmail,
            String nodeAdditional,
            String nodeWebsite,
            String nodeFacebook,
            String nodeRegisteredWith,
            String nodeRegistrationNumber,

            String nodeType,
            String area,
            String address,
            String latitude,
            String longitude,
            int categoryId,
            String openingtime,
            String breaktime,
            String closingtime,
            String road,
            String block,
            String landmark,
            String breaktime2, String floor,
                    String house_name,
                    String house_no,
                    String line,
                    String avenue,
                    String post_office,
                    String police_station,
                    String city,
                    String off_day){

        this.nodeId=nodeId;
        this.entSubCategoryId=entSubCategoryId;
        this.nodeName=nodeName;
        this.nodeNameBn=nodeNameBn;

        this.nodeDesignation=nodeDesignation;
        this.nodeContact=nodeContact;
        this.nodeEmail=nodeEmail;
        this.nodeAdditional=nodeAdditional;
        this.nodeWebsite=nodeWebsite;
        this.nodeFacebook=nodeFacebook;
        this.nodeRegisteredWith=nodeRegisteredWith;
        this.nodeRegistrationNumber=nodeRegistrationNumber;

        this.nodeType=nodeType;
        this.area=area;
        this.address=address;
        this.latitude=latitude;
        this.longitude=longitude;
        this.categoryId=categoryId;
        this.openingtime = openingtime;
        this.breaktime = breaktime;
        this.closingtime = closingtime;
        this.road = road;
        this.block = block;
        this.landmark=landmark;
        this.breaktime2=breaktime2;
        this.floor = floor;
        this.house_no = house_no;
        this.house_name = house_name;
        this.line = line;
        this.avenue = avenue;
        this.post_office = post_office;
        this.police_station = police_station;
        this.city = city;
        this.off_day = off_day;



    }

    public String getOpeningtime() {
        return openingtime;
    }

    public String getClosingtime() {
        return closingtime;
    }

    public String getBreaktime() {
        return breaktime;
    }

    public String getBreaktime2() {
        return breaktime2;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getRoad() {
        return road;
    }

    public String getBlock() {
        return block;
    }

    public String getFloor() {
        return floor;
    }

    public String getHouse_no() {
        return house_no;
    }

    public String getHouse_name() {
        return house_name;
    }

    public String getLine() {
        return line;
    }

    public String getAvenue() {
        return avenue;
    }

    public String getPost_office() {
        return post_office;
    }

    public String getPolice_station() {
        return police_station;
    }

    public String getCity() {
        return city;
    }

    public String getOff_day() {
        return off_day;
    }

    public static EntertainmentServiceProviderItemNew parseEntertainmentServiceProviderItem(JSONObject jo) throws JSONException
    {

        String _nodeId= jo.getString("id");


        String _nodeName = jo.getString("node_name");
        String _nodeNameBn= jo.getString("node_bn");

        String _latitude = jo.getString("lat");

        String _longitude= jo.getString("lon");
        String _nodeType = jo.getString("node_type");

        String floor=jo.getString("floor");

        String house_name=jo.getString("house_name");

        String house_no=jo.getString("house_no");

        String _road=jo.getString("road");
        String line=jo.getString("line");
        String avenue=jo.getString("avenue");
        String _block =jo.getString("block");
        String _area = jo.getString("area");
        String _landmark=jo.getString("landmark");
        String post_office=jo.getString("post_office");
        String police_station=jo.getString("police_station");
        String city=jo.getString("city");
        String _nodeContact = jo.getString("node_contact");
        String _nodeEmail = jo.getString("node_email");
        String _nodeWebsite = jo.getString("node_website");
        String _nodeFacebook = jo.getString("node_facebook");
        String _nodeDesignation = jo.getString("node_designation");
        String _openingtime=jo.getString("opening_time");

        String _breaktime=jo.getString("break_time");
        String _closingtime=jo.getString("closing_time");
        String _breaktime2=jo.getString("break_time");
        String off_day=jo.getString("off_day");



        String _nodeRegisteredWith = jo.getString("node_registered_with");
        String _nodeRegistrationNumber = jo.getString("node_registered_number");


        Integer _entSubCategoryId= 2;




        String _nodeAdditional = "additional";





        String _address = "address";

        int _categoryId= jo.getInt("category");





        return new EntertainmentServiceProviderItemNew(
                _nodeId,
                _entSubCategoryId,
                _nodeName,
                _nodeNameBn,
                _nodeDesignation,
                _nodeContact,
                _nodeEmail,
                _nodeAdditional,
                _nodeWebsite,
                _nodeFacebook,
                _nodeRegisteredWith,
                _nodeRegistrationNumber,
                _nodeType,
                _area,
                _address,
                _latitude,
                _longitude,
                _categoryId,
                _openingtime,
                _breaktime,
                _closingtime,
                _road, _block,_landmark, _breaktime2,floor,
                 house_name,
                 house_no,
                line,
                avenue,
                post_office,
                 police_station,
                 city,
                off_day);
    }


    public String getNodeId() {
        return nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public int getEntSubCategoryId() {
        return entSubCategoryId;
    }

    public String getNodeNameBn() {
        return nodeNameBn;
    }



    public String getNodeDesignation() {
        return nodeDesignation;
    }

    public String getNodeEmail() {
        return nodeEmail;
    }

    public String getNodeAdditional() {
        return nodeAdditional;
    }

    public String getNodeContact() {
        return nodeContact;
    }

    public String getNodeWebsite() {
        return nodeWebsite;
    }

    public String getNodeFacebook() {
        return nodeFacebook;
    }

    public String getNodeRegisteredWith() {
        return nodeRegisteredWith;
    }

    public String getNodeRegistrationNumber() {
        return nodeRegistrationNumber;
    }



    public String getNodeType() {
        return nodeType;
    }

    public String getArea() {
        return area;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
    public int getCategoryId() {
        return categoryId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }




}