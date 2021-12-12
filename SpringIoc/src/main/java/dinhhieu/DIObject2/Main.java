package dinhhieu.DIObject2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		School school1 = (School) ac.getBean("school1");
		school1.show();
		School school2 = (School) ac.getBean("school2");
		school2.show();
	}

}
