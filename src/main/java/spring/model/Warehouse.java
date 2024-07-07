package spring.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {

	private Long id;
	private String productName;
	private String productCode;
	private int quantity;
	private LocalDate date;
	private LocalDate expiredDate;
	private String description;
	private Long locationId;
}
