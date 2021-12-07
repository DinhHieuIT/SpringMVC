package dinhhieumvc.entity;

public enum Role {
	USER(1), ADMIN(2);

	public final int value;
	public static final int USER_AND_ADMIN_ROLES = USER.value | ADMIN.value;
	public static final int USER_ROLE = USER.value;
	public static final int ADMIN_ROLE = ADMIN.value;

	private Role(int value) {
		this.value = value;
	}

}
