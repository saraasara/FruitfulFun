package snownee.fruits.cherry.client.particle;

import java.util.stream.Stream;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RewindableStream;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PetalParticle extends TextureSheetParticle {

	private float angleStepX;
	private float angleStepZ;
	private Vector3f vForce;
	private float rollX;
	private float oRollX;
	private boolean inWater;

	private PetalParticle(ClientLevel world, double posX, double posY, double posZ) {
		super(world, posX, posY, posZ);
		this.lifetime = 100;
		age = random.nextInt(20);
		quadSize = 0.75f + random.nextFloat() * 0.25f;
		alpha = 0.7f + random.nextFloat() * 0.3f;

		float baseMotionX = 0.05f + random.nextFloat() * 0.02f;
		float baseMotionY = -0.1f;
		float baseMotionZ = 0.075f + random.nextFloat() * 0.02f;

		Vector3f motion = new Vector3f(baseMotionX, baseMotionY, baseMotionZ);
		motion.normalize();
		vForce = new Vector3f(baseMotionZ, 0, -baseMotionX);
		Quaternion rot = vForce.rotationDegrees(-90);
		vForce = motion.copy();
		vForce.transform(rot);
		motion.mul(0.1f + random.nextFloat() * 0.05f);

		this.xd = motion.x();
		this.yd = motion.y();
		this.zd = motion.z();

		angleStepX = 0.1f + random.nextFloat() * 0.1f;
		angleStepZ = 0.1f + random.nextFloat() * 0.1f;
	}

	public enum RenderType implements ParticleRenderType {
		INSTANCE;

		@SuppressWarnings("deprecation")
		@Override
		public void begin(BufferBuilder pBuilder, TextureManager pTextureManager) {
			RenderSystem.disableCull();
			RenderSystem.depthMask(true);
			RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			pBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
		}

		@Override
		public void end(Tesselator pTesselator) {
			pTesselator.end();
			RenderSystem.enableCull();
		}

		public String toString() {
			return "PARTICLE_SHEET_TRANSLUCENT_NO_CULL";
		}
	}

	@Override
	public ParticleRenderType getRenderType() {
		return RenderType.INSTANCE;
	}

	@Override
	public float getQuadSize(float pTicks) {
		if (lifetime - age < 10) {
			float f = Mth.sin((float) ((lifetime - age - pTicks) / 20 * Math.PI));
			f = Mth.clamp(f, 0, 1);
			return f;
		}
		return 1;
	}

	@Override
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		this.oRoll = this.roll;
		this.oRollX = this.rollX;

		if (this.age++ >= this.lifetime) {
			this.remove();
			return;
		}

		boolean lastOnGround = onGround;
		if (!onGround && !inWater) {
			float mul = age % 10 < 5 ? 0.03f : -0.03f;
			this.xd += vForce.x() * mul;
			this.yd += vForce.y() * mul;
			this.zd += vForce.z() * mul;
			roll += angleStepZ;
			rollX += angleStepX;
		}

		this.move(this.xd, this.yd, this.zd);

		if (this.onGround) {
			if (onGround && !lastOnGround) {
				age = lifetime - 20;
			}
			this.xd *= 0.5;
			this.zd *= 0.5;
		} else if (inWater) {
			this.xd *= 0.66;
			this.zd *= 0.66;
		} else {
			if (lastOnGround) {
				age = lifetime - 60;
			}
			this.xd *= 1.001;
			this.yd *= 0.998;
			this.zd *= 1.001;
		}
	}

	@Override
	public void move(double pX, double pY, double pZ) {
		//        if (!this.collidedY) {
		int ix = (int) x;
		int iy = (int) y;
		int iz = (int) z;
		BlockPos pos = new BlockPos(ix, iy, iz);
		LevelChunk chunk = level.getChunk(ix >> 4, iz >> 4);
		FluidState fluidState = chunk.getFluidState(ix, iy, iz);
		float height = fluidState.getHeight(level, pos);
		if (fluidState.is(FluidTags.WATER)) {
			if (!inWater && y <= height + iy) {
				inWater = true;
				age -= 60;
				yd = 0;
				yo = y = height + iy;
			}
			if (inWater) {
				Vec3 flow = fluidState.getFlow(level, pos);
				xd += flow.x * 0.02;
				zd += flow.z * 0.02;
				pX = xd;
				pZ = zd;
			}
		} else if (inWater) {
			//            inWater = false;
			//            yd = -0.1f;
		}

		double lastX = pX; // [d0]
		double lastY = pY; // [d1]
		double lastZ = pZ; // < Create variable 'lastZ' and assign it to 'z'.

		if (pX != 0.0D || pY != 0.0D || pZ != 0.0D) {
			Vec3 moveVec = Entity.collideBoundingBoxHeuristically((Entity) null, new Vec3(pX, pY, pZ), this.getBoundingBox(), this.level, CollisionContext.empty(), new RewindableStream<>(Stream.empty()));
			pX = moveVec.x;
			pY = moveVec.y;
			pZ = moveVec.z;
		}

		if (pX != 0.0D || pY != 0.0D || pZ != 0.0D) {
			if (inWater) {
				double targetY = height + iy;
				pY = Mth.clamp(targetY - y, -0.005, 0.02);
			}

			this.setBoundingBox(this.getBoundingBox().move(pX, pY, pZ));
			this.setLocationFromBoundingbox();
		}

		if (Math.abs(lastY) >= 1.0E-5F && Math.abs(pY) < 1.0E-5F) {
			this.stoppedByCollision = true;
		}

		if (!inWater) {
			this.onGround = lastY != pY && lastY < 0.0D;
		}
		if (lastX != pX) {
			this.xd = 0.0D;
		}

		if (lastZ != pZ) {
			this.zd = 0.0D;
		}

	}

	@Override
	public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
		Vec3 vec3d = renderInfo.getPosition();
		float f = (float) (Mth.lerp(partialTicks, this.xo, this.x) - vec3d.x());
		float f1 = (float) (Mth.lerp(partialTicks, this.yo, this.y) - vec3d.y());
		float f2 = (float) (Mth.lerp(partialTicks, this.zo, this.z) - vec3d.z());
		Quaternion quaternion = new Quaternion(renderInfo.rotation());

		float rx = Mth.lerp(partialTicks, this.oRollX, this.rollX);
		float rz = Mth.lerp(partialTicks, this.oRoll, this.roll);
		if (onGround || inWater) {
			quaternion = Vector3f.XP.rotationDegrees(90);
			if (inWater) {
				f1 += 0.16f + rz % 0.01;
			} else {
				f1 += 0.005f + rz % 0.01;
			}
		} else {
			quaternion = new Quaternion(renderInfo.rotation());
			quaternion.mul(Vector3f.XP.rotation(rx));
		}
		quaternion.mul(Vector3f.ZP.rotation(rz));

		Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
		vector3f1.transform(quaternion);
		Vector3f[] avector3f = new Vector3f[] { new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F) };
		float f4 = this.getQuadSize(partialTicks);
		float alpha = f4 * this.alpha;
		f4 *= quadSize * 0.15f;

		for (int i = 0; i < 4; ++i) {
			Vector3f vector3f = avector3f[i];
			vector3f.transform(quaternion);
			vector3f.mul(f4);
			vector3f.add(f, f1, f2);
		}

		float f7 = this.getU0();
		float f8 = this.getU1();
		float f5 = this.getV0();
		float f6 = this.getV1();
		int j = this.getLightColor(partialTicks);
		buffer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).uv(f8, f6).color(this.rCol, this.gCol, this.bCol, alpha).uv2(j).endVertex();
		buffer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).uv(f8, f5).color(this.rCol, this.gCol, this.bCol, alpha).uv2(j).endVertex();
		buffer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).uv(f7, f5).color(this.rCol, this.gCol, this.bCol, alpha).uv2(j).endVertex();
		buffer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).uv(f7, f6).color(this.rCol, this.gCol, this.bCol, alpha).uv2(j).endVertex();
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public Factory(SpriteSet sprite) {
			this.spriteSet = sprite;
		}

		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			PetalParticle noteparticle = new PetalParticle(worldIn, x, y, z);
			noteparticle.pickSprite(this.spriteSet);
			return noteparticle;
		}

	}
}
