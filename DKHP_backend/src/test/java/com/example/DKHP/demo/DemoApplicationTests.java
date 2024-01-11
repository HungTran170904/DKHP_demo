package com.example.DKHP.demo;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import university.DemoApplication;
import university.DTO.CourseDTO;
import university.Model.Course;
import university.Repository.CourseRepo;
import university.Repository.SemesterRepo;
import university.Repository.SubjectRepo;
import university.Repository.UserRepo;
import university.Service.StudentService;

@SpringBootTest
class DemoApplicationTests {
	@Test
	void contextLoads() {
	}

}
