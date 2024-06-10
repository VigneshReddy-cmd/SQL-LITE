package com.multiscreen.sq_lite;

public class Students {
    private String name;
    private String rollno;
    private String section;

    public Students(String name, String rollno, String section) {
        this.name = name;
        this.rollno = rollno;
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
