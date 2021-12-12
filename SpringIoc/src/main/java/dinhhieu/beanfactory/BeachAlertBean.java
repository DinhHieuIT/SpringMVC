package dinhhieu.beanfactory;

public class BeachAlertBean {
	
	private String message;
	

	public BeachAlertBean() {
		
	}
	
	public BeachAlertBean(String message) {
		super();
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
