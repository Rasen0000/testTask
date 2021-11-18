package ru.taskTask.dao;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class OrderLine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "order_id")
    private OrderAll orderAll;

    @ManyToOne
    @JoinColumn (name = "good_id")
    private Goods goods;

    private Long count;
}
