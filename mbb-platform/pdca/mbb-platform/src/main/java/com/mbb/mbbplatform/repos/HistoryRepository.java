package com.mbb.mbbplatform.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbb.mbbplatform.domain.History;

public interface HistoryRepository extends JpaRepository<History, Long> {

	List<History> findByBarcode(String barcode);

}
