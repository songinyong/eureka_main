/*
 * 주기적으로 작동해야하는 동기화 관련 메서드들을 스케줄러를 통해 실행하는 클래스
 * 
 * */

package post.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import post.service.SchedulerService;



@Component
public class Scheduler { 
	
	@Autowired
	private SchedulerService schedulerService;
	
	//동기화를 2분에 한번 동기화를 시행한다.
    @Scheduled(cron = "0 0/2 * * * ?") 
    public void runSync() { 
    	schedulerService.recvNftInfofromBlckdb();
    } 
    
    //sell_state 변경을 2분에 한번 확인하고 변경한다.
    @Scheduled(cron = "5 0/2 * * * ?") 
    public void runRevertTime() { 
    	schedulerService.revertState();
    } 
}
