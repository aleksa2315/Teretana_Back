package org.example.usermanagement.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.internal.bytebuddy.build.HashCodeAndEqualsPlugin;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@HashCodeAndEqualsPlugin.Enhance
public class DishIngredientKey implements Serializable {

    private Long dishId;
    private Long ingredientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishIngredientKey that = (DishIngredientKey) o;
        return Objects.equals(dishId, that.dishId) &&
                Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishId, ingredientId);
    }
}
