package com.example.springboot;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

@Builder
@Data
public class UserDetails implements Serializable {
    protected String firstName;
    protected String lastName;
    protected String ssn;
    protected String phoneNumber;

    public UserDetails(String firstName, String lastName, String ssn, String phoneNumber) {

        this.firstName=firstName;
        this.lastName=lastName;
        this.ssn=ssn;
        this.phoneNumber=phoneNumber;
    }

    public String getName() {
            return (this.firstName + " " + this.lastName);
    }
}
