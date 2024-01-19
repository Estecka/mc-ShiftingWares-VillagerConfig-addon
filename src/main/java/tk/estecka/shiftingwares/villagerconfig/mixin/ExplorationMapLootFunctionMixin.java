package tk.estecka.shiftingwares.villagerconfig.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.entity.Entity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ExplorationMapLootFunction;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.gen.structure.Structure;
import tk.estecka.shiftingwares.api.IHasItemCache;
import tk.estecka.shiftingwares.api.PersistentItemCache;
import tk.estecka.shiftingwares.villagerconfig.StructureKeys;
import tk.estecka.shiftingwares.villagerconfig.SwVcAddon;

@Mixin(ExplorationMapLootFunction.class)
public abstract class ExplorationMapLootFunctionMixin
{
	@Shadow @Final private TagKey<Structure> destination;

	@Inject( method="process", at=@At("HEAD"), cancellable=true )
	private void	RestoreCachedItem(ItemStack stack, LootContext context, CallbackInfoReturnable<ItemStack> info){
		Entity entity = context.get(LootContextParameters.THIS_ENTITY);
		if(!(entity instanceof IHasItemCache))
			return;

		String cacheKey = GetOrCreateKey();
		var cachedMap = PersistentItemCache.Resell(entity, cacheKey);
		if (cachedMap.isPresent()){
			stack = cachedMap.get();
			SwVcAddon.LOGGER.info("Reselling previously available map #{} @ {}", FilledMapItem.getMapId(stack), cacheKey);
			info.setReturnValue(stack);
		}
	}

	@Inject( method="process", at=@At("RETURN") )
	private void	CacheCreated(ItemStack stack, LootContext context, CallbackInfoReturnable<ItemStack> info){
		Entity entity = context.get(LootContextParameters.THIS_ENTITY);
		stack = info.getReturnValue();
		if(stack == null || !(entity instanceof IHasItemCache villager))
			return;

		PersistentItemCache cache = villager.shiftingwares$GetItemCache();
		cache.AddCachedItem(this.GetOrCreateKey(), stack);
	}

	@Unique
	private String GetOrCreateKey(){
		String cacheKey = StructureKeys.GetCacheKey(this.destination);
		if (cacheKey == null){
			cacheKey = destination.id().toString();
			SwVcAddon.LOGGER.error("No known cache key for structure: {}", this.destination, cacheKey);
			StructureKeys.RegisterStructure(destination, cacheKey);
		}
		return cacheKey;
	}
}
