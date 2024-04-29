package com.flip.iwan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flip.iwan.model.Transaction;




@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
	
	@Query(value= "select user1 as username , sum(nominal) as transacted_value "
			+ " from transaction o"
			+ " where trx_type ='DEBIT' and category ='TRANSFER' "
			+ " GROUP by user1 "
			+ " order by transacted_value desc limit 10 ", nativeQuery = true)
	public List<Object[]> getTopTenTrfUser();
	
	@Query(value= "select user1 as username , nominal as amount "
			+ "from transaction "
			+ "order by amount desc limit 10 ", nativeQuery = true)
	public List<Object[]> getTopTenTransaction();
}


