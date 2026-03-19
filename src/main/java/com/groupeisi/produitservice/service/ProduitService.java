package com.groupeisi.produitservice.service;
import com.groupeisi.produitservice.dto.ProduitDTO;
import com.groupeisi.produitservice.entities.Produit;
import com.groupeisi.produitservice.exception.ResourceNotFoundException;
import com.groupeisi.produitservice.mapper.ProduitMapper;
import com.groupeisi.produitservice.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service @RequiredArgsConstructor
public class ProduitService {
    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;
    @Cacheable("produits") public List<ProduitDTO> findAll() { return produitRepository.findAll().stream().map(produitMapper::toDTO).collect(Collectors.toList()); }
    public ProduitDTO findById(Long id) { return produitMapper.toDTO(produitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé: " + id))); }
    public ProduitDTO findByCode(String code) { return produitMapper.toDTO(produitRepository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé: " + code))); }
    public List<ProduitDTO> findDisponibles() { return produitRepository.findByStockGreaterThan(0.0).stream().map(produitMapper::toDTO).collect(Collectors.toList()); }
    @CacheEvict(value="produits", allEntries=true)
    public ProduitDTO save(ProduitDTO dto) {
        if (produitRepository.findByCode(dto.getCode()).isPresent()) throw new IllegalArgumentException("Code déjà existant: " + dto.getCode());
        return produitMapper.toDTO(produitRepository.save(produitMapper.toEntity(dto)));
    }
    @CacheEvict(value="produits", allEntries=true)
    public ProduitDTO update(Long id, ProduitDTO dto) {
        Produit p = produitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé: " + id));
        p.setNom(dto.getNom()); p.setDescription(dto.getDescription()); p.setStock(dto.getStock()); p.setNiveau(dto.getNiveau()); p.setEnseignant(dto.getEnseignant());
        return produitMapper.toDTO(produitRepository.save(p));
    }
    @CacheEvict(value="produits", allEntries=true)
    public void delete(Long id) { if (!produitRepository.existsById(id)) throw new ResourceNotFoundException("Produit non trouvé: " + id); produitRepository.deleteById(id); }
    // Appelé par commande-service (communication SYNCHRONE)
    @CacheEvict(value="produits", allEntries=true)
    public ProduitDTO diminuerStock(Long id, Double quantite) {
        Produit p = produitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé: " + id));
        if (p.getStock() < quantite) throw new IllegalArgumentException("Stock insuffisant pour: " + p.getNom());
        p.setStock(p.getStock() - quantite);
        return produitMapper.toDTO(produitRepository.save(p));
    }
}
