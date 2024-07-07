package spring.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LotDto {

	private Long id;
	private String lotNumber;
	private Date expiredDate;
	private double price;
	private Date date;
	private String uom;
	private int quantity;
	private Long productId;
}
