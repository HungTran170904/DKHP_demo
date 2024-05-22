package university.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import university.Model.Course;
import university.Model.Semester;
import university.Model.Student;

@Repository
public interface CourseRepo extends JpaRepository<Course,Integer>{
	public List<Course> findBySemester(Semester semester);
	@Query("select c from Course c where c.semester=?1 order by c.courseId ASC")
	public List<Course> findOpenedCourses(Semester semester);
	
	@Query("select c.id from Course c where c.semester=?1 and c IN (select reg.course from Registration reg where reg.student.id=?2)")
	public List<Integer> findEnrolledCourseIds(Semester semester,int studentId);
	
	@Query("select c from Course c where c.semester.id=?1 and c IN (select reg.course from Registration reg where reg.student.id=?2) order by c.courseId ASC")
	public List<Course> findEnrolledCourses(int semesterId,int studentId);
	
	@Query("select c from Course c where c.semester=?1 and c IN (select reg.course from Registration reg where reg.student.id=?2) order by c.courseId ASC")
	public List<Course> findEnrolledCourses(Semester semester,int studentId);
	
	@Query("select reg.course from Registration reg where reg.student.id=?1 and reg.course.id=?2")
	public Course findEnrolledCourseById(int studentId, int courseId);
	
	@Query("select c.id, c.registeredNumber from Course c where c.semester=?1")
	public List<Object[]> getUpdatedRegNum(Semester semester);
	
	public boolean existsByCourseId(String courseId);
	public boolean existsByMainCourse(Course course);
	public Optional<Course> findByCourseId(String courseId);
}
