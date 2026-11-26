package se.yrgo.datagatherer.metadata;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;

public class MetadataExtractor {

    public record Track(
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
            Cover cover
    ) {
    }

    private record Cover(
            String hashId,
            String path
    ) {
    }

    private static Path MUSIC_DIR = Path.of("music");
    private static Set<Cover> covers = new HashSet<>();


    public static List<Track> extractAllMetadata() {
        try {
            List<Track> tracks = new ArrayList<>();
            for (Path file : Files.walk(MUSIC_DIR).toList()) {
                if (!file.getFileName().toString().endsWith(".opus")) {
                    continue;
                }
                System.out.println(file.getFileName().toString());
                Track track = extractMetaFromFile(file);
                tracks.add(track);
            }
            return tracks;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    private static Track extractMetaFromFile(Path path) throws Exception {
        AudioFile audioFile = AudioFileIO.read(path.toFile());
        Tag tag = audioFile.getTag();

        int discNumber = tag.getFirst(FieldKey.DISC_NO).isEmpty() ? 1 :
                Integer.parseInt(tag.getFirst(FieldKey.DISC_NO));

        return new Track(
                sha256(path),
                MUSIC_DIR.relativize(path).toString(),

                tag.getFirst(FieldKey.TITLE),
                tag.getFirst(FieldKey.ARTIST),
                tag.getFirst(FieldKey.GENRE),
                tag.getFirst(FieldKey.ALBUM),
                tag.getFirst(FieldKey.LYRICS),
                tag.getFirst(FieldKey.YEAR),

                Integer.parseInt(tag.getFirst(FieldKey.TRACK)),
                discNumber,
                audioFile.getAudioHeader().getTrackLength(),
                Files.size(path),
                findCover(path)
        );
    }

    private static Cover findCover(Path soundFilePath) {
        Path path = soundFilePath.resolveSibling("cover.jpg");
        String pathString = MUSIC_DIR.relativize(path).toString();

        for (Cover cover : covers) {
            if (cover.path().equals(pathString)) {
                return cover;
            }
        }

        if (!Files.isRegularFile(path) || !Files.isReadable(path)) {
            return null;
        }

        Cover cover = new Cover(sha256(path), pathString);
        covers.add(cover);

        return cover;
    }

    private static String sha256(Path path) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            try (InputStream input = Files.newInputStream(path)) {
                byte[] buffer = new byte[8192];
                int bytesRead;

                while ((bytesRead = input.read(buffer)) != -1) {
                    digest.update(buffer, 0, bytesRead);
                }
            }

            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(digest.digest())
                    .substring(0, 16);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
