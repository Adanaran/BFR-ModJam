package adanaran.mods.bfr.crafting;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import adanaran.mods.bfr.BFR;

public class MillRecipes {

	private static final MillRecipes instance = new MillRecipes();
	private Map millingList = new HashMap();

	private MillRecipes() {
		this.addMilling(Item.wheat.itemID, new ItemStack(BFR.itemFlour));
	}

	public static MillRecipes getInstance() {
		return instance;
	}

	public void addMilling(int inputId, ItemStack result) {
		this.millingList.put(Integer.valueOf(inputId), result);
	}

	public Map getMillingList() {
		return this.millingList;
	}

	public ItemStack getSmeltingResult(ItemStack item) {
		if (item == null) {
			return null;
		}
		return (ItemStack) millingList.get(Integer.valueOf(item.itemID));
	}

}
