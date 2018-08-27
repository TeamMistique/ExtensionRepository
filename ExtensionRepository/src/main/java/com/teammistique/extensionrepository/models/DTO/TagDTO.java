package com.teammistique.extensionrepository.models.DTO;

public class TagDTO {
    private int tagID;
    private String tagName;

    public TagDTO() {
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
