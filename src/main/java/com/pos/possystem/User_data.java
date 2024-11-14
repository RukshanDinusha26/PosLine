/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.possystem;

import java.sql.Date;

/**
 *
 * @author Lenovo
 */
public class User_data {
    
        private Integer user_id;
        private String user_password;
        private Date created_date;
        
        public User_data(Integer user_id,String user_password, Date created_date){
            this.user_id = user_id;
            this.user_password = user_password;
            this.created_date = created_date;
        }
        
        public void setUser_id(Integer user_id){
            this.user_id = user_id;
        }
        
        public Integer getUser_id(){
            return user_id;
        }
        
        public void setUser_password(String user_password){
            this.user_password = user_password;
        }
        
        public String getUser_password(){
            return user_password;
        }
        
        public void setCreated_date(Date created_date){
            this.created_date = created_date;
        }
        
        public Date getCreated_date(){
            return created_date;
        }
        
}
