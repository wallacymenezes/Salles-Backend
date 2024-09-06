package com.projects.sallesregister.controller;

import com.projects.sallesregister.model.Venda;
import com.projects.sallesregister.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vendas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;




    @GetMapping("/buscar")
    public ResponseEntity<List<Venda>> buscarVendas(@RequestParam("termo") String termo) {
        List<Venda> vendas = vendaRepository.findByTermo(termo);
        if (vendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendas);
    }
}
