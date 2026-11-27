package se.yrgo.metadataprovider.controller;

import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yrgo.metadataprovider.entity.*;
import se.yrgo.metadataprovider.repository.*;
import se.yrgo.metadataprovider.service.CatalogSyncService;

@RestController
public class CatalogController {
    private final CatalogSyncService syncService;
    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;

    public CatalogController(CatalogSyncService syncService, AlbumRepository albumRepository,
            TrackRepository trackRepository) {
        this.syncService = syncService;
        this.albumRepository = albumRepository;
        this.trackRepository = trackRepository;
    }

    @PostMapping("/sync")
    public ResponseEntity<String> syncCatalog() {
        syncService.syncFromDataGatherer();
        return ResponseEntity.ok("Catalog successfully synced.");
    }

    @GetMapping("/albums")
    public List<Album> getAlbums() {
        return albumRepository.findAll();
    }

    @GetMapping("/tracks")
    public List<Track> getTracks() {
        return trackRepository.findAll();
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
        return albumRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/albums/search")
    public Optional<Album> getAlbumByName(@RequestParam(required = false) String title) {
        return albumRepository.findByTitle(title);
    }

    @GetMapping("/tracks/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable Long id) {
        return trackRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/tracks/search")
    public Optional<Track> getTrackByName(@RequestParam(required = false) String title) {
        return trackRepository.findByTitle(title);
    }
}
