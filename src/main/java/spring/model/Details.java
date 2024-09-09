package spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Details {

	private String id;
	private String product;
	private String lot;
	private int order_qty;
	private int total_qty;
	private double price;
	private String order_id;
	private String customer;
	private String expired;
	
}
