package snownee.fruits.food;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import snownee.fruits.FruitsMod;
import snownee.fruits.Hooks;
import snownee.fruits.food.datagen.FoodBlockLoot;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.datagen.provider.KiwiLootTableProvider;

@KiwiModule("food")
@KiwiModule.Optional
@KiwiModule.Category("food")
public class FoodModule extends AbstractModule {

	public static final class Foods {
		private static Supplier<MobEffectInstance> makeSupplier(String id, int duration, int amplifier) {
			return Suppliers.memoize(() -> {
				MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(id));
				if (effect == null) {
					return null;
				}
				return new MobEffectInstance(effect, duration, amplifier);
			});
		}

		private static final Supplier<MobEffectInstance> NOURISHED_PROVIDER = makeSupplier("farmersdelight:nourished", 6000, 0);
		private static final Supplier<MobEffectInstance> COMFORT_PROVIDER = makeSupplier("farmersdelight:comfort", 3600, 0);
		private static final Supplier<MobEffectInstance> REGENERATION_PROVIDER = makeSupplier("regeneration", 120, 0);
		private static final Supplier<MobEffectInstance> SPEED_PROVIDER = makeSupplier("speed", 1200, 0);
	}

	public static final TagKey<Item> PANDA_FOOD = itemTag(FruitsMod.ID, "panda_food");

	/* off */
	public static Item.Properties GRAPEFRUIT_PANNA_COTTA_PROP = itemProp().food(new FoodProperties.Builder()
			.nutrition(14)
			.saturationMod(1)
			.effect(Foods.SPEED_PROVIDER, 1)
			.build()
	);
	public static final KiwiGO<Block> GRAPEFRUIT_PANNA_COTTA = go(() -> new FoodBlock(Shapes.block()));
	public static Item.Properties DONAUWELLE_PROP = itemProp().food(new FoodProperties.Builder()
			.nutrition(14)
			.saturationMod(1)
			.effect(Foods.REGENERATION_PROVIDER, 1)
			.build()
	);
	public static final KiwiGO<Block> DONAUWELLE = go(() -> new FoodBlock(Shapes.block()));
	public static Item.Properties HONEY_POMELO_TEA_PROP = itemProp().food(new FoodProperties.Builder()
			.nutrition(1)
			.saturationMod(Hooks.farmersdelight ? 0.3F : 4)
			.effect(Foods.COMFORT_PROVIDER, 1)
			.alwaysEat()
			.build()
	).craftRemainder(Items.GLASS_BOTTLE);
	public static final KiwiGO<Block> HONEY_POMELO_TEA = go(() -> new FoodBlock(Shapes.block()));
	public static Item.Properties RICE_WITH_FRUITS_PROP = itemProp().food(new FoodProperties.Builder()
			.nutrition(9)
			.saturationMod(0.6F)
			.effect(Foods.COMFORT_PROVIDER, 1)
			.build()
	);
	public static final KiwiGO<Block> RICE_WITH_FRUITS = go(() -> new FoodBlock(Shapes.block()));
	public static final KiwiGO<Item> LEMON_ROAST_CHICKEN = go(() -> new FoodItem(itemProp().food(new FoodProperties.Builder()
			.nutrition(12)
			.saturationMod(0.9F)
			.effect(Foods.NOURISHED_PROVIDER, 1)
			.build()
	).craftRemainder(Items.BOWL)));
	public static Item.Properties LEMON_ROAST_CHICKEN_PROP = itemProp().craftRemainder(Items.BOWL);
	public static final KiwiGO<Block> LEMON_ROAST_CHICKEN_BLOCK = go(() -> new FeastBlock(Shapes.block(), LEMON_ROAST_CHICKEN));
	/* on */

	public FoodModule() {
		Hooks.food = true;
	}

	@Override
	protected void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		generator.addProvider(event.includeServer(), new KiwiLootTableProvider(generator).add(FoodBlockLoot::new, LootContextParamSets.BLOCK));
	}

}
