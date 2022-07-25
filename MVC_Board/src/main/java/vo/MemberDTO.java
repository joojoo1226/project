package vo;

import java.sql.Date;

/*
CREATE TABLE member(
	name VARCHAR(20) NOT NULL,
	id VARCHAR(16) PRIMARY KEY,
	passwd VARCHAR(16) NOT NULL,
	email VARCHAR(50) UNIQUE NOT NULL,
	post_code VARCHAR(5) NOT NULL,
	address1 VARCHAR(50) NOT NULL,
	address2 VARCHAR(50) NOT NULL,
	hire_date DATE NOT NULL
);
*/

public class MemberDTO {

	private String name;
	private String id;
	private String passwd;
	private String email;
	private String post_code;
	private String address1;
	private String address2;
	private Date hire_date;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPost_code() {
		return post_code;
	}
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public Date getHire_date() {
		return hire_date;
	}
	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}
	
	@Override
	public String toString() {
		return "MemberDTO [name=" + name + ", id=" + id + ", passwd=" + passwd + ", email=" + email + ", post_code="
				+ post_code + ", address1=" + address1 + ", address2=" + address2 + ", hire_date=" + hire_date + "]";
	}
	
}
