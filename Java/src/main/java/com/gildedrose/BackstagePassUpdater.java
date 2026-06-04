package com.gildedrose;

/**
 * Ageing rules for backstage passes: their value rises as the concert
 * approaches — by two when there are ten days or less and by three when there
 * are five days or less — then drops to zero once the concert is over.
 */
class BackstagePassUpdater extends AbstractItemUpdater {

    private static final int LAST_MINUTE_THRESHOLD = 5;
    private static final int NEAR_CONCERT_THRESHOLD = 10;

    @Override
    protected int dailyQualityChange(Item item) {
        if (item.sellIn <= LAST_MINUTE_THRESHOLD) {
            return 3;
        }
        if (item.sellIn <= NEAR_CONCERT_THRESHOLD) {
            return 2;
        }
        return 1;
    }

    @Override
    protected int expiredQualityChange(Item item) {
        // The pass is worthless after the concert: cancel out whatever is left.
        return -item.quality;
    }
}
