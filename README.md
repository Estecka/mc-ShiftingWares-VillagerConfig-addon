# Shifting-Wares: VillagerConfig Addon

Compatibility addon that enables ShiftingWares rerolls to use the custom trades and layouts defined by VillagerConfig. With this addon, those trades accept additional data, next to the costs and result:
- `shiftingwares:isPersistent`: *(boolean)* Forcibly marks the trade as persistent. This trade's rerollability will be limited to the same conditions as for map trades.
- `shiftingwares:tradeId`: *(Identifier)* When defined, this allows ShiftingWares to associate an existing trade to its source in the datapack, and prevent duplicates of that trade from being generated.

### Example:
```json
{
	"shiftingwares:isPersistent": true,
	"shiftingwares:tradeId": "minecraft:on_ocean_explorer_maps",
	"cost_a": { "type": "minecraft:item", "name": "minecraft:emerald" },
	"cost_b": { "type": "minecraft:item", "name": "minecraft:compass" },
	"result": {
		"type": "minecraft:item",
		"name": "minecraft:map",
		"functions": [
			{
				"decoration": "minecraft:monument",
				"destination": "minecraft:on_ocean_explorer_maps",
				"function": "minecraft:exploration_map",
				"search_radius": 100
			}
		],
	}
}
```

This data will **not** be present by default when VillagerConfig generates data from vanilla trade, however its presence is not required for map trades to be handled properly. Setting the trade Id on map trades (or any persistent trade) is still recommended.
