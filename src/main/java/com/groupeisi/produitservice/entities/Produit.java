package com.groupeisi.produitservice.entities;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name = "produits") @Data @NoArgsConstructor @AllArgsConstructor
public class Produit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String nom;
    @Column(nullable = false, unique = true) private String code;
    @Column(nullable = false) private String description;
    @Column(nullable = false) private Double stock;
    @Column(nullable = false) private String niveau;
    @Column(nullable = false) private String enseignant;
}
