package miku.Gui.Container;

import miku.Network.NetworkHandler;
import miku.Network.Packet.MikuInventoryPackage;
import miku.Render.MikuRenderItem;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class MikuGuiContainer extends GuiContainer {
    private static final ResourceLocation MIKU_INVENTORY_GUI_TEXTURE = new ResourceLocation("miku", "textures/gui/container/miku_inventory_container.png");

    private final MikuInventoryContainer container;

    private GuiButton pre;
    private GuiButton next;

    public MikuGuiContainer(MikuInventoryContainer container) {
        super(container);
        this.container = container;
        this.xSize = 240;
        this.ySize = 256;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
        pre = addButton(new GuiButton(0, (width - xSize) / 2 + 173, (height - ySize) / 2 + 22, 20, 20, "<"));
        next = addButton(new GuiButton(1, (width - xSize) / 2 + 213, (height - ySize) / 2 + 22, 20, 20, ">"));
        itemRender = MikuRenderItem.instance;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            switch (button.id) {
                case 0:
                    container.prePage();
                    NetworkHandler.INSTANCE.sendMessageToServer(new MikuInventoryPackage(false));
                    break;
                case 1:
                    container.nextPage();
                    NetworkHandler.INSTANCE.sendMessageToServer(new MikuInventoryPackage(true));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(container.inventory.getDisplayName().getUnformattedText(), 173, 8, 4210752);
        String page = String.valueOf(container.inventory.getField(0) + 1);
        int strSize = fontRenderer.getStringWidth(page);
        fontRenderer.drawString(page, 203 - strSize / 2, 27, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(MIKU_INVENTORY_GUI_TEXTURE);
        this.drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);
    }
}
