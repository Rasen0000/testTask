package ru.taskTask.services;

import org.springframework.data.repository.CrudRepository;
import ru.taskTask.dao.Goods;

public interface GoodsRepository extends CrudRepository<Goods, Long> {
}
