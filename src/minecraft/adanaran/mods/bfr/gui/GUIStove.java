package adanaran.mods.bfr.gui;

import org.lwjgl.opengl.GL11;

import adanaran.mods.bfr.entities.TileEntityStove;
import adanaran.mods.bfr.inventory.ContainerStove;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * 
 * @author Adanaran
 * @author Demitreus
 */

public class GUIStove extends GuiContainer {
	
	private TileEntityStove stoveInventory;
	public static final ResourceLocation guiTexture = new ResourceLocation("bfr","gui/container/stove.png");

	public GUIStove(InventoryPlayer invPlayer, TileEntityStove tileEntityStove, World world){
        super(new ContainerStove(invPlayer, tileEntityStove, world));
        this.stoveInventory = tileEntityStove;
	}
	
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.stoveInventory.isInvNameLocalized() ? this.stoveInventory.getInvName() : I18n.func_135053_a(this.stoveInventory.getInvName());
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.func_135053_a("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.func_110434_K().func_110577_a(guiTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;
        if(stoveInventory.isBurning()){
        	i1 = stoveInventory.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 9, l + 48 - i1, 176, 12 - i1, 14, i1 + 2);
        }
        //position of progressbar
        i1 = stoveInventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 89, l + 34, 176, 14, i1 + 1, 16);
		
	}
}
