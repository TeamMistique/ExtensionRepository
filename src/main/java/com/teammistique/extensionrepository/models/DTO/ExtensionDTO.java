package com.teammistique.extensionrepository.models.DTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ExtensionDTO implements Serializable {
    private int  id;
    private String name;
    private String description;
    private String link;
    private String file;
    private String image;
    private int downloadsCounter;
    private Date publishedDate;
    private Date createdDate;
    private Date featuredDate;
    private List<String> tagNames;
    private String username;
    private double version;
    private Date lastSuccessfulSync;
    private Date lastFailedSync;
    private String failedSyncDetails;
    private int issuesCounter;
    private int pullRequestsCounter;
    private Date lastCommitDate;


    public ExtensionDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDownloadsCounter() {
        return downloadsCounter;
    }

    public void setDownloadsCounter(int downloadsCounter) {
        this.downloadsCounter = downloadsCounter;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getFeaturedDate() {
        return featuredDate;
    }

    public void setFeaturedDate(Date featuredDate) {
        this.featuredDate = featuredDate;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public Date getLastSuccessfulSync() {
        return lastSuccessfulSync;
    }

    public void setLastSuccessfulSync(Date lastSuccessfulSync) {
        this.lastSuccessfulSync = lastSuccessfulSync;
    }

    public Date getLastFailedSync() {
        return lastFailedSync;
    }

    public void setLastFailedSync(Date lastFailedSync) {
        this.lastFailedSync = lastFailedSync;
    }

    public String getFailedSyncDetails() {
        return failedSyncDetails;
    }

    public void setFailedSyncDetails(String failedSyncDetails) {
        this.failedSyncDetails = failedSyncDetails;
    }

    public int getIssuesCounter() {
        return issuesCounter;
    }

    public void setIssuesCounter(int issuesCounter) {
        this.issuesCounter = issuesCounter;
    }

    public int getPullRequestsCounter() {
        return pullRequestsCounter;
    }

    public void setPullRequestsCounter(int pullRequestsCounter) {
        this.pullRequestsCounter = pullRequestsCounter;
    }

    public Date getLastCommitDate() {
        return lastCommitDate;
    }

    public void setLastCommitDate(Date lastCommitDate) {
        this.lastCommitDate = lastCommitDate;
    }
}
