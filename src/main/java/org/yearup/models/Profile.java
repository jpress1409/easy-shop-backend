package org.yearup.models;

public class Profile
{
    private int userId;
    private String firstName = "";
    private String lastName = "";
    private String phone = "";
    private String email = "";
    private String address = "";
    private String city = "";
    private String state = "";
    private String zip = "";

    public Profile()
    {
    }

    public Profile(int userId, String firstName, String lastName, String phone, String email, String address, String city, String state, String zip)
    {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getEmail()
    {
        return email;
    }

    public String getAddress()
    {
        return address;
    }

    public String getCity()
    {
        return city;
    }

    public String getState()
    {
        return state;
    }

    public String getZip()
    {
        return zip;
    }
}
