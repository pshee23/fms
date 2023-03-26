package com.shpfms.repository.adapter;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Repository;

import com.shpfms.model.entity.Branch;
import com.shpfms.repository.BranchRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class BranchPersistenceAdapter {

	private final BranchRepository branchRepository;
	
	public Branch saveBranch(Branch branch) {
		return branchRepository.save(branch);
	}
	
	public void deleteBranch(long branchId) {
		branchRepository.deleteById(branchId);
	}
	
	public List<Branch> findAllBranch() {
		return branchRepository.findAll();
	}
	
	public Branch findBranchInfoById(Long branchId) {
		Optional<Branch> branchOptional = branchRepository.findById(branchId);
		return branchOptional.orElseThrow(EntityNotFoundException::new);
	}
	
	public boolean isBranchExist(Long branchId) {
		return branchRepository.existsById(branchId);
	}
}
