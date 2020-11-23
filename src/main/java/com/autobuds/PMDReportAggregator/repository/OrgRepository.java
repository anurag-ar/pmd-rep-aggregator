package com.autobuds.PMDReportAggregator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autobuds.PMDReportAggregator.model.Org;
import com.autobuds.PMDReportAggregator.model.OrgId;

public interface OrgRepository extends JpaRepository<Org, OrgId> {

	@Query("Select org from Org as org where org.id.userId=?1 ")
	public List<Org> findAllOrg(String email); 
}
