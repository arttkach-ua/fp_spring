package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.Messages;
import com.epam.tkach.carrent.PageParameters;
import com.epam.tkach.carrent.Pages;
import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.entity.Order;
import com.epam.tkach.carrent.entity.User;
import com.epam.tkach.carrent.entity.enums.CarClass;
import com.epam.tkach.carrent.entity.enums.OrderStatuses;
import com.epam.tkach.carrent.entity.enums.Role;
import com.epam.tkach.carrent.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.exceptions.NoSuchCarException;
import com.epam.tkach.carrent.exceptions.NoSuchOrderException;
import com.epam.tkach.carrent.exceptions.NoSuchUserException;
import com.epam.tkach.carrent.exceptions.OrderProcessingException;
import com.epam.tkach.carrent.service.CarBrandService;
import com.epam.tkach.carrent.service.CarService;
import com.epam.tkach.carrent.service.OrderService;
import com.epam.tkach.carrent.service.UserService;
import com.epam.tkach.carrent.util.dto.OrderDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class OrderController {
    private static final Logger logger = LogManager.getLogger(OrderController.class);

    @Autowired
    CarService carService;

    @Autowired
    CarBrandService carBrandService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;


    @GetMapping("/orders/selectCar")
    public String showAvailableCars(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                    @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                    @RequestParam(value = "car_class_filter", required = false, defaultValue = "") CarClass carClassFilter,
                                    @RequestParam(value = "car_brand_filter", required = false, defaultValue = "0") CarBrand carBrandFilter,
                                    @RequestParam(value = "transmission_filter", required = false, defaultValue = "") TransmissionTypes transmissionFilter,
                                    @RequestParam(value = "sort_field", required = false, defaultValue = "") String sortField,
                                    Model model){

        model.addAttribute("carClasses", CarClass.values());
        model.addAttribute("carBrands", carBrandService.getAll());
        model.addAttribute("transmissionList", TransmissionTypes.values());
        model.addAttribute(PageParameters.SORT_LIST, carService.getSortingList());
        //Setting filter fields
        model.addAttribute("car_class_filter", carClassFilter!=null ? carClassFilter : "");
        model.addAttribute("car_brand_filter", carBrandFilter!=null ? carBrandFilter.getId() : -1);
        model.addAttribute("transmission_filter", transmissionFilter!=null ? transmissionFilter : "");
        model.addAttribute("sort_field", sortField!=null ? sortField : "");
        model.addAttribute(PageParameters.ENTITY_LIST, carService.getCarsForNewOrder(pageNumber,size,carClassFilter,carBrandFilter,transmissionFilter, sortField));
        return Pages.SELECT_CAR;
    }

    @GetMapping("/orders/create/{id}")
    public String openNewOrderPage(HttpSession session, @PathVariable("id") int id, Model model){
        try {
            OrderDto dto = new OrderDto();
            dto.setCar(carService.findById(id));
            dto.setClient(userService.getCurrentUser());
            dto.setDocuments(dto.getClient().getDocumentInfo());
            dto.setDaysCount(1);//default value
            dto.setAmount(dto.getCar().getTariff().getRentPrice());//default value
            model.addAttribute(PageParameters.ORDER_DTO, dto);
            return Pages.CREATE_ORDER;
        } catch (NoSuchCarException | NoSuchUserException e) {
           logger.error(e);
           return Pages.ERROR;
        }
    }

    @PostMapping("/orders/create")
    public String createOrder(@Valid OrderDto orderDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute(PageParameters.ORDER_DTO, orderDto);
            return Pages.CREATE_ORDER;
        }
        try {
            orderService.createNew(orderDto);
        } catch (OrderProcessingException|NoSuchCarException e) {
            logger.error(e);
            model.addAttribute(PageParameters.ERROR_MESSAGE, e.getMessage());
            return Pages.ERROR;
        }
        System.out.println(orderDto.toString());
        return "redirect:/";
    }

    @GetMapping("/orders/list")
    public String showOrders(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                             @RequestParam(value = "car_brand_filter", required = false, defaultValue = "0") CarBrand carBrandFilter,
                             @RequestParam(value = "status_filter", required = false, defaultValue = "") OrderStatuses statusFilter,
                             @RequestParam(value = "sort_field", required = false, defaultValue = "") String sortField,
                             Model model) {

        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser.getRole()== Role.CLIENT){
                model.addAttribute(PageParameters.ENTITY_LIST, orderService.getPage(pageNumber, size,carBrandFilter,statusFilter,currentUser, sortField));
            }else{
                model.addAttribute(PageParameters.ENTITY_LIST, orderService.getPage(pageNumber, size,carBrandFilter,statusFilter,null, sortField));
            }

            model.addAttribute(PageParameters.CAR_BRANDS, carBrandService.getAll());
            model.addAttribute(PageParameters.STATUSES, OrderStatuses.values());
            model.addAttribute("sortList", orderService.getSortList());
            //Setting filter fields
            model.addAttribute("status_filter", statusFilter!=null ? statusFilter : "");
            model.addAttribute("car_brand_filter", carBrandFilter!=null ? carBrandFilter.getId() : -1);
            model.addAttribute("sort_field", sortField!=null ? sortField : "");
            return Pages.ORDERS;
        } catch (NoSuchUserException e) {
            logger.error(e);
            return Pages.ERROR;
        }
    }

    @GetMapping("/orders/confirmOrder/{id}")
    public String confirmOrder(@PathVariable("id") int id, Model model){
        if (id<=0) return Pages.ERROR;
        try {
            orderService.confirmOrder(id);
            return "redirect:/orders/list";
        } catch (NoSuchOrderException|OrderProcessingException e) {
            model.addAttribute(PageParameters.ERROR_MESSAGE, e.getMessage());
            logger.error(e);
            return Pages.ERROR;
        }
    }
    @GetMapping("orders/declineOrder/{id}")
    public String openDeclineOrderPage(@PathVariable("id") int id, Model model){
        try {
            Order order = orderService.findById(id);
            model.addAttribute(PageParameters.ORDER_DTO, Order.toDTO(order));
            return Pages.DECLINE_ORDER;
        } catch (NoSuchOrderException e) {
            model.addAttribute(PageParameters.ERROR_MESSAGE, e.getMessage());
            logger.error(e);
            return Pages.ERROR;
        }
    }

    @PostMapping("orders/decline")
    public String declineOrder(OrderDto orderDto,
                               BindingResult bindingResult, Model model){
        if (orderDto.getComment().isEmpty()){
            bindingResult.rejectValue("comment", "error.comment", Messages.ERROR_EMPTY_COMMENT);
            model.addAttribute(PageParameters.ORDER_DTO, orderDto);
            return Pages.DECLINE_ORDER;
        }
        try {
            orderService.declineOrder(orderDto.getId(), orderDto.getComment());
            return "redirect:/orders/list";
        } catch (NoSuchOrderException e) {
            model.addAttribute(PageParameters.ERROR_MESSAGE, e.getMessage());
            logger.error(e);
            return Pages.ERROR;
        }
    }

    @GetMapping("/orders/closeOrder/{id}")
    public String closeOrder(@PathVariable("id") int id, Model model){
        try {
            orderService.closeOrder(id);
            return "redirect:/orders/list";
        } catch (NoSuchOrderException e) {
            model.addAttribute(PageParameters.ERROR_MESSAGE, e.getMessage());
            logger.error(e);
            return Pages.ERROR;
        }
    }

    @GetMapping("/orders/closeWithDamage/{id}")
    public String openCloseOrderWithDamagePage(@PathVariable("id") int id, Model model){
        try {
            Order order = orderService.findById(id);
            model.addAttribute(PageParameters.ORDER_DTO, Order.toDTO(order));
            return Pages.CLOSE_WITH_DAMAGE;
        } catch (NoSuchOrderException e) {
            model.addAttribute(PageParameters.ERROR_MESSAGE, e.getMessage());
            logger.error(e);
            return Pages.ERROR;
        }
    }

    @PostMapping("/orders/closeWithDamage")
    public String closeOrderWithDamage(OrderDto orderDto,
                                       BindingResult bindingResult, Model model){
        if (orderDto.getComment().isEmpty()) bindingResult.rejectValue("comment", "error.comment", Messages.ERROR_EMPTY_COMMENT);
        if (orderDto.getDamageAmount()==0||orderDto.getDamageAmount()<0) bindingResult.rejectValue("damageAmount", "error.damageAmount", Messages.ERROR_AMOUNT);

        if (bindingResult.hasErrors()){
            model.addAttribute(PageParameters.ORDER_DTO, orderDto);
            return Pages.CLOSE_WITH_DAMAGE;
        }
        try {
            orderService.closeOrderWithDamage(orderDto);
            return "redirect:/orders/list";
        } catch (NoSuchOrderException e) {
            model.addAttribute(PageParameters.ERROR_MESSAGE, e.getMessage());
            logger.error(e);
            return Pages.ERROR;
        }
    }

}
