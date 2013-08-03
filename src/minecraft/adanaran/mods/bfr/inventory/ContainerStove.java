package adanaran.mods.bfr.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import adanaran.mods.bfr.crafting.BFRCraftingManager;
import adanaran.mods.bfr.entities.TileEntityStove;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerStove extends Container {

	public InventoryPlayer invPlayer;
	private TileEntityStove stove;
	private World worldObj;
	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;
	private ItemStack cookResult;

	public ContainerStove(InventoryPlayer inventory,
			TileEntityStove tileEntityStove, World world) {
		invPlayer = inventory;
		stove = tileEntityStove;
		this.worldObj = world;
		this.addSlotToContainer(new Slot(tileEntityStove, 0, 8, 53));
		this.addSlotToContainer(new SlotCookware(tileEntityStove, 1, 8, 17));
		this.addSlotToContainer(new SlotStove(inventory.player,
				tileEntityStove, 2, 124, 35));
		int i;
		int slotIndex = 3;
		for (i = 0; i < 3; ++i) {
			for (int i1 = 0; i1 < 3; ++i1) {
				// 3x3 Cooking Field; slot 3 - 11
				this.addSlotToContainer(new Slot(tileEntityStove, slotIndex++,
						34 + i1 * 18, 17 + i * 18));
			}
		}
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				// Playerinventory; slot 12 - 38
				this.addSlotToContainer(new Slot(this.invPlayer, j + i * 9 + 9
						, 8 + j * 18, 84 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			System.out.println(i);
			// Last row of Playerinventory; slot 39 - 47
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

	/**
	 * Called when a player shift-clicks on a slot. You must override this or
	 * you will crash when someone does that. Returns the clicked Item stack (i
	 * guess).
	 */
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedSlot) {
		System.out.println("clickedSlot: " + clickedSlot);
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(clickedSlot);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (clickedSlot == 0) {
				if (!this.mergeItemStack(itemstack1, 12, 47, false)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}
		return itemstack;
	}

	public ItemStack getCookResult(ItemStack[] itemStacks) {
		InventoryCrafting inventorycrafting = new InventoryCrafting(this, 2, 5);
		for (int i = 3; i < 11; i++) {
			inventorycrafting.setInventorySlotContents(i - 3, itemStacks[i]);
		}
		inventorycrafting.setInventorySlotContents(9, itemStacks[0]);
		cookResult = BFRCraftingManager.getInstance().findMatchingRecipe(
				inventorycrafting, worldObj);
		return cookResult == null ? null : cookResult.copy();
	}
}
