package se.yrgo.datagatherer.metadata_extraction;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import se.yrgo.datagatherer.entity.TrackMetadata;
import se.yrgo.datagatherer.service.MetadataService;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MetadataExtractor {

    public record Cover(String hashId, String path){}
    private static final Path MUSIC_DIR = Path.of("music");

    /**
     * Scans the "music" folder in the project root for any .opus files
     * @return a List of Paths
     */
    public static List<Path> scanMusicDirectoryForSongs(){
        try (Stream<Path> s = Files.walk(MUSIC_DIR)) {
            return s.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".opus"))
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static Set<TrackMetadata> scanAllTracks(MetadataService service) {
        List<Path> files = MetadataExtractor.scanMusicDirectoryForSongs();

        Set<TrackMetadata> tracks = new HashSet<>();
        Map<String, TrackMetadata> cached = service.getCachedMetadata().stream()
                .collect(Collectors.toMap(TrackMetadata::getPath, Function.identity()));

        Set<MetadataExtractor.Cover> coverPaths = new HashSet<>();

        System.out.println("Scanning " + files.size() + " files");
        int index = 0;
        for (Path file : files) {
            if(index % 100 == 0){
                System.out.printf("%d/%d\n", index + 1, files.size());
            }
            index++;

            try {
                TrackMetadata trackMetadata = cached.get(file.toString());

                long size = Files.size(file);
                long modifiedTime = Files.getLastModifiedTime(file).toMillis();

                if (trackMetadata == null
                        || trackMetadata.getFileSizeBytes() != size
                        || trackMetadata.getLastModified() != modifiedTime) {
                    trackMetadata = MetadataExtractor.extractMetaFromFile(file, coverPaths);
                }

                tracks.add(trackMetadata);
            } catch (Exception e){
                System.err.println(e.getMessage());
            }
        }

        service.persist(tracks);

        return tracks;
    }


    public static TrackMetadata extractMetaFromFile(Path path, Set<Cover> covers) throws Exception{
        AudioFile audioFile = AudioFileIO.read(path.toFile());
        Tag tag = audioFile.getTag();

        int discNumber = tag.getFirst(FieldKey.DISC_NO).isEmpty() ? 1 :
                Integer.parseInt(tag.getFirst(FieldKey.DISC_NO));

        Cover cover = findCover(path, covers);

        return new TrackMetadata(
                sha256(path),
                path.toString(),
                tag.getFirst(FieldKey.TITLE),
                tag.getFirst(FieldKey.ARTIST),
                tag.getFirst(FieldKey.GENRE),
                tag.getFirst(FieldKey.ALBUM),
                tag.getFirst(FieldKey.LYRICS),
                tag.getFirst(FieldKey.YEAR),
                parseTrackNumber(tag.getFirst(FieldKey.TRACK)),
                discNumber,
                audioFile.getAudioHeader().getTrackLength(),
                Files.size(path),
                Files.getLastModifiedTime(path).toMillis(),
                cover != null ? cover.hashId() : null,
                cover != null ? cover.path() : null
        );
    }

    /**
     * Parses a track number string to int.
     * @param trackNumber A string such as "03"
     * @return an int of the number, or 0 if it fails.
     */
    private static int parseTrackNumber(String trackNumber) {
        try {
            return Integer.parseInt(trackNumber);
        } catch (NumberFormatException e) {
            System.err.println("Failed to parse track number: " + trackNumber);
            return 0;
        }
    }

    /**
     * Finds and returns a Cover record object if a cover is found in the same directory as the sound file.
     * Since many tracks can have the same cover, it first checks if a cover has been found before.
     * @param soundFilePath - the track, such as song.opus
     * @param covers - Set of found covers. Will return from this set if already stored in it
     * @return Cover object or null
     */
    private static Cover findCover(Path soundFilePath, Set<Cover> covers) {
        Path path = soundFilePath.resolveSibling("cover.jpg");
        String pathString = path.toString();

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

    /**
     * Returns a 16-char length unique string.
     * @param path - The file to be hashed
     * @return A string of the hash, such as g0u2CKRvhELqF1Vx
     */
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
