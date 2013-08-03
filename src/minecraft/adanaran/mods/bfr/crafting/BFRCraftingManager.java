package adanaran.mods.bfr.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import adanaran.mods.bfr.BFR;
import adanaran.mods.bfr.items.ItemCookwareBase;

/**
 * @author Adanaran
 * 
 */
public class BFRCraftingManager {

	private static BFRCraftingManager instance = new BFRCraftingManager();

	/** A list of all the recipes added */
	private ArrayList<BFRRecipe> recipes = new ArrayList<BFRRecipe>();

	/**
	 * 
	 */
	private BFRCraftingManager() {
		// TODO Rezepte!
		addRecipe(new ItemStack(Item.bread), BFR.itemCakePan, new Object[] {Item.bucketWater, Item.wheat});
	}

	public static BFRCraftingManager getInstance() {
		return instance;
	}

	public void addRecipe(ItemStack result, ItemCookwareBase cookware,
			Object... ingredients) {
		ArrayList arraylist = new ArrayList();
		Object[] aobject = ingredients;
		int i = ingredients.length;
		for (int j = 0; j < i; ++j) {
			Object object1 = aobject[j];
			if (object1 instanceof ItemStack) {
				arraylist.add(((ItemStack) object1).copy());
			} else if (object1 instanceof Item) {
				arraylist.add(new ItemStack((Item) object1));
			} else {
				if (!(object1 instanceof Block)) {
					throw new RuntimeException("Invalid shapeless recipy!");
				}
				arraylist.add(new ItemStack((Block) object1));
			}
		}
		this.recipes.add(new BFRRecipe(result, cookware, arraylist));
	}

	public ItemStack findMatchingRecipe(
			InventoryCrafting par1InventoryCrafting, World par2World) {
		for (int j = 0; j < this.recipes.size(); ++j) {
			IRecipe irecipe = (IRecipe) this.recipes.get(j);
			if (irecipe.matches(par1InventoryCrafting, par2World)) {
				return irecipe.getCraftingResult(par1InventoryCrafting);
			}
		}
		return null;
	}

	/**
	 * returns the List<> of all recipes
	 */
	public List getRecipeList() {
		return this.recipes;
	}
}
