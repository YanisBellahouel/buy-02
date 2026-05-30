package com.buy01.product.repository;

import com.buy01.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByUserId(String userId);

    // Recherche par mot-clé dans le nom ou la description
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name, String description);

    // Filtrage par catégorie
    List<Product> findByCategory(String category);

    // Filtrage par disponibilité
    List<Product> findByIsAvailable(Boolean isAvailable);

    // Recherche + catégorie
    List<Product> findByNameContainingIgnoreCaseAndCategory(String name, String category);

    // Filtrage par prix
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // Recherche complète combinée
    @Query("{ $and: [ " +
			"{ $or: [ { 'name': { $regex: ?0, $options: 'i' } }, { 'description': { $regex: ?0, $options: 'i' } } ] }, " +
			"{ $or: [ { 'category': { $exists: false } }, { 'category': ?1 } ] }, " +
			"{ $or: [ { 'price': { $exists: false } }, { 'price': { $gte: ?2, $lte: ?3 } } ] }, " +
			"{ 'isAvailable': true } " +
			"] }")
    List<Product> searchProducts(String keyword, String category, Double minPrice, Double maxPrice);
}