package dev.robothanzo.uhchelper.modes;

import dev.robothanzo.uhchelper.UHCHelper;
import dev.robothanzo.uhchelper.modes.Cutclean.CutcleanBlockBreakListener;
import dev.robothanzo.uhchelper.modes.Cutclean.CutcleanEntityDeathListener;
import dev.robothanzo.uhchelper.modes.LuckyLeaves.LuckyleavesLeafBreakListener;
import dev.robothanzo.uhchelper.modes.NoClean.NoCleanDeathListener;
import dev.robothanzo.uhchelper.modes.Timebomb.TimebombDeathListener;
import dev.robothanzo.uhchelper.modes.UHC.UHCDeathListener;

public class RegistrationService {
    public RegistrationService(UHCHelper plugin) {
        UHCDeathListener uhcDeathListener = new UHCDeathListener(plugin);
        plugin.getServer().getPluginManager().registerEvents(uhcDeathListener, plugin);

        TimebombDeathListener timeBombDeathListener = new TimebombDeathListener(plugin);
        plugin.getServer().getPluginManager().registerEvents(timeBombDeathListener, plugin);

        CutcleanBlockBreakListener cutcleanBlockBreakListener = new CutcleanBlockBreakListener(plugin);
        CutcleanEntityDeathListener cutcleanEntityDeathListener = new CutcleanEntityDeathListener(plugin);
        plugin.getServer().getPluginManager().registerEvents(cutcleanBlockBreakListener, plugin);
        plugin.getServer().getPluginManager().registerEvents(cutcleanEntityDeathListener, plugin);

        LuckyleavesLeafBreakListener luckyleavesLeafBreakListener = new LuckyleavesLeafBreakListener(plugin);
        plugin.getServer().getPluginManager().registerEvents(luckyleavesLeafBreakListener, plugin);

        NoCleanDeathListener noCleanDeathListener = new NoCleanDeathListener(plugin);
        plugin.getServer().getPluginManager().registerEvents(noCleanDeathListener, plugin);
    }
}
