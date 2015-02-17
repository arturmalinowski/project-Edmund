package cs.group.edmund.launch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SuppressWarnings("unused")
public class EdmundRunner {

    private ConfigurableApplicationContext edmundRunner;

    public static void main(String[] args) {
        new EdmundRunner();
    }

    public void startEdmund() {
        edmundRunner = new SpringApplicationBuilder(EdmundRunner.class)
                .showBanner(true)
                .run();
    }

    public void stopEdmund() {
        SpringApplication.exit(edmundRunner);
    }
}
