package sh.tiago.repository;

/**
 * Created by tiago.oliveira on 30/01/17.
 */

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import sh.tiago.entity.Url;

import java.util.Optional;

public interface UrlRepository extends Repository<Url, String> {
    Url save(Url entity);
    Optional<Url> findOne(String id);

}
