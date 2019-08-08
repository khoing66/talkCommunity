package study.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author khoing
 */
@SpringBootApplication
@MapperScan("study.community.mapper")
//@ComponentScan(basePackages={"study.community"})
@EnableScheduling
public class CommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
