package com.aunanant.SpringGCpSecurity.controllers;

import com.aunanant.SpringGCpSecurity.entity.Detail;
import com.aunanant.SpringGCpSecurity.entity.Product;
import com.aunanant.SpringGCpSecurity.repository.ProductRepository;
import com.aunanant.SpringGCpSecurity.repository.DetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/details")


public class DetailController {
	private final DetailRepository detailRepository;
	private final ProductRepository productRepository;
	
	@Autowired
	public DetailController(DetailRepository detailRepository, ProductRepository productRepository) {
		super();
		this.detailRepository = detailRepository;
		this.productRepository = productRepository;
	}

	@PostMapping("/admin")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Detail> create(@RequestBody @Valid Detail detail) {
		Optional<Product> optionalProduct = productRepository.findById(detail.getProduct().getId());
		if (!optionalProduct.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}

		detail.setProduct(optionalProduct.get());

		Detail savedDetail = detailRepository.save(detail);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedDetail.getId()).toUri();

		return ResponseEntity.created(location).body(savedDetail);
	}

	@PutMapping("/admin/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Detail> update(@RequestBody @Valid Detail detail, @PathVariable Long id) {
		Optional<Product> optionalProduct = productRepository.findById(detail.getProduct().getId());
		if (!optionalProduct.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}

		Optional<Detail> optionalDetail = detailRepository.findById(id);
		if (!optionalDetail.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}

		detail.setProduct(optionalProduct.get());
		detail.setId(optionalDetail.get().getId());
		detailRepository.save(detail);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("admin/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Detail> delete(@PathVariable Long id) {
		Optional<Detail> optionalDetail = detailRepository.findById(id);
		if (!optionalDetail.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}

		detailRepository.delete(optionalDetail.get());

		return ResponseEntity.noContent().build();
	}

	@GetMapping("admin/")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Detail>> getAllTutorials(@RequestParam(required = false) String name) {
		try {
			List<Detail> details = new ArrayList<Detail>();

			if (name == null)
				detailRepository.findAll().forEach(details::add);
			else
				detailRepository.findByDetailNameContaining(name).forEach(details::add);

			if (details.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(details, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("admin/{id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Detail> getDetailById(@PathVariable Long id) {
		Optional<Detail> optionalDetail = detailRepository.findById(id);
		if (!optionalDetail.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}

		return ResponseEntity.ok(optionalDetail.get());
	}

	@GetMapping("admin/product/{productId}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Detail>> getByProductId(@PathVariable Long productId) {
		return ResponseEntity.ok(detailRepository.findByProductId(productId));
	}

	

}
