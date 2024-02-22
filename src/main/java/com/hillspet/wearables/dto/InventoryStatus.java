package com.hillspet.wearables.dto;

public class InventoryStatus {
   private int inventoryStatusId;
   private String inventoryStatusName;
   private String type;

    public int getInventoryStatusId() {
        return inventoryStatusId;
    }

    public void setInventoryStatusId(int inventoryStatusId) {
        this.inventoryStatusId = inventoryStatusId;
    }

    public String getInventoryStatusName() {
        return inventoryStatusName;
    }

    public void setInventoryStatusName(String inventoryStatusName) {
        this.inventoryStatusName = inventoryStatusName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InventoryStatus{");
        sb.append("inventoryStatusId=").append(inventoryStatusId);
        sb.append(", inventoryStatusName='").append(inventoryStatusName).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
