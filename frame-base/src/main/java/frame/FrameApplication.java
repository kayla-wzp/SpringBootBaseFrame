package frame;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"frame"})
@MapperScan(basePackages = {"frame.base.dao"})
@EnableTransactionManagement
public class FrameApplication {
	public static void main(String[] args) {
		SpringApplication.run(FrameApplication.class, args);
	}
}

