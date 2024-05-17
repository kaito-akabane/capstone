package com.example.ProjectCC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectCCApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectCCApplication.class, args);
//			.getBean(SpringJdbcPracticeApplication.class).execute();
	}
	
//	@Autowired
//	UserCrudRepository repository;
//
//	private void execute() {
//		executeInsert();
//		executeSelect();
//	}
//
//	private void executeInsert() {
//		User user = new User("asdf", "1234", "Kang", true);
//		user = repository.save(user);
//		System.out.println("등록 데이터 : "+user);
//	}
//
//	private void executeSelect() {
//		System.out.println("전체 데이터 출력");
//		Iterable<User> users = repository.findAll();
//		for(User u : users) {
//			System.out.println(u);
//		}
//	}
}
