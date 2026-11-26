package se.yrgo.datagatherer.dto;

import se.yrgo.datagatherer.metadata.MetadataExtractor;
import se.yrgo.datagatherer.metadata.RawTrack;

public record TrackMetadataDto(
        String hashId,

        String title,
        String artist,
        String genre,
        String album,
        String year,

        int trackNumber,
        int discNumber,
        int durationSeconds,
        long fileSizeBytes,
        String coverUrl
) {
    public static TrackMetadataDto createDto(RawTrack t) {

        return new TrackMetadataDto(
                t.hashId(),
                t.title(),
                t.artist(),
                t.genre(),
                t.album(),
                t.year(),
                t.trackNumber(),
                t.discNumber(),
                t.durationSeconds(),
                t.fileSizeBytes(),
                t.cover().hashId()
        );
    }

}