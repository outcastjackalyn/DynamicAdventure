package com.outcastjackalyn.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.outcastjackalyn.scenes.IDynExit;
import com.outcastjackalyn.scenes.LockableExit;
import jjcard.text.game.ConcealableGameElement;
import jjcard.text.game.IGameElement;
import com.outcastjackalyn.scenes.IDynLocation;
/**
 * Utility class for working on GameElements and ConcealableGameElements to display the results.
 * All methods are null safe and will return an empty string if null passed in.
 *
 */
public final class DynamicDescriptionUtil {
    private static final String COMMA_DELIMINATOR = ", ";
    private static final char SPACE = ' ';
    private static final char PERIOD = '.';
    private static final String EMPTY = "";
    public static final String DEFAULT_EXIT_START = "<br>You look for a way out:<br>";
    private DynamicDescriptionUtil(){
        //all static methods
    }
    /**
     * Returns stream that is empty of elements is null or empty, otherwise returns stream with elements filtered out depending on <code> excludeHidden</code>
     * @param elements
     * @param excludeHidden
     * @return Stream
     */
    public static <I extends ConcealableGameElement> Stream<I> getConcealableStream(Collection<I> elements, final boolean excludeHidden){
        if (elements == null || elements.isEmpty()){
            return Stream.empty();
        }
        final boolean includeHidden = !excludeHidden;
        return elements.stream().filter((e) -> includeHidden || !e.isHidden());
    }
    /**
     * Returns stream that is empty of elements is null or empty, otherwise returns stream with elements filtered out depending on <code> excludeHidden</code>
     * @param elements
     * @return Stream
     */
    public static <I extends ConcealableGameElement> Stream<I> getHiddenStream(Collection<I> elements){
        if (elements == null || elements.isEmpty()){
            return Stream.empty();
        }
        return elements.stream().filter((e) -> e.isHidden());
    }
    /**
     * Returns comma separated String of element names, filtering out hidden elements if <code>excludeHidden</code> is true
     * @param elements
     * @param excludeHidden
     * @return the comma separated String
     * @see #getConcealableStream(Collection, boolean)
     */
    public static <I extends ConcealableGameElement> String getConcealableNames(Map<?, I> elements, final boolean excludeHidden){
        return getConcealableNames(elements == null? null: elements.values(), excludeHidden);
    }
    /**
     * Returns comma separated String of element names, filtering out hidden elements if <code>excludeHidden</code> is true
     * @param elements
     * @param excludeHidden
     * @return the comma separated String
     * @see #getConcealableStream(Collection, boolean)
     */
    public static <I extends ConcealableGameElement> String getConcealableNames(Collection<I> elements, final boolean excludeHidden){
        return getConcealableStream(elements, excludeHidden).map(I::getName).collect(Collectors.joining(COMMA_DELIMINATOR));
    }
    /**
     * Returns List of element names, filtering out hidden elements if <code>excludeHidden</code> is true
     * @param elements
     * @param excludeHidden
     * @return the List
     * @see #getConcealableStream(Collection, boolean)
     */
    public static <I extends ConcealableGameElement> List<String> getConcealableNamesList(Map<?, I> elements, final boolean excludeHidden){
        return getConcealableNamesList(elements == null? null: elements.values(), excludeHidden);
    }
    /**
     * Returns List of element names, filtering out hidden elements if <code>excludeHidden</code> is true
     * @param elements
     * @param excludeHidden
     * @return the List
     * @see #getConcealableStream(Collection, boolean)
     */
    public static <I extends ConcealableGameElement> List<String> getConcealableNamesList(Collection<I> elements, final boolean excludeHidden){
        return getConcealableStream(elements, excludeHidden).map(I::getName).collect(Collectors.toList());
    }
    /**
     * Returns comma separated String of element room descriptions, filtering out hidden elements if <code>excludeHidden</code> is true
     * @param elements
     * @param excludeHidden
     * @return the comma separated String
     * @see #getConcealableStream(Collection, boolean)
     */
    public static <I extends ConcealableGameElement> String getConcealableRoomDescriptions(Map<?,I> elements, final boolean excludeHidden){
        return getConcealableRoomDescriptions(elements == null? null: elements.values(), excludeHidden);
    }
    /**
     * Returns comma separated String of element room descriptions, filtering out hidden elements if <code>excludeHidden</code> is true
     * @param elements
     * @param excludeHidden
     * @return the comma separated String
     * @see #getConcealableStream(Collection, boolean)
     */
    public static <I extends ConcealableGameElement> String getConcealableRoomDescriptions(Collection<I> elements, final boolean excludeHidden){
        return getConcealableStream(elements, excludeHidden).map(I::getRoomDescription).collect(Collectors.joining(COMMA_DELIMINATOR));
    }

