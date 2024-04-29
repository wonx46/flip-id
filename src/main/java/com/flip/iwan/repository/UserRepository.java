package com.flip.iwan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flip.iwan.model.User;




@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	@Query(value= "select o from User o where o.username =:username ")
	public User getContactByUserName(@Param(value = "username") String username);
	
	

}
