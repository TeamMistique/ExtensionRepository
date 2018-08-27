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
}
