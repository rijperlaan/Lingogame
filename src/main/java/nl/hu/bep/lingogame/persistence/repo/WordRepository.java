package nl.hu.bep.lingogame.persistence.repo;

import nl.hu.bep.lingogame.persistence.model.Word;
import org.springframework.data.repository.CrudRepository;

public interface WordRepository extends CrudRepository<Word, Long> {
}
