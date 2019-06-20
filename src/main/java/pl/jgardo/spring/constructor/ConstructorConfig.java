package pl.jgardo.spring.constructor;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "pl.jgardo.spring.constructor.service")
public class ConstructorConfig {

}
