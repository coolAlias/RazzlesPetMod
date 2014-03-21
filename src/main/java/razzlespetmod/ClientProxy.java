package razzlespetmod;

import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelPig;
import razzlespetmod.client.render.RenderIEntityPet;
import razzlespetmod.entity.EntityRandomPet;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	public ClientProxy() {
		RenderingRegistry.registerEntityRenderingHandler(EntityRandomPet.class, new RenderIEntityPet(new ModelChicken(), 0.5F, new ModelCow(), new ModelPig(), new ModelPig(0.5F)));
	}
}
