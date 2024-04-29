package com.flip.iwan.threads;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TaskFetch implements Runnable {

	 private final String name;
	 
	 private final String url;

     public TaskFetch(String name,String url) {
         this.name = name;
         this.url = url;
     }
	
	@Override
	public void run() {
		 System.out.println("Task "+name+" starting..");
		 RestTemplate restTemplate = new RestTemplate();
		 long start = System.currentTimeMillis();
		 ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity()	, String.class);
		 
		 long end = System.currentTimeMillis();
		 long timeInMilis = end - start;
		 System.out.println("Task "+name+" complete in "+timeInMilis+"ms");

	}

	  private HttpEntity<Object> getHttpEntity() {
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");
	        headers.add("User-Agent", "PostmanRuntime/7.36.3");
	        return new HttpEntity<>(headers);
	    }

}
