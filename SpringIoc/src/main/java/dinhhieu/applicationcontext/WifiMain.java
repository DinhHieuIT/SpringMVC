package dinhhieu.applicationcontext;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WifiMain {

	public static void main(String[] args) {
		ApplicationContext passwifi = new ClassPathXmlApplicationContext("applicationContext.xml");
		GuestPass gp = (GuestPass) passwifi.getBean("passwifi");
		gp.connect();

	}

}
