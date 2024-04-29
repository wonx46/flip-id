package com.flip.iwan.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class MessageResponse implements Serializable{

	
	private static final long serialVersionUID = -2343222778340632272L;
	public final static int ERROR_CODE_410 = 410;
    public final static int ERROR_CODE_411 = 411;
    public final static int ERROR_CODE_412 = 412; 
    public final static int ERROR_CODE_INTERNAL_SERVER = 500;
    public final static int ERROR_CODE_400 = 400;
    public final static int ERROR_CODE_401 = 401;
    public final static int ERROR_CODE_404 = 404;
    public final static int CODE_SUCCESS = 200;
    public final static int CODE_NO_CONTENT = 204;
    public final static int CODE_SUCCESS_CREATED = 201;
    public final static int CODE_USER_NOT_FOUND = 400;
    
    public final static String OK = "OK";
    public final static String USER_NOT_FOUND = "User not found";
    public final static String USERNAME_ALREADY_EXIST = "Username already exists";
    public final static String INVALID_PAYLOAD = "Invalid payload";
    public final static String BALANCE_READ_SUCCESS = "Balance read success";
    public final static String UNAUTHORIZED_USER = "Unauthorized user";
    public final static String DESTINATION_NOT_FOUND = "Destination user not found";
    public final static String INSUFFICIENT_BALANCE = "Insufficient balance";
    public final static String TRANSFER_SUCCESS =  "Transfer success";
    public final static String TOPUP_SUCCESS =  "Topup successful";
    public final static String INVALID_TOPUP_AMOUNT = "Invalid topup amount";

    private long timestamp = System.currentTimeMillis();
    private Integer status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String messageDetail;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<?> data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<?> errors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer page;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer total;

    public MessageResponse() {
    }

    public MessageResponse(Integer status) {
        this.setStatus(status);
    }

    public MessageResponse(Integer status, String message) {
        this.setStatus(status);
        this.setMessage(message);
    }

    public MessageResponse(Integer status, String message, List<?> data) {
        this.setStatus(status);
        this.setMessage(message);
        this.setData(data);
    }

    public MessageResponse(Integer status, String message, List<?> data, int total) {
        this.setStatus(status);
        this.setMessage(message);
        this.setData(data);
        this.setTotal(total);
    }


}
