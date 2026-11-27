package se.yrgo.datagatherer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.yrgo.datagatherer.dto.TrackMetadataDto;
import se.yrgo.datagatherer.entity.TrackMetadata;
import se.yrgo.datagatherer.metadata_extraction.MetadataExtractor;
import se.yrgo.datagatherer.service.MetadataService;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

    MetadataService metadataService;

    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @PostMapping
    public ResponseEntity<List<TrackMetadataDto>> generateMetadata() {
        Set<TrackMetadata> tracks = MetadataExtractor.scanAllTracks(metadataService);
        return ResponseEntity.ok(
                tracks.stream()
                        .map(TrackMetadataDto::createDto)
                        .toList()
        );
    }

    @GetMapping
    public ResponseEntity<List<TrackMetadataDto>> getCachedTrackMetadata() {
        var tracks =  metadataService.getCachedMetadata().stream()
                .map(TrackMetadataDto::createDto).toList();

        return ResponseEntity.ok(tracks);
    }

}
