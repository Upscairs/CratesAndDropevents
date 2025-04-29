package dev.upscairs.cratesAndDropevents.gui.defaults;

import dev.upscairs.cratesAndDropevents.gui.functional.InvGuiUtils;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NumberSelectionGui extends InteractableGui implements InventoryHolder {

    int number;
    int maxNumber;
    int minNumber;

    //Skulls from 0 to 25
    private static final ItemStack[] numberItems = {
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/3f09018f46f349e553446946a38649fcfcf9fdfd62916aec33ebca96bb21b5"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/ca516fbae16058f251aef9a68d3078549f48f6d5b683f19cf5a1745217d72cc"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/4698add39cf9e4ea92d42fadefdec3be8a7dafa11fb359de752e9f54aecedc9a"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/fd9e4cd5e1b9f3c8d6ca5a1bf45d86edd1d51e535dbf855fe9d2f5d4cffcd2"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/f2a3d53898141c58d5acbcfc87469a87d48c5c1fc82fb4e72f7015a3648058"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/d1fe36c4104247c87ebfd358ae6ca7809b61affd6245fa984069275d1cba763"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/3ab4da2358b7b0e8980d03bdb64399efb4418763aaf89afb0434535637f0a1"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/297712ba32496c9e82b20cc7d16e168b035b6f89f3df014324e4d7c365db3fb"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/abc0fda9fa1d9847a3b146454ad6737ad1be48bdaa94324426eca0918512d"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/d6abc61dcaefbd52d9689c0697c24c7ec4bc1afb56b8b3755e6154b24a5d8ba"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/7af3fd473a648b847ccda1d2074479bb7672771dc435223468ed9ff7b76cb3"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/48cab53b02098e681a46d1d7f5ff691746adf4e1fb3afe3516dd2af944569"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/bfd83b5baae4cb85694a14d6d13341ef71aa3d92d37de07bea77b2c9dc53e"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/b1e5985be4886f9f16e2447c3f461053b451342d4fb0166fb2f88df7422136b4"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/1681456438ae9b2d4d2bfab9cf3ffa9354eebdb3f02ce2957929348e5b85ff95"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/719c4db7365b1b88b129e704184213fe078d88bc3d4ae3d52290f61d955d51"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/9f5dd079b98fdac43a19a795ba46fd97f23ea7757d92ad0a69adc973289e5a"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/fe51bfe8ebfaa585a787e1cb772c7fd7d9a9286d95efa54d66fa8274f188f"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/b6de9a5a2d8a23707019b9ef61d1662860e0b1653df6c27616be2c76fcd1875"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/dbd45904d34b636b2f64261b3d8bced25828c2b8c4823b7e183ee8a6f1a284d"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/74b93c721519e149649b7e4afb6d76cfc814608ae9c95e7c3db4bf4bdaacf31e"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/61acccb13724b77e1ef4639e880a6faeec1d8e7af72e193d8e58a6aac72e8"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/f841fe2b5e6eb9343a53d022cba8952fea9171668f997c575639743ebf517"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/6ea7dbd23d58dbb2e8f980c83bf383a7b513c8f68ac1ba58a2c20670e7dc"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/bd2170165af65f5bc298d0ff54ccf0a62c8b4385415f7fb38c33ffc1908e82"),
            InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/fda7cc5cb6f6c370438b795372ad6ab23ae9589a8fc579d1d4ae227661935"),
    };

    public NumberSelectionGui(InteractableGui interactableGui, int startNumber, int minNumber, int maxNumber, CommandSender sender) {

        super(interactableGui);

        if(maxNumber < minNumber) maxNumber = minNumber;
        if(maxNumber > 2500) maxNumber = 2500;
        if(minNumber < 0) minNumber = 0;
        if(startNumber < minNumber) startNumber = minNumber;
        if(startNumber > maxNumber) startNumber = maxNumber;

        this.number = startNumber;
        this.maxNumber = maxNumber;
        this.minNumber = minNumber;

        setSize(36);

        setHolder(this);
        placeItems();

    }

    public void placeItems() {

        flushItems();

        setItem(9, generateHugeDecreaseItem());
        setItem(10, generateMediumDecreaseItem());
        setItem(11, generateSmallDecreaseItem());
        setItem(15, generateSmallIncreaseItem());
        setItem(16, generateMediumIncreaseItem());
        setItem(17, generateHugeIncreaseItem());

        setNumberItems();

        setItem(30, generateDoneItem());
        setItem(32, generateCancelItem());

    }

    private ItemStack generateHugeDecreaseItem() {
        ItemStack stack = InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/118a2dd5bef0b073b13271a7eeb9cfea7afe8593c57a93821e43175572461812");
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultHeaderComponent("-100", "#FF5555").decoration(TextDecoration.BOLD, true));
        stack.setItemMeta(meta);
        return stack;
    }

    private ItemStack generateMediumDecreaseItem() {
        ItemStack stack = InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/864f779a8e3ffa231143fa69b96b14ee35c16d669e19c75fd1a7da4bf306c");
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultHeaderComponent("-10", "#FF5555").decoration(TextDecoration.BOLD, true));
        stack.setItemMeta(meta);
        return stack;
    }

    private ItemStack generateSmallDecreaseItem() {
        ItemStack stack = InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/37aee9a75bf0df7897183015cca0b2a7d755c63388ff01752d5f4419fc645");
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultHeaderComponent("-1", "#FF5555").decoration(TextDecoration.BOLD, true));
        stack.setItemMeta(meta);
        return stack;
    }

    private ItemStack generateSmallIncreaseItem() {
        ItemStack stack = InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/682ad1b9cb4dd21259c0d75aa315ff389c3cef752be3949338164bac84a96e");
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultHeaderComponent("+1", "#55FF55").decoration(TextDecoration.BOLD, true));
        stack.setItemMeta(meta);
        return stack;
    }

    private ItemStack generateMediumIncreaseItem() {
        ItemStack stack = InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/d9eccc5c1c79aa7826a15a7f5f12fb40328157c5242164ba2aef47e5de9a5cfc");
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultHeaderComponent("+10", "#55FF55").decoration(TextDecoration.BOLD, true));
        stack.setItemMeta(meta);
        return stack;
    }

    private ItemStack generateHugeIncreaseItem() {
        ItemStack stack = InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/d99f28332bcc349f42023c29e6e641f4b10a6b1e48718cae557466d51eb922");
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultHeaderComponent("+100", "#55FF55").decoration(TextDecoration.BOLD, true));
        stack.setItemMeta(meta);
        return stack;
    }

    private ItemStack generateDoneItem() {
        ItemStack stack = new ItemStack(Material.LIME_WOOL);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultHeaderComponent("Done", "#55FF55").decoration(TextDecoration.BOLD, true));
        stack.setItemMeta(meta);
        return stack;
    }

    private ItemStack generateCancelItem() {
        ItemStack stack = new ItemStack(Material.RED_WOOL);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultHeaderComponent("Cancel", "#FF5555").decoration(TextDecoration.BOLD, true));
        stack.setItemMeta(meta);
        return stack;
    }

    private void setNumberItems() {

        int hundred = number / 100;
        int ten = (number / 10) % 10;
        int single = number % 10;

        setItem(12, generateNumberItem(hundred));
        setItem(13, generateNumberItem(ten));
        setItem(14, generateNumberItem(single));

    }

    private ItemStack generateNumberItem(int digit) {
        ItemStack stack = numberItems[digit];
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultHeaderComponent("", "#FFFFFF"));
        stack.setItemMeta(meta);
        return stack;
    }

    private void flushItems() {
        for(int i = 0; i < getInventory().getSize(); i++) {
            setItem(i, new ItemStack(Material.AIR));
        }
    }

    public int getNumber() {
        return number;
    }

    @Override
    public InventoryGui handleInvClick(int slot) {

        if(slot >= 9 && slot <= 17) {

            switch(slot) {
                case 9: number -= 100; break;
                case 10: number -= 10; break;
                case 11: number -= 1; break;
                case 15: number += 1; break;
                case 16: number += 10; break;
                case 17: number += 100; break;
                default: break;
            }

            if(number < minNumber) number = minNumber;
            if(number > maxNumber) number = maxNumber;

            setNumberItems();
            return new PreventCloseGui();

        }

        return super.handleInvClick(slot);
    }








}
