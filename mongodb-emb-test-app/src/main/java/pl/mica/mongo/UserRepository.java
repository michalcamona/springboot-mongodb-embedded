package pl.mica.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.mica.model.User;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    public List<User> findAllByName(String name);

}
