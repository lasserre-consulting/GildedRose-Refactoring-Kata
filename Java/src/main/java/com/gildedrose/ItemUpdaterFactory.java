package com.gildedrose;

/**
 * Selects the {@link ItemUpdater} that matches an item, based on its name.
 *
 * <p>The strategies are stateless, so a single shared instance of each is
 * reused for every item.</p>
 */
final class ItemUpdaterFactory {

    private static final String AGED_BRIE = "Aged Brie";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";

    private static final ItemUpdater STANDARD = new StandardItemUpdater();
    private static final ItemUpdater AGED_BRIE_UPDATER = new AgedBrieUpdater();
    private static final ItemUpdater BACKSTAGE_PASS_UPDATER = new BackstagePassUpdater();
    private static final ItemUpdater LEGENDARY = new LegendaryItemUpdater();

    private ItemUpdaterFactory() {
    }

    static ItemUpdater updaterFor(Item item) {
        switch (item.name) {
            case AGED_BRIE:
                return AGED_BRIE_UPDATER;
            case SULFURAS:
                return LEGENDARY;
            case BACKSTAGE_PASS:
                return BACKSTAGE_PASS_UPDATER;
            default:
                return STANDARD;
        }
    }
}
