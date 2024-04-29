package com.flip.iwan.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.flip.iwan.bean.TransferPayload;
import com.flip.iwan.model.User;
import com.flip.iwan.services.FlipServices;
import com.flip.iwan.utils.JwtTokenUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ContactController {
	

	private final FlipServices flipServices;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	 @PostMapping("/v1/create_user")
	 public ResponseEntity<?>  createContact(@RequestBody User user) {
	      return  flipServices.createUser(user);
	  }
	 
	 @GetMapping("/v1/list_user")
	 public ResponseEntity<?>  getAllUser() {
	      return  flipServices.getAllUser();
	  }
	 
	 @GetMapping("/v1/balance_read")
	 public ResponseEntity<?>  getBalance(@RequestHeader("Authorization") String auth) {
		 Claims allClaims = jwtTokenUtil.getAllClaimsFromToken(auth.split(" ")[1]);
		 return  flipServices.getBalance( allClaims.getSubject());
	  }
	 
	 @PostMapping("/v1/transfer")
	 public ResponseEntity<?>  transfer(@RequestHeader("Authorization") String auth,
			 @RequestBody TransferPayload payload) {
		 Claims allClaims = jwtTokenUtil.getAllClaimsFromToken(auth.split(" ")[1]);
	      return  flipServices.transferUser( allClaims.getSubject(), payload);
	  }	    
	 
	 @PostMapping("/v1/balance_topup")
	 public ResponseEntity<?>  topupBalance(@RequestHeader("Authorization") String auth,
			 @RequestBody TransferPayload payload) {
		 Claims allClaims = jwtTokenUtil.getAllClaimsFromToken(auth.split(" ")[1]);
		 payload.setTo_username(allClaims.getSubject());
	      return  flipServices.balanceTopup( payload);
	  }	  
	 
	 @GetMapping("/v1/top_users")
	 public ResponseEntity<?>  getTopTenUser() {
		 return  flipServices.getTopTenUserTrf();
	  }
	 
	 @GetMapping("/v1/top_transactions_per_user")
	 public ResponseEntity<?> getTopTransaction() {
		 return  flipServices.getTopTenUserTransactions();
	  }
	 
}
