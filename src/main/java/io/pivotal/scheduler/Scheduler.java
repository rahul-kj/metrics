package io.pivotal.scheduler;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.pivotal.domain.Attribute;
import io.pivotal.domain.CustomJobMetric;
import io.pivotal.domain.JobDetail;
import io.pivotal.domain.Metrics;
import io.pivotal.domain.VMMetric;
import io.pivotal.service.EmailService;
import io.pivotal.service.MetricService;

@Service
public class Scheduler {
	
	@Autowired
	MetricService metricService;
	
	@Autowired
	EmailService emailService;

	@Scheduled(fixedDelay = 60000)
	public void checkSystemHealth() {
		StringBuilder errors = new StringBuilder();

		Metrics metrics = metricService.getMetrics();
		List<VMMetric> vmMetrics = metrics.getVmMetrics();
		for (VMMetric vmMetric : vmMetrics) {
			if (!vmMetric.getFixedAttribute().getSystem_healthy().equalsIgnoreCase("1.0")) {
				JobDetail jobDetail = vmMetric.getJobDetail();
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(jobDetail.getDeployment()).append(":").append(jobDetail.getJob()).append(":")
						.append(jobDetail.getIndex()).append(":").append(jobDetail.getIp()).append("\n");
				errors.append(stringBuilder.toString());
			}
		}

		String errorMsg = null;
		List<CustomJobMetric> customJobMetrics = metrics.getCustomJobMetrics();
		for (CustomJobMetric customJobMetric : customJobMetrics) {
			JobDetail jobDetail = customJobMetric.getJobDetail();
			errorMsg = calculateDiegoCellsMemory(customJobMetric, jobDetail);
			errors.append(errorMsg);
		}
		
		if(!errors.toString().isEmpty()) {
			emailService.sendEmail(errors.toString());
		}
		
	}

	private String calculateDiegoCellsMemory(CustomJobMetric customJobMetric, JobDetail jobDetail) {
		String message  = "";
		float totalMem = 0;
		float totalRemainingMem = 0;
		if (jobDetail.getJob().contains("diego_cell-partition")) {
			List<Attribute> attributes = customJobMetric.getCustomAttributes();
			for (Attribute attribute : attributes) {
				if (attribute.getName().equals("opentsdb.nozzle.rep.CapacityTotalMemory")) {
					totalMem += Float.valueOf(attribute.getValue());
				}

				if (attribute.getName().equals("opentsdb.nozzle.rep.CapacityRemainingMemory")) {
					totalRemainingMem += Float.valueOf(attribute.getValue());
				}
			}

			float percentUsage = (totalRemainingMem / totalMem) * 100;

			if (percentUsage < 30) {
				DecimalFormat df = new DecimalFormat("#0.00");
				String formattedPercentage = df.format(percentUsage);
				message = "The total percentage of memory remaining on Diego Cells is: " + formattedPercentage;
				System.out
						.println(message);
			}
		}
		
		return message;

	}
}
