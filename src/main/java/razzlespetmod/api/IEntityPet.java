package razzlespetmod.api;

import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * This will be an interface for all pets, providing methods for current model, size, etc.
 *
 */
public interface IEntityPet {
	
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
	 * Returns the main model this pet will use for rendering; models should all be initialized
	 * and referenced statically to avoid creating new models each time
	 */
	@SideOnly(Side.CLIENT)
	public ModelBase getMainModel();
	
	/**
	 * Returns an alternate model for the render pass; used by pigs and wolves, for example,
	 * to render saddled / collared versions (I think...)
	 */
	@SideOnly(Side.CLIENT)
	public ModelBase getModelForRenderpass();

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
