package com.projects.sallesregister.controller;

import com.projects.sallesregister.model.Venda;
import com.projects.sallesregister.repository.VendaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @GetMapping
    public ResponseEntity<List<Venda>> buscarTodasVendas() {
        List<Venda> vendas = vendaRepository.findAll();
        if (vendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarVendaPorId(@PathVariable Long id) {
        return vendaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Venda> cadastrarVenda(@Valid @RequestBody Venda venda) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaRepository.save(venda));
    }

    @PutMapping
    public ResponseEntity<Venda> atualizarVenda(@Valid @RequestBody Venda venda) {
        return vendaRepository.findById(venda.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(vendaRepository.save(venda)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public void deletarVenda(@PathVariable Long id) {
        Optional<Venda> venda = vendaRepository.findById(id);

        if (venda.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        vendaRepository.deleteById(id);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Venda>> buscarVendas(@RequestParam("termo") String termo) {
        List<Venda> vendas = vendaRepository.findByTermo(termo);
        if (vendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendas);
    }
}
