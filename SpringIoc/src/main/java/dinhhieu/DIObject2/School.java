package dinhhieu.DIObject2;

import java.util.List;

public class School {
	
	private String name;
	private String address;
	private List<Teacher> teachers;
	
	public School() {
		
	}

	public School(String name, String address, List<Teacher> teachers) {
		super();
		this.name = name;
		this.address = address;
		this.teachers = teachers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}


	public void show() {
		System.out.println("School [name=" + name + ", address=" + address + ", teachers=" + teachers + "]"); 
	}
	
}
