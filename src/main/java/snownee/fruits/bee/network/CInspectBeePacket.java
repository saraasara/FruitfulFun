package snownee.fruits.bee.network;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import snownee.fruits.bee.BeeAttributes;
import snownee.kiwi.network.KiwiPacket;
import snownee.kiwi.network.PacketHandler;

@KiwiPacket(value = "inspect_bee", dir = KiwiPacket.Direction.PLAY_TO_SERVER)
public class CInspectBeePacket extends PacketHandler {
	public static CInspectBeePacket I;

	@Override
	public CompletableFuture<FriendlyByteBuf> receive(Function<Runnable, CompletableFuture<FriendlyByteBuf>> executor, FriendlyByteBuf buf, @Nullable ServerPlayer player) {
		Objects.requireNonNull(player);
		int id = buf.readVarInt();
		return executor.apply(() -> {
			Entity entity = player.level().getEntity(id);
			if (entity instanceof Bee bee) {
				SInspectBeeReplyPacket.send(player, id, BeeAttributes.of(bee));
			}
		});
	}
}
