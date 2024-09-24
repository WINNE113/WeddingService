package com.wedding.backend.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private String jobName; // Tên của job

    private List<String> errorGlobal = new ArrayList<>(); // Danh sách chứa các lỗi trong quá trình chạy job

    // Constructor nhận vào tên job và danh sách lỗi toàn cục
    public JobCompletionNotificationListener(String jobName, List<String> errorGlobal) {
        this.jobName = jobName;
        this.errorGlobal = errorGlobal;
    }

    // Phương thức này được gọi sau khi job kết thúc
    @Override
    public void afterJob(JobExecution jobExecution) {
        // Kiểm tra và in ra trạng thái của job sau khi hoàn thành
        if (!errorGlobal.isEmpty()) {
            System.out.println("Job " + jobName + " failed with status: " + jobExecution.getStatus());
            System.out.println("Errors: " + errorGlobal);
        } else {
            System.out.println("Job " + jobName + " finished successfully with status: " + jobExecution.getStatus());
        }
    }

    // Phương thức này được gọi trước khi job bắt đầu
    @Override
    public void beforeJob(JobExecution jobExecution) {
        // In ra thông tin bắt đầu job
        System.out.println("Job " + jobName + " started at " + new Date());
    }
}