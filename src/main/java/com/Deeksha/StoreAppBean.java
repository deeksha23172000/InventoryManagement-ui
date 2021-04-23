package com.Deeksha;

import com.Deeksha.entity.Store;
import com.Deeksha.interceptor.Logged;
import com.Deeksha.store.StoreService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SessionScoped
@Named
public class StoreAppBean implements Serializable {
    @NotEmpty
    private Long id;
    @Size(min = 5, max = 20)
    @NotEmpty
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getListOfInventory() {
        return listOfInventory;
    }

    public void setListOfInventory(String listOfInventory) {
        this.listOfInventory = listOfInventory;
    }

    public Date getStoreDate() {
        return storeDate;
    }

    public void setStoreDate(Date storeDate) {
        this.storeDate = storeDate;
    }

    @NotEmpty
    private String location;

    @NotEmpty
    private String listOfInventory;

    @NotEmpty
    private Date storeDate;


    @EJB
    private StoreService storeService;

    public List<Store> getStoreList() {
        return storeService.getStoreList();
    }

    @Logged
    public String addStoreItems() {
        Store store = new Store(name, location);
        Optional<Store> storeExists = storeService.getStoreList().stream().filter(p ->
                p.getName().equals(name) && p.getLocation().equals(location)).findFirst();
        if (storeExists.isPresent()) {
            storeService.removeFromList(store);
        } else {
            storeService.addToList(store);
        }
        clearFields();
        return "storeList";
    }


    private void clearFields() {
        setName("");
        setLocation("");
        setListOfInventory("");
    }
}
