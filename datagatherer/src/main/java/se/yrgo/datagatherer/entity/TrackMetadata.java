package se.yrgo.datagatherer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TrackMetadata {
    @Id
    String hashId;
    String path;

    String title;
    String artist;
    String genre;
    String album;
    String lyrics;
    String year;

    int trackNumber;
    int discNumber;
    int durationSeconds;
    long fileSizeBytes;
    long lastModified;
    String coverHashId;
    String coverPath;

    public TrackMetadata() {
    }

    public TrackMetadata(
            String hashId,
            String path,
            String title,
            String artist,
            String genre,
            String album,
            String lyrics,
            String year,
            int trackNumber,
            int discNumber,
            int durationSeconds,
            long fileSizeBytes,
            long lastModified,
            String coverHashId,
            String coverPath
    ) {
        this.hashId = hashId;
        this.path = path;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.album = album;
        this.lyrics = lyrics;
        this.year = year;
        this.trackNumber = trackNumber;
        this.discNumber = discNumber;
        this.durationSeconds = durationSeconds;
        this.fileSizeBytes = fileSizeBytes;
        this.lastModified = lastModified;
        this.coverHashId = coverHashId;
        this.coverPath = coverPath;
    }

    public String getHashId() {
        return hashId;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public String getAlbum() {
        return album;
    }

    public String getLyrics() {
        return lyrics;
    }

    public String getYear() {
        return year;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public int getDiscNumber() {
        return discNumber;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public long getFileSizeBytes() {
        return fileSizeBytes;
    }

    public long getLastModified() {
        return lastModified;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public String getCoverHashId() {
        return coverHashId;
    }
}

