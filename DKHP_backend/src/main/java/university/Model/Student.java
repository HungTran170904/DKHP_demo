package university.Model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
public class Student {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String falcutyName;
	private String program;
	private Integer khoaTuyen;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userId", nullable=false)
	private User user;
}
