package com.teammistique.extensionrepository.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "extensions")
public class Extension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExtensionID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "StatusID")
    @JsonManagedReference
    private Status status;

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

    public Extension() {
    }

    public Extension(Status status, String name, String description, User owner, int downloadsCounter, String file, String link, List<Tag> tags) {
        this.status = status;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.downloadsCounter = downloadsCounter;
        this.file = file;
        this.link = link;
        this.tags = tags;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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
}
