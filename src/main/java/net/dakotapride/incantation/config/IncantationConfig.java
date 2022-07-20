package net.dakotapride.incantation.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

@Config(name = "incantation")
public class IncantationConfig implements ConfigData {

    public static boolean isSoulsComeAliveAddonActive;
    public int enchantedBerryBushSpawnChance = 16;

    @Override
    public void validatePostLoad() {

        LocalDate isSoulsComeAliveActive;

        isSoulsComeAliveActive = LocalDate.now();
        int localMonth = isSoulsComeAliveActive.get(ChronoField.MONTH_OF_YEAR);

        enchantedBerryBushSpawnChance = Math.max(enchantedBerryBushSpawnChance, 1);
        if (localMonth == 10) {
            isSoulsComeAliveAddonActive = true;
        } else {
            isSoulsComeAliveAddonActive = false;
        }
    }
}
