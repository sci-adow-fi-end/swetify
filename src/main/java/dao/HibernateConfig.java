package dao;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Properties;

public class HibernateConfig {

    public static Properties getHibernateProperties() {
        Properties properties = new Properties();

        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();

        // Common properties
        properties.setProperty("jakarta.persistence.jdbc.url", "jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1");
        properties.setProperty("jakarta.persistence.jdbc.user", "sa");
        properties.setProperty("jakarta.persistence.jdbc.password", "");
        properties.setProperty("javax.persistence.jdbc.driver", "org.h2.Driver");
        properties.setProperty("jakarta.persistence.schema-generation.database.action", "drop-and-create");

        // Environment-specific properties
        String env = dotenv.get("APP_ENV");
        if ("debug".equals(env)) {
            properties.setProperty("hibernate.show_sql", "true");
            properties.setProperty("hibernate.format_sql", "true");
            properties.setProperty("hibernate.highlight_sql", "true");
        } else {
            properties.setProperty("hibernate.show_sql", "false");
            properties.setProperty("hibernate.format_sql", "false");
            properties.setProperty("hibernate.highlight_sql", "false");
        }

        return properties;
    }
}
