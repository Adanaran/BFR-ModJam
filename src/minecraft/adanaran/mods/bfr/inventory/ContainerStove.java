package adanaran.mods.bfr.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import adanaran.mods.bfr.entities.TileEntityStove;
import adanaran.mods.bfr.items.FoodRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

public class ContainerStove extends Container {

	public InventoryPlayer invPlayer;
	private TileEntityStove stove;
	private World worldObj;
	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;
	// TODO das was hier steht, wird produziert... steht hier null oder nichts, passiert auch nichts
	public ItemStack cookResult = new ItemStack(Item.diamond);
	private FoodRecipes foodRecipes = new FoodRecipes();

	public ContainerStove(InventoryPlayer inventory,
			TileEntityStove tileEntityStove, World world) {
		invPlayer = inventory;
		stove = tileEntityStove;
		this.worldObj = world;
		this.addSlotToContainer(new SlotCookware(tileEntityStove, 0, 8, 17));
		this.addSlotToContainer(new Slot(tileEntityStove, 1, 8, 53));
		this.addSlotToContainer(new SlotStove(inventory.player,
				tileEntityStove, 2, 124, 35));
		int i;
		int slotIndex = 3;
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				// Playerinventory
				this.addSlotToContainer(new Slot(this.invPlayer, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
			}
			for (int i1 = 0; i1 < 3; ++i1) {
				// 3x3 Cooking Field
				this.addSlotToContainer(new Slot(tileEntityStove, slotIndex++,
						34 + i1 * 18, 17 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			// Last row of Playerinventory
			this.addSlotToContainer(new Slot(this.invPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.stove.stoveCookTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.stove.stoveBurnTime);
		par1ICrafting.sendProgressBarUpdate(this, 2,
				this.stove.currentItemBurnTime);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastCookTime != this.stove.stoveCookTime) {
				icrafting.sendProgressBarUpdate(this, 0,
						this.stove.stoveCookTime);
			}

			if (this.lastBurnTime != this.stove.stoveBurnTime) {
				icrafting.sendProgressBarUpdate(this, 1,
						this.stove.stoveBurnTime);
			}

			if (this.lastItemBurnTime != this.stove.currentItemBurnTime) {
				icrafting.sendProgressBarUpdate(this, 2,
						this.stove.currentItemBurnTime);
			}
		}
		stove.container = this;
		this.lastCookTime = this.stove.stoveCookTime;
		this.lastBurnTime = this.stove.stoveBurnTime;
		this.lastItemBurnTime = this.stove.currentItemBurnTime;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.stove.stoveCookTime = par2;
		}

		if (par1 == 1) {
			this.stove.stoveBurnTime = par2;
		}

		if (par1 == 2) {
			this.stove.currentItemBurnTime = par2;
		}
	}
}
