package spring.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.dto.InvoiceDto;
import spring.dto.OrderDetailDto;
import spring.model.Invoice;
import spring.model.OrderDetail;
import spring.repository.InvoiceRepository;

import java.util.List;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value="/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/add_invoice", method = RequestMethod.GET)
    public ModelAndView showRegister(Model model) {
        Invoice invoice = new Invoice();
        return new ModelAndView("invoice_add", "invoice", invoice);
    }

    @RequestMapping(value = "/do_invoice", method = RequestMethod.POST)
    public String showResult(@ModelAttribute("invoice") Invoice invoice, Model model) {

        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setQuantity(invoice.getQuantity());
        invoiceDto.setProductId(invoice.getProductId());
        invoiceDto.setOrderId(invoice.getOrderId());

        int i = invoiceRepository.insertInvoice(invoiceDto);
        if (i > 0) {
            return "redirect:/invoice/showinvoices";
        } else {
            model.addAttribute("invoice", new Invoice());
            return "add_invoice";
        }
    }

    @GetMapping(value = "/showinvoices")
    public String showAllInvoices(Model model) {
        List<InvoiceDto> invoiceDtos = invoiceRepository.showAllInvoices();
        model.addAttribute("invoiceDtoList", invoiceDtos);
        return "invoice_list";
    }

    @GetMapping(value = "/editinvoice/{id}")
    public String showInvoiceById(@PathVariable("id") Long id, Model model) {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setId(id);

        InvoiceDto dto = invoiceRepository.showInvoice(invoiceDto);

        Invoice invoice = modelMapper.map(dto, Invoice.class);

        model.addAttribute("invoice", invoice);
        return "invoice_update";
    }


    @PostMapping(value = "/doupdate")
    public String updateInvoice(@ModelAttribute("invoice") Invoice invoice) {
        InvoiceDto invoiceDto = modelMapper.map(invoice, InvoiceDto.class);

        int i = invoiceRepository.updateInvoice(invoiceDto);
        if (i > 0) {
            return "redirect:/invoice/showinvoices";
        } else {
            return "redirect:editinvoice/" + invoiceDto.getId();
        }
    }

    @RequestMapping(value = "/deleteinvoice/{id}", method = RequestMethod.GET)
    public String deleteInvoice(@PathVariable("id") Long id, Model model) {
        return null;
    }
}
