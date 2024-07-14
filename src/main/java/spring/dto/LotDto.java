package spring.dto;
import java.sql.Date;

<<<<<<< HEAD
import java.sql.Date;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
=======
>>>>>>> origin/ATD
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
public class LotDto {
	private Long id;
	private String lotNumber;
	private Date expiredDate;
	private double price;
	private Date date;
	private String uom;
<<<<<<< HEAD
	private Date date;
	private Date expiredDate;
=======
	private int quantity;
>>>>>>> origin/ATD
	private Long productId;
	private String productName;
}
