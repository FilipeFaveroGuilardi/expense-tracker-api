package ffg.fillipe.expense_tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigDotEnv {
    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure().directory("src/main/resources/.env").ignoreIfMalformed().ignoreIfMissing().load();
    }
}
