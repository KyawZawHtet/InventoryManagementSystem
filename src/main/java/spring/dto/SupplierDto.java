package spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {

	private Long id;
	private String name;
	private String email;
	private String phoneNumber;
	private String addres;
	private Long userId;
}
