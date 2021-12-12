package dinhhieu.DIObject;

public class Teacher {
	
	public String name;
	public String age;
	public String subject;
	
	public Teacher() {
		
	}

	public Teacher(String name, String age, String subject) {
		super();
		this.name = name;
		this.age = age;
		this.subject = subject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void show() {
		System.out.println("Teacher [name=" + name + ", age=" + age + ", subject=" + subject + "]"); 
	}
	
	

}
