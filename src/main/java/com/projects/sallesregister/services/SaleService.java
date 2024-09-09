package com.projects.sallesregister.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.projects.sallesregister.dto.SaleDTO;
import com.projects.sallesregister.entities.Sale;
import com.projects.sallesregister.repositories.SaleRepository;
import com.projects.sallesregister.services.exceptions.DatabaseException;
import com.projects.sallesregister.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	@Transactional(readOnly = true)
	public Page<SaleDTO> findAllPaged(Pageable pageable) {
		Page<Sale> list = repository.findAll(pageable);
		return list.map(x -> new SaleDTO(x));
	}

	@Transactional(readOnly = true)
	public SaleDTO findById(Long id) {
		Sale sale = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
		return new SaleDTO(sale, sale.getUser());
	}

	@Transactional
	public SaleDTO insert(SaleDTO dto) {
		Sale entity = new Sale();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new SaleDTO(entity);
	}

	@Transactional
	public SaleDTO update(Long id, SaleDTO dto) {
		try {
			Sale entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new SaleDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Sale id not found: " + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Sale id not found: " + id);
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Referential Integrity Failure");
		}
	}

	private void copyDtoToEntity(SaleDTO dto, Sale entity) {
		entity.setClientName(dto.getClientName());
		entity.setCpf(dto.getCpf());
		entity.setWhatsapp(dto.getWhatsapp());
		entity.setPhone(dto.getPhone());
		entity.setCardBrand(dto.getCardBrand());
		entity.setCardBankName(dto.getCardBankName());
		entity.setSaleValue(dto.getSaleValue());
		entity.setSaleStatus(dto.getSaleStatus());
		entity.setNotes(dto.getNotes());
		entity.setClientType(dto.getClientType());
		entity.setCnpj(dto.getCnpj());
		entity.setUser(dto.getUser());
	}

	@Transactional(readOnly = true)
	public Page<SaleDTO> searchSales(String term, Pageable pageable) {
		List<Sale> list = repository.findByTermo(term);
		List<SaleDTO> dtos = list.stream().map(SaleDTO::new).toList();
		return new PageImpl<>(dtos, pageable, dtos.size());
	}
}
