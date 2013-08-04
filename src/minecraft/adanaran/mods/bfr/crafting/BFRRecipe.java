package adanaran.mods.bfr.crafting;

import java.util.List;

import adanaran.mods.bfr.items.ItemCookwareBase;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

/**
 * @author Adanaran
 * 
 */
public class BFRRecipe extends ShapelessRecipes {

	private ItemCookwareBase cookware;

	public BFRRecipe(ItemStack result, ItemCookwareBase cookware,
			List ingredients) {
		super(result, ingredients);
		this.cookware = cookware;
	}

	@Override
	public boolean matches(InventoryCrafting par1InventoryCrafting,
			World par2World) {
		return super.matches(par1InventoryCrafting, par2World);
	}
	

	public boolean matches(InventoryCrafting par1InventoryCrafting,
			World par2World, ItemStack cookWare) {
		return matches(par1InventoryCrafting, par2World)
				&& cookWare.getItem() instanceof ItemCookwareBase
				&& cookWare.getItem().getClass() == cookware.getClass();
	}
	@Override
	public int getRecipeSize() {
		return super.getRecipeSize() + 1;
	}
}
