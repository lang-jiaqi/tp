package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.Trait;

/**
 * Exports the currently displayed cat list to an HTML file.
 * AI-generated.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the currently displayed cat list to export.html in the CatPals folder.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Exported %1$d cat(s) to export.html";
    public static final String MESSAGE_FAILURE = "Failed to write export.html: %1$s";

    private static final Path OUTPUT_PATH = Paths.get("export.html");

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Cat> cats = model.getFilteredCatList();

        String html = buildHtml(cats);
        try {
            Files.writeString(OUTPUT_PATH, html);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, cats.size()));
    }

    /**
     * Builds the full HTML string for the given list of cats.
     * AI-generated.
     */
    private String buildHtml(List<Cat> cats) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n")
          .append("<html lang=\"en\">\n")
          .append("<head>\n")
          .append("  <meta charset=\"UTF-8\">\n")
          .append("  <title>CatPals Export</title>\n")
          .append("  <style>\n")
          .append("    body { font-family: sans-serif; padding: 20px; }\n")
          .append("    h1 { color: #333; }\n")
          .append("    .cat-card { display: flex; align-items: center; gap: 16px;\n")
          .append("                border: 1px solid #ccc; border-radius: 8px;\n")
          .append("                padding: 12px; margin-bottom: 12px; }\n")
          .append("    .cat-card img { width: 80px; height: 80px; object-fit: cover;\n")
          .append("                    border-radius: 4px; }\n")
          .append("    .cat-card .no-image { width: 80px; height: 80px; background: #eee;\n")
          .append("                          border-radius: 4px; display: flex;\n")
          .append("                          align-items: center; justify-content: center;\n")
          .append("                          color: #aaa; font-size: 12px; }\n")
          .append("    .cat-name { font-size: 1.1em; font-weight: bold; }\n")
          .append("    .cat-detail { color: #555; font-size: 0.9em; }\n")
          .append("  </style>\n")
          .append("</head>\n")
          .append("<body>\n")
          .append("  <h1>CatPals Export</h1>\n")
          .append("  <p>").append(cats.size()).append(" cat(s) listed.</p>\n");

        for (int i = 0; i < cats.size(); i++) {
            Cat cat = cats.get(i);
            sb.append(buildCatCard(i + 1, cat));
        }

        sb.append("</body>\n</html>\n");
        return sb.toString();
    }

    /**
     * Builds an HTML card for a single cat.
     * AI-generated.
     */
    private String buildCatCard(int index, Cat cat) {
        StringBuilder sb = new StringBuilder();
        sb.append("  <div class=\"cat-card\">\n");

        // Image column
        if (cat.getImage().hasImage()) {
            sb.append("    <img src=\"").append(escapeHtml(cat.getImage().value))
              .append("\" alt=\"").append(escapeHtml(cat.getName().fullName)).append("\">\n");
        } else {
            sb.append("    <div class=\"no-image\">No image</div>\n");
        }

        // Info column
        sb.append("    <div>\n")
          .append("      <div class=\"cat-name\">").append(index).append(". ")
          .append(escapeHtml(cat.getName().fullName)).append("</div>\n");

        // Traits
        StringBuilder traits = new StringBuilder();
        for (Trait t : cat.getTraits()) {
            if (traits.length() > 0) {
                traits.append(", ");
            }
            traits.append(escapeHtml(t.traitName));
        }
        sb.append("      <div class=\"cat-detail\">Traits: ").append(traits).append("</div>\n")
          .append("      <div class=\"cat-detail\">Location: ")
          .append(escapeHtml(cat.getLocation().value)).append("</div>\n")
          .append("      <div class=\"cat-detail\">Health: ")
          .append(escapeHtml(cat.getHealth().value)).append("</div>\n")
          .append("    </div>\n")
          .append("  </div>\n");

        return sb.toString();
    }

    /**
     * Escapes special HTML characters to prevent injection.
     * AI-generated.
     */
    private String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;");
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ExportCommand;
    }
}