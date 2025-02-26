package org.example.usermanagement.repository;

import org.example.usermanagement.entity.DishIngredient;
import org.example.usermanagement.entity.DishIngredientKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishIngredientRepository extends JpaRepository<DishIngredient, DishIngredientKey> {
}
