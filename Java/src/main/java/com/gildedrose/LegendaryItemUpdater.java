package com.gildedrose;

/**
 * Ageing rules for legendary items such as "Sulfuras": they never have to be
 * sold and never alter, so neither their quality nor their sell date changes.
 */
class LegendaryItemUpdater implements ItemUpdater {

    @Override
    public void update(Item item) {
        // A legendary item is timeless: nothing to do.
    }
}
