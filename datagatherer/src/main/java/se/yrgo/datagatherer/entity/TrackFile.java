package se.yrgo.datagatherer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TrackFile {

    @Id
    private String hashId;
    private String path;

    public TrackFile() {}

    public TrackFile(String hashId, String path) {
        this.hashId = hashId;
        this.path = path;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
