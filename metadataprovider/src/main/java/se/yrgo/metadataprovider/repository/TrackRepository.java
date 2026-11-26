package se.yrgo.metadataprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.yrgo.metadataprovider.entity.Track;

public interface TrackRepository extends JpaRepository<Track, Long> {

}
