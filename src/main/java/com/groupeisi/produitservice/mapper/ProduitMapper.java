package com.groupeisi.produitservice.mapper;
import com.groupeisi.produitservice.dto.ProduitDTO;
import com.groupeisi.produitservice.entities.Produit;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface ProduitMapper {
    ProduitDTO toDTO(Produit p);
    Produit toEntity(ProduitDTO dto);
}
