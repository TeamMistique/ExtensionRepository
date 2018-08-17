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

  //  @ManyToOne
    @Column(name = "StatusID")
  //  @JsonManagedReference
    private int status;

    @Column(name ="Name")
    private String name;

    @Column(name ="Description")
    private String description;

//  @ManyToOne
  @Column(name = "Owner") // @Join
 //  @JsonManagedReference
    private String owner;

    @Column(name ="Downloads")
    private int downloadsCounter;

    @Column(name ="File")
    private String file;

    @Column(name ="Link")
    private String link;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "extension_tag",
//            joinColumns = {@JoinColumn(name = "ExtensionID")},
//            inverseJoinColumns = {@JoinColumn(name = "TagID")}
//    )
//    @JsonManagedReference
//    private List<Tag> tags;

    public Extension() {
    }

    public Extension(int status, String name, String description, String owner, int downloadsCounter, String file, String link, List<Tag> tags) {
        this.status = status;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.downloadsCounter = downloadsCounter;
        this.file = file;
        this.link = link;

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
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


}
