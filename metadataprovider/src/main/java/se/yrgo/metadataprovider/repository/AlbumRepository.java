package se.yrgo.metadataprovider.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import se.yrgo.metadataprovider.entity.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByTitleAndArtist(String title, String artist);

    Optional<Album> findByTitle(String title);

    Optional<Album> findByGenre(String genre);
}
