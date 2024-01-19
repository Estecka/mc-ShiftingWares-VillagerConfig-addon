package tk.estecka.shiftingwares.villagerconfig.mixin;

import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import me.drex.villagerconfig.data.TradeGroup;
import me.drex.villagerconfig.data.TradeTier;

@Mixin(TradeTier.class)
public interface ITradeTierMixin
{
	@Accessor List<TradeGroup>	getGroups();
}
