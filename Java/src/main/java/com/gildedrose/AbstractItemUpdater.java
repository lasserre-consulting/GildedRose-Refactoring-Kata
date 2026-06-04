package com.gildedrose;

/**
 * Base strategy for ordinary items, whose quality is bounded to
 * {@code [MIN_QUALITY, MAX_QUALITY]} and whose sell date moves one day closer
 * on every update.
 *
 * <p>The lifecycle of such an item is always the same:</p>
 * <ol>
 *     <li>apply the quality change for the day,</li>
 *     <li>move one day closer to the sell date,</li>
 *     <li>apply an extra quality change if the sell date has passed.</li>
 * </ol>
 *
 * <p>Subclasses only describe <em>how much</em> the quality changes; the
 * bookkeeping (clamping and ageing) lives here once and for all.</p>
 */
abstract class AbstractItemUpdater implements ItemUpdater {

    protected static final int MIN_QUALITY = 0;
    protected static final int MAX_QUALITY = 50;

    @Override
    public final void update(Item item) {
        adjustQuality(item, dailyQualityChange(item));
        item.sellIn--;
        if (isExpired(item)) {
            adjustQuality(item, expiredQualityChange(item));
        }
    }

    /** Quality change applied every day, based on the remaining sell-in. */
    protected abstract int dailyQualityChange(Item item);

    /** Extra quality change applied once the sell date has passed. */
    protected abstract int expiredQualityChange(Item item);

    private static boolean isExpired(Item item) {
        return item.sellIn < 0;
    }

    private static void adjustQuality(Item item, int delta) {
        int updated = item.quality + delta;
        item.quality = Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, updated));
    }
}
