package spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.dto.OrderDetailDto;
import spring.dto.OrderDto;
import spring.dto.ProductDto;
import spring.model.OrderDetail;
import spring.repository.OrderDetailRepository;
import spring.repository.OrderRepository;
import spring.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value="/orderdetail")
public class OrderDetailController {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/add_orderdetail", method = RequestMethod.GET)
	public ModelAndView showRegister(Model model) {
		OrderDetail orderDetail = new OrderDetail();
		return new ModelAndView("order_detail_add", "orderDetail", orderDetail);
	}

	@RequestMapping(value = "/do_orderdetail", method = RequestMethod.POST)
	public String showResult(@ModelAttribute("orderDetail") OrderDetail orderDetail, Model model) {

		OrderDetailDto orderDetailDto = new OrderDetailDto();
		orderDetailDto.setQuantity(orderDetail.getQuantity());
		orderDetailDto.setProductId(orderDetail.getProductId());
		orderDetailDto.setOrderId(orderDetail.getOrderId());

		int i = orderDetailRepository.insertOrderDetail(orderDetailDto);
		if (i > 0) {
			return "redirect:/orderdetail/showorderdetails";
		} else {
			model.addAttribute("orderDetail", new OrderDetail());
			return "add_orderdetail";
		}
	}

	@GetMapping(value = "/showorderdetails")
	public String showAllOrderDetails(Model model) {
		List<OrderDetailDto> orderDetailDtos = orderDetailRepository.showAllOrderDetails();
		model.addAttribute("orderDetailDtoList", orderDetailDtos);
		return "order_detail_list";
	}

	@GetMapping(value = "/editorderdetail/{id}")
	public String showOrderDetailById(@PathVariable("id") Long id, Model model) {
		OrderDetailDto orderDetailDto = new OrderDetailDto();
		orderDetailDto.setId(id);

		OrderDetailDto dto = orderDetailRepository.showOrderDetail(orderDetailDto);

		OrderDetail orderDetail = modelMapper.map(dto, OrderDetail.class);

		model.addAttribute("orderDetail", orderDetail);
		return "order_detail_update";
	}


	@PostMapping(value = "/doupdate")
	public String updateOrderDetail(@ModelAttribute("orderDetail") OrderDetail orderDetail) {
		OrderDetailDto orderDetailDto = modelMapper.map(orderDetail, OrderDetailDto.class);

		int i = orderDetailRepository.updateOrderDetail(orderDetailDto);
		if (i > 0) {
			return "redirect:/orderdetail/showorderdetails";
		} else {
			return "redirect:editorderdetail/" + orderDetailDto.getId();
		}
	}

	@RequestMapping(value = "/deleteorderdetail/{id}", method = RequestMethod.GET)
	public String deleteOrderDetail(@PathVariable("id") Long id, Model model) {
		return null;
	}

	@ModelAttribute("products")
	public List<ProductDto> getProducts() {
		List<ProductDto> productDtos = productRepository.showAllProducts().stream()
				.map(product -> new ProductDto(product.getId(), product.getName(), product.getCategoryId())).collect(Collectors.toList());
		return productDtos;
	}

	@ModelAttribute("orders")
	public List<OrderDto> getOrders() {
		List<OrderDto> orderDtos = orderRepository.showAllOrders().stream()
				.map(orders -> new OrderDto(orders.getId(), orders.getOrderDate(), orders.getStatus(), orders.getLocationId())).collect(Collectors.toList());
		return orderDtos;
	}
}
