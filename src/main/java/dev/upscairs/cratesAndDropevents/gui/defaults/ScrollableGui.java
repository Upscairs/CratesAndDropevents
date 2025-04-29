package dev.upscairs.cratesAndDropevents.gui.defaults;

import dev.upscairs.cratesAndDropevents.gui.functional.InvGuiUtils;
import dev.upscairs.cratesAndDropevents.gui.helper.ListableGuiObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class ScrollableGui<E extends List<? extends ListableGuiObject>> extends InteractableGui {

    private int page;
    private int maxPage;
    private E listedObjects;

    private boolean showPageInTitle = false;
    private String rawTitle = "";

    public ScrollableGui(InteractableGui interactableGui, E listedObjects, int page) {
        super(interactableGui);
        this.listedObjects = listedObjects;

        maxPage = (listedObjects.size() - 1) / 45;

        setPage(page);

    }

    public void setPage(int page) {

        this.page = page;

        if(page > maxPage) {
            this.page = maxPage;
        }
        if(page < 0) {
            this.page = 0;
        }

        updatePageInTitle();
        placeItems();
    }

    private void flushItems() {
        for(int i = 0; i < getInventory().getSize(); i++) {
            setItem(i, new ItemStack(Material.AIR));
        }
    }

    public void placeItems() {

        flushItems();

        if(page > 0) {
            setItem(45, generateScrollLeftItem());
        }
        if(page < maxPage) {
            setItem(53, generateScrollRightItem());
        }

        for(int i = 45*page; i < 45*(page+1) && i < listedObjects.size(); i++) {
            setItem(i%45, listedObjects.get(i).getRenderItem());
        }

        super.placeItems();

    }

    private ItemStack generateScrollLeftItem() {
        ItemStack stack = InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/e35e42fc7060c223acc965f7c5996f272644af40a4723a372f5903f8e9f188e7");
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultHeaderComponent("Previous Page", "#B1B1B1"));
        stack.setItemMeta(meta);
        return stack;
    }

    private ItemStack generateScrollRightItem() {
        ItemStack stack = InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/aee0f82fb33f6cfa5169b9f5eafe4dc1c73618c9783b131adada411d8f605505");
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultHeaderComponent("Next Page", "#B1B1B1"));
        stack.setItemMeta(meta);
        return stack;
    }

    public int getPage() {
        return page;
    }

    @Override
    public InventoryGui handleInvClick(int slot) {

        if(slot == 45) {
            setPage(page - 1);
            return this;
        }
        else if(slot == 53) {
            setPage(page + 1);
            return this;
        }
        else {
            return super.handleInvClick(slot);
        }

    }

    public E getListedObjects() {
        return listedObjects;
    }

    public void setListedObjects(E listedObjects) {
        this.listedObjects = listedObjects;
        setPage(page);
    }

    /**
     *
     * Appends the page number to the title if showPageInTitle is true.
     *
     * @param title
     */
    @Override
    public void setTitle(String title) {

        if(!showPageInTitle) {
            super.setTitle(title);
        }
        else {
            this.rawTitle = title;
            super.setTitle(rawTitle + " (" + (page+1) + "/" + (maxPage+1) + ")");
        }

    }

    public void updatePageInTitle() {
        setTitle(rawTitle);
    }

    public void showPageInTitle(boolean showPageInTitle) {
        this.showPageInTitle = showPageInTitle;
        updatePageInTitle();
    }

    public boolean isPageInTitleShown() {
        return showPageInTitle;
    }

}
