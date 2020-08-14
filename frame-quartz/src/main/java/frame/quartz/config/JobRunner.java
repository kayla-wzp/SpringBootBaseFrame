package frame.quartz.config;

import frame.quartz.domain.QuartzJob;
import frame.quartz.utils.QuartzManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import frame.quartz.repository.QuartzJobRepository;

import java.util.List;

/*
 * �˴���Ҫ���@Configuration ��Ҫ��QuartzJob�޸Ķ�Ӧ�Ķ�ʱ��������Լ��ֶ���
 */
@Slf4j
public class JobRunner implements ApplicationRunner {

	private final QuartzJobRepository quartzJobRepository;

	private final QuartzManage quartzManage;

	public JobRunner(QuartzJobRepository quartzJobRepository, QuartzManage quartzManage) {
		this.quartzJobRepository = quartzJobRepository;
		this.quartzManage = quartzManage;
	}

	/**
	 * ��Ŀ����ʱ���¼������õĶ�ʱ����
	 * 
	 * @param args /
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("--------------------ע�붨ʱ����---------------------");
		List<QuartzJob> quartzJobs = quartzJobRepository.findByIsPauseIsFalse();
		quartzJobs.forEach(quartzManage::addJob);
		log.info("--------------------��ʱ����ע�����---------------------");

	}
}
