package com.vosouq.prototype.user.controller.crud;

import com.vosouq.commons.model.Gender;
import io.swagger.annotations.ApiModelProperty;

public class UserUpdateRequest {

    private String firstName;
    private String lastName;
    @ApiModelProperty(allowableValues = "MALE, FEMALE")
    private Gender gender;
    @ApiModelProperty(example = "+989124442211")
    private String phoneNumber;
    @ApiModelProperty(allowableValues = "range[1,150]")
    private Integer age;
    private Integer income;
    private String address;
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
