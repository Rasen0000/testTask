package ru.taskTask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.taskTask.dao.Goods;
import ru.taskTask.services.GoodsRepository;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping
public class GoodsController {


    @Autowired
    private GoodsRepository goodsRepository;

    @GetMapping("/goods")
    public String goods(Model model) {
        Iterable<Goods> goods = goodsRepository.findAll();
        model.addAttribute("goods", goods);
        return "goods";

    }

    @GetMapping("/goods/add")
    public String goodsAdd(Model model) {
        return "goods-add";

    }

    @PostMapping("/goods/add")
    public String goodsAddNew(@RequestParam String name, @RequestParam Integer price, Model model) {
        Goods goods = new Goods(name, price);
        goodsRepository.save(goods);
        return "redirect:/goods";
    }


    @GetMapping("/goods/{id}")  ///поиск по id
    public String orderInfo(@PathVariable(value = "id") long id, Model model) {
        if (!goodsRepository.existsById(id)) {

            return "redirect:/goods"; //todo: допилить ответ если запись не найдена
        }
        Optional<Goods> orderAll = goodsRepository.findById(id);
        ArrayList<Goods> result = new ArrayList<>();
        orderAll.ifPresent(result::add);
        model.addAttribute("product", result);
        return "goods-info";

    }

    @GetMapping("/goods/{id}/edit")
    public String goodsEdit(@PathVariable(value = "id") long id, Model model) {
        if (!goodsRepository.existsById(id)) {
            return "redirect:/goods"; //todo: допилить ответ если запись не найдена
        }

        Optional<Goods> orderAll = goodsRepository.findById(id);
        ArrayList<Goods> result = new ArrayList<>();
        orderAll.ifPresent(result::add);
        model.addAttribute("product", result);
        return "goods-edit";

    }


    @PostMapping("/goods/{id}/edit")
    public String goodsUpdate (@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam Integer price, Model model) {
        Goods goods = goodsRepository.findById(id).orElseThrow();
        goods.setName(name);
        goods.setPrice(price);
        goodsRepository.save(goods);
        return "redirect:/goods";
    }

    @PostMapping("/goods/{id}/remove")
    public String goodsRemove (@PathVariable(value = "id") long id, Model model) {
        Goods goods = goodsRepository.findById(id).orElseThrow();
        goodsRepository.delete(goods);
        return "redirect:/goods";
    }




}


