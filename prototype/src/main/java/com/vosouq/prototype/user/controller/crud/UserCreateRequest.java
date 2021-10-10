package com.vosouq.prototype.user.controller.crud;

import com.vosouq.commons.model.Gender;
import com.vosouq.commons.validator.PhoneNumberConstraint;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserCreateRequest {

    @NotNull
    @NotEmpty
    @ApiModelProperty(required = true)
    private String firstName;
    @NotNull
    @NotEmpty
    @ApiModelProperty(required = true)
    private String lastName;
    @NotNull
    @ApiModelProperty(required = true, allowableValues = "MALE, FEMALE")
    private Gender gender;
    @NotNull
    @NotEmpty
    @PhoneNumberConstraint
    @ApiModelProperty(required = true, example = "+989124442211")
    private String phoneNumber;
    @Min(1)
    @Max(150)
    @ApiModelProperty(required = true, allowableValues = "range[1,150]")
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
