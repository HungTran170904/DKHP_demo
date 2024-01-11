package university.Model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Subject {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String subjectId;
	private String subjectName;
	private Integer theoryCreditNumber;
	private Integer practiceCreditNumber;
	@OneToMany(mappedBy="subject",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	List<Course> courses;
	@OneToMany(mappedBy="currSubject",cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	List<SubjectRelation> relations;
	public Subject(String subjectName, Integer theoryCreditNumber, Integer practiceCreditNumber) {
		this.subjectName = subjectName;
		this.theoryCreditNumber = theoryCreditNumber;
		this.practiceCreditNumber = practiceCreditNumber;
	}
}
