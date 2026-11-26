package se.yrgo.datagatherer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.yrgo.datagatherer.entity.CoverFile;
import se.yrgo.datagatherer.entity.TrackFile;
import se.yrgo.datagatherer.metadata.MetadataExtractor;
import se.yrgo.datagatherer.repository.CoverFileRepository;
import se.yrgo.datagatherer.repository.TrackFileRepository;

import java.util.List;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

    CoverFileRepository coverFileRepository;
    TrackFileRepository trackFileRepository;

    public MetadataController(CoverFileRepository coverFileRepository, TrackFileRepository trackFileRepository) {
        this.coverFileRepository = coverFileRepository;
        this.trackFileRepository = trackFileRepository;
    }

    @PostMapping
    public ResponseEntity<List<MetadataExtractor.Track>> generateMetadata() {

        coverFileRepository.deleteAll();
        trackFileRepository.deleteAll();

        List<MetadataExtractor.Track> tracks = MetadataExtractor.extractAllMetadata();

        List<TrackFile> trackFiles = tracks.stream()
                .map(t -> new TrackFile(t.hashId(), t.path()))
                .toList();

        List<CoverFile> coverFiles = tracks.stream()
                .map(t -> new CoverFile(t.hashId(), t.path()))
                .toList();

        trackFileRepository.saveAll(trackFiles);
        coverFileRepository.saveAll(coverFiles);

        return ResponseEntity.ok(tracks);
    }

}
