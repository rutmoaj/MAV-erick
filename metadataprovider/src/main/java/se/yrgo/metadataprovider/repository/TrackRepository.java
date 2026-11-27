package se.yrgo.metadataprovider.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import se.yrgo.metadataprovider.entity.Track;

public interface TrackRepository extends JpaRepository<Track, Long> {
    boolean existsByHashId(String hashId);

     Optional<Track> findByTitle(String title);

    // Optional<Track> findByArtist(String artist);
}
