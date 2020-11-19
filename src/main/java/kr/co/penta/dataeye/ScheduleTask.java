package kr.co.penta.dataeye;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class ScheduleTask {
	
	@Value("${dataeye.ldap.url}") private String url;
    @Value("${dataeye.ldap.domain}") private String domain;
    @Value("${dataeye.ldap.searchBase}") private String searchBase;
    @Value("${dataeye.ldap.svId}") private String svId;
    @Value("${dataeye.ldap.svPw}") private String svPw;
    
	@Scheduled(fixedDelay = 12000)
	public void task1() {
		System.out.println("The current date (1) : " + LocalDateTime.now());
	}
	
	// "0 0 * * * *" = the top of every hour of every day.
	// "*/10 * * * * *" = 매 10초마다 실행한다.
	// "0 0 8-10 * * *" = 매일 8, 9, 10시에 실행한다
	// "0 0 6,19 * * *" = 매일 오전 6시, 오후 7시에 실행한다.
	// "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
	// "0 0 9-17 * * MON-FRI" = 오전 9시부터 오후 5시까지 주중(월~금)에 실행한다.
	// "0 0 0 25 12 ?" = every Christmas Day at midnight
	@Scheduled(cron = "0 0 * * * *")
	public void task2() {
		System.out.println("The current date (2) : " + LocalDateTime.now());
	}
}
