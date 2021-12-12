package dinhhieu.beanfactory;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

public class AlertMainBean {

	public static void main(String[] args) {
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("beans.xml"));
		BeachAlertBean alert1 = (BeachAlertBean) factory.getBean("beachalert1");
		alert1.print();
		BeachAlertBean alert2 = (BeachAlertBean) factory.getBean("beachalert2");
		alert2.print();
		
	}

}
