package org.example.usermanagement.service;

import org.example.usermanagement.entity.Ingredient;
import org.example.usermanagement.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    /**
     * Dohvata sve sastojke iz baze.
     */
    public Page<Ingredient> getIngredients(Pageable pageable, String search) {
        if (search != null && !search.isEmpty()) {
            return ingredientRepository.findByNameContainingIgnoreCase(search, pageable);
        } else {
            return ingredientRepository.findAll(pageable);
        }
    }

    /**
     * Dohvata sastojak po ID-u.
     */
    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    /**
     * Kreira novi sastojak.
     */
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    /**
     * Ažurira postojeći sastojak.
     */
    public Ingredient updateIngredient(Long id, Ingredient ingredient) {
        Optional<Ingredient> existingIngredient = ingredientRepository.findById(id);
        if (existingIngredient.isPresent()) {
            Ingredient updatedIngredient = existingIngredient.get();
            updatedIngredient.setName(ingredient.getName());
            return ingredientRepository.save(updatedIngredient);
        }
        return null;
    }

    /**
     * Briše sastojak po ID-u.
     */
    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}
