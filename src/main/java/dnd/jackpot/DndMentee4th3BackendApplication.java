package dnd.jackpot;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DndMentee4th3BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DndMentee4th3BackendApplication.class, args);
	}

//	@Bean(initMethod = "start", destroyMethod = "stop")
//    public Server h2Server() throws SQLException {
//        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-ifNotExists", "-tcpPort", "8082");
//    }
}
