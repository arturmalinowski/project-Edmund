package cs.group.edmund.launch;

import cs.group.edmund.clue.controllers.SolverController;
import cs.group.edmund.typeSelector.Selector;
import cs.group.edmund.utils.Dictionary;
import cs.group.edmund.utils.Thesaurus;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("unused")
public class EdmundConfig {

    @Bean
    public Thesaurus thesaurus() {
        return new Thesaurus();
    }

    @Bean
    public Selector selector() {
        return new Selector(thesaurus(), dictionary());
    }

    @Bean
    public Dictionary dictionary() { return new Dictionary(); }

    @Bean
    public SolverController solverController() {
        return new SolverController();
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory servletFactory = new TomcatEmbeddedServletContainerFactory();
        servletFactory.setPort(9090);
        return servletFactory;
    }
}
