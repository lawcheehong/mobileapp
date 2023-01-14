package com.example.fyp_management_mobileapp;

public class submissionClass {

    public String name, url, studentcomment, title_one, title_two, title_three;


    public submissionClass(String name, String url, String studentcomment) {
        this.name = name;
        this.url = url;
        this.studentcomment = studentcomment;
    }

    public submissionClass(String name, String url, String title_one, String title_two, String title_three) {
        this.name = name;
        this.url = url;
        this.title_one = title_one;
        this.title_two = title_two;
        this.title_three = title_three;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStudentcomment() {
        return studentcomment;
    }

    public void setStudentcomment(String studentcomment) {
        this.studentcomment = studentcomment;
    }

    public String getTitle_one() {
        return title_one;
    }

    public void setTitle_one(String title_one) {
        this.title_one = title_one;
    }

    public String getTitle_two() {
        return title_two;
    }

    public void setTitle_two(String title_two) {
        this.title_two = title_two;
    }

    public String getTitle_three() {
        return title_three;
    }

    public void setTitle_three(String title_three) {
        this.title_three = title_three;
    }






    public submissionClass() {
    }

}
