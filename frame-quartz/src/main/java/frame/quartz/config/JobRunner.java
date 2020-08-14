package frame.quartz.config;

import frame.quartz.domain.QuartzJob;
import frame.quartz.utils.QuartzManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import frame.quartz.repository.QuartzJobRepository;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/*
 * 需要加注解@Configuration 并且在QuartzJob修改表名以及表字段
 */
@Configuration
@Slf4j
public class JobRunner implements ApplicationRunner {

	private final QuartzJobRepository quartzJobRepository;

	private final QuartzManage quartzManage;

	public JobRunner(QuartzJobRepository quartzJobRepository, QuartzManage quartzManage) {
		this.quartzJobRepository = quartzJobRepository;
		this.quartzManage = quartzManage;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("--------------------注入定时任务---------------------");
		List<QuartzJob> quartzJobs = quartzJobRepository.findByIsPauseIsFalse();
		quartzJobs.forEach(quartzManage::addJob);
		log.info("--------------------定时任务注入完成---------------------");
	}
}
