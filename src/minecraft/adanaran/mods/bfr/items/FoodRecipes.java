package adanaran.mods.bfr.items;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class FoodRecipes implements IRecipe {

	private ItemStack result;

	@Override
	public boolean matches(InventoryCrafting inventorycrafting, World world) {
		// TODO hier die rezepte prüfen
		if (inventorycrafting.getStackInSlot(0) != null
				&& inventorycrafting.getStackInSlot(0).getItem().itemID == Item.coal.itemID) {
			if (result == null || result.getItem() != Item.coal)
				result = new ItemStack(Item.diamond);
			return true;
		}
		result = null;
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public int getRecipeSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	public static ItemStack getCookResult(ItemStack stack, World worldObj) {
		// TODO @Demitreus Rezepte einfügen. Siehe FurnaceRecipes
		return stack;
	}

}
