package razzlespetmod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import razzlespetmod.entity.EntityRandomPet;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod(modid = RazzlesPetMod.MODID, version = RazzlesPetMod.VERSION)
@NetworkMod(clientSideRequired=true, serverSideRequired=true)
public class RazzlesPetMod
{
	@Instance(RazzlesPetMod.MODID)
	public static RazzlesPetMod instance;

	@SidedProxy(clientSide = RazzlesPetMod.MODID + ".ClientProxy", serverSide = RazzlesPetMod.MODID + ".CommonProxy")
	public static CommonProxy proxy;

	public static final String MODID = "razzlespetmod";
	public static final String VERSION = "0.1.0";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		registerEntities();
		proxy.registerRenderers();
	}

	protected void registerEntities() {
		int modEntityIndex = 0;
		EntityRegistry.registerModEntity(EntityRandomPet.class, "RandomPet", ++modEntityIndex, RazzlesPetMod.instance, 80, 3, false);
		registerEntityEgg(EntityRandomPet.class, getNextEggId(), 0x555555, 0x000000);
	}

	/**
	 * Registers a spawn egg to the entity
	 */
	protected void registerEntityEgg(Class<? extends Entity> entity, int id, int primaryColor, int secondaryColor){
		EntityList.IDtoClassMapping.put(id, entity);
		EntityList.entityEggs.put(id, new EntityEggInfo(id, primaryColor, secondaryColor));
	}

	/**
	 * Returns the next available egg id
	 */
	protected int getNextEggId() {
		int i = 0;
		while (EntityList.entityEggs.containsKey(i)) {
			++i;
		}
		return i;
	}
}
