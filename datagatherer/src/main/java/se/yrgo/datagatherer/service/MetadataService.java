package se.yrgo.datagatherer.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.yrgo.datagatherer.entity.TrackMetadata;
import se.yrgo.datagatherer.metadata_extraction.MetadataExtractor;
import se.yrgo.datagatherer.repository.TrackMetaDataRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class MetadataService {


    TrackMetaDataRepository trackMetaDataRepository;

    public MetadataService(TrackMetaDataRepository trackMetaDataRepository) {
        this.trackMetaDataRepository = trackMetaDataRepository;
    }

    public List<TrackMetadata> getCachedMetadata() {
        return trackMetaDataRepository.findAll();
    }

    public String getCoverPathById(String coverHashId) {
        var metadata = trackMetaDataRepository.findFirstByCoverHashId(coverHashId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return metadata.getCoverPath();
    }

    public String getTrackPathById(String trackHashId) {
        var metadata = trackMetaDataRepository.findById(trackHashId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return metadata.getPath();
    }

    @Transactional
    public void persist(Collection<TrackMetadata> tracks) {
        Set<String> keep = tracks.stream()
                .map(TrackMetadata::getHashId)
                .collect(Collectors.toSet());

        List<String> stale = trackMetaDataRepository.findAllHashIds().stream()
                .filter(id -> !keep.contains(id))
                .toList();

        if (!stale.isEmpty()) {
            trackMetaDataRepository.deleteAllByIdInBatch(stale);
        }
        trackMetaDataRepository.saveAll(tracks);
    }


}
