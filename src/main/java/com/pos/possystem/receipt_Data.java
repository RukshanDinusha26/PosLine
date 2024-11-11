/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.possystem;

/**
 *
 * @author Lenovo
 */
public class receipt_Data {
    
    private Integer itemid;
    private String item_name;
    private Double price;
    private Integer item_qty;
    private String qty_type;
    
    public receipt_Data(Integer itemid, String item_name, Double price, Integer item_qty, String qty_type){
   
        this.itemid = itemid;
        this.item_name = item_name;
        this.price = price;
        this.item_qty= item_qty;
        this.qty_type = qty_type;
    }
    
    public Integer getID(){
        return itemid;
    }
    
    public Double getPrice()
    {
        return price;
    }
    
    public String getName()
    {
        return item_name;
    }
    
    public Integer getQuantity()
    {
        return item_qty;
    }
    public String getUnit()
    {
        return qty_type;
    }
    
    public double getTotalPrice()
    {
        return this.getPrice()* this.getQuantity();
    }
    
            
    
}
