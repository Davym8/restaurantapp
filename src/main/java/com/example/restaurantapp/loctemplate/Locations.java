package com.example.restaurantapp.loctemplate;

public class Locations
{
    private int index;
    private String name;
    private String latitude;
    private String longitude;
    public Locations()
    {

    }
    public Locations(int index,String name, String latitude, String longitude)
    {
        this.index = index;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getIndex()
    {
        return index;
    }
    public void setIndex(int index)
    {
        this.index = index;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getLatitude()
    {
        return latitude;
    }
    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }
    public String getLongitude()
    {
        return longitude;
    }
    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

}