package ro.msg.learning.shop.mapper;

import ro.msg.learning.shop.dto.ProductDto;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.model.Supplier;

public class ProductMapper {

    public static ProductDto toProductDto(Product product) {
        if (product == null || product.getCategory() == null) {
            return null;
        }

        return ProductDto.builder()
                .productName(product.getName())
                .productDescription(product.getDescription())
                .price(product.getPrice())
                .weight(product.getWeight())
                .imageUrl(product.getImageUrl())
                .supplierId(product.getSupplier().getSupplierId())
                .categoryId(product.getCategory().getProductCategoryId())
                .categoryName(product.getCategory().getName())
                .categoryDescription(product.getCategory().getDescription())
                .build();
    }

    public static Product toProduct(ProductDto dto) {
        if (dto == null) {
            return null;
        }

        ProductCategory category = new ProductCategory();
        category.setProductCategoryId(dto.getCategoryId());

        Supplier supplier = new Supplier();
        supplier.setSupplierId(dto.getSupplierId());

        return Product.builder()
                .name(dto.getProductName())
                .description(dto.getProductDescription())
                .price(dto.getPrice())
                .weight(dto.getWeight())
                .imageUrl(dto.getImageUrl())
                .category(category)
                .supplier(supplier)
                .build();
    }

}
