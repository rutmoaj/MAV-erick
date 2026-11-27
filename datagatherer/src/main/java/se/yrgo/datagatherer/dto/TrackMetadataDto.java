package se.yrgo.datagatherer.dto;
import se.yrgo.datagatherer.entity.TrackMetadata;

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
        String coverId
) {
    public static TrackMetadataDto createDto(TrackMetadata t) {

        return new TrackMetadataDto(
                t.getHashId(),
                t.getTitle(),
                t.getArtist(),
                t.getGenre(),
                t.getAlbum(),
                t.getYear(),
                t.getTrackNumber(),
                t.getDiscNumber(),
                t.getDurationSeconds(),
                t.getFileSizeBytes(),
                t.getCoverHashId()
        );
    }

}