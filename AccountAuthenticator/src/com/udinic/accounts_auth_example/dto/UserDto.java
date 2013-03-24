package com.udinic.accounts_auth_example.dto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * User: alexv
 * Date: 11/10/11
 * Time: 14:45
 */
public class UserDto {
    private String id;
    private String name;
    private String email;
    private String password;
    private List<String> phoneNumbers = new ArrayList<String>();
    private InstallationDetailsDto instDetails;

    // Remove in the future
    private String facebookId;
    private String facebookAccessToken;
    private boolean anonymous;
    private Date creationDate;
    private boolean fake;

    public UserDto() {
    }


    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (id != null ? !id.equals(userDto.id) : userDto.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


    public InstallationDetailsDto getInstDetails() {
        return instDetails;
    }

    public void setInstDetails(InstallationDetailsDto instDetails) {
        this.instDetails = instDetails;
    }

    public String getEmail() {
        return email != null ? email.toLowerCase().trim() : null;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getEmails() {
        return email != null ? asList(email.toLowerCase()) : null;
    }

    public void setEmails(List<String> emails) {
        if (!emails.isEmpty()) {
            this.email = emails.get(0);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getFacebookAccessToken() {
        return facebookAccessToken;
    }

    public void setFacebookAccessToken(String facebookAccessToken) {
        this.facebookAccessToken = facebookAccessToken;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public boolean isFake() {
        return fake;
    }

    public void setFake(boolean fake) {
        this.fake = fake;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", id='" + getId() + '\'' +
                '}';
    }



}
