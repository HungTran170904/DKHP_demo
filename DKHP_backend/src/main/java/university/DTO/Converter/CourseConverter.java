package university.DTO.Converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import university.DTO.CourseDTO;
import university.DTO.SubjectDTO;
import university.Model.Course;
import university.Model.Semester;
@Component
public class CourseConverter {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	SubjectConverter subjectConverter;
	public CourseDTO convertToDTO(Course c) {
		CourseDTO dto=modelMapper.map(c,CourseDTO.class);
		dto.setSubject(subjectConverter.convertToDTO(c.getSubject()));
		if(c.getMainCourse()!=null) dto.setMainCourseId(c.getMainCourse().getCourseId());
		return dto;
	}
	public List<CourseDTO> convertToDTO(List<Course> courses) {
		List<CourseDTO> dtos=new ArrayList();
		for(Course c:courses) dtos.add(convertToDTO(c));
		return dtos;
	}
	public Course convertToCourse(CourseDTO dto) {
		Course c=modelMapper.map(dto,Course.class); 
		c.setRegisteredNumber(0);
		return c;
	}
}
