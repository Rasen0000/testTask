package ru.taskTask.services;

import org.springframework.data.repository.CrudRepository;
import ru.taskTask.dao.OrderAll;

public interface OrderRepository extends CrudRepository <OrderAll, Long> {



}
