package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.DrugAndFood;
import org.springframework.data.repository.CrudRepository;

public interface DrugAndFoodRepository extends CrudRepository<DrugAndFood, Long> {
    DrugAndFood findDrugAndFoodById(Long id);
    DrugAndFood findDrugAndFoodByDataKey(String dataKey);
}
