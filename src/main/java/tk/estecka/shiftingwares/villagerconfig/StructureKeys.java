package tk.estecka.shiftingwares.villagerconfig;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.gen.structure.Structure;

public class StructureKeys 
{
	static private final Map<TagKey<Structure>, String> STRUCT_TO_NAME = new HashMap<>();

	static {
		// Initializes STRUCT_TO_NAME via the initialization of SellMapFactories.
		new TradeOffers();
	}

	static public void	RegisterStructure(TagKey<Structure> structure, @NotNull String cacheKey){
		if (cacheKey.equals(STRUCT_TO_NAME.get(structure)))
			SwVcAddon.LOGGER.warn("Duplicate map registration for {} @ {}", structure, cacheKey);
		else if (STRUCT_TO_NAME.containsKey(structure))
			SwVcAddon.LOGGER.error("Overwriting registration for {} @ {} => {}", structure, STRUCT_TO_NAME.get(structure), cacheKey);

		STRUCT_TO_NAME.put(structure, cacheKey);
	}

	static public String	GetCacheKey(TagKey<Structure> structure) {
		return STRUCT_TO_NAME.get(structure);
	}

}
