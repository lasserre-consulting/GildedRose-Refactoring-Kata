package com.gildedrose;

/**
 * Ageing rules for conjured items: they degrade twice as fast as standard
 * goods, both before and after the sell date.
 */
class ConjuredItemUpdater extends AbstractItemUpdater {

    @Override
    protected int dailyQualityChange(Item item) {
        return -2;
    }

    @Override
    protected int expiredQualityChange(Item item) {
        return -2;
    }
}
