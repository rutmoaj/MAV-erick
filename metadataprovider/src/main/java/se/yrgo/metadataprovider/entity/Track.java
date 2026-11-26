package se.yrgo.metadataprovider.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String hashId;
    private String title;
    private int trackNumber;
    private int discNumber;
    private int durationSeconds;
    private long fileSizeBytes;
    @ManyToOne
    @JsonIgnore
    private Album album;

    public Track() {
    }

    public Track(Long id, String hashId, String title, int trackNumber, int discNumber, int durationSeconds,
            long fileSizeBytes) {
        this.id = id;
        this.hashId = hashId;
        this.title = title;
        this.trackNumber = trackNumber;
        this.discNumber = discNumber;
        this.durationSeconds = durationSeconds;
        this.fileSizeBytes = fileSizeBytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(int discNumber) {
        this.discNumber = discNumber;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public long getFileSizeBytes() {
        return fileSizeBytes;
    }

    public void setFileSizeBytes(long fileSizeBytes) {
        this.fileSizeBytes = fileSizeBytes;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

}
