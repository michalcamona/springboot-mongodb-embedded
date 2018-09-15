package pl.mica.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import pl.mica.model.User;
import pl.mica.mongo.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/db")
public class DbRestController {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/update")
    public Mono<User> update(@RequestBody User user) {
        return Mono.just(userRepository.save(user));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable(name = "id") String id) {
        userRepository.deleteById(id);
        return Mono.empty();
    }

    @GetMapping("/get/all/{name}")
    public Flux<Stream<User>> getAllByName(@PathVariable(name = "name") String name) {
        return Flux.just(userRepository.findAllByName(name).stream());
    }

    @PutMapping("/put")
    public Mono<User> create(@RequestBody User user) {
        return Mono.just(userRepository.save(user));
    }

    @GetMapping("/get/{id}")
    public Mono<User> getDbObject(@PathVariable(name = "id") String id) {
        return Mono.justOrEmpty(userRepository.findById(id));
    }
}
