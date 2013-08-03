package adanaran.mods.bfr;

import java.util.List;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.common.Configuration;
import adanaran.mods.bfr.blocks.BlockStove;
import adanaran.mods.bfr.entities.TileEntityStove;
import adanaran.mods.bfr.items.ItemCakePan;
import adanaran.mods.bfr.items.ItemPan;
import adanaran.mods.bfr.items.ItemPot;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * 
 * @author Adanaran
 */
@Mod(modid = "bfr", name = "Better Food Recipes", version = "0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
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
	public static int idBlockStoveActive;
	public static int idItemPot;
	public static int idItemPotStone;
	public static int idItemPotGold;
	public static int idItemPotDiamond;
	public static int idItemPan;
	public static int idItemPanStone;
	public static int idItemPanGold;
	public static int idItemPanDiamond;
	public static int idItemCakePan;
	public static int idItemCakePanStone;
	public static int idItemCakePanGold;
	public static int idItemCakePanDiamond;

	// Block-Section
	public static BlockStove blockStove;
	public static BlockStove blockStoveOn;
	
	// Item-Section
	public static ItemPot itemPot;
	public static ItemPot itemPotStone;
	public static ItemPot itemPotGold;
	public static ItemPot itemPotDiamond;
	public static ItemPan itemPan;
	public static ItemPan itemPanStone;
	public static ItemPan itemPanGold;
	public static ItemPan itemPanDiamond;
	public static ItemCakePan itemCakePan;
	public static ItemCakePan itemCakePanStone;
	public static ItemCakePan itemCakePanGold;
	public static ItemCakePan itemCakePanDiamond;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		Configuration cfg = new Configuration(
				event.getSuggestedConfigurationFile());
		try {
			cfg.load();
			// Block
  			idBlockStove = cfg.getBlock("blockStove", 3010).getInt(3010);
  			idBlockStoveActive = cfg.getBlock("blockStoveOn", 3011).getInt(3011);
  
 			// Item
 			idItemPot 			= cfg.getItem("itemPot", 3850).getInt(3850);
 			idItemPotStone 		= cfg.getItem("itemPotStone", 3851).getInt(3851);
 			idItemPotGold 		= cfg.getItem("itemPotGold", 3852).getInt(3852);
 			idItemPotDiamond 	= cfg.getItem("itemPotDiamond", 3853).getInt(3853);
 			idItemPan 			= cfg.getItem("itemPan", 3854).getInt(3854);
 			idItemPanStone		= cfg.getItem("itemPanStone", 3855).getInt(3855);
 			idItemPanGold 		= cfg.getItem("itemPanGold", 3856).getInt(3856);
 			idItemPanDiamond 	= cfg.getItem("itemPanDiamond", 3857).getInt(3857);
 			idItemCakePan		= cfg.getItem("itemCakePan", 3858).getInt(3858);
 			idItemCakePanStone 	= cfg.getItem("itemCakePanStone", 3859).getInt(3859);
 			idItemCakePanGold 	= cfg.getItem("itemCakePanGold", 3860).getInt(3860);
 			idItemCakePanDiamond = cfg.getItem("itemCakePanDiamond", 3861).getInt(3861);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {
		// Items registrieren
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		registerStove();
		registerCookware();
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
		blockStoveOn = new BlockStove(idBlockStoveActive, true);
		blockStove.setUnlocalizedName("Stove");
		blockStoveOn.setUnlocalizedName("activeStove");
		blockStove.setCreativeTab(CreativeTabs.tabDecorations);
		GameRegistry.registerBlock(blockStove, blockStove.getUnlocalizedName());
		GameRegistry.registerBlock(blockStoveOn, blockStoveOn.getUnlocalizedName());
		GameRegistry.addRecipe(new ItemStack(blockStove), new Object[] { "CCC",
				"C C", "CCC", Character.valueOf('C'), Block.stone });
		LanguageRegistry.addName(blockStove, "Stove");
		
		GameRegistry.registerTileEntity(TileEntityStove.class, "TileEntityStove");
	}

	private static void registerCookware() {
		// TODO Rezepte
		itemPot = new ItemPot(idItemPot, EnumToolMaterial.IRON);
		itemPotStone = new ItemPot(idItemPotStone, EnumToolMaterial.STONE);
		itemPotGold = new ItemPot(idItemPotGold, EnumToolMaterial.GOLD);
		itemPotDiamond = new ItemPot(idItemPotDiamond, EnumToolMaterial.EMERALD);
		itemPot.setUnlocalizedName("Pot");
		itemPotStone.setUnlocalizedName("Stonepot");
		itemPotGold.setUnlocalizedName("Goldpot");
		itemPotDiamond.setUnlocalizedName("Diamondpot");
		itemPot.setCreativeTab(CreativeTabs.tabTools);
		itemPotStone.setCreativeTab(CreativeTabs.tabTools);
		itemPotGold.setCreativeTab(CreativeTabs.tabTools);
		itemPotDiamond.setCreativeTab(CreativeTabs.tabTools);
		GameRegistry.registerItem(itemPot, itemPot.getUnlocalizedName());
		GameRegistry.registerItem(itemPotStone, itemPotStone.getUnlocalizedName());
		GameRegistry.registerItem(itemPotGold, itemPotGold.getUnlocalizedName());
		GameRegistry.registerItem(itemPotDiamond, itemPotDiamond.getUnlocalizedName());

		LanguageRegistry.addName(itemPot, "Pot");
		LanguageRegistry.addName(itemPotStone, "Stonepot");
		LanguageRegistry.addName(itemPotGold, "Goldpot");
		LanguageRegistry.addName(itemPotDiamond, "Diamondpot");
		
		itemPan = new ItemPan(idItemPan, EnumToolMaterial.IRON);
		itemPanStone = new ItemPan(idItemPanStone, EnumToolMaterial.STONE);
		itemPanGold = new ItemPan(idItemPanGold, EnumToolMaterial.GOLD);
		itemPanDiamond = new ItemPan(idItemPanDiamond, EnumToolMaterial.EMERALD);
		itemPan.setUnlocalizedName("Pan");
		itemPanStone.setUnlocalizedName("Stonepan");
		itemPanGold.setUnlocalizedName("Goldpan");
		itemPanDiamond.setUnlocalizedName("Diamondpan");
		itemPan.setCreativeTab(CreativeTabs.tabTools);
		itemPanStone.setCreativeTab(CreativeTabs.tabTools);
		itemPanGold.setCreativeTab(CreativeTabs.tabTools);
		itemPanDiamond.setCreativeTab(CreativeTabs.tabTools);
		GameRegistry.registerItem(itemPan, itemPan.getUnlocalizedName());
		GameRegistry.registerItem(itemPanStone, itemPanStone.getUnlocalizedName());
		GameRegistry.registerItem(itemPanGold, itemPanGold.getUnlocalizedName());
		GameRegistry.registerItem(itemPanDiamond, itemPanDiamond.getUnlocalizedName());

		LanguageRegistry.addName(itemPan, "Pan");
		LanguageRegistry.addName(itemPanStone, "Stonepan");
		LanguageRegistry.addName(itemPanGold, "Goldpan");
		LanguageRegistry.addName(itemPanDiamond, "Diamondpan");
		
		itemCakePan = new ItemCakePan(idItemCakePan, EnumToolMaterial.IRON);
		itemCakePanStone = new ItemCakePan(idItemCakePanStone, EnumToolMaterial.STONE);
		itemCakePanGold = new ItemCakePan(idItemCakePanGold, EnumToolMaterial.GOLD);
		itemCakePanDiamond = new ItemCakePan(idItemCakePanDiamond, EnumToolMaterial.EMERALD);
		itemCakePan.setUnlocalizedName("Cakepan");
		itemCakePanStone.setUnlocalizedName("Stonecakepan");
		itemCakePanGold.setUnlocalizedName("Goldcakepan");
		itemCakePanDiamond.setUnlocalizedName("Diamondcakepan");
		itemCakePan.setCreativeTab(CreativeTabs.tabTools);
		itemCakePanStone.setCreativeTab(CreativeTabs.tabTools);
		itemCakePanGold.setCreativeTab(CreativeTabs.tabTools);
		itemCakePanDiamond.setCreativeTab(CreativeTabs.tabTools);
		GameRegistry.registerItem(itemCakePan, itemCakePan.getUnlocalizedName());
		GameRegistry.registerItem(itemCakePanStone, itemCakePanStone.getUnlocalizedName());
		GameRegistry.registerItem(itemCakePanGold, itemCakePanGold.getUnlocalizedName());
		GameRegistry.registerItem(itemCakePanDiamond, itemCakePanDiamond.getUnlocalizedName());

		LanguageRegistry.addName(itemCakePan, "Cakepan");
		LanguageRegistry.addName(itemCakePanStone, "Stonecakepan");
		LanguageRegistry.addName(itemCakePanGold, "Goldcakepan");
		LanguageRegistry.addName(itemCakePanDiamond, "Diamondcakepan");
	}
}
