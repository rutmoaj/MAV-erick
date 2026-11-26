package se.yrgo.datagatherer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.yrgo.datagatherer.entity.TrackMetadata;

import javax.sound.midi.Track;
import java.util.Optional;

public interface TrackMetaDataRepository extends JpaRepository<TrackMetadata, String> {
    boolean existsByPath(String path);

    Optional<TrackMetadata> findFirstByPath(String path);

    Optional<TrackMetadata> findFirstByCoverHashId(String coverHashId);
}
