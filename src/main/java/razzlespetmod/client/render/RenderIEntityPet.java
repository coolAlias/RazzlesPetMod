package razzlespetmod.client.render;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import razzlespetmod.api.IEntityPet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * Animations should be handled directly in the Model class using one or both of the following methods
 * from ModelBase:
 * {@link ModelBase#setRotationAngles}
 * {@link ModelBase#setLivingAnimations}
 *
 */
@SideOnly(Side.CLIENT)
public class RenderIEntityPet extends RenderLiving
{
	/** A list storing all possible models for this entity */
	private final List<ModelBase> models = new ArrayList<ModelBase>();
	/** Index of the current main model */
	int mainModelIndex = 0;
	/** Index of the model to be used for the current render pass, or -1 if no model is to be used */
	int renderModelIndex = -1;

	/**
	 * 
	 * @param model the main model; added automatically as the first entry in the model list
	 * @param shadowSize shadow size for the main model
	 * @param args any further models to be used should be passed here
	 */
	public RenderIEntityPet(ModelBase model, float shadowSize, Object... args) {
		super(model, shadowSize);
		models.add(model);
		for (Object o : args) {
			if (o instanceof ModelBase) {
				models.add((ModelBase) o);
			}
		}
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entity, float partialTick) {
		preRenderPet((IEntityPet) entity, partialTick);
	}

	protected void preRenderPet(IEntityPet pet, float partialTick) {
		/*
		mainModel = pet.getMainModel();
		this.shadowSize = pet.getCurrentShadowSize();
		this.renderPassModel = pet.getModelForRenderpass();
		*/
		this.mainModel = models.get(pet.getMainModelIndex() % models.size());
		int i = pet.getRenderPassModelIndex();
		this.renderPassModel = (i < 0 ? this.mainModel : models.get(i % models.size()));
		this.shadowSize = pet.getCurrentShadowSize();
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

	@Override
	protected float handleRotationFloat(EntityLivingBase entity, float partialTick) {
		if (((IEntityPet) entity).useDefaultRotationFloat()) {
			return (entity.ticksExisted + partialTick);
		} else {
			return ((IEntityPet) entity).getRotationFloat(partialTick);
		}
	}
}
