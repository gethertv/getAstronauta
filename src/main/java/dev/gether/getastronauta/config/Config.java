package dev.gether.getaustronauta.config;

import com.google.common.collect.Lists;
import dev.gether.getaustronauta.rune.RuneType;
import dev.gether.getaustronauta.utils.ItemBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Config extends OkaeriConfig {

    @Comment("inv title")
    public String title = "&0Astronauta";
    @Comment("inv size [9,18,27,36,45,54]")
    public int invSize = 45;

    @Comment("background items")
    public Set<BackgroundItem> backgroundConfig = Set.of(
            new BackgroundItem(
                    List.of(0,8,36,44),
                    new ItemStack(Material.LIME_STAINED_GLASS_PANE)
            ),
            new BackgroundItem(
                    List.of(1,7,9,17,27,35,37,43),
                    new ItemStack(Material.BLUE_STAINED_GLASS_PANE)
            ),
            new BackgroundItem(
                    List.of(2,3,5,6,18,26,38,39,41,42),
                    new ItemStack(Material.WHITE_STAINED_GLASS_PANE)
            )
    );

    public ItemStack itemWithListRunes = ItemBuilder.create(Material.BOOK)
            .name("&b&lTWOJE RUNY")
            .lore(new ArrayList<>(List.of("&7", "{runes}", "&7")))
            .build();
    @Comment("Slot do powyzszego przedmiotu")
    public int slotItemRunes = 13;
    @Comment("Inv title dla losowania runy")
    public String titleSpinInv = "&0Losowanie...";

    @Comment("Koszt losowania runy")
    public int costDrawing = 100;
    @Comment("background items")
    public Set<BackgroundItem> spinBackground = Set.of(
            new BackgroundItem(
                    List.of(0,1,2,3,5,6,7,8,18,19,20,21,23,24,25,26),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE)
            ),
            new BackgroundItem(
                    List.of(4,22),
                    new ItemStack(Material.LIME_STAINED_GLASS_PANE)
            )
    );

    @Comment("Czas trwania efektu poison")
    public int secondPoison = 3;
    @Comment("Czas trwania efektu weakness")
    public int secondWeakness = 3;
    @Comment("Czas trwania efektu slowness")
    public int secondSlowness = 3;
    public List<Material> allowMaterial = new ArrayList<>(List.of(Material.DIAMOND_BLOCK, Material.DIAMOND_ORE));
    @Comment("Materiał na który ma działać boost drop")
    @Comment("Slot perk dropu")
    public int slotDrop = 21;
    @Comment("Slot perk Rankingu")
    public int slotRank = 23;
    @Comment("Slot na losowanie runy")
    public int slotDrawRune = 22;

    public ItemStack drawItem = ItemBuilder.create(Material.NETHER_STAR)
            .name("&b&lLOSOWANIE RUNY")
            .lore(new ArrayList<>(List.of(
                    "&8▎ &7Możesz wylosować jedną z &f8 run",
                    "&8▎ &7Koszt: &b100x Odłamków"
            )))
            .build();
    public Map<RuneType, Map<Integer, ItemStack>> itemTypeLevel = Map.of(
            RuneType.BOOST_DROP, Map.of(
                    0,
                    ItemBuilder.create(Material.NETHERITE_PICKAXE)
                            .name("&a&lPerk Dropu")
                            .lore(new ArrayList<>(Arrays.asList(
                                    "&8▎ &7Aktualny poziom: &cNieodblokowano",
                                    "",
                                    "&8▎ &6Poziomy:",
                                    "&8▎ &cI &8- &4x1.5 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cII &8- &4x2 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cIII &8- &4x2.5 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cIV &8- &4x3 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cV &8- &4x3.25 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cVI &8- &4x3.5 &cwiększy &4drop &cz generatorów",
                                    "",
                                    "&8▎ Koszt: &b50x Odłamków",
                                    "&eKliknij, aby ulepszyć"
                            )))
                            .build(),
                    1,
                    ItemBuilder.create(Material.NETHERITE_PICKAXE)
                            .name("&a&lPerk Dropu")
                            .lore(new ArrayList<>(Arrays.asList(
                                    "&8▎ &7Aktualny poziom: &f1",
                                    "",
                                    "&8▎ &6Poziomy:",
                                    "&8▎ &aI &8- &2x1.5 &awiększy &2drop &az generatorów",
                                    "&8▎ &cII &8- &4x2 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cIII &8- &4x2.5 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cIV &8- &4x3 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cV &8- &4x3.25 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cVI &8- &4x3.5 &cwiększy &4drop &cz generatorów",
                                    "",
                                    "&8▎ Koszt: &b100x Odłamków",
                                    "&eKliknij, aby ulepszyć"
                            )))
                            .build(),
                    2,
                    ItemBuilder.create(Material.NETHERITE_PICKAXE)
                            .name("&a&lPerk Dropu")
                            .lore(new ArrayList<>(Arrays.asList(
                                    "&8▎ &7Aktualny poziom: &f2",
                                    "",
                                    "&8▎ &6Poziomy:",
                                    "&8▎ &aI &8- &2x1.5 &awiększy &2drop &az generatorów",
                                    "&8▎ &aII &8- &2x2 &awiększy &2drop &az generatorów",
                                    "&8▎ &cIII &8- &4x2.5 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cIV &8- &4x3 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cV &8- &4x3.25 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cVI &8- &4x3.5 &cwiększy &4drop &cz generatorów",
                                    "",
                                    "&8▎ Koszt: &b150x Odłamków",
                                    "&eKliknij, aby ulepszyć"
                            )))
                            .build(),
                    3,
                    ItemBuilder.create(Material.NETHERITE_PICKAXE)
                            .name("&a&lPerk Dropu")
                            .lore(new ArrayList<>(Arrays.asList(
                                    "&8▎ &7Aktualny poziom: &f3",
                                    "",
                                    "&8▎ &6Poziomy:",
                                    "&8▎ &aI &8- &2x1.5 &awiększy &2drop &az generatorów",
                                    "&8▎ &aII &8- &2x2 &awiększy &2drop &az generatorów",
                                    "&8▎ &aIII &8- &2x2.5 &awiększy &2drop &az generatorów",
                                    "&8▎ &cIV &8- &4x3 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cV &8- &4x3.25 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cVI &8- &4x3.5 &cwiększy &4drop &cz generatorów",
                                    "",
                                    "&8▎ Koszt: &b200x Odłamków",
                                    "&eKliknij, aby ulepszyć"
                            )))
                            .build(),
                    4,
                    ItemBuilder.create(Material.NETHERITE_PICKAXE)
                            .name("&a&lPerk Dropu")
                            .lore(new ArrayList<>(Arrays.asList(
                                    "&8▎ &7Aktualny poziom: &f4",
                                    "",
                                    "&8▎ &6Poziomy:",
                                    "&8▎ &aI &8- &2x1.5 &awiększy &2drop &az generatorów",
                                    "&8▎ &aII &8- &2x2 &awiększy &2drop &az generatorów",
                                    "&8▎ &aIII &8- &2x2.5 &awiększy &2drop &az generatorów",
                                    "&8▎ &aIV &8- &2x3 &awiększy &2drop &az generatorów",
                                    "&8▎ &cV &8- &4x3.25 &cwiększy &4drop &cz generatorów",
                                    "&8▎ &cVI &8- &4x3.5 &cwiększy &4drop &cz generatorów",
                                    "",
                                    "&8▎ Koszt: &b250x Odłamków",
                                    "&eKliknij, aby ulepszyć"
                            )))
                            .build(),
                    5,
                    ItemBuilder.create(Material.NETHERITE_PICKAXE)
                            .name("&a&lPerk Dropu")
                            .lore(new ArrayList<>(Arrays.asList(
                                    "&8▎ &7Aktualny poziom: &f5",
                                    "",
                                    "&8▎ &6Poziomy:",
                                    "&8▎ &aI &8- &2x1.5 &awiększy &2drop &az generatorów",
                                    "&8▎ &aII &8- &2x2 &awiększy &2drop &az generatorów",
                                    "&8▎ &aIII &8- &2x2.5 &awiększy &2drop &az generatorów",
                                    "&8▎ &aIV &8- &2x3 &awiększy &2drop &az generatorów",
                                    "&8▎ &aV &8- &2x3.25 &awiększy &2drop &az generatorów",
                                    "&8▎ &cVI &8- &4x3.5 &cwiększy &4drop &cz generatorów",
                                    "",
                                    "&8▎ Koszt: &b300x Odłamków",
                                    "&eKliknij, aby ulepszyć"
                            )))
                            .build(),
                    6,
                    ItemBuilder.create(Material.NETHERITE_PICKAXE)
                            .name("&a&lPerk Dropu")
                            .lore(new ArrayList<>(Arrays.asList(
                                    "&8▎ &7Aktualny poziom: &f6",
                                    "",
                                    "&8▎ &6Poziomy:",
                                    "&8▎ &aI &8- &2x1.5 &awiększy &2drop &az generatorów",
                                    "&8▎ &aII &8- &2x2 &awiększy &2drop &az generatorów",
                                    "&8▎ &aIII &8- &2x2.5 &awiększy &2drop &az generatorów",
                                    "&8▎ &aIV &8- &2x3 &awiększy &2drop &az generatorów",
                                    "&8▎ &aV &8- &2x3.25 &awiększy &2drop &az generatorów",
                                    "&8▎ &aVI &8- &2x3.5 &awiększy &2drop &az generatorów",
                                    ""
                            )))
                            .build()
            ),
            RuneType.BOOST_POINTS, Map.of(
                    0,
                    ItemBuilder.create(Material.GOLDEN_SWORD)
                            .name("&a&lPerk Rankingu")
                            .lore(new ArrayList<>(Arrays.asList(
                                    "&8▎ &7Aktualny poziom: &cNieodblokowano",
                                    "",
                                    "&8▎ &6Poziomy:",
                                    "&8▎ &cI &8- &cwiększy &4ranking &cz zabójstw o &410%",
                                    "&8▎ &cII &8- &cwiększy &4ranking &cz zabójstw o &420%",
                                    "&8▎ &cIII &8- &cwiększy &4ranking &cz zabójstw o &435%",
                                    "&8▎ &cIV &8- &cwiększy &4ranking &cz zabójstw o &450%",
                                    "",
                                    "&8▎ Koszt: &b25x Odłamków",
                                    "&eKliknij, aby ulepszyć"
                            )))
                            .build(),
                    1,
                    ItemBuilder.create(Material.GOLDEN_SWORD)
                            .name("&a&lPerk Rankingu")
                            .lore(new ArrayList<>(Arrays.asList(
                                    "&8▎ &7Aktualny poziom: &f1",
                                    "",
                                    "&8▎ &6Poziomy:",
                                    "&8▎ &aI &8- &awiększy &2ranking &az zabójstw o &210%",
                                    "&8▎ &cII &8- &cwiększy &4ranking &cz zabójstw o &420%",
                                    "&8▎ &cIII &8- &cwiększy &4ranking &cz zabójstw o &435%",
                                    "&8▎ &cIV &8- &cwiększy &4ranking &cz zabójstw o &450%",
                                    "",
                                    "&8▎ Koszt: &b50x Odłamków",
                                    "&eKliknij, aby ulepszyć"
                            )))
                            .build(),
                    2,
                    ItemBuilder.create(Material.GOLDEN_SWORD)
                            .name("&a&lPerk Rankingu")
                            .lore(new ArrayList<>(Arrays.asList(
                                    "&8▎ &7Aktualny poziom: &f2",
                                    "",
                                    "&8▎ &6Poziomy:",
                                    "&8▎ &aI &8- &awiększy &2ranking &az zabójstw o &210%",
                                    "&8▎ &aII &8- &awiększy &2ranking &az zabójstw o &220%",
                                    "&8▎ &cIII &8- &cwiększy &4ranking &cz zabójstw o &435%",
                                    "&8▎ &cIV &8- &cwiększy &4ranking &cz zabójstw o &450%",
                                    "",
                                    "&8▎ Koszt: &b75x Odłamków",
                                    "&eKliknij, aby ulepszyć"
                            )))
                            .build(),
                    3,
                    ItemBuilder.create(Material.GOLDEN_SWORD)
                            .name("&a&lPerk Rankingu")
                            .lore(new ArrayList<>(Arrays.asList(
                                    "&8▎ &7Aktualny poziom: &f3",
                                    "",
                                    "&8▎ &6Poziomy:",
                                    "&8▎ &aI &8- &awiększy &2ranking &az zabójstw o &210%",
                                    "&8▎ &aII &8- &awiększy &2ranking &az zabójstw o &220%",
                                    "&8▎ &aIII &8- &awiększy &2ranking &az zabójstw o &235%",
                                    "&8▎ &cIV &8- &cwiększy &4ranking &cz zabójstw o &450%",
                                    "",
                                    "&8▎ Koszt: &b100x Odłamków",
                                    "&eKliknij, aby ulepszyć"
                            )))
                            .build(),
                    4,
                    ItemBuilder.create(Material.GOLDEN_SWORD)
                            .name("&a&lPerk Rankingu")
                            .lore(new ArrayList<>(Arrays.asList(
                                    "&8▎ &7Aktualny poziom: &f4",
                                    "",
                                    "&8▎ &6Poziomy:",
                                    "&8▎ &aI &8- &awiększy &2ranking &az zabójstw o &210%",
                                    "&8▎ &aII &8- &awiększy &2ranking &az zabójstw o &220%",
                                    "&8▎ &aIII &8- &awiększy &2ranking &az zabójstw o &235%",
                                    "&8▎ &aIV &8- &awiększy &2ranking &az zabójstw o &250%",
                                    ""
                            )))
                            .build()
            )
    );
    public ItemStack itemCoins = ItemBuilder.create(Material.GOLD_NUGGET).name("&cZLOTA MONETA").build();


    // class for config background
    public static class BackgroundItem extends OkaeriConfig {

        @Comment("Slots")
        public List<Integer> slots;

        @Comment("Material")
        public ItemStack item;

        public BackgroundItem(List<Integer> slots, ItemStack item) {
            this.slots = slots;
            this.item = item;
        }
    }
}
