package spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

	private Long id;
	private int quantity;
	private double unitPrice;
	private Long orderId;
	private Long productId;
}
