package member.vo;


public class MemberVO {
	private String userid, userpwd, name, phone_number, admin;
	
	public MemberVO() {}
	
	public MemberVO(String userid, String userpwd, String name, String phone_number, String admin) {
		super();
		this.userid = userid;
		this.userpwd = userpwd;
		this.name = name;
		this.phone_number = phone_number;
		this.admin = admin;
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}

	
}
