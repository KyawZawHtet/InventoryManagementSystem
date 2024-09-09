package spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailsDto {

	private String oId;
	private String oDate;
	private String invoiceId;
	private String customer;
	private String address;
	private String pName;
	private String description;
	private double quantity;
	private double price;
	private String expired;
	private double subTotal;
	private int discount;
	private double total;
	
}
