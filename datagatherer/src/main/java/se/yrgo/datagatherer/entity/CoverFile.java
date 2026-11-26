package se.yrgo.datagatherer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class CoverFile {

    @Id
    private String hashId;
    private String path;

    public CoverFile() {}

    public CoverFile(String hashId, String path) {
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

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof CoverFile coverFile)) return false;

        return Objects.equals(hashId, coverFile.hashId) && Objects.equals(path, coverFile.path);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(hashId);
        result = 31 * result + Objects.hashCode(path);
        return result;
    }
}
