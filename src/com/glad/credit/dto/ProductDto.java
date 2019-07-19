package com.glad.credit.dto;

import java.util.Map;

public class ProductDto {
	 private String request_sn;
     private String inf_id;
     private String acct_id;
     private String prod_id;
     private Long req_time;
     private Map<String, Object> req_data;
     private Map<String, Object> ext_data;

     public ProductDto() {
     }

     public Long getReq_time() {
         return req_time;
     }

     public void setReq_time(Long req_time) {
         this.req_time = req_time;
     }

     public String getRequest_sn() {
         return request_sn;
     }

     public void setRequest_sn(String request_sn) {
         this.request_sn = request_sn;
     }

     public String getInf_id() {
         return inf_id;
     }

     public void setInf_id(String inf_id) {
         this.inf_id = inf_id;
     }

     public String getAcct_id() {
         return acct_id;
     }

     public void setAcct_id(String acct_id) {
         this.acct_id = acct_id;
     }

     public String getProd_id() {
         return prod_id;
     }

     public void setProd_id(String prod_id) {
         this.prod_id = prod_id;
     }

     public Map<String, Object> getReq_data() {
         return req_data;
     }

     public void setReq_data(Map<String, Object> req_data) {
         this.req_data = req_data;
     }

     public Map<String, Object> getExt_data() {
         return ext_data;
     }

     public void setExt_data(Map<String, Object> ext_data) {
         this.ext_data = ext_data;
     }
}
