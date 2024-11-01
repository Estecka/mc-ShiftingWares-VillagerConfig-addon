package tk.estecka.shiftingwares.villagerconfig.mixin;

import java.util.Optional;
import java.util.function.Function;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.drex.villagerconfig.data.BehaviorTrade;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import tk.estecka.shiftingwares.ShiftingTradeData;
import tk.estecka.shiftingwares.api.IShiftingTradeFactory;

@Unique
@Mixin(BehaviorTrade.class)
public abstract class BehaviorTradeMixin
implements IShiftingTradeFactory
{
	private boolean isPersistent = false;
	private Identifier tradeId = null;

	@Override public boolean shiftingwares$IsItemPersistent(){ return this.isPersistent; }
	@Override public Identifier shiftingwares$GetTradeId(){ return this.tradeId; }

	@ModifyExpressionValue( method="<clinit>", remap=false, at=@At(value="INVOKE", target="com/mojang/serialization/codecs/RecordCodecBuilder.create(Ljava/util/function/Function;)Lcom/mojang/serialization/Codec;") )
	static private Codec<BehaviorTrade> AddShiftingData(Codec<BehaviorTrade> original){
		return RecordCodecBuilder.create(builder -> 
			builder.group(
				MapCodec.assumeMapUnsafe(original).forGetter(Function.identity()),
				Identifier.CODEC.optionalFieldOf("shiftingwares:tradeId").forGetter( trade->Optional.ofNullable(IShiftingTradeFactory.Of(trade).shiftingwares$GetTradeId()) ),
				Codec.BOOL.fieldOf("shiftingwares:isPersistent").orElse(false).forGetter( trade->IShiftingTradeFactory.Of(trade).shiftingwares$IsItemPersistent() )
			)
			.apply(builder, BehaviorTradeMixin::ShiftingDataInit)
		);
	}

	static private BehaviorTrade ShiftingDataInit(BehaviorTrade original, Optional<Identifier> tradeId, boolean isPersistent){
		BehaviorTradeMixin originalMixin = (BehaviorTradeMixin)(Object)original;
		originalMixin.isPersistent = isPersistent;
		originalMixin.tradeId = tradeId.orElse(null);
		return original;
	}

}
