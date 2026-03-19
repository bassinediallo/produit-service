package com.groupeisi.produitservice.controller;
import com.groupeisi.produitservice.dto.ProduitDTO;
import com.groupeisi.produitservice.service.ProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/produits") @RequiredArgsConstructor
@Tag(name = "Produits") @SecurityRequirement(name = "bearerAuth")
public class ProduitController {
    private final ProduitService produitService;
    @GetMapping @Operation(summary = "Tous les produits") public ResponseEntity<List<ProduitDTO>> findAll() { return ResponseEntity.ok(produitService.findAll()); }
    @GetMapping("/{id}") @Operation(summary = "Produit par ID") public ResponseEntity<ProduitDTO> findById(@PathVariable Long id) { return ResponseEntity.ok(produitService.findById(id)); }
    @GetMapping("/code/{code}") @Operation(summary = "Produit par code") public ResponseEntity<ProduitDTO> findByCode(@PathVariable String code) { return ResponseEntity.ok(produitService.findByCode(code)); }
    @GetMapping("/disponibles") @Operation(summary = "Produits disponibles (stock > 0)") public ResponseEntity<List<ProduitDTO>> findDisponibles() { return ResponseEntity.ok(produitService.findDisponibles()); }
    @PostMapping @Operation(summary = "Créer un produit") public ResponseEntity<ProduitDTO> save(@RequestBody ProduitDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(produitService.save(dto)); }
    @PutMapping("/{id}") @Operation(summary = "Mettre à jour") public ResponseEntity<ProduitDTO> update(@PathVariable Long id, @RequestBody ProduitDTO dto) { return ResponseEntity.ok(produitService.update(id, dto)); }
    @DeleteMapping("/{id}") @Operation(summary = "Supprimer") public ResponseEntity<Void> delete(@PathVariable Long id) { produitService.delete(id); return ResponseEntity.noContent().build(); }
    // Endpoint appelé par commande-service (communication SYNCHRONE)
    @PutMapping("/{id}/diminuer-stock")
    @Operation(summary = "Diminuer le stock — appelé par commande-service (sync)")
    public ResponseEntity<ProduitDTO> diminuerStock(@PathVariable Long id, @RequestParam Double quantite) {
        return ResponseEntity.ok(produitService.diminuerStock(id, quantite));
    }
}
