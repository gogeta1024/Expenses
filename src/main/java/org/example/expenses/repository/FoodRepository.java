package org.example.expenses.repository;

import org.example.expenses.entity.FoodCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<FoodCost, Long> {
}
