package com.teammistique.extensionrepository.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "extensions")
public class Extension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExtensionID")
    private int id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "Owner")
    @JsonManagedReference
    private User owner;

    @Column(name = "Downloads")
    private int downloadsCounter;

    @Column(name = "File")
    private String file;

    @Column(name = "Link")
    private String link;

    @Column(name = "PublishedDate")
    private Date publishedDate;

    @Column(name = "FeaturedDate")
    private Date featuredDate;

    @Column(name = "CreatedDate")
    private Date createdDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "extension_tag",
            joinColumns = {@JoinColumn(name = "ExtensionID")},
            inverseJoinColumns = {@JoinColumn(name = "TagID")}
    )
    @JsonManagedReference
    private List<Tag> tags;

    @Column(name = "Issues")
    private int issuesCounter;

    @Column(name = "PullRequests")
    private int pullRequestsCounter;

    @Column(name = "LastCommit")
    private Date lastCommitDate;

    @OneToOne
    @JoinColumn(name = "ImageID")
    @JsonManagedReference
    private Image image;


    public Extension() {
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getDownloadsCounter() {
        return downloadsCounter;
    }

    public void setDownloadsCounter(int downloadsCounter) {
        this.downloadsCounter = downloadsCounter;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
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

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Date getFeaturedDate() {
        return featuredDate;
    }

    public void setFeaturedDate(Date featuredDate) {
        this.featuredDate = featuredDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
