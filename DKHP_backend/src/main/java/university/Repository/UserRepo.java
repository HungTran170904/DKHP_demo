package university.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import university.Model.Role;
import university.Model.Student;
import university.Model.User;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
	public Optional<User> findByEmail(String email);
	public Optional<User> findByUserId(String UserId);
	public Student findByName(String name);
	public boolean existsByUserId(String userId);
	public boolean existsByEmail(String email);
	public boolean existsByRole(Role role);
}
