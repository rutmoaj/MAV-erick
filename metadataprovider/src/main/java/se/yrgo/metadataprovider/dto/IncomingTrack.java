package se.yrgo.metadataprovider.dto;

public record IncomingTrack(String hashId, String title, String artist, String genre, String album, String year,
        int trackNumber, int discNumber, int durationSeconds, long fileSizeBytes, Cover cover) {

    public record Cover(String hashId) {
    }
}
