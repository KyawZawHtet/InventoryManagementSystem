package spring.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private int quantity;
	private Long productId;
}