    /**
     * Returns comma separated String of element hidden descriptions
     * @param elements
     * @return the comma separated String
     * @see #getConcealableStream(Collection, boolean)
     */
    public static <I extends IDynExit> String getConcealableHiddenDescriptions(Map<?,I> elements){
        return getConcealableHiddenDescriptions(elements == null? null: elements.values());
    }

    /**
     * Returns comma separated String of element hidden descriptions
     * @param elements
     * @return the comma separated String
     * @see #getConcealableStream(Collection, boolean)
     */
    public static <I extends IDynExit> String getConcealableHiddenDescriptions(Collection<I> elements){
        return getHiddenStream(elements).map(I::getHiddenDescription).collect(Collectors.joining(COMMA_DELIMINATOR));
    }





    /**
     * Returns comma separated String of element view descriptions, filtering out hidden elements if <code>excludeHidden</code> is true
     * @param elements
     * @param excludeHidden
     * @return the comma separated String
     * @see #getConcealableStream(Collection, boolean)
     */
    public static <I extends ConcealableGameElement> String getConcealableViewDescriptions(Map<?,I> elements, final boolean excludeHidden){
        return getConcealableViewDescriptions(elements == null? null: elements.values(), excludeHidden);
    }
    /**
     * Returns comma separated String of element view descriptions, filtering out hidden elements if <code>excludeHidden</code> is true
     * @param elements
     * @param excludeHidden
     * @return the comma separated String
     * @see #getConcealableStream(Collection, boolean)
     */
    public static <I extends ConcealableGameElement> String getConcealableViewDescriptions(Collection<I> elements, final boolean excludeHidden){
        return getConcealableStream(elements, excludeHidden).map(I::getViewDescription).collect(Collectors.joining(COMMA_DELIMINATOR));
    }

    /**
     * Return comma separated String of element room descriptions.
     * @param elements
     * @return comma separated String
     */
    public static <I extends IGameElement> String getGameElementRoomDescriptions(Map<?,I> elements){
        return getGameElementRoomDescriptions(elements == null? null: elements.values());
    }
    /**
     * Return comma separated String of element room descriptions.
     * @param elements
     * @return comma separated String
     */
    public static <I extends IGameElement> String getGameElementRoomDescriptions(Collection<I> elements){
        if (elements == null){
            return EMPTY;
        }
        return elements.stream().map(I::getRoomDescription).collect(Collectors.joining(COMMA_DELIMINATOR));
    }
    /**
     * Returns String containing the description of the <code>location</code> along with it's Inventory, Mob, and Exits Description if they are non-empty.
     * The <code>exitStart<code> is placed before the Description for the exits, if any.
     * @param location
     * @param exitStart null defaults to empty string
     * @return description of room and it's elements
     */
    public static String showRoom(final IDynLocation location, final String exitStart){
        if (location == null){
            return EMPTY;
        }
        final String _exitStart = exitStart == null ? "" : exitStart;
        final StringBuilder re = new StringBuilder(location.getDescription());
        String curDescrip;
        boolean isFurniture = true;
        if (!(curDescrip = location.getMobDescriptions()).isEmpty()){
            re.append(SPACE).append(curDescrip).append(PERIOD);
        }
        if (!(curDescrip = location.getFurnishingsDescriptions()).isEmpty()){
            re.append(SPACE).append(curDescrip);
        }
        else {
            isFurniture = false;
        }
        if (!(curDescrip = location.getHiddenExitsDescriptions()).isEmpty()){
            if(isFurniture) {
                re.append(COMMA_DELIMINATOR).append(curDescrip).append(PERIOD);
            } else {
                re.append(SPACE).append(curDescrip).append(PERIOD);
            }
        } else if (isFurniture) {
            re.append(PERIOD);
        }
        if (!(curDescrip = location.getInventoryDescriptions()).isEmpty()){
            re.append(SPACE).append(curDescrip).append(PERIOD);
        }
        if (!(curDescrip = location.getExitsDescriptions()).isEmpty()){
            re.append(SPACE).append(_exitStart).append(curDescrip).append(PERIOD);
        }
        return re.toString();
    }
    /**
     * Returns String containing the description of the <code>location</code> along with it's Inventory, Mob, and Exits Description if they are non-empty.
     * Calls {@link #showRoom(IDynLocation, String)} with {@link #DEFAULT_EXIT_START}
     * @param location
     * @return description of room and it's elements
     * @see #showRoom(IDynLocation, String)
     */
    public static String showRoom(final IDynLocation location){
        return showRoom(location, DEFAULT_EXIT_START);
    }
}
