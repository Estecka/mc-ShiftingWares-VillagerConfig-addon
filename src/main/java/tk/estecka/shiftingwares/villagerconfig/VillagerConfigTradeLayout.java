package tk.estecka.shiftingwares.villagerconfig;

import java.util.ArrayList;
import java.util.List;
import me.drex.villagerconfig.VillagerConfig;
import me.drex.villagerconfig.data.TradeGroup;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.TradeOffers.Factory;
import tk.estecka.shiftingwares.api.ITradeLayoutProvider;
import tk.estecka.shiftingwares.villagerconfig.mixin.ITradeTableMixin;
import tk.estecka.shiftingwares.villagerconfig.mixin.ITradeTierMixin;

public class VillagerConfigTradeLayout
implements ITradeLayoutProvider
{
	public List<Factory[]>	GetTradeLayout(VillagerEntity villager){
		List<Factory[]> layout = new ArrayList<>();

		final VillagerProfession job = villager.getVillagerData().getProfession();
		final int jobLevel = villager.getVillagerData().getLevel();
		final ITradeTableMixin table = (ITradeTableMixin)VillagerConfig.TRADE_MANAGER.getTrade(Registries.VILLAGER_PROFESSION.getId(job));

		if (table == null){
			SwVcAddon.LOGGER.warn("No trade table for job {}", job);
			return null;
		}

		for (int lvl=VillagerData.MIN_LEVEL; lvl<=jobLevel; ++lvl)
		for (TradeGroup group : ((ITradeTierMixin)table.callGetTradeTier(lvl)).getGroups())
		{
			Factory[] trades = group.getTrades(villager).toArray(n->new Factory[n]);
			for (int i=0; i<trades.length; ++i)
				layout.add(trades);
		}

		return layout;
	}

}
