package razzlespetmod.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import razzlespetmod.api.IEntityPet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderIEntityPet extends RenderLiving {

	public RenderIEntityPet(ModelBase model, float shadowSize) {
		super(model, shadowSize);
	}
	
	@Override
	protected void preRenderCallback(EntityLivingBase entity, float partialTick) {
		preRenderPet((IEntityPet) entity, partialTick);
	}
	
	protected void preRenderPet(IEntityPet pet, float partialTick) {
		mainModel = pet.getMainModel();
		this.renderPassModel = pet.getModelForRenderpass();
		float scale = pet.getRenderScale();
		GL11.glScalef(scale, scale, scale);
	}
	
	@Override
	protected int shouldRenderPass(EntityLivingBase entity, int renderPass, float partialTick) {
		return shouldRenderPet((IEntityPet) entity, renderPass, partialTick);
	}
	
	protected int shouldRenderPet(IEntityPet pet, int renderPass, float partialTick) {
		if (pet.getTexture(renderPass, partialTick) != null) {
			bindTexture(pet.getTexture(renderPass, partialTick));
		}
		return pet.shouldRender(renderPass, partialTick);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ((IEntityPet) entity).getTexture();
	}
}
