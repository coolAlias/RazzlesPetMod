package razzlespetmod.api;

import net.minecraft.entity.EntityOwnable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * This will be an interface for all pets, providing methods for current model, size, etc.
 *
 */
public interface IEntityPet extends EntityOwnable {

	/**
	 * Return true if this itemstack can be used to swap models when interacting with the pet
	 */
	public boolean isPetItem(ItemStack stack);

	/**
	 * Called upon interacting with a pet item; should change the current model - recommended to
	 * use DataWatcher to store the current model data for easy server-client synchronization
	 * Rescales the entity's hit box based on the new height and width for the current model
	 */
	public void changeModel();

	/**
	 * Returns the current height for the entity; used to rescale the hit-box upon changing models 
	 */
	public float getCurrentHeight();

	/**
	 * Returns the current width for the entity; used to rescale the hit-box upon changing models 
	 */
	public float getCurrentWidth();

	/**
	 * Returns the current shadow size for rendering
	 */
	@SideOnly(Side.CLIENT)
	public float getCurrentShadowSize();

	/**
	 * Whether the entity should render this pass; this can also be used to set various
	 * openGL settings, such as color, alpha blend, etc.
	 * @return 0 will not render (invisible), 1 will render,
	 * -1 seems to also be used to not render, but in a different context from 0
	 */
	@SideOnly(Side.CLIENT)
	public int shouldRender(int renderPass, float partialTick);

	/**
	 * The scale at which to render the model
	 */
	@SideOnly(Side.CLIENT)
	public float getRenderScale();

	/**
	 * Whether this pet should use the default rotation float defined in RendererLivingEntity
	 * or will define the rotation float itself using getRotationFloat
	 * @return true to use the default value
	 */
	@SideOnly(Side.CLIENT)
	public boolean useDefaultRotationFloat();

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is; wolves, for
	 * example, return the current tail rotation for use in the Model class
	 * NOTE that this is only used if useDefaultRotationFloat returns false
	 * @param partialTick TODO pretty sure this parameter is partialTick
	 */
	@SideOnly(Side.CLIENT)
	public float getRotationFloat(float partialTick);

	/**
	 * Returns the index of the main model currently in use; indices are based on the order in
	 * which models were added to the RenderIEntityPet constructor
	 */
	@SideOnly(Side.CLIENT)
	public int getMainModelIndex();

	/**
	 * Returns the index of the model to be used for the current render pass, if any; indices are
	 * based on the order in which models were added to the RenderIEntityPet constructor
	 * Pigs and wolves, for example, return an alternate model to render saddled / collared versions
	 * @return -1 to not use any alternate model
	 */
	@SideOnly(Side.CLIENT)
	public int getRenderPassModelIndex();

	/**
	 * Returns the texture to be used by this pet; this should be done using pre-initialized
	 * static ResourceLocations, not by creating a new location each time
	 */
	@SideOnly(Side.CLIENT)
	public ResourceLocation getTexture();

	/**
	 * Can return an alternate texture based on render pass and partial tick, or null if the
	 * main texture should be used. Pigs, for example, use this to render the saddled texture
	 * instead of the main texture during pass 0.
	 */
	@SideOnly(Side.CLIENT)
	public ResourceLocation getTexture(int renderPass, float partialTick);

}
