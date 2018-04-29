package com.outcastjackalyn.objects;

import com.outcastjackalyn.scenes.LockState;
import jjcard.text.game.ConcealableGameElement;
import jjcard.text.game.IItem;
import jjcard.text.game.impl.ItemUse;

import java.util.Map;

public interface IFurniture extends ConcealableGameElement {



    public int getMoney();
    public Map<String, IItem> getInventory();
    /**
     * Returns item matching given key from inventory
     * @param key
     * @return
     */
    public IItem getItem(String key);


    public String inventoryOverview();

    public boolean containsItem(String key);

    /**
     * Add Item to inventory
     * @param add
     * @return previous item associated with name
     */
    public IItem addItem(IItem add);
    public void addAllItems(Map<String, IItem> items);

    /**
     * Removes the Inventory from the furniture and returns the result
     * @return inventory
     */
    public Map<String, IItem> removeInventory();

    public IItem removeItem(String key);

    public boolean isEmpty();

    public boolean isOpen();

    public boolean isMovable();

    public ItemUse getUse();

    public void setUse(ItemUse use);

    /**
     * returns true if item can be retrieved by the player
     * @return
     */
    public boolean canGet();

    public void setLockState(LockState lockState);

    public LockState getLockState();

    public void setHidden(boolean hidden);

    public void setMovable(boolean movable);

    public int getCost();

    public int getLevel();


}
