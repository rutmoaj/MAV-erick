package se.yrgo.metadataprovider.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import se.yrgo.metadataprovider.dto.IncomingTrack;
import se.yrgo.metadataprovider.entity.Track;
import se.yrgo.metadataprovider.repository.AlbumRepository;
import se.yrgo.metadataprovider.repository.TrackRepository;

@Service
public class TrackSeedService {
    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;

    public TrackSeedService(TrackRepository trackRepository, AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
        this.trackRepository = trackRepository;
    }

    // Ta emot låtar från IncomingTrack
    // Om albumet inte finns -
    // Skapa Track och koppla till album
    // Spara via repository

}
