package se.yrgo.datagatherer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.yrgo.datagatherer.entity.TrackFile;

import java.util.Optional;

public interface TrackFileRepository extends JpaRepository<TrackFile, String> {
    public void deleteAll();
}
