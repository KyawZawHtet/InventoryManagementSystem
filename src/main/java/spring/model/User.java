package spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private Long id;
	private String username;
	private String email;
	private String password;
	private String confirmPassword;
	private String phoneNumber;
	private boolean status;
	private Long roleId;
}
