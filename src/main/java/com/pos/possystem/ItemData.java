/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.possystem;

/**
 *
 * @author Lenovo
 */
public class ItemData {

    private Integer itemid;
    private String item_name;
    private Double item_price;
    private Integer item_barcode;
    private String item_unit_type;
    private String item_alpha_search;
    private Double item_discount;
    private Integer item_stock;
    
    public ItemData(Integer itemid, String item_name, Double item_price, Integer item_barcode, String item_unit_type,
            String item_alpha_search, Double item_discount)
    {
        this.itemid = itemid;
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_barcode = item_barcode;
        this.item_unit_type = item_unit_type;
        this.item_alpha_search = item_alpha_search;
        this.item_discount = item_discount;
    }
    
    public ItemData(Integer itemid,String item_name, Double item_price, String item_unit_type)
    {
        this.itemid = itemid;
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_unit_type = item_unit_type;
    }
    
    public ItemData(Integer itemid,String item_name,String item_unit_type,Integer item_stock)
    {
        this.itemid = itemid;
        this.item_name = item_name;
        this.item_unit_type = item_unit_type;
        this.item_stock = item_stock;
    }
    
    public void setItemid(Integer itemid){
        this.itemid = itemid;
    }
    public Integer getItemid(){
        return itemid;
    }
    
    public void setItem_name(String item_name){
       this.item_name = item_name;
    }
    
    public String getItem_name() {
        return item_name;
    }
    
    public void setItem_price(Double item_price){
        this.item_price = item_price;
    }
    
    public Double getItem_price(){
        return item_price;
    }
    
    public void setItem_barcode(Integer item_barcode){
        this.item_barcode = item_barcode;
    }
    
    public Integer getItem_barcode(){
        return item_barcode;
    }
    
    public void setItem_unit_type(String item_unit_type){
        this.item_unit_type = item_unit_type;
    }
            
    public String getItem_unit_type(){
        return item_unit_type;
    }
    
    public void setItem_alpha_search(String item_alpha_search){
        this.item_alpha_search = item_alpha_search;
    }
    
    public String getItem_alpha_search(){
        return item_alpha_search;
    }
    
    public void setItem_discount(Double item_discount){
        this.item_discount = item_discount;
    }
    
    public Double getItem_discount(){
        return item_discount;
    }
    
    public void setItem_stock(Integer item_stock)
    {
        this.item_stock = item_stock;
    }
    
    public Integer getItem_stock()
    {
        return item_stock;
    }
        
}
