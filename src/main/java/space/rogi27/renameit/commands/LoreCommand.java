package space.rogi27.renameit.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import eu.pb4.placeholders.api.TextParserUtils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Supplier;

// TODO: Move lore manipulations to another function
public class LoreCommand {

    public static int addLore(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (context.getSource().getEntity().isPlayer()) {
            if (context.getSource().getPlayer().getMainHandStack().isEmpty()) {
                context.getSource().sendMessage(Text.translatable("text.renameit.empty").formatted(Formatting.YELLOW));
                return 0;
            }

            MutableText loreString = TextParserUtils.formatTextSafe(context.getArgument("text", String.class)).copy();
            if (!loreString.getStyle().isItalic()) loreString = loreString.fillStyle(Style.EMPTY.withItalic(false));
            
            NbtCompound itemNbt = context.getSource().getPlayer().getMainHandStack().getOrCreateSubNbt("display");

            NbtList lore = new NbtList();
            if (itemNbt.contains("Lore")) {
                lore = itemNbt.getList("Lore", NbtElement.STRING_TYPE);
            }

            lore.add(NbtString.of(Text.Serializer.toJson(loreString)));
            itemNbt.put("Lore", lore);

            context.getSource().sendMessage(Text.translatable("text.renameit.lore_added", loreString.copy().formatted(Formatting.WHITE)).formatted(Formatting.GREEN));
            return 1;
        } else {
            return 0;
        }
    }

    public static int setLore(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (context.getSource().getEntity().isPlayer()) {
            if (context.getSource().getPlayer().getMainHandStack().isEmpty()) {
                context.getSource().sendMessage(Text.translatable("text.renameit.empty").formatted(Formatting.YELLOW));
                return 0;
            }
            NbtCompound itemNbt = context.getSource().getPlayer().getMainHandStack().getSubNbt("display");

            MutableText loreString = TextParserUtils.formatTextSafe(context.getArgument("text", String.class)).copy();
            if (!loreString.getStyle().isItalic()) loreString = loreString.fillStyle(Style.EMPTY.withItalic(false));

            if (!itemNbt.contains("Lore")) {
                context.getSource().sendMessage(Text.translatable("text.renameit.lore_nolore").formatted(Formatting.YELLOW));
                return 0;
            }

            NbtList lore = itemNbt.getList("Lore", NbtElement.STRING_TYPE);
            if (lore.size() + 1 <= context.getArgument("line", Integer.class)) {
                context.getSource().sendMessage(Text.translatable("text.renameit.lore_noline").formatted(Formatting.RED));
                return 0;
            }

            lore.set(context.getArgument("line", Integer.class) - 1, NbtString.of(Text.Serializer.toJson(loreString)));
            itemNbt.put("Lore", lore);

            context.getSource().sendMessage(Text.translatable("text.renameit.lore_line_changed", context.getArgument("line", Integer.class), loreString.formatted(Formatting.WHITE)).formatted(Formatting.GREEN));
            return 1;
        } else {
            return 0;
        }
    }

    public static int deleteLore(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (context.getSource().getEntity().isPlayer()) {
            if (context.getSource().getPlayer().getMainHandStack().isEmpty()) {
                context.getSource().sendMessage(Text.translatable("text.renameit.empty").formatted(Formatting.YELLOW));
                return 0;
            }
            NbtCompound itemNbt = context.getSource().getPlayer().getMainHandStack().getSubNbt("display");

            if (!itemNbt.contains("Lore")) {
                context.getSource().sendMessage(Text.translatable("text.renameit.lore_nolore").formatted(Formatting.YELLOW));
                return 0;
            }

            NbtList lore = itemNbt.getList("Lore", NbtElement.STRING_TYPE);
            if (lore.size() + 1 <= context.getArgument("line", Integer.class)) {
                context.getSource().sendMessage(Text.translatable("text.renameit.lore_noline").formatted(Formatting.RED));
                return 0;
            }

            lore.remove(context.getArgument("line", Integer.class) - 1);
            itemNbt.put("Lore", lore);

            context.getSource().sendMessage(Text.translatable("text.renameit.lore_deleted", context.getArgument("line", Integer.class)).formatted(Formatting.GREEN));
            return 1;
        } else {
            return 0;
        }
    }
}
