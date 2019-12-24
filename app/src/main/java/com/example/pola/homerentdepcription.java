package com.example.pola;

public class homerentdepcription {
 String Currenttime,URL,currentdate,homedescription,homelocation,homerent,title,uid;

    public homerentdepcription() {
    }

    public homerentdepcription(String currenttime, String URL, String currentdate, String homedescription, String homelocation, String homerent, String title, String uid) {
        Currenttime = currenttime;
        this.URL = URL;
        this.currentdate = currentdate;
        this.homedescription = homedescription;
        this.homelocation = homelocation;
        this.homerent = homerent;
        this.title = title;
        this.uid = uid;
    }

    public String getCurrenttime() {
        return Currenttime;
    }

    public void setCurrenttime(String currenttime) {
        Currenttime = currenttime;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public String getHomedescription() {
        return homedescription;
    }

    public void setHomedescription(String homedescription) {
        this.homedescription = homedescription;
    }

    public String getHomelocation() {
        return homelocation;
    }

    public void setHomelocation(String homelocation) {
        this.homelocation = homelocation;
    }

    public String getHomerent() {
        return homerent;
    }

    public void setHomerent(String homerent) {
        this.homerent = homerent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

