package spring.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.mapper.Mapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.dto.DetailsDto;
import spring.dto.InvoiceDetailsDto;
import spring.dto.LocationDto;
import spring.dto.LotDto;
import spring.dto.OrderDetailDto;
import spring.dto.OrderDto;
import spring.model.Invoice;
import spring.model.Lot;
import spring.model.OrderBean;
import spring.model.OrderDetail;
import spring.dto.ProductDto;
import spring.repository.InvoiceRepository;
import spring.repository.LocationRepository;
import spring.repository.LotRepository;
import spring.repository.OrderDetailRepository;
import spring.repository.OrderRepository;
import spring.repository.ProductRepository;

@Controller
@RequestMapping(value="/order")
@AllArgsConstructor
@NoArgsConstructor
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private LotRepository lotRepository;
	
	@Autowired
	private LocationRepository locationRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private OrderDetailRepository detailRepo;
	
	@Autowired
	private InvoiceRepository invoiceRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	@GetMapping("/chooseProducts")
	public String showOrder(ModelMap model)
	{
		List<LotDto> products = lotRepository.getAllLots();
		model.addAttribute("products", products);
		return "order_to_choose";
	}
	
	@PostMapping("/choose_order")
	public String chooseOrder(HttpServletRequest req, ModelMap model)
	{
		String[] ids = req.getParameterValues("order_id");
		List<LotDto> products=null;
		 if (ids != null && ids.length > 0) {
			 products= lotRepository.getLotsToOrder(ids);
	        }
		 model.addAttribute("products", products);
		return "order_add";
	}
	
	
	@PostMapping("/do_order")
	public String doOrder(HttpServletRequest req)
	{
		String[] lot = req.getParameterValues("product");
		String[] qty = req.getParameterValues("order_qty");
		String[] price = req.getParameterValues("price");
		String customer = req.getParameter("customer");
		double amount = 0;
		
		for(int i=0; i<qty.length; i++)
		{
			amount += Double.valueOf(qty[i]) * Double.valueOf(price[i]);
		}
		
		OrderBean order= new OrderBean();
		order.setOrderDate(java.time.LocalDate.now());
		order.setStatus("false");
		order.setTotalAmount(amount);
		order.setCustomer(customer);
		OrderDto dto = modelMapper.map(order, OrderDto.class);
		orderRepo.insertOrder(dto);
		
		long order_id = orderRepo.getOrderID();
		for(int i=0; i<lot.length; i++)
		{
			LotDto dbRs = lotRepository.getLotById(lot[i]);
			dbRs.setQuantity(Integer.valueOf(qty[i]));
			
			Lot lotBean = modelMapper.map(dbRs, Lot.class);
			
			OrderDetail detail = new OrderDetail();
			detail.setOrderId(order_id+"");
			detail.setProductId(lotBean.getLotNumber());
			detail.setQuantity(lotBean.getQuantity());
			detail.setUnitPrice(lotBean.getPrice());
			
			OrderDetailDto detailDto = modelMapper.map(detail, OrderDetailDto.class);
			detailRepo.insertDto(detailDto);
			
		}
		
		return "redirect:lists";
	}
	
	
	@GetMapping("/lists")
	public String getOrders(ModelMap model)
	{
		List<OrderDto> dbRs = orderRepo.getAll();
		model.addAttribute("orders", dbRs);
		return "order_list";
	}
	
	@ModelAttribute("customers")
	public List<LocationDto> getCustInfo()
	{
		List<LocationDto> locations = locationRepo.getAllLocations();
		
		return locations;
	}
	
	
	@GetMapping("/invoice/{id}")
	public String showOrderDt(@PathVariable("id")Integer id, ModelMap model)
	{
		List<DetailsDto> dbRs = detailRepo.getByOID(id);
		model.addAttribute("orders", dbRs);
		model.addAttribute("orderId", id);	
		return "invoice_show";
	}
	
	
	@PostMapping("/doconfirm")
	public String doConfirm(HttpServletRequest req)
	{
		String[] orderQty = req.getParameterValues("orderQty");
		String[] lot = req.getParameterValues("lot");
		Map<String, String> qtyLot = new HashMap<>();
		for(int i=0; i<lot.length; i++)
		{
			qtyLot.put(lot[i], orderQty[i]);
		}
		String orderId = req.getParameter("orderId");
		
		for(Map.Entry<String, String> entry: qtyLot.entrySet())
		{
			Invoice invoiceBean = new Invoice();
			invoiceBean.setOrderId(orderId);
			invoiceBean.setLot(entry.getKey());
			invoiceBean.setQty(entry.getValue());
			invoiceRepo.insert(invoiceBean);
			LotDto dbRs = lotRepository.getLotById(entry.getKey());
			Lot lotBean = modelMapper.map(dbRs, Lot.class);
			int decreaseQty = lotBean.getQuantity() - Integer.valueOf(entry.getValue());
			lotRepository.updateQty(entry.getKey(), decreaseQty);	
		}

		orderRepo.updateStatusByID(orderId);
		
		return "redirect:/order/lists";
	}

	@GetMapping("/details/{oId}")
	public String getDetails(@PathVariable("oId")Integer oId, ModelMap model)
	{
		List<InvoiceDetailsDto> dbRs = invoiceRepo.makeDetails(oId);
		double total = 0;
		for(InvoiceDetailsDto inv: dbRs)
		{
			model.addAttribute("oid", inv.getOId());
			model.addAttribute("date", inv.getODate());
			model.addAttribute("invoice", inv.getInvoiceId());
			model.addAttribute("customer", inv.getCustomer());
			model.addAttribute("address", inv.getAddress());
			total += inv.getPrice() * inv.getQuantity();
		}
		model.addAttribute("total", total);
		model.addAttribute("details", dbRs);
		return "invoice_details";
	}
	
	@GetMapping("/invoice/pdf/{oId}")
	public ResponseEntity<ByteArrayResource> getPDF(@PathVariable("oId")Integer oId)
	{
		List<InvoiceDetailsDto> dbRs = invoiceRepo.makeDetails(oId);
		double total = 0;
		byte[] pdfContents = null;
		for(InvoiceDetailsDto inv: dbRs)
		{
			total += inv.getPrice() * inv.getQuantity();
			pdfContents = PDFMaker.makePDF(inv.getOId(), inv.getODate(), inv.getInvoiceId(), inv.getCustomer(), inv.getAddress(), dbRs);
		}
		
		ByteArrayResource resource = new ByteArrayResource(pdfContents);
		return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfContents.length)
                .body(resource);
	}
}
