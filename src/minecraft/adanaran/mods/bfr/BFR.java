package adanaran.mods.bfr;

import java.util.List;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.common.Configuration;
import adanaran.mods.bfr.blocks.BlockStove;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * 
 * @author Adanaran
 */
@Mod(modid = "bfr", name = "Better Food Recipes", version = "0.1")
public class BFR {

	// Mod Instace
	@Instance("bfr")
	public static BFR instance;

	// Proxy
	@SidedProxy(clientSide = "adanaran.mods.bfr.ClientProxy", serverSide = "adanaran.mods.bfr.Proxy")
	public static Proxy proxy;

	// Log
	public static Logger logger;

	// ID-Section
	public static int idBlockStove;

	// Block-Section
	public static BlockStove blockStove;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		Configuration cfg = new Configuration(
				event.getSuggestedConfigurationFile());
		try {
			cfg.load();
			// Enter Block and Item IDs here for config-values
			idBlockStove = cfg.getBlock("blockStove", 3010).getInt(3010);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cfg.save();
		}
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {
		// Items registrieren
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		registerStove();
		removeRecipe(new ItemStack(Item.bread));
	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		// Gut aussehen :P
		
	}
	
	private static void removeRecipe(ItemStack is){
		List<IRecipe> l = CraftingManager.getInstance().getRecipeList();
		for(int i=0;i<l.size();i++){
			IRecipe r = l.get(i);
			if (r instanceof ShapedRecipes){
				ShapedRecipes sr = (ShapedRecipes)r;
				ItemStack res = sr.getRecipeOutput();
				if(ItemStack.areItemStacksEqual(is, res)){
					l.remove(i--);
				}
			}
		}
	}

	// Registration Section

	private static void registerStove() {
		blockStove = new BlockStove(idBlockStove, false);
		blockStove.setUnlocalizedName("Stove");
		blockStove.setCreativeTab(CreativeTabs.tabDecorations);
		GameRegistry.registerBlock(blockStove, blockStove.getUnlocalizedName());
		GameRegistry.addRecipe(new ItemStack(blockStove), new Object[] { "CCC",
				"C C", "CCC", Character.valueOf('C'), Block.stone });
		LanguageRegistry.addName(blockStove, "Stove");
		
	}
}
