package ru.taskTask.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import ru.taskTask.dao.OrderAll;
import ru.taskTask.services.OrderRepository;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {


    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderController controller;


    @Test
    public void homeTest() throws Exception {
        this.mockMvc.perform(get("/")) ///выполнение url запроса
                .andDo(print())  //вывод результата в консоль
                .andExpect(status().isOk())  //статус на гет запрос сайта вернет 200(ок)
                .andExpect(content().string(containsString("Главная страница")));
    }

    @AfterEach
    public void resetDb() {
        orderRepository.deleteAll();
    }

    private OrderAll createTestOrder(String client, String date, String address) {
        OrderAll orderAll = new OrderAll(client, date, address);
        return orderRepository.save(orderAll);
    }


    @Test
    public void givenId() throws Exception{
        Long id = createTestOrder("Michail", "дата", "Ростов").getId();
        mockMvc.perform(
                        get("/order/{id}", id))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.client").value("Michail"))
//                .andExpect(jsonPath("$.date").value("дата"))
//                .andExpect(jsonPath("$.address").value("Ростов"));
    }

    @Test
    public void addOrderTest() throws Exception{
        MockMultipartHttpServletRequestBuilder multipart = multipart("/order/add")
                .file("client", "123".getBytes(StandardCharsets.UTF_8))
                .file("date", "123".getBytes(StandardCharsets.UTF_8))
                .file("address", "123".getBytes(StandardCharsets.UTF_8));
        this.mockMvc.perform(multipart)
                .andDo(print());
    }
}