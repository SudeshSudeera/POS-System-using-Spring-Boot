package com.devstack.pos;

import com.devstack.pos.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PosApplication implements CommandLineRunner {
	@Autowired
	private UserRoleService userRoleService;
	public static void main(String[] args) {
		SpringApplication.run(PosApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		userRoleService.initializeRoles();
	}
}
