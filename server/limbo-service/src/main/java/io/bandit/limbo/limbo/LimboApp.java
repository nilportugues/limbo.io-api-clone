package io.bandit.limbo.limbo;

import io.bandit.limbo.limbo.infrastructure.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@ComponentScan
@EnableConfigurationProperties({ LiquibaseProperties.class })
@EnableAutoConfiguration
@EnableSwagger2
public class LimboApp {

    private static final Logger log = LoggerFactory.getLogger(LimboApp.class);
    private Environment env;

    @Inject
    public LimboApp(final Environment env) {
        this.env = env;
    }

    /**
     * Initializes limbo.
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     */
    @PostConstruct
    public void initApplication() {
        log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */
    public static void main(String[] args) throws UnknownHostException {
        final SpringApplication app = new SpringApplication(LimboApp.class);
        final Environment env = app.run(args).getEnvironment();

        log.info("\n----------------------------------------------------------");
        log.info("Application '{}' is running! Access URLs:\t", env.getProperty("spring.application.name"));
        log.info("Local: \t\thttp://localhost:{}\t", env.getProperty("server.port"));
        log.info("External: \t\thttp://{}:{}", InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"));
        log.info("Swagger JSON: \thttp://localhost:{}/v2/api-docs", env.getProperty("server.port"));
        log.info("Swagger Web Client: http://localhost:{}/swagger-ui.html", env.getProperty("server.port"));
        log.info("\n----------------------------------------------------------");
    }
}
