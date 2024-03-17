package university.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import university.Repository.CourseRepo;
import university.Util.OpeningRegPeriods;

@Service
public class SseService {
	@Autowired
	CourseRepo courseRepo;
	@Autowired
	OpeningRegPeriods openingRegPeriods;
	@Autowired
	ExecutorService sseExecutor;
	@Autowired
	Set<SseEmitter> emitters;
	private final Logger LOGGER=LoggerFactory.getLogger(SseService.class);
	public SseEmitter addEmitter() {
		System.out.println("A connection is received");
		var emitter=new SseEmitter(24*60*60*60*1000L);
		emitter.onCompletion(()->{
			LOGGER.info("A sse connection is closed");
			emitters.remove(emitter);
		});
		emitters.add(emitter);
		return emitter;
	}
	public Map<Integer,Integer> getUpdatedRegNumbers(){
		Map<Integer,Integer> regNumbers=new HashMap();
		List<Object[]> results=courseRepo.getUpdatedRegNum(openingRegPeriods.getCurrRegPeriod().getSemester());
		for (Object[] result : results) {
			   regNumbers.put((Integer) result[0], (Integer) result[1]);
		}
		return regNumbers;
	}
	public void startSendingEvents() {
		sseExecutor.execute(()->{
			try {
				while(true) {
					if(!emitters.isEmpty()) {
						System.out.println("Send events");
						CompletableFuture.supplyAsync(()->{
							return getUpdatedRegNumbers();
						}).thenAccept(result->{
							var deletedEmitters=new ArrayList<SseEmitter>();
							for(var emitter: emitters) {
								try {
									emitter.send(result);
								} catch (Exception e) {
									emitter.completeWithError(e);
									deletedEmitters.add(emitter);
								}
							}
							deletedEmitters.forEach(emitter->emitters.remove(emitter));
						});
					}
					Thread.sleep(3000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
