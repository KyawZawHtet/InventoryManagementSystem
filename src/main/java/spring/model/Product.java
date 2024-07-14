package spring.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	private String id;
	@NotEmpty private String code;
	@NotEmpty private String name;
	@NotEmpty private String category;
	@NotEmpty private String uom;
	@NotEmpty private String description;
}
