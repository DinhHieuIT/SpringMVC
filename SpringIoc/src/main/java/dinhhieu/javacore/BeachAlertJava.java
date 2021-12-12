package dinhhieu.javacore;

public class BeachAlertJava {
	
	private String message;
	
	public BeachAlertJava() {
		
	}

	public BeachAlertJava(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void print() {
		System.out.println(message);
	}

}
