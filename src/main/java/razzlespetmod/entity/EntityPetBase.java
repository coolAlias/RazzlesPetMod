package razzlespetmod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import razzlespetmod.api.IEntityPet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * Base pet class provides bare minimum of model-changing functionality and basic
 * interactions such as sitting.
 *
 */
public abstract class EntityPetBase extends EntityTameable implements IEntityPet
{
	/** DataWatcher slot for current model index */
	protected static final int MODEL_WATCHER = 22;

	public EntityPetBase(World world) {
		super(world);
	}

	/** Returns the index of the current model */
	protected int getCurrentModelIndex() {
		return dataWatcher.getWatchableObjectInt(MODEL_WATCHER);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getMainModelIndex() {
		return getCurrentModelIndex();
	}

	/**
	 * Returns the total number of models available; should always be at least 1 and should
	 * be the same as the number of models passed to the RenderIEntityPet constructor
	 */
	protected abstract int getNumberModels();

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(MODEL_WATCHER, 0);
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean useDefaultRotationFloat() {
		return true;
	}

	@Override
	public void changeModel() {
		dataWatcher.updateObject(MODEL_WATCHER, (getCurrentModelIndex() + 1) % getNumberModels());
		setSize(getCurrentHeight(), getCurrentWidth());
	}

	@Override
	public boolean isPetItem(ItemStack stack) {
		return stack != null && stack.getItem() == Item.stick;
	}

	@Override
	public boolean interact(EntityPlayer player) {
		if (isTamed()) {
			if (player.getCommandSenderName().equalsIgnoreCase(getOwnerName())) {
				ItemStack stack = player.getHeldItem();
				if (isPetItem(stack)) {
					changeModel();
					return true;
				} else if ((stack == null || !isBreedingItem(stack)) && !worldObj.isRemote) {
					aiSit.setSitting(!isSitting());
					isJumping = false;
					setPathToEntity(null);
					setTarget(null);
					setAttackTarget(null);
				}
			}
		} else {
			if (!worldObj.isRemote) {
				setTamed(true);
				setPathToEntity(null);
				setAttackTarget(null);
				aiSit.setSitting(true);
				setHealth(getMaxHealth());
				setOwner(player.getCommandSenderName());
				playTameEffect(true);
				worldObj.setEntityState(this, (byte) 7);
			}
			return true;
		}
		return super.interact(player);
	}

	@Override
	public Entity getOwner() {
		return super.func_130012_q();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("CurrentModel", getCurrentModelIndex());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		dataWatcher.updateObject(MODEL_WATCHER, compound.getInteger("CurrentModel"));
	}
}
