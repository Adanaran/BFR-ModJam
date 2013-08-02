package adanaran.mods.bfr.entities;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

/**
 * 
 * @author Adanaran
 */
public class TileEntityStove extends TileEntity implements ISidedInventory {

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 2, 1 };
	private static final int[] slots_sides = new int[] { 1 };

	/**
	 * The ItemStacks that hold the items currently being used in the stove
	 */
	private ItemStack[] stoveItemStacks = new ItemStack[3];
	public IInventory cookingMatrix;
	public ItemStack cookResult;

	/** The number of ticks that the stove will keep burning */
	public int stoveBurnTime;

	/**
	 * The number of ticks that a fresh copy of the currently-burning item would
	 * keep the stove burning for
	 */
	public int currentItemBurnTime;

	/** The number of ticks that the current item has been cooking for */
	public int stoveCookTime;
	public EntityLivingBase player;

	public boolean canCook() {

		return cookingMatrix != null
				&& cookResult != null
				&& this.stoveItemStacks[0] != null
				&& this.stoveItemStacks[1] != null
				&& (this.stoveItemStacks[2].isItemEqual(cookResult) || this.stoveItemStacks[2] == null)
				&& (stoveItemStacks[2].stackSize + cookResult.stackSize) <= getInventoryStackLimit()
				&& (stoveItemStacks[2].stackSize + cookResult.stackSize) <= cookResult
						.getMaxStackSize();
	}

	public void cookItem() {
		if (canCook()) {
			// damage Cookware
			stoveItemStacks[1].damageItem(1, player);
			// add cookResult
			if (stoveItemStacks[2] == null) {
				stoveItemStacks[2] = cookResult.copy();
			} else if (this.stoveItemStacks[2].isItemEqual(cookResult)) {
				stoveItemStacks[2].stackSize += cookResult.stackSize;
			}
			// decrease Fuel
			--this.stoveItemStacks[0].stackSize;

			if (this.stoveItemStacks[0].stackSize <= 0) {
				this.stoveItemStacks[0] = null;
			}
			// decrease items in cooking-Matrix
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					this.cookingMatrix.decrStackSize(i, j);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * cooked
	 */
	public int getCookProgressScaled(int par1) {
		return this.stoveCookTime * par1 / 200;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
	 * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
	 */
	public int getBurnTimeRemainingScaled(int par1) {
		if (this.currentItemBurnTime == 0) {
			this.currentItemBurnTime = 200;
		}

		return this.stoveBurnTime * par1 / this.currentItemBurnTime;
	}

	/**
	 * Returns true if the furnace is currently burning
	 */
	public boolean isBurning() {
		return this.stoveBurnTime > 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		// TODO @Demitreus fertig machen
		super.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		// TODO @Demitreus fertig machen
		super.writeToNBT(nbt);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		// TODO Auto-generated method stub
		// brauchen wirs?
		super.onDataPacket(net, pkt);
	}

	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		// ?? noch nich ganz kapiert
		return false;
	}

	public String getInvName() {
		return "Stove";
	}

	@Override
	public int getSizeInventory() {
		return this.stoveItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.stoveItemStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.stoveItemStacks[i] != null) {
			ItemStack itemstack;

			if (this.stoveItemStacks[i].stackSize <= j) {
				itemstack = this.stoveItemStacks[i];
				this.stoveItemStacks[i] = null;
				return itemstack;
			} else {
				itemstack = this.stoveItemStacks[i].splitStack(j);

				if (this.stoveItemStacks[i].stackSize == 0) {
					this.stoveItemStacks[i] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.stoveItemStacks[i] != null) {
			ItemStack itemstack = this.stoveItemStacks[i];
			this.stoveItemStacks[i] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.stoveItemStacks[i] = itemstack;

		if (itemstack != null
				&& itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord,
				this.zCoord) != this ? false : entityplayer.getDistanceSq(
				(double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
				(double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {
		// Without function
	}

	@Override
	public void closeChest() {
		// Without function
	}

	/**
	 * Return true if item is a fuel source (getItemBurnTime() > 0).
	 */
	public static boolean isItemFuel(ItemStack par0ItemStack) {
		return getItemBurnTime(par0ItemStack) > 0;
	}

	/**
	 * Returns the number of ticks that the supplied fuel item will keep the
	 * furnace burning, or 0 if the item isn't fuel
	 */
	public static int getItemBurnTime(ItemStack par0ItemStack) {
		if (par0ItemStack == null) {
			return 0;
		} else {
			int i = par0ItemStack.getItem().itemID;
			Item item = par0ItemStack.getItem();

			if (par0ItemStack.getItem() instanceof ItemBlock
					&& Block.blocksList[i] != null) {
				Block block = Block.blocksList[i];

				if (block == Block.woodSingleSlab) {
					return 150;
				}

				if (block.blockMaterial == Material.wood) {
					return 300;
				}

				if (block == Block.field_111034_cE) {
					return 16000;
				}
			}

			if (item instanceof ItemTool
					&& ((ItemTool) item).getToolMaterialName().equals("WOOD"))
				return 200;
			if (item instanceof ItemSword
					&& ((ItemSword) item).getToolMaterialName().equals("WOOD"))
				return 200;
			if (item instanceof ItemHoe
					&& ((ItemHoe) item).getMaterialName().equals("WOOD"))
				return 200;
			if (i == Item.stick.itemID)
				return 100;
			if (i == Item.coal.itemID)
				return 1600;
			if (i == Item.bucketLava.itemID)
				return 20000;
			if (i == Block.sapling.blockID)
				return 100;
			if (i == Item.blazeRod.itemID)
				return 2400;
			return GameRegistry.getFuelValue(par0ItemStack);
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return var1 == 0 ? slots_bottom : (var1 == 1 ? slots_top : slots_sides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int j) {
		return this.isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int j) {
		return j != 0 || slot != 1
				|| itemstack.itemID == Item.bucketEmpty.itemID;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}

}
