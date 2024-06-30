package spring.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LotDto {

	private String lotNumber;
	private int quantity;
	private double price;
	private LocalDate date;
	private LocalDate expiredDate;
	private Long productId;
	private Long locationId;
}
