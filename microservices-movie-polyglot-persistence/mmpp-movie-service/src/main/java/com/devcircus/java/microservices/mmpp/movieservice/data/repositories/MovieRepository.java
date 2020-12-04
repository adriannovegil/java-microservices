package service.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import service.data.domain.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

    Page<Movie> findByIdIn(@Param("ids") Long[] ids, Pageable pageable);
}
