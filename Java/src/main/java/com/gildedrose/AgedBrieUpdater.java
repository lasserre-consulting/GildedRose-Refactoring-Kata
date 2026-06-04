package com.gildedrose;

/**
 * Ageing rules for "Aged Brie": it improves with age, twice as fast once the
 * sell date has passed.
 */
class AgedBrieUpdater extends AbstractItemUpdater {

    @Override
    protected int dailyQualityChange(Item item) {
        return 1;
    }

    @Override
    protected int expiredQualityChange(Item item) {
        return 1;
    }
}
