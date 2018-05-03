package com.outcastjackalyn.objects.furniture;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.outcastjackalyn.objects.IFurniture;
import com.outcastjackalyn.scenes.LockState;
import jjcard.text.game.IItem;
import jjcard.text.game.impl.AbstractGameElement;
import jjcard.text.game.impl.ItemUse;
import jjcard.text.game.util.MapUtil;
import jjcard.text.game.util.ObjectsUtil;

import java.util.Map;

@JsonDeserialize(builder = Furniture.Builder.class)
public class Furniture extends AbstractGameElement implements IFurniture {
    @JsonProperty("money")
    private int money = 0;
    @JsonProperty("inven")
    private Map<String, IItem> inventory;
    @JsonProperty("hid")
    private boolean hidden = false;
    @JsonProperty("state")
    private LockState lockState = LockState.ALWAYS_OPEN;
    @JsonProperty("mov")
    private boolean movable = true;
    @JsonProperty("use")
    private ItemUse use = ItemUse.Item;
    @JsonProperty("invdescrip")
    private String inventoryDescription;
    @JsonIgnore
    private static final MapUtil MAP_UTIL = MapUtil.getInstance();


    public static class Builder extends AbstractGameElement.Builder{
        private int money = 0;
        private Map<String, IItem> inventory = MapUtil.newHashMap();
        private boolean hidden = false;
        private LockState lockState = LockState.ALWAYS_OPEN;
        private boolean movable = true;
        private ItemUse use = ItemUse.Item;
        private String inventoryDescription;


        public Builder(){
            super();
        }
        public Builder(Furniture furniture){
            super(furniture);
            money = furniture.money;
            inventory = furniture.inventory;
            hidden = furniture.hidden;
            lockState = furniture.lockState;
            movable = furniture.movable;
            use = furniture.use;
            inventoryDescription = furniture.inventoryDescription;
        }
        public Builder(AbstractGameElement element){
            super(element);
        }
        @JsonProperty("money")
        public Furniture.Builder money(int money){
            this.money = money;
            return this;
        }
        @JsonProperty("inven")
        public Furniture.Builder inventory(Map<String, IItem> inventory){
            this.inventory = MapUtil.getMapOrNew(inventory);
            return this;
        }
        public Furniture.Builder addItem(IItem item){
            MAP_UTIL.addItemToMap(inventory, item);
            return this;
        }
        @JsonProperty("hid")
        public Furniture.Builder hidden(boolean hidden){
            this.hidden = hidden;
            return this;
        }
        @JsonProperty("state")
        public Furniture.Builder lockState(LockState lockState){
            this.lockState = lockState;
            return this;
        }
        @JsonProperty("mov")
        public Furniture.Builder movable(boolean movable){
            this.movable = movable;
            return this;
        }
        @JsonProperty("use")
        public Furniture.Builder use(ItemUse use){
            this.use = use;
            return this;
        }
        @JsonProperty("invdescrip")
        public Furniture.Builder inventoryDescription(String inventoryDescription){
            this.inventoryDescription = inventoryDescription;
            return this;
        }
        public Furniture.Builder name(String name){
            super.name(name);
            return this;
        }
        public Furniture.Builder roomDescription(String roomDescrip){
            super.roomDescription(roomDescrip);
            return this;
        }
        public Furniture.Builder viewDescription(String viewDescription){
            super.viewDescription(viewDescription);
            return this;
        }
        public Furniture.Builder validateFields(boolean validateFields){
            super.validateFields(validateFields);
            return this;
        }
        public Furniture build(){
            return new Furniture(this);
        }
    }

    protected Furniture(Furniture.Builder builder){
        super(builder);
        setHidden(builder.hidden);
        setLockState(builder.lockState);
        setMovable(builder.movable);
        setUse(builder.use);
        setInventoryDescription(builder.inventoryDescription);
        setMoney(builder.money);
        inventory = builder.inventory;
    }



    public int getMoney(){
        return money;
    }
    public Map<String, IItem> getInventory() {
        return inventory;
    }
    public IItem getItem(String key){
        return MAP_UTIL.getItemFromLowercaseMap(inventory, key);
    }


    public void changeMoney(int change){
        setMoney(this.money + change);
    }
    public void setMoney(int money){
        this.money = money;
        if (doValidateFields() && this.money < 0){
            this.money = 0;
        }
    }


    public IItem addItem(IItem add){
        return MAP_UTIL.addItemToMap(inventory, add);
    }
    public void addAllItems(Map<String, IItem> addMap) {
        inventory.putAll(addMap);
    }
    public IItem removeItem(String key){
        return MAP_UTIL.removeItemFromMap(inventory, key);
    }
    public void setInventory(Map<String, IItem> inventoryNew){
        inventory = MapUtil.getMapOrNew(inventoryNew);
    }
    public Map<String, IItem> removeInventory(){
        Map<String, IItem> returnIn = inventory;
        inventory = MapUtil.newHashMap();
        return returnIn;
    }
    public boolean containsItem(String key){
        return MAP_UTIL.containsKey(inventory, key);
    }
    public int inventorySize() {
        return inventory.size();
    }


   /* public String getRoomFullDescription() {
        String str = AbstractGameElement.getRoomDescription();
        return ;
    }*/

    public Furniture(String name){
        super(name);
    }
    public boolean isEmpty() {
        return inventory.isEmpty();
    }
    public boolean isHidden(){
        return hidden;
    }
    public boolean isOpen(){
        boolean bool =  false;
        if(this.lockState == LockState.ALWAYS_OPEN || this.lockState == LockState.UNLOCKED) {
            bool = true;
        }
        return bool;
    }
    public LockState getLockState() { return lockState; }
    public boolean isMovable(){
        return movable;
    }
    public ItemUse getUse() {
        return use;
    }
    public String getInventoryDescription() { return inventoryDescription; }

    public void setHidden(boolean hidden){
        this.hidden = hidden;
    }
    public void setLockState(LockState lockState){
        this.lockState = lockState;
    }
    /**
     * returns true if this object is movable and not hidden
     * @return
     */
  //  public boolean canGet(){  return movable && !hidden;  }
    public void setMovable(boolean movable){
        this.movable = movable;
    }
    public void setUse(ItemUse use){
        this.use = use;
    }
    public void setInventoryDescription(String inventoryDescription) { this.inventoryDescription = inventoryDescription; }
    public boolean equals(Object o){
        if (o == this){
            return true;
        }
        if (o == null){
            return false;
        }
        if (o instanceof Furniture){
            Furniture m = (Furniture) o;
            if (!super.equals(o)){
                return false;
            }
            if (money != m.money){
                return false;
            }
            if (ObjectsUtil.notEqualKeys(this.inventory, m.inventory)){
                return false;
            }
            if (hidden != m.hidden){
                return false;
            }
            if (lockState != m.lockState){
                return false;
            }
            if (movable != m.movable){
                return false;
            }
            if (inventoryDescription != m.inventoryDescription){
                return false;
            }
            return this.use.equals(m.getUse());
        } else {
            return false;
        }
    }

    public int hashCode(){
        int start = super.hashCode();
        start = start * ObjectsUtil.DEFAULT_PRIME + ObjectsUtil.getKeysHash(inventory);
        return ObjectsUtil.getHashWithStart(start, ObjectsUtil.DEFAULT_PRIME, money, hidden, lockState, movable, use, inventoryDescription);
    }

    public String inventoryOverview(){
        return MapUtil.getKeysAsString(inventory);
    }
}
