package com.gildedrose;

/**
 * Strategy that knows how to age a single {@link Item} by one day.
 *
 * <p>Each category of item (standard goods, Aged Brie, Backstage passes,
 * legendary items, …) has its own implementation, which keeps the ageing
 * rules of one category isolated from the others.</p>
 */
interface ItemUpdater {

    /** Updates the given item for the passing of a single day. */
    void update(Item item);
}
