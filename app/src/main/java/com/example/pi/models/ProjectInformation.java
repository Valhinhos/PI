package com.example.pi.models;

public class ProjectInformation {

    private String projectName;
    private String professorName;
    private String projectResume;
    private String projectContact;
    private String imageName;
    private String raMatching;
    private String userUploader;
    private String userProfileID;
    private String userID;
    private String projectClass;

    public ProjectInformation(){}

    public ProjectInformation(String projectName, String professorName, String projectResume, String projectContact, String imageName, String raMatching, String userUploader, String userProfileID, String userID, String projectClass) {
        this.projectName = projectName;
        this.professorName = professorName;
        this.projectResume = projectResume;
        this.projectContact = projectContact;
        this.imageName = imageName;
        this.raMatching = raMatching;
        this.userUploader = userUploader;
        this.userProfileID = userProfileID;
        this.userID = userID;
        this.projectClass = projectClass;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getProjectResume() {
        return projectResume;
    }

    public void setProjectResume(String projectResume) {
        this.projectResume = projectResume;
    }

    public String getProjectContact() {
        return projectContact;
    }

    public void setProjectContact(String projectContact) {
        this.projectContact = projectContact;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getRaMatching() {
        return raMatching;
    }

    public void setRaMatching(String raMatching) {
        this.raMatching = raMatching;
    }

    public String getUserUploader() {
        return userUploader;
    }

    public void setUserUploader(String userUploader) {
        this.userUploader = userUploader;
    }

    public String getUserProfileID() {
        return userProfileID;
    }

    public void setUserProfileID(String userProfileID) {
        this.userProfileID = userProfileID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProjectClass() {
        return projectClass;
    }

    public void setProjectClass(String projectClass) {
        this.projectClass = projectClass;
    }
}
