package com.Deeksha;

import com.Deeksha.entity.Inventory;
import com.Deeksha.interceptor.Logged;
import com.Deeksha.inventory.InventoryService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@SessionScoped
@Named
public class InventoryAppBean implements Serializable {
    @NotEmpty
    private Long id;
    @Size(min = 5, max = 20)
    @NotEmpty
    private String name;

    @Size(min = 3, max = 20)
    private String sport;

    @NotEmpty
    private int quantity;

    @NotEmpty
    private double price;


    @EJB
    private InventoryService inventoryService;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public List<Inventory> getInventoryList() {
        return inventoryService.getInventoryList();
    }

    @Logged
    public String addInventory() {
        Inventory inventory = new Inventory(name, sport, quantity, price);
        Optional<Inventory> inventoryExists = inventoryService.getInventoryList().stream().filter(p ->
                p.getName().equals(name) && p.getSport().equals(sport)).findFirst();
        if (inventoryExists.isPresent()) {
            inventoryService.removeFromList(inventory);
        } else {
            inventoryService.addToList(inventory);
        }
        clearFields();
        return "inventoryList";
    }

    private void clearFields() {
        setName("");
        setPrice(0);
        setQuantity(0);
        setSport("");
    }
}
