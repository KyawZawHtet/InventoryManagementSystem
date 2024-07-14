package spring.model;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
=======
import java.sql.Date;

>>>>>>> origin/ATD
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class Lot {
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
