package dinhhieu.applicationcontext;

public class GuestPass {
	
	private String name;
	private String password;
	
	public GuestPass() {
		
	}

	public GuestPass(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "WifiGuest [name=" + name + ", password=" + password + "]";
	}
	
	public void connect() {
		System.out.println("Name of guest is : " + this.name );
		System.out.println("Pass of guest is : " + this.password);
	}
}
