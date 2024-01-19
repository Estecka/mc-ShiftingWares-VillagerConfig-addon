package tk.estecka.shiftingwares.villagerconfig.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.item.map.MapIcon;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.village.TradeOffers.SellMapFactory;
import net.minecraft.world.gen.structure.Structure;
import tk.estecka.shiftingwares.villagerconfig.StructureKeys;

@Mixin(SellMapFactory.class)
public abstract class SellMapFactoryMixin
{
	@Shadow @Final private String nameKey;
	@Shadow @Final private TagKey<Structure> structure;

	@Inject( method="<init>", at=@At("TAIL"))
	private void	RegisterStructure(int price, TagKey<Structure> structure, String namekey, MapIcon.Type icon, int maxUses, int experience, CallbackInfo info){
		StructureKeys.RegisterStructure(this.structure, this.nameKey);
	}
}
