package razzlespetmod.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * An example pet showing off the model-changing capabilities
 *
 */
public class EntityRandomPet extends EntityPetBase
{
	private static final ResourceLocation chickenTextures = new ResourceLocation("textures/entity/chicken.png");
	private static final ResourceLocation cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
	private static final ResourceLocation pigTextures = new ResourceLocation("textures/entity/pig/pig.png");
	private static final ResourceLocation saddledPigTextures = new ResourceLocation("textures/entity/pig/pig_saddle.png");

	public EntityRandomPet(World world) {
		super(world);
		this.setSize(0.3F, 0.7F); // start off as a chicken
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(3, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(4, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.30000001192092896D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(20.0D);
	}

	@Override
	public float getCurrentHeight() {
		switch(getCurrentModelIndex()) {
		case 0: return 0.3F;
		case 1: return 0.9F;
		default: return 0.9F;
		}
	}

	@Override
	public float getCurrentWidth() {
		switch(getCurrentModelIndex()) {
		case 0: return 0.7F;
		case 1: return 1.3F;
		default: return 0.9F;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getCurrentShadowSize() {
		switch(getCurrentModelIndex()) {
		case 0: return 0.3F;
		default: return 0.7F;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int shouldRender(int renderPass, float partialTick) {
		// some 'fancy' stuff for the saddled pig, modified from RenderPig's shouldRenderPass code
		return (getCurrentModelIndex() == 3 ? (renderPass == 0 ? 1 : -1) : 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getRenderScale() {
		return 1.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getRotationFloat(float partialTick) {
		return 0; // not used for this example: using default rotation float instead
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getMainModelIndex() {
		// main pig model remains the same for both saddled and unsaddled
		// obviously this is just for demonstration; there would be better ways to accomplish this
		return (getCurrentModelIndex() == 3 ? 2 : getCurrentModelIndex());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPassModelIndex() {
		return (getCurrentModelIndex() == 3 ? 3 : -1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getTexture() {
		switch(getCurrentModelIndex()) {
		case 0: return chickenTextures;
		case 1: return cowTextures;
		// this fails to render the pig when saddled; thus the renderPass version below
		// just use the regular pig texture in general for both saddled and unsaddled versions
		// rather than trying to return the saddled texture here
		
		// Keep in mind that it just LOOKS like a saddled pig - it cannot be ridden or otherwise
		// treated like a pig
		default: return pigTextures;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getTexture(int renderPass, float partialTick) {
		return (getCurrentModelIndex() == 3 && renderPass == 0 ? saddledPigTextures : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected int getNumberModels() {
		return 4;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entity) {
		EntityRandomPet pet = new EntityRandomPet(worldObj);
		if (getOwnerName() != null && getOwnerName().trim().length() > 0) {
			pet.setTamed(true);
			pet.setOwner(getOwnerName());
		}
		return pet;
	}
}
