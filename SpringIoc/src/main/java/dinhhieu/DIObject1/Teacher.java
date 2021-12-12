package dinhhieu.DIObject1;

public class Teacher {
	
	private String name;
	private String age;
	private String subject;
	
	private School school;

	public Teacher() {
		
	}

	public Teacher(String name, String age, String subject, School school) {
		super();
		this.name = name;
		this.age = age;
		this.subject = subject;
		this.school = school;
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

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	
	public void show() {
		System.out.println("Teacher [name=" + name + ", age=" + age + ", subject=" + subject + ", school=" + school.toString() + "]");
	}
	
	
}
