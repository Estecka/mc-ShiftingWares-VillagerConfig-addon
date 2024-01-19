package tk.estecka.shiftingwares.villagerconfig.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import me.drex.villagerconfig.data.TradeTable;
import me.drex.villagerconfig.data.TradeTier;

@Mixin(TradeTable.class)
public interface ITradeTableMixin
{
	@Invoker TradeTier	callGetTradeTier(int level);
}
