package spring.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	private Long id;
	private LocalDate orderDate;
	private double totalAmount;
	private String status;
	private Long userId;
}
