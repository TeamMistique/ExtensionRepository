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

    @Column(name = "Published")
    private int status;

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

    @Column(name = "Featured")
    private int featured;

    public Extension() {
    }

    public Extension(int status, String name, String description, User owner, int downloadsCounter, String file, String link, List<Tag> tags, int issuesCounter, int pullRequestsCounter, Date lastCommitDate, int featured) {
        this.status = status;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.downloadsCounter = downloadsCounter;
        this.file = file;
        this.link = link;
        this.tags = tags;
        this.issuesCounter = issuesCounter;
        this.pullRequestsCounter = pullRequestsCounter;
        this.lastCommitDate = lastCommitDate;
        this.featured = featured;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }
}
