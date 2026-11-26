package se.yrgo.datagatherer.metadata;

public record RawTrack(
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
        RawCover cover
) {
}