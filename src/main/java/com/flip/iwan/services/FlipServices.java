package com.flip.iwan.services;

import java.math.BigDecimal;
import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flip.iwan.bean.MessageResponse;
import com.flip.iwan.bean.TopTenTransaction;
import com.flip.iwan.bean.TopTenUserTransfer;
import com.flip.iwan.bean.TransferPayload;
import com.flip.iwan.bean.UserBalance;
import com.flip.iwan.bean.UserToken;
import com.flip.iwan.enumz.EnumCategory;
import com.flip.iwan.enumz.EnumStsTrx;
import com.flip.iwan.enumz.EnumTypeTrx;
import com.flip.iwan.model.Transaction;
import com.flip.iwan.model.User;
import com.flip.iwan.repository.TransactionRepository;
import com.flip.iwan.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;


@CacheConfig(cacheNames = {
        "flipServices"
})
@Service("flipServices")
@RequiredArgsConstructor
public class FlipServices {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	 public ResponseEntity<?>  getTopTenUserTransactions() {
			try {
				MessageResponse response = new MessageResponse(MessageResponse.CODE_SUCCESS, MessageResponse.OK);
				List<Object[]> topTenTrfUser = transactionRepository.getTopTenTransaction();
				List<TopTenTransaction> list = new ArrayList<TopTenTransaction>();
				for (Object[] objects : topTenTrfUser) {
					TopTenTransaction o = new TopTenTransaction();
					o.setUsername((String) objects[0]);
					o.setAmount((BigDecimal) objects[1]);
					list.add(o);
				}
				
				response.setData(list);
				
				return new ResponseEntity(response, HttpStatus.OK);
			} catch (Exception e) {
				 return new ResponseEntity(new MessageResponse(MessageResponse.ERROR_CODE_INTERNAL_SERVER,
		                   e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
 
	
	 public ResponseEntity<?>  getTopTenUserTrf() {
			try {
				MessageResponse response = new MessageResponse(MessageResponse.CODE_SUCCESS, MessageResponse.OK);
				List<Object[]> topTenTrfUser = transactionRepository.getTopTenTrfUser();
				List<TopTenUserTransfer> list = new ArrayList<TopTenUserTransfer>();
				for (Object[] objects : topTenTrfUser) {
					TopTenUserTransfer o = new TopTenUserTransfer();
					o.setUsername((String) objects[0]);
					o.setTransacted_value((BigDecimal) objects[1]);
					list.add(o);
				}
				
				response.setData(list);
				
				return new ResponseEntity(response, HttpStatus.OK);
			} catch (Exception e) {
				 return new ResponseEntity(new MessageResponse(MessageResponse.ERROR_CODE_INTERNAL_SERVER,
		                   e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
    
    public ResponseEntity<?>  balanceTopup(TransferPayload payload) {
		try {
			MessageResponse response = new MessageResponse(MessageResponse.CODE_NO_CONTENT, MessageResponse.TOPUP_SUCCESS);
			
			String userDestination = payload.getTo_username();
			BigDecimal nominal = payload.getAmount();
			if(userDestination == null || userDestination.isEmpty()) {
				response.setMessage(MessageResponse.DESTINATION_NOT_FOUND);
				response.setStatus(MessageResponse.ERROR_CODE_404);
				return new ResponseEntity(response, HttpStatus.NOT_FOUND);
			}
			
			
			User dest = userRepository.getContactByUserName(userDestination);
			if(dest == null) {
				response.setMessage(MessageResponse.DESTINATION_NOT_FOUND);
				response.setStatus(MessageResponse.ERROR_CODE_404);
				return new ResponseEntity(response, HttpStatus.NOT_FOUND);
			}
			
			if(nominal == null || nominal.doubleValue()<=0 || nominal.doubleValue()>10000000) {
				response.setMessage(MessageResponse.INVALID_TOPUP_AMOUNT);
				response.setStatus(MessageResponse.ERROR_CODE_400);
				return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
			}

			
			
			doTopupBalance(dest, nominal);
			
			return new ResponseEntity(response, HttpStatus.OK);
		} catch (Exception e) {
			 return new ResponseEntity(new MessageResponse(MessageResponse.ERROR_CODE_INTERNAL_SERVER,
	                   e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
    

	private void doTopupBalance(User dest, BigDecimal nominal) {

		
		BigDecimal prevBalanceDest = dest.getBalance();
		BigDecimal finalBalanceDest = dest.getBalance().add(nominal);
		dest.setBalance(finalBalanceDest);
		
		
		Transaction trx = new Transaction();
		trx.setCategory(EnumCategory.TOPUP.getString());
		trx.setCreated_date(Calendar.getInstance().getTime());
		trx.setNominal(nominal);
		trx.setStatus(EnumStsTrx.SUCCESS.getString());
		trx.setTrx_type(EnumTypeTrx.CREDIT.getString());
		trx.setUser1(dest.getUsername());
		trx.setNotes("Topup balance with nominal "+nominal+",previous balance "+prevBalanceDest+" current balance "+finalBalanceDest);
		transactionRepository.saveAndFlush(trx);
		userRepository.saveAndFlush(dest);
		
		
	}



	public ResponseEntity<?>  transferUser(String userOrigin,TransferPayload payload) {
		try {
			MessageResponse response = new MessageResponse(MessageResponse.CODE_NO_CONTENT, MessageResponse.TRANSFER_SUCCESS);
			
			String userDestination = payload.getTo_username();
			BigDecimal nominal = payload.getAmount();
			if(userDestination == null || userDestination.isEmpty()) {
				response.setMessage(MessageResponse.DESTINATION_NOT_FOUND);
				response.setStatus(MessageResponse.ERROR_CODE_404);
				return new ResponseEntity(response, HttpStatus.NOT_FOUND);
			}
			
			User org = userRepository.getContactByUserName(userOrigin);
			if(org == null) {
				response.setMessage(MessageResponse.USER_NOT_FOUND);
				response.setStatus(MessageResponse.ERROR_CODE_404);
				return new ResponseEntity(response, HttpStatus.NOT_FOUND);
			}
			
			
			User dest = userRepository.getContactByUserName(userDestination);
			if(dest == null) {
				response.setMessage(MessageResponse.DESTINATION_NOT_FOUND);
				response.setStatus(MessageResponse.ERROR_CODE_404);
				return new ResponseEntity(response, HttpStatus.NOT_FOUND);
			}
			
			if(nominal == null || nominal.doubleValue()<=0) {
				response.setMessage(MessageResponse.INVALID_PAYLOAD);
				response.setStatus(MessageResponse.ERROR_CODE_400);
				return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
			}

			if(org.getBalance() == null || org.getBalance().doubleValue()<=0 
					|| org.getBalance().doubleValue() < nominal.doubleValue()) {
				response.setMessage(MessageResponse.INSUFFICIENT_BALANCE);
				response.setStatus(MessageResponse.ERROR_CODE_400);
				return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
			}
			
			
			
			doTransferBalance(org, dest, nominal);
			
			return new ResponseEntity(response, HttpStatus.OK);
		} catch (Exception e) {
			 return new ResponseEntity(new MessageResponse(MessageResponse.ERROR_CODE_INTERNAL_SERVER,
	                   e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private void doTransferBalance(User org, User dest, BigDecimal nominal) {
		
		BigDecimal prevBalance = org.getBalance();
		BigDecimal finalBalance = org.getBalance().subtract(nominal);
		org.setBalance(finalBalance);
		
		BigDecimal prevBalanceDest = dest.getBalance();
		BigDecimal finalBalanceDest = dest.getBalance().add(nominal);
		dest.setBalance(finalBalanceDest);
		
		
		Transaction trx = new Transaction();
		trx.setCategory(EnumCategory.TRANSFER.getString());
		trx.setCreated_date(Calendar.getInstance().getTime());
		trx.setNominal(nominal);
		trx.setStatus(EnumStsTrx.SUCCESS.getString());
		trx.setTrx_type(EnumTypeTrx.DEBIT.getString());
		trx.setUser1(org.getUsername());
		trx.setUser2(dest.getUsername());
		trx.setNotes("Transfer balance from "+org.getName()+" to "+dest.getName()+" with nominal "+nominal+",previous balance "+prevBalance+" current balance "+finalBalance);
		transactionRepository.saveAndFlush(trx);
		
		
		
		Transaction trx2 = new Transaction();
		trx2.setCategory(EnumCategory.TRANSFER.getString());
		trx2.setCreated_date(Calendar.getInstance().getTime());
		trx2.setNominal(nominal);
		trx2.setStatus(EnumStsTrx.SUCCESS.getString());
		trx2.setTrx_type(EnumTypeTrx.CREDIT.getString());
		trx2.setUser1(dest.getUsername());
		trx2.setUser2(org.getUsername());
		trx2.setNotes("Receiving from "+org.getName()+" with nominal "+nominal+"previous balance "+prevBalanceDest+", current balance "+finalBalanceDest);
		transactionRepository.saveAndFlush(trx2);
		
		userRepository.saveAndFlush(org);
		userRepository.saveAndFlush(dest);
	}

	public ResponseEntity<?>  getBalance(String userName){
		 MessageResponse response =  new MessageResponse(MessageResponse.CODE_SUCCESS, MessageResponse.BALANCE_READ_SUCCESS);
		try {
			User existUser = userRepository.getContactByUserName(userName);
			if(existUser == null) {
				response.setMessage(MessageResponse.UNAUTHORIZED_USER);
				response.setStatus(MessageResponse.ERROR_CODE_401);
				return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
			}
			
			
			UserBalance userBalance = new UserBalance();
			userBalance.setBalance(existUser.getBalance());
			List<UserBalance> list = new ArrayList<UserBalance>();
			list.add(userBalance);
			response.setData(list);
			
			 return new ResponseEntity(response, HttpStatus.OK);
		} catch (Exception e) {
			   return new ResponseEntity(new MessageResponse(MessageResponse.ERROR_CODE_INTERNAL_SERVER,
	                   e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
		
	}
	
	public ResponseEntity<?>  getAllUser(){
		 MessageResponse messageResponse =  new MessageResponse(MessageResponse.CODE_SUCCESS, MessageResponse.OK);
		try {
			 List<User> users = userRepository.findAll();
			 messageResponse.setData(users);
			
			 return new ResponseEntity(messageResponse, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			   return new ResponseEntity(new MessageResponse(MessageResponse.ERROR_CODE_INTERNAL_SERVER,
 	                   e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
		
	}
	
	public ResponseEntity<?>  createUser(User user) {
		try {
			MessageResponse response = new MessageResponse(MessageResponse.CODE_SUCCESS, MessageResponse.OK);
			
			if(user.getId()==null || user.getId().isEmpty()) {
				user.setGeneratedId();
			}
			
			if(!isValidUserPayload(user)) {
				response.setMessage(MessageResponse.INVALID_PAYLOAD);
				response.setStatus(MessageResponse.ERROR_CODE_400);
				return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
			}
			
			User existUser = userRepository.getContactByUserName(user.getUsername());
			if(existUser != null) {
				response.setMessage(MessageResponse.USERNAME_ALREADY_EXIST);
				response.setStatus(MessageResponse.ERROR_CODE_400);
				return new ResponseEntity(response, HttpStatus.CONFLICT);
			}
			
			user.setCreated_date(Calendar.getInstance().getTime());
			user.setBalance(BigDecimal.ZERO);
			userRepository.saveAndFlush(user);
			Date now = new Date();
			
			Map<String, Object> claims = new HashMap<>();
			String jwtToken =  Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(DateUtils.addDays(now, 1))
			.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

			UserToken userToken = new UserToken();
			userToken.setToken(jwtToken);
			
			List<UserToken> list = new ArrayList<UserToken>();
			list.add(userToken);
			response.setData(list);
			return new ResponseEntity(response, HttpStatus.CREATED);
		} catch (Exception e) {
			 return new ResponseEntity(new MessageResponse(MessageResponse.ERROR_CODE_INTERNAL_SERVER,
	                   e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private boolean isValidUserPayload(User user) {
		if(user.getUsername()==null || user.getUsername().isEmpty()) {
			return false;
		}
		if(user.getName()==null || user.getName().isEmpty()) {
			return false;
		}
		if(user.getPhone()==null || user.getPhone().isEmpty()) {
			return false;
		}
		return true;
	}

	
}
