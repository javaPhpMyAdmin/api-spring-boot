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

import com.expenseTracker.expenseTracker.controllers.dto.MakerDTO;
import com.expenseTracker.expenseTracker.entities.Maker;
import com.expenseTracker.expenseTracker.service.IMakerService;

@RestController
@RequestMapping("/api/v1/maker")
public class MakerController {

    @Autowired
    private IMakerService makerService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Maker> makerOptional = makerService.findById(id);
        if (makerOptional.isPresent()) {
            Maker maker = makerOptional.get();

            MakerDTO makerDTO = MakerDTO.builder()
                    .id(maker.getId())
                    .name(maker.getName())
                    .productList(maker.getProductList())
                    .build();

            return ResponseEntity.ok(makerDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<MakerDTO> makerList = makerService.findAll()
                .stream()
                .map(maker -> MakerDTO.builder()
                        .id(maker.getId())
                        .name(maker.getName())
                        .productList(maker.getProductList())
                        .build())
                .toList();

        return ResponseEntity.ok(makerList);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDTO) throws URISyntaxException {
        if (makerDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        makerService.save(Maker.builder()
                .name(makerDTO.getName())
                .build());

        return ResponseEntity.created(new URI("/api/v1/maker/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMaker(@RequestBody MakerDTO makerDTO, @PathVariable Long id) {

        Optional<Maker> makerFound = makerService.findById(id);

        if (makerFound.isPresent()) {
            Maker maker = makerFound.get();
            maker.setName(makerDTO.getName());
            System.out.println("MAKER TO UPDATE------");
            System.out.println(maker);
            makerService.save(maker);
            return ResponseEntity.ok("Maker updated");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        if (id != null) {
            System.out.println("MAKER TO DELETE------");
            makerService.deleteById(id);
            return ResponseEntity.ok("Maker deleted");
        }

        return ResponseEntity.badRequest().build();
    }

}
