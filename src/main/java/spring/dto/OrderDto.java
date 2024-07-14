package spring.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

	private Long id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date orderDate;
	private String status;
	private Long locationId;
}
