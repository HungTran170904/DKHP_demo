package university.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import university.Model.Registration;
import university.Model.RegistrationKey;
import university.Model.RegistrationKey;

@Repository
public interface RegistrationRepo extends JpaRepository<Registration,RegistrationKey> {
	@Query(value="insert into Registration(student_id, course_id) values(?1,?2)", nativeQuery=true)
	@Modifying
    @Transactional
	public void addCourse(int studentId, int courseId);
	@Query(value="delete from Registration where student_id=?1 and course_id=?2", nativeQuery=true)
	@Modifying
    @Transactional
	public void removeCourse(int studentId, int courseId);
	@Query("select reg.result from Registration reg where reg.student.id=?1 and reg.course.id=?2")
	public boolean getResult(int studentId, int courseId);
	@Query("select reg from Registration reg where reg.student.id=?1 and reg.course.subject.id=?2")
	public Registration findByStudentAndSubject(int studentId, int subjectId);
}
