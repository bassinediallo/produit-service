package com.groupeisi.produitservice.repository;
import com.groupeisi.produitservice.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Optional<Produit> findByCode(String code);
    List<Produit> findByNiveau(String niveau);
    List<Produit> findByStockGreaterThan(Double stock);
}
