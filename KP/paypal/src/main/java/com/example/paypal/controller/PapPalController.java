package com.example.paypal.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.paypal.client.PayPalClient;
import com.example.paypal.client.UppClient;
import com.example.paypal.dto.UserDTO;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@CrossOrigin("*")
//@RequestMapping(value = "localhost:8085/paypal")
public class PapPalController {

    private PayPalClient payPalClient = new PayPalClient();
    @Autowired
    PapPalController(PayPalClient payPalClient){
        this.payPalClient = payPalClient;
    }
    @Autowired
    private UppClient uppClient;

    @GetMapping(value = "/make/payment")
    public Map<String, Object> makePayment(@RequestParam("sum") String sum){
    	//UserDTO user =uppClient.getUser(userId);
        return payPalClient.createPayment(sum);
    }
    
    
    @GetMapping(value = "/complete/payment")
    public Map<String, Object> completePayment(HttpServletRequest request) {
        return payPalClient.completePayment(request);
    }
    
}
