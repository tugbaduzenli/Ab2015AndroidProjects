package com.example.tugbasalih.ab2015sqlite;

/**
 * Created by tugba on 02.02.2015.
 */
public class Customer {

    private long id;
    private String name;
    private String age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        if( Integer.parseInt(age) < 18)
            throw new ArithmeticException("asdfdasd");
        this.age = age;
    }

    public String toString()
    {
        return  this.getName() +"-" + this.getAge();
    }
}
