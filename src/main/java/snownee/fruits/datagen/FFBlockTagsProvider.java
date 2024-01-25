package snownee.fruits.datagen;

import static snownee.fruits.CoreModule.ALL_LEAVES;
import static snownee.fruits.CoreModule.APPLE_LEAVES;
import static snownee.fruits.CoreModule.APPLE_SAPLING;
import static snownee.fruits.CoreModule.CITRON_LEAVES;
import static snownee.fruits.CoreModule.CITRON_SAPLING;
import static snownee.fruits.CoreModule.CITRUS_BUTTON;
import static snownee.fruits.CoreModule.CITRUS_DOOR;
import static snownee.fruits.CoreModule.CITRUS_FENCE;
import static snownee.fruits.CoreModule.CITRUS_FENCE_GATE;
import static snownee.fruits.CoreModule.CITRUS_HANGING_SIGN;
import static snownee.fruits.CoreModule.CITRUS_LOG;
import static snownee.fruits.CoreModule.CITRUS_PLANKS;
import static snownee.fruits.CoreModule.CITRUS_PRESSURE_PLATE;
import static snownee.fruits.CoreModule.CITRUS_SIGN;
import static snownee.fruits.CoreModule.CITRUS_SLAB;
import static snownee.fruits.CoreModule.CITRUS_STAIRS;
import static snownee.fruits.CoreModule.CITRUS_TRAPDOOR;
import static snownee.fruits.CoreModule.CITRUS_WALL_HANGING_SIGN;
import static snownee.fruits.CoreModule.CITRUS_WALL_SIGN;
import static snownee.fruits.CoreModule.CITRUS_WOOD;
import static snownee.fruits.CoreModule.GRAPEFRUIT_LEAVES;
import static snownee.fruits.CoreModule.GRAPEFRUIT_SAPLING;
import static snownee.fruits.CoreModule.LEMON_LEAVES;
import static snownee.fruits.CoreModule.LEMON_SAPLING;
import static snownee.fruits.CoreModule.LIME_LEAVES;
import static snownee.fruits.CoreModule.LIME_SAPLING;
import static snownee.fruits.CoreModule.ORANGE_LEAVES;
import static snownee.fruits.CoreModule.ORANGE_SAPLING;
import static snownee.fruits.CoreModule.POMELO_LEAVES;
import static snownee.fruits.CoreModule.POMELO_SAPLING;
import static snownee.fruits.CoreModule.POTTED_APPLE;
import static snownee.fruits.CoreModule.POTTED_CITRON;
import static snownee.fruits.CoreModule.POTTED_GRAPEFRUIT;
import static snownee.fruits.CoreModule.POTTED_LEMON;
import static snownee.fruits.CoreModule.POTTED_LIME;
import static snownee.fruits.CoreModule.POTTED_ORANGE;
import static snownee.fruits.CoreModule.POTTED_POMELO;
import static snownee.fruits.CoreModule.POTTED_TANGERINE;
import static snownee.fruits.CoreModule.STRIPPED_CITRUS_LOG;
import static snownee.fruits.CoreModule.STRIPPED_CITRUS_WOOD;
import static snownee.fruits.CoreModule.TANGERINE_LEAVES;
import static snownee.fruits.CoreModule.TANGERINE_SAPLING;
import static snownee.fruits.cherry.CherryModule.CHERRY_LEAVES;
import static snownee.fruits.cherry.CherryModule.CHERRY_SAPLING;
import static snownee.fruits.cherry.CherryModule.PEACH_PINK_PETALS;
import static snownee.fruits.cherry.CherryModule.POTTED_CHERRY;
import static snownee.fruits.cherry.CherryModule.POTTED_REDLOVE;
import static snownee.fruits.cherry.CherryModule.REDLOVE_BUTTON;
import static snownee.fruits.cherry.CherryModule.REDLOVE_DOOR;
import static snownee.fruits.cherry.CherryModule.REDLOVE_FENCE;
import static snownee.fruits.cherry.CherryModule.REDLOVE_FENCE_GATE;
import static snownee.fruits.cherry.CherryModule.REDLOVE_HANGING_SIGN;
import static snownee.fruits.cherry.CherryModule.REDLOVE_LEAVES;
import static snownee.fruits.cherry.CherryModule.REDLOVE_LOG;
import static snownee.fruits.cherry.CherryModule.REDLOVE_PLANKS;
import static snownee.fruits.cherry.CherryModule.REDLOVE_PRESSURE_PLATE;
import static snownee.fruits.cherry.CherryModule.REDLOVE_SAPLING;
import static snownee.fruits.cherry.CherryModule.REDLOVE_SIGN;
import static snownee.fruits.cherry.CherryModule.REDLOVE_SLAB;
import static snownee.fruits.cherry.CherryModule.REDLOVE_SLIDING_DOOR;
import static snownee.fruits.cherry.CherryModule.REDLOVE_STAIRS;
import static snownee.fruits.cherry.CherryModule.REDLOVE_TRAPDOOR;
import static snownee.fruits.cherry.CherryModule.REDLOVE_WALL_HANGING_SIGN;
import static snownee.fruits.cherry.CherryModule.REDLOVE_WALL_SIGN;
import static snownee.fruits.cherry.CherryModule.REDLOVE_WOOD;
import static snownee.fruits.cherry.CherryModule.STRIPPED_REDLOVE_LOG;
import static snownee.fruits.cherry.CherryModule.STRIPPED_REDLOVE_WOOD;
import static snownee.fruits.compat.farmersdelight.FarmersDelightModule.CITRUS_CABINET;
import static snownee.fruits.compat.farmersdelight.FarmersDelightModule.REDLOVE_CABINET;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import snownee.fruits.CoreModule;
import snownee.fruits.FruitfulFun;
import snownee.fruits.Hooks;
import snownee.fruits.vacuum.VacModule;
import snownee.kiwi.AbstractModule;

