package seedu.address.model.cat;

import java.io.File;

/**
 * Utility class for resolving cat image files.
 * AI-generated.
 */
public class CatImageUtil {

    /**
     * Resolves the image file for the given cat.
     * Uses the explicit image path if set; otherwise tries to auto-detect a matching
     * file in the images/ folder by cat name (e.g. "Bowie" matches images/Bowie.png).
     * Returns null if no image file can be found.
     * AI-generated.
     */
    public static File resolveImageFile(Cat cat) {
        // 1. Explicit path set — use it directly
        if (cat.getImage().hasImage()) {
            File f = new File(cat.getImage().value);
            return f.exists() ? f : null;
        }

        // 2. Auto-detect: try images/{name}.ext and images/{name_lowercase}.ext
        String[] extensions = {"png", "jpg", "jpeg"};
        String catName = cat.getName().fullName;

        for (String ext : extensions) {
            File f = new File("images/" + catName + "." + ext);
            if (f.exists()) {
                return f;
            }
            File fLower = new File("images/" + catName.toLowerCase() + "." + ext);
            if (fLower.exists()) {
                return fLower;
            }
        }

        return null;
    }

}
