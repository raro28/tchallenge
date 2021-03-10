package mx.ekthor.challenge.persistence.repositories;

import mx.ekthor.challenge.persistence.entities.PlayerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerEntity, Long> {

}