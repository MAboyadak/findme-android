package com.example.pc.findme;

public class ListItem {

    private int id;
    private String fullName;
    private int age;
   private String city;
    private String personData;
    private String gender;
    private String userName;
    private String userImg;
    private String postTime;
    private int number;
    private String imgUrl;






    public ListItem(int id, String fullName, String gender, int age, String imgUrl ,String city,String personData,String userName,String userImg,String postTime,int number)
    {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.userImg = userImg;
        this.postTime = postTime;
        this.number = number;
        this.gender = gender;
        this.city=city;
        this.personData=personData;

        this.age    = age;
        this.imgUrl = imgUrl;
    }

    public String getUserImg() {
        return userImg;
    }

    public String getPostTime() {
        return postTime;
    }

    public int getNumber() {
        return number;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName()
    {
        return fullName;
    }
    public String getGender()
    {
        return gender;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPersonData() {
        return personData;
    }

    public void setPersonData(String personData) {
        this.personData = personData;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId()
    {
        return id;
    }

    public int getAge()
    {
        return age;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }


}
