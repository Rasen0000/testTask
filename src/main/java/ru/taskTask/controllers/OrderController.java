package ru.taskTask.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.taskTask.services.OrderRepository;
import ru.taskTask.dao.OrderAll;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/order")
    public String order(Model model) {
        Iterable<OrderAll> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "order";

    }

    @GetMapping("/order/add")
    public String orderAdd(Model model) {
        return "order-add";

    }

    @PostMapping("/order/add")
    public String orderAddNew(@RequestParam String client, @RequestParam String date, @RequestParam String address, Model model) {
        OrderAll orderAll = new OrderAll(client, date, address);
        orderRepository.save(orderAll);
        return "redirect:/order";
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home"; //вызов html шаблона
    }

    @GetMapping("/order/{id}")  ///поиск по id
    public String orderInfo(@PathVariable(value = "id") long id, Model model) {
        if (!orderRepository.existsById(id)) {
//            log.info("Запись не найдена");
            return "redirect:/order"; //todo: допилить ответ если запись не найдена
        }
        Optional<OrderAll> orderAll = orderRepository.findById(id);
        ArrayList<OrderAll> result = new ArrayList<>();
        orderAll.ifPresent(result::add);
        model.addAttribute("order", result);
        return "order-info";

    }

    @GetMapping("/order/{id}/edit")
    public String orderEdit(@PathVariable(value = "id") long id, Model model) {
        if (!orderRepository.existsById(id)) {
            return "redirect:/order"; //todo: допилить ответ если запись не найдена
        }

        Optional<OrderAll> orderAll = orderRepository.findById(id);
        ArrayList<OrderAll> result = new ArrayList<>();
        orderAll.ifPresent(result::add);
        model.addAttribute("order", result);
        return "order-edit";

    }


    @PostMapping("/order/{id}/edit")
    public String orderUpdate (@PathVariable(value = "id") long id, @RequestParam String client, @RequestParam String date, @RequestParam String address, Model model) {
        OrderAll orderAll = orderRepository.findById(id).orElseThrow();
        orderAll.setClient(client);
        orderAll.setDate(date);
        orderAll.setAddress(address);
        orderRepository.save(orderAll);
        return "redirect:/order";
    }

    @PostMapping("/order/{id}/remove")
    public String orderRemove (@PathVariable(value = "id") long id, Model model) {
        OrderAll orderAll = orderRepository.findById(id).orElseThrow();
        orderRepository.delete(orderAll);
        return "redirect:/order";
    }



//    @RequestMapping(value = "/addOrder", method = RequestMethod.POST) // добавление нового заказа через "curl localhost:8081/add -d id=2 -d client=Client2 -d date=Date2 -d address=address2"
//    public @ResponseBody
//    String addNewOrder (@RequestParam(required=false,name="id") Long id
//            ,@RequestParam(required=false,name="client") String client
//            , @RequestParam(required=false,name="date") String date, @RequestParam(required=false,name="address") String address) {
//
//        OrderAll orderAll = new OrderAll(); //не работает, если создан конструктор в OrderAll
//        orderAll.setId(id);
//        orderAll.setClient(client);
//        orderAll.setDate(date);
//        orderAll.setAddress(address);
//        orderRepository.save(orderAll);
//        return "Saved";
//    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)  //вывод всех заказов
    public @ResponseBody
    Iterable<OrderAll> getAllOrders() {
        // This returns a JSON or XML with the orders

        return orderRepository.findAll();
    }


//    @GetMapping("/greeting")
//    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "greeting";
//    }


}
