package com.expenseTracker.expenseTracker.controllers.dto;

import java.util.List;

import com.expenseTracker.expenseTracker.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MakerDTO {
    private Long id;
    private String name;
    private List<Product> productList;
}
