package dev.upscairs.cratesAndDropevents.gui.defaults;

public class InteractableGui extends GuiDecorator {

    public InteractableGui(InventoryGui inventoryGui) {
        super(inventoryGui);
    }

    public InventoryGui handleInvClick(int slot) {
        return new PreventCloseGui();
    }

    public void placeItems() {
        return;
    }

}
