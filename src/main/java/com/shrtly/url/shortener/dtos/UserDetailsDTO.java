package com.shrtly.url.shortener.dtos;

public class UserDetailsDTO {
    private Long userId;
    private String username;
    private String email;
    private String userRole;
    private String fullName;

    public UserDetailsDTO(Long userId, String username, String email, String userRole, String fullName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.userRole = userRole;
        this.fullName = fullName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
