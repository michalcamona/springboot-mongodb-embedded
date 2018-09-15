package pl.mica;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
@Configuration
public class MongodbEmbTestAppApplication implements ApplicationRunner {

    String ip = "localhost";
    int port = 27017;
    private MongodExecutable mongodExecutable;

    public static void main(String[] args) {
        SpringApplication.run(MongodbEmbTestAppApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String ip = "localhost";
        int port = 27017;
        Storage storage = new Storage("db1", null, 0);

        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6())).replication(storage)
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();

        MongoClient client = new MongoClient(ip, port);

    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(new MongoClient(ip, port), "micadb");
    }
}
