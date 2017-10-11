package io.bandit.limbo.limbo.behaviour;

import io.bandit.limbo.limbo.LimboApp;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Cucumber.class)
@CucumberOptions(
    format = {
        "pretty",
        "json:target/cucumber.json",
        "lv.ctco.cukes.core.formatter.CukesJsonFormatter:target/cucumber2.json"
    },
    features = {
        "classpath:features/"
    },
    glue = "lv.ctco.cukes",
    strict = true
)
public class RunBehaviourTest {

    @BeforeClass
    public static void setUp() throws Exception {
        final SpringApplication app = new SpringApplication(LimboApp.class);
        System.setProperty("spring.profiles.active", "test");
        app.run();
    }
}
