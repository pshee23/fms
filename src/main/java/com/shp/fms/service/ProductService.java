package com.shp.fms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shp.fms.common.exception.NoResultByIdException;
import com.shp.fms.common.type.ServiceType;
import com.shp.fms.model.ProductInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.model.entity.Company;
import com.shp.fms.model.entity.Product;
import com.shp.fms.model.request.ProductRequestBody;
import com.shp.fms.repository.CompanyRepository;
import com.shp.fms.repository.ProductRepository;
import com.shp.fms.repository.mapper.ProductMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;

	private final CompanyRepository companyRepository;
	
	private final BranchService branchService;
	
	private final ProductMapper productMapper;

	public ProductInfo registerProduct(ProductRequestBody registerInfo) {
		Company company = companyRepository.findById(registerInfo.getCompanyId()).get();
		Branch branch = branchService.getBranchById(registerInfo.getBranchId());
		Product product = productMapper.mapToProduct(registerInfo, company, branch);
		product = productRepository.save(product);
		return productMapper.mapToProductInfo(product);
	}
	
	public ProductInfo modifyProduct(long productId, ProductRequestBody modifyInfo) {
		Product product = getProductById(productId);
		Company company = companyRepository.findById(modifyInfo.getCompanyId()).get();
		Branch branch = branchService.getBranchById(modifyInfo.getBranchId());
		product = productMapper.mapToProduct(product, modifyInfo, company, branch);
		product = productRepository.save(product);
		return productMapper.mapToProductInfo(product);
	}
	
	public boolean deleteProduct(long lockerId) {
		productRepository.deleteById(lockerId);
		if(productRepository.existsById(lockerId)) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<ProductInfo> getAllProductInfo() {
		List<Product> lockerList = productRepository.findAll();
		return productMapper.mapToProductInfoList(lockerList);
	}
	
	public Product getProductById(long productId) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isEmpty()) {
			throw new NoResultByIdException(productId, ServiceType.PRODUCT.getName());
		}
		return product.get();
	}
	
	public ProductInfo getProductInfoById(long productId) {
		return productMapper.mapToProductInfo(getProductById(productId));
	}
	
	public List<ProductInfo> getProductInfoListByBranchId(long branchId) {
		List<Product> productList = productRepository.findByBranch_BranchId(branchId);
		if(productList.isEmpty()) {
			throw new NoResultByIdException(ServiceType.BRANCH.getName(), branchId, ServiceType.PRODUCT.getName());
		}
		return productMapper.mapToProductInfoList(productList);
	}
}
