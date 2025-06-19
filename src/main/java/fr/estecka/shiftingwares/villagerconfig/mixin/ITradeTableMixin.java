package fr.estecka.shiftingwares.villagerconfig.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import me.drex.villagerconfig.common.data.TradeTable;
import me.drex.villagerconfig.common.data.TradeTier;

@Mixin(TradeTable.class)
public interface ITradeTableMixin
{
	@Invoker(remap=false) TradeTier	callGetTradeTier(int level);
}
