package com.example.springboot;

import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        Logger logger = LogManager.getLogger(UserDetails.class.getName());
        logger.debug("Requested Name: " + this.firstName + " " + this.lastName);
        return (this.firstName + " " + this.lastName);
    }
}
