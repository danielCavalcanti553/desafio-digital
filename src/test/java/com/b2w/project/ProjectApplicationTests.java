package com.b2w.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.test.context.SpringBootTest;

import com.b2w.project.repository.PlanetaRepositoryTest;
import com.b2w.project.resource.PlanetaResourceTest;
import com.b2w.project.service.PlanetaServiceTest;

@RunWith(Suite.class)
@SuiteClasses({PlanetaRepositoryTest.class, PlanetaResourceTest.class, PlanetaServiceTest.class})
@SpringBootTest
public class ProjectApplicationTests {

	@Test
	public void contextLoads() {
	}

}
