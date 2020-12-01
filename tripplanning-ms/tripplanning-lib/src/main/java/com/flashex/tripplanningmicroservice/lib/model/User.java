package com.flashex.tripplanningmicroservice.lib.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Setter
@Getter
@NoArgsConstructor
public class User {

    private String userId;
    private String name;
    private String email;
    private long contactNumber;
    private String role;
    private String userName;
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
