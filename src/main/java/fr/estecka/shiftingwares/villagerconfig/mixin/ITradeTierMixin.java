package fr.estecka.shiftingwares.villagerconfig.mixin;

import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import me.drex.villagerconfig.common.data.TradeGroup;
import me.drex.villagerconfig.common.data.TradeTier;

@Mixin(TradeTier.class)
public interface ITradeTierMixin
{
	@Accessor(remap=false) List<TradeGroup>	getGroups();
}
