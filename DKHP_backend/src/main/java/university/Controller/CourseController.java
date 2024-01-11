package university.Controller;

import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import reactor.core.publisher.Flux;
import university.DTO.CourseDTO;
import university.Model.Course;
import university.Service.CourseService;
import university.Util.OpeningRegPeriods;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {
	@Autowired
	CourseService courseService;
	@Autowired
	OpeningRegPeriods openingRegPeriods;
	@GetMapping("/admin/all")
	public ResponseEntity<List<CourseDTO>> getAllCourses() {
		List<CourseDTO> courses=courseService.getAllCourses();
		return new ResponseEntity(courses, HttpStatus.OK);
	}
	@GetMapping("/openedCourses")
	public ResponseEntity<List<CourseDTO>> getOpenedCourses() {
		openingRegPeriods.validateRegPeriod();
		List<CourseDTO> courses=courseService.getOpenedCourses();
		return new ResponseEntity(courses, HttpStatus.OK);
	}
	@GetMapping("/enrolledCourses")
	public ResponseEntity<List<Integer>> getEnrolledCourses() {
		openingRegPeriods.validateRegPeriod();
		List<Integer> courseIds=courseService.getEnrolledCourses();
		return new ResponseEntity(courseIds, HttpStatus.OK);
	}
	@GetMapping("/studiedCourses")
	public ResponseEntity<List<CourseDTO>> getStudiedCourses(
			@RequestParam(value="semesterId") int semesterId,
			@RequestParam(value="studentId") int studentId){
		openingRegPeriods.validateRegPeriod();
		List<CourseDTO> courses=courseService.getStudiedCourses(semesterId);
		return new ResponseEntity(courses, HttpStatus.OK);
	}
	@PostMapping("/admin/addCourse")
	public ResponseEntity<CourseDTO> addCourse(
			@RequestBody CourseDTO dto){
		return ResponseEntity.ok(courseService.addCourse(dto));
	}
	@DeleteMapping("/admin/removeCourse/{id}")
	public ResponseEntity<String> removeCourse(
			@PathVariable("id") int id){
		return ResponseEntity.ok(courseService.removeCourse(id));
	}
	@GetMapping(path="/updateRegNumbers", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Map<Integer,Integer>> streamRegNumbers(){
		return courseService.streamFlux();
	}
}
