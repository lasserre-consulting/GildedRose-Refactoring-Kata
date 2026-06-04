package com.gildedrose;

/**
 * Ageing rules for ordinary goods: quality drops by one each day and twice as
 * fast once the sell date has passed.
 */
class StandardItemUpdater extends AbstractItemUpdater {

    @Override
    protected int dailyQualityChange(Item item) {
        return -1;
    }

    @Override
    protected int expiredQualityChange(Item item) {
        return -1;
    }
}
