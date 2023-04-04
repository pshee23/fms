package com.shp.fms.repository.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shp.fms.controller.request.ProductRequestBody;
import com.shp.fms.model.ProductInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.model.entity.Company;
import com.shp.fms.model.entity.Product;

@Component
public class ProductMapper {

	public ProductInfo mapToProductInfo(Product product) {
		return ProductInfo.builder()
				.productId(product.getProductId())
				.companyId(product.getCompany().getCompanyId())
				.branchId(product.getBranch().getBranchId())
				.name(product.getName())
				.price(product.getPrice())
				.startDate(product.getStartDate())
				.endDate(product.getEndDate())
				.type(product.getType())
				.build();
	}
	
	public Product mapToProduct(ProductRequestBody registerInfo, Company company, Branch branch) {
		Product product = new Product();
		product.setCompany(company);
		product.setBranch(branch);
		product.setName(registerInfo.getName());
		product.setPrice(registerInfo.getPrice());
		product.setType(registerInfo.getType());
		product.setStartDate(registerInfo.getStartDate());
		product.setEndDate(registerInfo.getEndDate());
		return product;
	}
	
	public Product mapToProduct(Product originProduct, ProductRequestBody modifyInfo, Company company, Branch branch) {
		originProduct.setCompany(company);
		originProduct.setBranch(branch);
		originProduct.setName(modifyInfo.getName());
		originProduct.setPrice(modifyInfo.getPrice());
		originProduct.setType(modifyInfo.getType());
		originProduct.setStartDate(modifyInfo.getStartDate());
		originProduct.setEndDate(modifyInfo.getEndDate());
		return originProduct;
	}
	
	public List<ProductInfo> mapToProductInfoList(List<Product> ProductList) {
		List<ProductInfo> ProductInfoList = new ArrayList<>();
		
		if(ProductList.isEmpty()) {
			return ProductInfoList;
		}
		
		for(Product Product : ProductList) {
			ProductInfo ProductInfo = mapToProductInfo(Product);
			ProductInfoList.add(ProductInfo);
		}
		
		return ProductInfoList;
	}
}
