package spring.dto;

import java.sql.Date;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LotDto {

	private Long id;
	private String lotNumber;
	private int quantity;
	private double price;
	private String uom;
	private Date date;
	private Date expiredDate;
	private Long productId;
	private String productName;
}
