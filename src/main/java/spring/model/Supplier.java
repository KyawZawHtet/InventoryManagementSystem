package spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

	private Long id;
	private String name;
	private String contactName;
	private String email;
	private String phoneNumber;
	private String address;
}
