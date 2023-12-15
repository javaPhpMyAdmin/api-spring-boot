package com.expenseTracker.expenseTracker.controllers.dto;

import java.math.BigDecimal;

import com.expenseTracker.expenseTracker.entities.Maker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Maker maker;

    public boolean isValidDTO(ProductDTO productDTO) {
        if (productDTO.getName() == null || productDTO.getName().isBlank() || productDTO.getPrice() == null
                || productDTO.getMaker() == null) {
            return false;
        }
        return true;
    }
}