public class FFBlockTagsProvider extends FabricTagProvider.BlockTagProvider {
	static final TagKey<Block> CITRUS_LOGS = AbstractModule.blockTag(FruitfulFun.ID, "citrus_logs");
	static final TagKey<Block> REDLOVE_LOGS = AbstractModule.blockTag(FruitfulFun.ID, "redlove_logs");

	public FFBlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		getOrCreateTagBuilder(CITRUS_LOGS).add(
				CITRUS_LOG.get(),
				CITRUS_WOOD.get(),
				STRIPPED_CITRUS_LOG.get(),
				STRIPPED_CITRUS_WOOD.get());
		getOrCreateTagBuilder(REDLOVE_LOGS).add(
				REDLOVE_LOG.get(),
				REDLOVE_WOOD.get(),
				STRIPPED_REDLOVE_LOG.get(),
				STRIPPED_REDLOVE_WOOD.get());
		tag(BlockTags.LOGS_THAT_BURN).addTag(CITRUS_LOGS).addTag(REDLOVE_LOGS);
		getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(CITRUS_FENCE.get(), REDLOVE_FENCE.get());
		getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(CITRUS_FENCE_GATE.get(), REDLOVE_FENCE_GATE.get());
		getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(CITRUS_BUTTON.get(), REDLOVE_BUTTON.get());
		getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(CITRUS_SLAB.get(), REDLOVE_SLAB.get());
		getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(CITRUS_STAIRS.get(), REDLOVE_STAIRS.get());
		getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(CITRUS_PRESSURE_PLATE.get(), REDLOVE_PRESSURE_PLATE.get());
		getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(CITRUS_TRAPDOOR.get(), REDLOVE_TRAPDOOR.get());
		getOrCreateTagBuilder(BlockTags.PLANKS).add(CITRUS_PLANKS.get(), REDLOVE_PLANKS.get());
		getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(CITRUS_WALL_SIGN.get(), REDLOVE_WALL_SIGN.get());
		getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(CITRUS_SIGN.get(), REDLOVE_SIGN.get());
		getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(CITRUS_WALL_HANGING_SIGN.get(), REDLOVE_WALL_HANGING_SIGN.get());
		getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(CITRUS_HANGING_SIGN.get(), REDLOVE_HANGING_SIGN.get());
		getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(CITRUS_DOOR.get(), REDLOVE_DOOR.get(), REDLOVE_SLIDING_DOOR.get());
		getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(
				POTTED_APPLE.get(),
				POTTED_CITRON.get(),
				POTTED_GRAPEFRUIT.get(),
				POTTED_LEMON.get(),
				POTTED_LIME.get(),
				POTTED_TANGERINE.get(),
				POTTED_ORANGE.get(),
				POTTED_POMELO.get(),
				POTTED_REDLOVE.get(),
				POTTED_CHERRY.get());
		getOrCreateTagBuilder(BlockTags.SAPLINGS).add(
				APPLE_SAPLING.get(),
				CITRON_SAPLING.get(),
				GRAPEFRUIT_SAPLING.get(),
				LEMON_SAPLING.get(),
				LIME_SAPLING.get(),
				TANGERINE_SAPLING.get(),
				ORANGE_SAPLING.get(),
				POMELO_SAPLING.get(),
				CHERRY_SAPLING.get(),
				REDLOVE_SAPLING.get());
		getOrCreateTagBuilder(ALL_LEAVES).add(
				APPLE_LEAVES.get(),
				CITRON_LEAVES.get(),
				GRAPEFRUIT_LEAVES.get(),
				LEMON_LEAVES.get(),
				LIME_LEAVES.get(),
				TANGERINE_LEAVES.get(),
				ORANGE_LEAVES.get(),
				POMELO_LEAVES.get(),
				CHERRY_LEAVES.get(),
				REDLOVE_LEAVES.get());
		tag(BlockTags.LEAVES).addTag(ALL_LEAVES);
		getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_HOE).addTag(ALL_LEAVES);
		getOrCreateTagBuilder(BlockTags.FLOWERS).add(
				REDLOVE_LEAVES.get(),
				CHERRY_SAPLING.get(),
				PEACH_PINK_PETALS.get());
		getOrCreateTagBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(PEACH_PINK_PETALS.get());
		getOrCreateTagBuilder(CoreModule.CANDLES)
				.addOptionalTag(BlockTags.CANDLES.location())
				.addOptionalTag(BlockTags.CANDLE_CAKES.location())
				.addOptionalTag(new ResourceLocation("supplementaries:candle_holders"));
		getOrCreateTagBuilder(VacModule.VCD_PERFORM_USING)
				.addTag(ALL_LEAVES)
				.addOptionalTag(BlockTags.CAVE_VINES.location())
				.add(Blocks.SWEET_BERRY_BUSH);
		getOrCreateTagBuilder(VacModule.VCD_PERFORM_BREAKING)
				.add(Blocks.COCOA);

		if (Hooks.farmersdelight) {
			getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
					.addOptional(CITRUS_CABINET.key())
					.addOptional(REDLOVE_CABINET.key());
		}
	}
}
