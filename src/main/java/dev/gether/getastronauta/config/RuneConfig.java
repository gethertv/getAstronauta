package dev.gether.getastronauta.config;

import dev.gether.getastronauta.rune.Rune;
import dev.gether.getastronauta.rune.RuneLevel;
import dev.gether.getastronauta.rune.RuneType;
import dev.gether.getastronauta.utils.ItemBuilder;
import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RuneConfig extends OkaeriConfig {


    // map with TYPE of rune , next LEVEL and RUNE
    public Map<RuneType, Rune> runes = Map.of(
            /*
                BOOST DROP
             */
            RuneType.BOOST_DROP,
                new Rune(
                        true,
                        RuneType.BOOST_DROP,
                        ItemBuilder
                                .create(Material.BOOK)
                                .name("&a&lPerk dropu")
                                .lore(new ArrayList<>(List.of(
                                        "",
                                        "&4&lNIE JEST DOSTĘPNY PODCZAS LOSOWANIA"
                                )))
                                .glow(true)
                                .build(),
                        Map.of(
                                // level 0 - default
                                0, new RuneLevel("&a&lPerk dropu 0", 0, 0),
                                // level 1
                                1, new RuneLevel("&a&lPerk dropu 1", 50, 1.5),
                                // level 2
                                2, new RuneLevel("&a&lPerk dropu 2", 100, 2),
                                // level 3
                                3, new RuneLevel("&a&lPerk dropu 3", 150, 2.5),
                                // level 4
                                4, new RuneLevel("&a&lPerk dropu 4", 200, 3),
                                // level 5
                                5, new RuneLevel("&a&lPerk dropu 5", 250, 3.25),
                                // level 6
                                6, new RuneLevel("&a&lPerk dropu 6", 300, 3.5)

                        )
                ),
            /*
                BOOST POINTS
             */
            RuneType.BOOST_POINTS,
            new Rune(
                    true,
                    RuneType.BOOST_POINTS,
                    ItemBuilder
                            .create(Material.BOOK)
                            .name("&a&lPerk Rankingu")
                            .lore(new ArrayList<>(List.of(
                                    "",
                                    "&4&lNIE JEST DOSTĘPNY PODCZAS LOSOWANIA"
                            )))
                            .glow(true)
                            .build(),
                    Map.of(
                            // level 0 - default
                            0, new RuneLevel("&a&lPerk rankingu 0", 0, 0),
                            // level 1
                            1, new RuneLevel("&a&lPerk rankingu 1", 25, 10),
                            // level 2
                            2, new RuneLevel("&a&lPerk rankingu 2", 50, 20),
                            // level 3
                            3, new RuneLevel("&a&lPerk rankingu 3", 75, 35),
                            // level 4
                            4, new RuneLevel("&a&lPerk rankingu 4", 100, 50)

                    )
            ),
            /*
                RUNE STRENGTH
             */
            RuneType.STRENGTH,
            new Rune(
                    true,
                    RuneType.STRENGTH,
                    ItemBuilder.create(Material.BOOK)
                            .name("&3&lRuna Siły")
                            .lore(new ArrayList<>(List.of(
                                    "",
                                    "&b&lZwiększa twoje",
                                    "&3&lzadawane obrażenia",
                                    "&f1 &bPoziom 0.5 &3obrażenia od ataku",
                                    "&f2 &bPoziom 1 &3obrażenie od ataku",
                                    "&f3 &bPoziom 1.5 &3obrażenia od ataku",
                                    "&f4 &bPoziom 2 &3obrażenia od ataku",
                                    "&f5 &bPoziom 2.5 &3obrażenia od ataku",
                                    "&f6 &bPoziom 3 &3obrażenia od ataku",
                                    ""
                            )))
                            .glow(true)
                            .build(),
                    Map.of(
                            // level 0 - default
                            0, new RuneLevel("&b&lRuna Siły 0", 1, 0),
                            // level 1
                            1, new RuneLevel("&b&lRuna Siły 1", 1, 0.5),
                            // level 2
                            2, new RuneLevel("&b&lRuna Siły 2", 1, 1),
                            // level 3
                            3, new RuneLevel("&b&lRuna Siły 3", 1, 1.5),
                            // level 4
                            4, new RuneLevel("&b&lRuna Siły 4", 1, 2),
                            // level 5
                            5, new RuneLevel("&b&lRuna Siły 5", 1, 2),
                            // level 6
                            6, new RuneLevel("&b&lRuna Siły 6", 1, 2)
                    )
            ),
            /*
                RUNE WEAKNESS
             */
            RuneType.WEAKNESS,
            new Rune(
                    true,
                    RuneType.WEAKNESS,
                    ItemBuilder.create(Material.BOOK)
                            .name("&7&lRuna Osłabienia")
                            .lore(new ArrayList<>(List.of(
                                    "",
                                    "&b&lZwiększa twoje szanse",
                                    "&e&lna osłabienie przeciwnika",
                                    "&f1 &bPoziom 1% szansy &ena osłabienie",
                                    "&f2 &bPoziom 3% szansy &ena osłabienie",
                                    "&f3 &bPoziom 5% szansy &ena osłabienie",
                                    ""
                            )))
                            .glow(true)
                            .build(),
                    Map.of(
                            // level 0 - default
                            0, new RuneLevel("&7&lRuna Osłabienia 0", 1, 0),
                            // level 1
                            1, new RuneLevel("&7&lRuna Osłabienia 1", 1, 1),
                            // level 2
                            2, new RuneLevel("&7&lRuna Osłabienia 2", 1, 3),
                            // level 3
                            3, new RuneLevel("&7&lRuna Osłabienia 3", 1, 5)
                    )
            ),
            /*
                RUNE DURABILITY
             */
            RuneType.DURABILITY,
            new Rune(
                    true,
                    RuneType.DURABILITY,
                    ItemBuilder.create(Material.BOOK)
                            .name("&a&lRuna Wytrzymałości")
                            .lore(new ArrayList<>(List.of(
                                    "",
                                    "&b&lZwiększa wytrzymałość",
                                    "&c&ltwojej zbroi",
                                    "&f1 &bPoziom 10% &7wytrzymałości",
                                    "&f2 &bPoziom 15% &7wytrzymałości",
                                    "&f3 &bPoziom 20% &7wytrzymałości",
                                    ""
                            )))
                            .glow(true)
                            .build(),
                    Map.of(
                            // level 0 - default
                            0, new RuneLevel("&a&lRuna Wytrzymałości 0", 1, 0),
                            // level 1
                            1, new RuneLevel("&a&lRuna Wytrzymałości 1", 1, 10),
                            // level 2
                            2, new RuneLevel("&a&lRuna Wytrzymałości 2", 1, 15),
                            // level 3
                            3, new RuneLevel("&a&lRuna Wytrzymałości 3", 1, 20)
                    )
            ),
            /*
                RUNE OF HEARTS
             */
            RuneType.HEARTS,
            new Rune(
                    true,
                    RuneType.HEARTS,
                    ItemBuilder.create(Material.BOOK)
                            .name("&c&lRuna Życia")
                            .lore(new ArrayList<>(List.of(
                                    "",
                                    "&b&lZwiększa twoją ilość",
                                    "&c&lserc na pasku",
                                    "&f1 &bPoziom 0.5 &cserca więcej",
                                    "&f2 &bPoziom 1 &cserce więcej",
                                    "&f3 &bPoziom 1.5 &cserca więcej",
                                    "&f4 &bPoziom 2 &cserca więcej",
                                    "&f5 &bPoziom 2.5 &cserca więcej",
                                    "&f6 &bPoziom 3 &cserca więcej",
                                    ""
                            )))
                            .glow(true)
                            .build(),
                    Map.of(
                            // level 0 - default
                            0, new RuneLevel("&c&lRuna Życia 0", 1, 0),
                            // level 1
                            1, new RuneLevel("&c&lRuna Życia 1", 1, 0.5),
                            // level 2
                            2, new RuneLevel("&c&lRuna Życia 2", 1, 1),
                            // level 3
                            3, new RuneLevel("&c&lRuna Życia 3", 1, 1.5),
                            // level 4
                            4, new RuneLevel("&c&lRuna Życia 4", 1, 2),
                            // level 5
                            5, new RuneLevel("&c&lRuna Życia 5", 1, 2.5),
                            // level 6
                            6, new RuneLevel("&c&lRuna Życia 6", 1, 3)
                    )
            ),
            /*
                RUNE RESISTANCE
             */
            RuneType.RESISTANCE,
            new Rune(
                    true,
                    RuneType.RESISTANCE,
                    ItemBuilder.create(Material.BOOK)
                            .name("&7&lRuna Odporności")
                            .lore(new ArrayList<>(List.of(
                                    "",
                                    "&b&lZwiększa twoją odporność",
                                    "&f1 &bPoziom 5% &7odporności",
                                    "&f2 &bPoziom 10% &7odporności",
                                    "&f3 &bPoziom 15% &7odporności",
                                    "&f4 &bPoziom 20% &7odporności",
                                    "&f5 &bPoziom 25% &7odporności",
                                    ""
                            )))
                            .glow(true)
                            .build(),
                    Map.of(
                            // level 0 - default
                            0, new RuneLevel("&7&lRuna Odporności 0", 1, 0),
                            // level 1
                            1, new RuneLevel("&7&lRuna Odporności 1", 1, 5),
                            // level 2
                            2, new RuneLevel("&7&lRuna Odporności 2", 1, 10),
                            // level 3
                            3, new RuneLevel("&7&lRuna Odporności 3", 1, 15),
                            // level 4
                            4, new RuneLevel("&7&lRuna Odporności 4", 1, 20),
                            // level 5
                            5, new RuneLevel("&7&lRuna Odporności 5", 1, 25)
                    )
            ),
            /*
                RUNE POISON
             */
            RuneType.POISON,
            new Rune(
                    true,
                    RuneType.POISON,
                    ItemBuilder.create(Material.BOOK)
                            .name("&2&lRuna Trucizny")
                            .lore(new ArrayList<>(List.of(
                                    "",
                                    "&b&lZwiększa twoje szanse",
                                    "&2&lna zatrucie przeciwnika",
                                    "&f1 &bPoziom 5% szansy &2na zatrucie",
                                    "&f2 &bPoziom 10% szansy &2na zatrucie",
                                    "&f3 &bPoziom 15% szansy &2na zatrucie",
                                    ""
                            )))
                            .glow(true)
                            .build(),
                    Map.of(
                            // level 0 - default
                            0, new RuneLevel("&2&lRuna Trucizny 0", 1, 0),
                            // level 1
                            1, new RuneLevel("&2&lRuna Trucizny 1", 1, 5),
                            // level 2
                            2, new RuneLevel("&2&lRuna Trucizny 2", 1, 10),
                            // level 3
                            3, new RuneLevel("&2&lRuna Trucizny 3", 1, 15)
                    )
            ),
            /*
                RUNE SLOWNESS
             */
            RuneType.SLOWNESS,
            new Rune(
                    true,
                    RuneType.SLOWNESS,
                    ItemBuilder.create(Material.BOOK)
                            .name("&8&lRuna Spowolnienia")
                            .lore(new ArrayList<>(List.of(
                                    "",
                                    "&b&lZwiększa twoje szanse",
                                    "&8&lna spowolnienie przeciwnika",
                                    "&f1 &bPoziom 5% szansy &7na spowolnienie",
                                    "&f2 &bPoziom 10% szansy &7na spowolnienie",
                                    "&f3 &bPoziom 15% szansy &7na spowolnienie",
                                    ""
                            )))
                            .glow(true)
                            .build(),
                    Map.of(
                            // level 0 - default
                            0, new RuneLevel("&8&lRuna Spowolnienia 0", 1, 0),
                            // level 1
                            1, new RuneLevel("&8&lRuna Spowolnienia 1", 1, 5),
                            // level 2
                            2, new RuneLevel("&8&lRuna Spowolnienia 2", 1, 10),
                            // level 3
                            3, new RuneLevel("&8&lRuna Spowolnienia 3", 1, 15)
                    )
            ),
             /*
                RUNE LIFE STEAL
             */
            RuneType.LIFE_STEAL,
            new Rune(
                    true,
                    RuneType.LIFE_STEAL,
                    ItemBuilder.create(Material.BOOK)
                            .name("&4&lRuna LifeSteal")
                            .lore(new ArrayList<>(List.of(
                                    "",
                                    "&b&lZwiększa twoje szanse",
                                    "&4&lna uleczenie",
                                    "&f1 &bPoziom 1% szansy &4na uleczenie",
                                    "&f2 &bPoziom 3% szansy &4na uleczenie",
                                    "&f3 &bPoziom 5% szansy &4na uleczenie",
                                    ""
                            )))
                            .glow(true)
                            .build(),
                    Map.of(
                            // level 0 - default
                            0, new RuneLevel("&4&lRuna LifeSteal 0", 1, 0),
                            // level 1
                            1, new RuneLevel("&4&lRuna LifeSteal 1", 1, 1),
                            // level 2
                            2, new RuneLevel("&4&lRuna LifeSteal 2", 1, 3),
                            // level 3
                            3, new RuneLevel("&4&lRuna LifeSteal 3", 1, 5)
                    )
            )



    );

}
