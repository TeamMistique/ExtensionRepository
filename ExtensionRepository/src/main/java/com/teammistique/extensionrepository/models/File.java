package com.teammistique.extensionrepository.models;

import java.io.Serializable;

public class File implements Serializable {
    private String fileName;
    private String downloadURI;
    private String fileType;
    private long size;

    public File(String fileName, String downloadURI, String fileType, long size) {
        this.fileName = fileName;
        this.downloadURI = downloadURI;
        this.fileType = fileType;
        this.size = size;
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
