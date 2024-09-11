package com.projects.sallesregister.controller;

import com.projects.sallesregister.model.Cargo;
import com.projects.sallesregister.repository.CargoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cargos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CargoController {

    @Autowired
    private CargoRepository cargoRepository;

    // Listar todos os cargos
    @GetMapping
    public ResponseEntity<List<Cargo>> buscarTodosCargos() {
        List<Cargo> cargos = cargoRepository.findAll();
        if (cargos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cargos);
    }

    // Buscar cargo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cargo> buscarCargoPorId(@PathVariable Long id) {
        return cargoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar novo cargo
    @PostMapping
    public ResponseEntity<Cargo> cadastrarCargo(@Valid @RequestBody Cargo cargo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cargoRepository.save(cargo));
    }

    // Atualizar cargo existente
    @PutMapping
    public ResponseEntity<Cargo> atualizarCargo(@Valid @RequestBody Cargo cargo) {
        return cargoRepository.findById(cargo.getId())
                .map(existingCargo -> ResponseEntity.status(HttpStatus.OK).body(cargoRepository.save(cargo)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Deletar cargo por ID
    @DeleteMapping("/{id}")
    public void deletarCargo(@PathVariable Long id) {
        Optional<Cargo> cargo = cargoRepository.findById(id);

        if (cargo.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        cargoRepository.deleteById(id);
    }
}
