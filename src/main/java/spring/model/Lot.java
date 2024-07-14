package spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lot {

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
