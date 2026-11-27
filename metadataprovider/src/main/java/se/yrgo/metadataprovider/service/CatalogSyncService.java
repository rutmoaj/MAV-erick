package se.yrgo.metadataprovider.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import jakarta.transaction.Transactional;
import se.yrgo.metadataprovider.repository.*;
import se.yrgo.metadataprovider.entity.*;
import se.yrgo.metadataprovider.dto.*;

@Service
public class CatalogSyncService {
    private final RestClient restClient;
    private final RestTemplate restTemplate;
    private final TrackRepository trackRepository;
    private final AlbumRepository albumRepository;

    public CatalogSyncService(TrackRepository trackRepository, AlbumRepository albumRepository) {
        this.restClient = RestClient.builder().baseUrl("http://localhost:8080").build();
        this.restTemplate = new RestTemplate();
        this.trackRepository = trackRepository;
        this.albumRepository = albumRepository;
    }

    // @Transactional
    public void syncFromDataGatherer() {
        ResponseEntity<IncomingTrack[]> response = restTemplate.postForEntity("http://localhost:8080/metadata", null,
                IncomingTrack[].class);

        IncomingTrack[] incomingTracks = response.getBody();
        if (incomingTracks == null) {
            return;
        }

        for (IncomingTrack dto : incomingTracks) {
            if (trackRepository.existsByHashId(dto.hashId())) {
                continue;
            }

            Album album = albumRepository.findByTitleAndArtist(dto.album(), dto.artist())
                    .orElseGet(() -> {
                        Album a = new Album();
                        a.setTitle(dto.album());
                        a.setArtist(dto.artist());
                        return albumRepository.save(a);
                    });

            Track track = new Track();
            track.setHashId(dto.hashId());
            track.setTitle(dto.title());
            track.setTrackNumber(dto.trackNumber());
            track.setDiscNumber(dto.discNumber());
            track.setDurationSeconds(dto.durationSeconds());
            track.setFileSizeBytes(dto.fileSizeBytes());
            track.setAlbum(album);
            trackRepository.save(track);
        }
    }

}
