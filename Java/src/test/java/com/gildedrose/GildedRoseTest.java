package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Characterization tests for the inventory system.
 *
 * <p>These tests pin down the behaviour described in the requirements before and
 * after the refactoring, so that the rewrite of {@link GildedRose#updateQuality()}
 * is provably behaviour-preserving for every category of item.</p>
 */
class GildedRoseTest {

    private static final String AGED_BRIE = "Aged Brie";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";

    /** Advances the inventory by a single day and returns the (only) updated item. */
    private static Item updateSingle(Item item) {
        GildedRose app = new GildedRose(new Item[] { item });
        app.updateQuality();
        return app.items[0];
    }

    @Nested
    @DisplayName("A standard item")
    class StandardItem {

        @Test
        @DisplayName("loses one sellIn and one quality per day before the sell date")
        void degradesByOneBeforeSellDate() {
            Item item = updateSingle(new Item("Elixir of the Mongoose", 5, 7));

            assertEquals(4, item.sellIn);
            assertEquals(6, item.quality);
        }

        @Test
        @DisplayName("degrades twice as fast once the sell date has passed")
        void degradesTwiceAsFastAfterSellDate() {
            Item item = updateSingle(new Item("Elixir of the Mongoose", 0, 7));

            assertEquals(-1, item.sellIn);
            assertEquals(5, item.quality);
        }

        @Test
        @DisplayName("never has a negative quality")
        void qualityIsNeverNegative() {
            Item item = updateSingle(new Item("Elixir of the Mongoose", 5, 0));

            assertEquals(0, item.quality);
        }

        @Test
        @DisplayName("never drops below zero even past the sell date")
        void qualityStaysAtZeroPastSellDate() {
            Item item = updateSingle(new Item("Elixir of the Mongoose", -1, 1));

            assertEquals(0, item.quality);
        }
    }

    @Nested
    @DisplayName("Aged Brie")
    class AgedBrie {

        @Test
        @DisplayName("increases in quality as it gets older")
        void increasesInQuality() {
            Item item = updateSingle(new Item(AGED_BRIE, 2, 0));

            assertEquals(1, item.sellIn);
            assertEquals(1, item.quality);
        }

        @Test
        @DisplayName("increases twice as fast once the sell date has passed")
        void increasesTwiceAsFastAfterSellDate() {
            Item item = updateSingle(new Item(AGED_BRIE, 0, 0));

            assertEquals(2, item.quality);
        }

        @Test
        @DisplayName("never has a quality above fifty")
        void qualityIsCappedAtFifty() {
            Item item = updateSingle(new Item(AGED_BRIE, 2, 50));

            assertEquals(50, item.quality);
        }

        @Test
        @DisplayName("does not exceed fifty even when ageing twice as fast")
        void qualityIsCappedAtFiftyPastSellDate() {
            Item item = updateSingle(new Item(AGED_BRIE, -1, 49));

            assertEquals(50, item.quality);
        }
    }

    @Nested
    @DisplayName("Sulfuras")
    class Sulfuras {

        @Test
        @DisplayName("never changes quality nor sell date")
        void neverChanges() {
            Item item = updateSingle(new Item(SULFURAS, 0, 80));

            assertEquals(0, item.sellIn);
            assertEquals(80, item.quality);
        }

        @Test
        @DisplayName("remains constant even with a negative sell date")
        void neverChangesPastSellDate() {
            Item item = updateSingle(new Item(SULFURAS, -1, 80));

            assertEquals(-1, item.sellIn);
            assertEquals(80, item.quality);
        }
    }

    @Nested
    @DisplayName("Backstage passes")
    class BackstagePasses {

        @Test
        @DisplayName("increase by one when there are more than ten days left")
        void increaseByOneWhenFarFromConcert() {
            Item item = updateSingle(new Item(BACKSTAGE, 11, 20));

            assertEquals(21, item.quality);
        }

        @Test
        @DisplayName("increase by two when there are ten days or less")
        void increaseByTwoWhenTenDaysOrLess() {
            Item item = updateSingle(new Item(BACKSTAGE, 10, 20));

            assertEquals(22, item.quality);
        }

        @Test
        @DisplayName("increase by three when there are five days or less")
        void increaseByThreeWhenFiveDaysOrLess() {
            Item item = updateSingle(new Item(BACKSTAGE, 5, 20));

            assertEquals(23, item.quality);
        }

        @Test
        @DisplayName("drop to zero once the concert is over")
        void dropToZeroAfterConcert() {
            Item item = updateSingle(new Item(BACKSTAGE, 0, 20));

            assertEquals(-1, item.sellIn);
            assertEquals(0, item.quality);
        }

        @Test
        @DisplayName("never exceed a quality of fifty")
        void qualityIsCappedAtFifty() {
            Item item = updateSingle(new Item(BACKSTAGE, 5, 49));

            assertEquals(50, item.quality);
        }
    }
}
