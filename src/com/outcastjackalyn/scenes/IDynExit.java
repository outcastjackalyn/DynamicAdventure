package com.outcastjackalyn.scenes;

import jjcard.text.game.ConcealableGameElement;
import jjcard.text.game.util.ObjectsUtil;

/**
 * An IDynExit is an interface using the IGameElement names as directions.
 * It then uses that to return an IDynLocation.
 *
 */
public interface IDynExit extends ConcealableGameElement {

    public IDynLocation getLocation();

    public void setHidden(boolean hidden);

    public void setLockState(LockState lockState);

    public boolean isOpen();

    public LockState getLockState();

    public String getHiddenDescription();

    public void setRoomDescription(String roomDescription);

    public void setViewDescription(String viewDescription);

    public String getHiddenName();
}
