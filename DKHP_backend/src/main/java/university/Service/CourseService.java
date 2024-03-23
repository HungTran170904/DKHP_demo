package university.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.transaction.Transactional;
import university.DTO.CourseDTO;
import university.DTO.Converter.CourseConverter;
import university.Exception.RequestException;
import university.Model.Course;
import university.Model.Semester;
import university.Model.Student;
import university.Model.Subject;
import university.Repository.CourseRepo;
import university.Repository.SemesterRepo;
import university.Repository.StudentRepo;
import university.Repository.SubjectRepo;
import university.Util.InfoChecking;
import university.Util.OpeningRegPeriods;

@Service
@Transactional
public class CourseService {
	@Autowired
	CourseRepo courseRepo;
	@Autowired
	StudentRepo studentRepo;
	@Autowired
	SubjectRepo subjectRepo;
	@Autowired
	SemesterRepo semesterRepo;
	@Autowired
	CourseConverter courseConverter;
	@Autowired
	InfoChecking infoChecking;
	@Autowired
	OpeningRegPeriods openingRegPeriods;
	public int getStudentIdFromSecurityContext() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth==null) throw new BadCredentialsException("You need to login before register courses!");
		UserDetails userDetails=(UserDetails) auth.getPrincipal();
		int userId= Integer.valueOf(userDetails.getUsername());
		return studentRepo.findStudentIdByUserId(userId);
	}
	public List<CourseDTO> getAllCourses(){
		return courseConverter.convertToDTO(courseRepo.findAll());
	}
	public List<CourseDTO> getOpenedCourses(){
		List<Course> courses=courseRepo.findOpenedCourses(openingRegPeriods.getCurrRegPeriod().getSemester());
		return courseConverter.convertToDTO(courses);
	}
	public List<Integer> getEnrolledCourses(){
		int studentId=getStudentIdFromSecurityContext();
		List<Integer> courseIds=courseRepo.findEnrolledCourseIds(openingRegPeriods.getCurrRegPeriod().getSemester(),studentId);
		return courseIds;
	}
	public List<CourseDTO> getStudiedCourses(int semesterId){
		int studentId=getStudentIdFromSecurityContext();
		List<Course> courses=courseRepo.findEnrolledCourses(semesterId,studentId);
			return courseConverter.convertToDTO(courses);
	}
	public CourseDTO addCourse(CourseDTO dto) {
		CourseDTO response=null;
		if(dto.getCourseId()==null) throw new RequestException("CourseId is required");
		if(courseRepo.existsByCourseId(dto.getCourseId()))
			throw new RequestException("The course id "+dto.getCourseId()+" has already existed");
		if(dto.getBeginDate()==null||dto.getEndDate()==null) throw new RequestException("BeginDate and EndDate are required");
		if(dto.getBeginDate().isAfter(dto.getEndDate())) throw new RequestException("BeginDate must be before EndDate");
		if(dto.getBeginShift()>=dto.getEndShift()) throw new RequestException("BeginShift must be smaller than EndShift");
		Course c=courseConverter.convertToCourse(dto);
		String subjectId=infoChecking.getSubjectId(c.getCourseId());
		Subject s=subjectRepo.findBySubjectId(subjectId).orElseThrow(()->new RequestException("SubjectId "+subjectId+" does not exists!"));
		c.setSubject(s);
		if(dto.getMainCourseId()!=null) {
			Course mainCourse=courseRepo.findByCourseId(dto.getMainCourseId()).orElseThrow(()->new RequestException("mainCourseId "+dto.getMainCourseId()+" does not exist"));
			if(mainCourse.getMainCourse()!=null) throw new RequestException("MainCourse "+mainCourse.getCourseId()+" is not a theory course");
			c.setMainCourse(mainCourse);
		}
		Semester se=semesterRepo.findById(dto.getSemesterId()).orElseThrow(()->new RequestException("SemesterId "+dto.getSemesterId()+" not found!"));
		c.setSemester(se);
		Course courseSave=courseRepo.save(c);
		if(courseSave!=null) {
			response=courseConverter.convertToDTO(courseSave);
		}
		return response;
	}
	public String removeCourse(Integer courseId) {
		var c=courseRepo.findById(courseId);
		if(c.isEmpty()) throw new RequestException("Course id "+courseId+" not found!Please try again"); 
		courseRepo.delete(c.get());
		return c.get().getCourseId();
	}
}
