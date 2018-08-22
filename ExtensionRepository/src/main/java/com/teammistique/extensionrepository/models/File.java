package com.teammistique.extensionrepository.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name="files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FileID")
    private int fileID;

    @Column(name = "FileName")
    private String fileName;

    @Column(name = "DownloadUri")
    private String downloadURI;

    @Column(name = "FileType")
    private String fileType;

    @Column(name = "Size")
    private long size;

    @OneToOne(mappedBy = "file")
    @JsonBackReference
    private  Extension extension;

    public File() {
    }

    public File(String fileName, String downloadURI, String fileType, long size) {
        this.fileName = fileName;
        this.downloadURI = downloadURI;
        this.fileType = fileType;
        this.size = size;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadURI() {
        return downloadURI;
    }

    public void setDownloadURI(String downloadURI) {
        this.downloadURI = downloadURI;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
