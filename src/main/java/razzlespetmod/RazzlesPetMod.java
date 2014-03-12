package razzlespetmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = RazzlesPetMod.MODID, version = RazzlesPetMod.VERSION)
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
		
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenderers();
	}
}
