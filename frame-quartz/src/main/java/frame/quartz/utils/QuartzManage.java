package frame.quartz.utils;

import frame.quartz.domain.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;
import frame.quartz.exception.BadRequestException;

import javax.annotation.Resource;
import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;


@Slf4j
@Component
public class QuartzManage {

	private static final String JOB_NAME = "TASK_";

	@Resource(name = "scheduler")
	private Scheduler scheduler;

	public void addJob(QuartzJob quartzJob){
		try {
			// ����job��Ϣ
			JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class).
					withIdentity(JOB_NAME + quartzJob.getId()).build();

			//ͨ������������cron ���ʽ���� Trigger
			Trigger cronTrigger = newTrigger()
					.withIdentity(JOB_NAME + quartzJob.getId())
					.startNow()
					.withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()))
					.build();

			cronTrigger.getJobDataMap().put(QuartzJob.JOB_KEY, quartzJob);

			//��������ʱ��
			((CronTriggerImpl)cronTrigger).setStartTime(new Date());

			//ִ�ж�ʱ����
			scheduler.scheduleJob(jobDetail,cronTrigger);

			// ��ͣ����
			if (quartzJob.getIsPause()) {
				pauseJob(quartzJob);
			}
		} catch (Exception e){
			log.error("������ʱ����ʧ��", e);
			throw new BadRequestException("������ʱ����ʧ��");
		}
	}

	/**
	 * ����job cron���ʽ
	 * @param quartzJob /
	 */
	public void updateJobCron(QuartzJob quartzJob){
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + quartzJob.getId());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// ����������򴴽�һ����ʱ����
			if(trigger == null){
				addJob(quartzJob);
				trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			}
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression());
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			//��������ʱ��
			((CronTriggerImpl)trigger).setStartTime(new Date());
			trigger.getJobDataMap().put(QuartzJob.JOB_KEY,quartzJob);

			scheduler.rescheduleJob(triggerKey, trigger);
			// ��ͣ����
			if (quartzJob.getIsPause()) {
				pauseJob(quartzJob);
			}
		} catch (Exception e){
			log.error("���¶�ʱ����ʧ��", e);
			throw new BadRequestException("���¶�ʱ����ʧ��");
		}

	}

	/**
	 * ɾ��һ��job
	 * @param quartzJob /
	 */
	public void deleteJob(QuartzJob quartzJob){
		try {
			JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
			scheduler.pauseJob(jobKey);
			scheduler.deleteJob(jobKey);
		} catch (Exception e){
			log.error("ɾ����ʱ����ʧ��", e);
			throw new BadRequestException("ɾ����ʱ����ʧ��");
		}
	}

	/**
	 * �ָ�һ��job
	 * @param quartzJob /
	 */
	public void resumeJob(QuartzJob quartzJob){
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + quartzJob.getId());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// ����������򴴽�һ����ʱ����
			if(trigger == null) {
				addJob(quartzJob);
			}
			JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
			scheduler.resumeJob(jobKey);
		} catch (Exception e){
			log.error("�ָ���ʱ����ʧ��", e);
			throw new BadRequestException("�ָ���ʱ����ʧ��");
		}
	}

	/**
	 * ����ִ��job
	 * @param quartzJob /
	 */
	public void runJobNow(QuartzJob quartzJob){
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + quartzJob.getId());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// ����������򴴽�һ����ʱ����
			if(trigger == null) {
				addJob(quartzJob);
			}
			JobDataMap dataMap = new JobDataMap();
			dataMap.put(QuartzJob.JOB_KEY, quartzJob);
			JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
			scheduler.triggerJob(jobKey,dataMap);
		} catch (Exception e){
			log.error("��ʱ����ִ��ʧ��", e);
			throw new BadRequestException("��ʱ����ִ��ʧ��");
		}
	}

	/**
	 * ��ͣһ��job
	 * @param quartzJob /
	 */
	public void pauseJob(QuartzJob quartzJob){
		try {
			JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
			scheduler.pauseJob(jobKey);
		} catch (Exception e){
			log.error("��ʱ������ͣʧ��", e);
			throw new BadRequestException("��ʱ������ͣʧ��");
		}
	}
}
