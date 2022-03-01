package com.tnf;

import com.tnf.entity.Journal;
import com.tnf.service.JournalService;
import com.tnf.service.JournalServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class JournalApplication {

	public static void main(String[] args) {
		ApplicationContext run = SpringApplication.run(JournalApplication.class, args);
		JournalService journalService = run.getBean(JournalServiceImpl.class);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean loop = true;
		while (loop) {
			System.out.println("Press 1 For insert");
			System.out.println("Press 2 For update");
			System.out.println("Press 3 For display All Journals");
			System.out.println("Press 4 For exit");
			try {
				int input = Integer.parseInt(br.readLine());
				switch (input) {
					case 1:
						journalService.insertOrUpdate(true);
						break;
					case 2:
						journalService.insertOrUpdate(false);
						break;
					case 3:
						List<Journal> all = journalService.getJournals();
						all.forEach(System.out::println);
						break;
					case 4:
						loop = false;
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + input);
				}
			} catch (Exception e) {
				System.out.println("Invalid input try with other");
				System.out.println(e.getMessage());
			}
		}
	}

}
