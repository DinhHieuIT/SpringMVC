package dinhhieu.DIObject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		Teacher teacher1 = (Teacher) ac.getBean("teacher1");
		teacher1.show();
		Teacher teacher2 = (Teacher) ac.getBean("teacher2");
		teacher2.show();

	}

}
