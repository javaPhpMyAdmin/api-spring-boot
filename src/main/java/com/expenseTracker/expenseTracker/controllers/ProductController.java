package com.expenseTracker.expenseTracker.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenseTracker.expenseTracker.controllers.dto.ProductDTO;
import com.expenseTracker.expenseTracker.entities.Product;
import com.expenseTracker.expenseTracker.service.IProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    public IProductService productService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Product> productFound = productService.findById(id);

        if (productFound.isPresent()) {
            Product product = productFound.get();
            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .maker(product.getWhomakethis())
                    .build();

            return ResponseEntity.ok(productDTO);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<Product> productList = productService.findAll();

        List<ProductDTO> producListDTO = productList.stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .maker(product.getWhomakethis())
                        .price(product.getPrice())
                        .build())
                .toList();

        return ResponseEntity.ok(producListDTO);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductDTO productDTO) throws URISyntaxException {

        if (!productDTO.isValidDTO(productDTO)) {
            return ResponseEntity.badRequest().build();
        }
        Product productFormatted = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .whomakethis(productDTO.getMaker())
                .build();

        productService.save(productFormatted);

        return ResponseEntity.created(new URI("/api/v1/product/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
        Optional<Product> product = productService.findById(id);

        if (product.isPresent()) {
            Product productToSave = product.get();
            productToSave.setName(productDTO.getName());
            productToSave.setPrice(productDTO.getPrice());
            productToSave.setWhomakethis(productDTO.getMaker());

            productService.save(productToSave);

            return ResponseEntity.ok("Product updated");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (id != null) {
            productService.deleteById(id);
            return ResponseEntity.ok("Product deleted successfully");
        }

        return ResponseEntity.badRequest().build();
    }
}
