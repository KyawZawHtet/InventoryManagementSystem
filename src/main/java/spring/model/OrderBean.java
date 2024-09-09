package spring.model;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class OrderBean {

	public OrderBean()
	{
		
	}
	
	private Long id;
	private LocalDate orderDate;
	private double totalAmount;
	private String status;
	private String customer;
	private Long userId;
}
