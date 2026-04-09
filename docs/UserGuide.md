---
layout: page
title: User Guide
---
![banner](images/CatPals_banner.png)

> **First run?** Budget about **10 minutes** for [Getting Started](#getting-started) (longer only if you need to install Java). **Problems?** See [FAQ](#faq).

# Table of Contents

<!-- TOC -->

* [Table of Contents](#table-of-contents)
  * [About CatPals](#about-catpals)
    * [What is CatPals?](#what-is-catpals)
    * [Why is this app needed?](#why-is-this-app-needed)
    * [Who are the target users?](#who-are-the-target-users)
    * [How do users interact with CatPals?](#how-do-users-interact-with-catpals)
    * [What value does CatPals provide?](#what-value-does-catpals-provide)
    * [Where can you find more information?](#where-can-you-find-more-information)
  * [Getting Started](#getting-started)
  * [A Quick Tour of the Interface](#a-quick-tour-of-the-interface)
    * [Splash screen](#splash-screen)
    * [Main window](#main-window)
    * [Navigating the cat list](#navigating-the-cat-list)
    * [Confirmation dialogs](#confirmation-dialogs)
  * [Features](#features)
    * [Viewing help : `help`](#viewing-help--help)
    * [Adding a cat: `add`](#adding-a-cat-add)
    * [Attaching a cat photo: `attach`](#attaching-a-cat-photo-attach)
    * [Listing all cats: `list`](#listing-all-cats-list)
    * [Updating a cat profile : `update`](#updating-a-cat-profile--update)
    * [Locating cats by name, location, traits or health status : `find`](#locating-cats-by-name-location-traits-or-health-status--find)
    * [Deleting a cat : `delete`](#deleting-a-cat--delete)
    * [Exporting the cat list : `export`](#exporting-the-cat-list--export)
    * [Clearing all entries : `clear`](#clearing-all-entries--clear)
    * [Undo the previous action : `undo`](#undo-the-previous-action--undo)
    * [Exiting the program : `exit`](#exiting-the-program--exit)
    * [Saving the data](#saving-the-data)
    * [Editing the data file](#editing-the-data-file)
    * [Archiving data files `[coming in v2.0]`](#archiving-data-files-coming-in-v20)
  * [FAQ](#faq)
  * [Known issues](#known-issues)
  * [Command summary](#command-summary)

<!-- TOC -->

---

## About CatPals

### What is CatPals?

CatPals is a **desktop app for NUS Cat Café volunteers** to manage stray-cat records on campus. It keeps names, locations, traits, health status, and photos all in one place, instead of scattered notes or group chats.

### Why is this app needed?

With so many cats spread across campus, details can easily get lost in messages or forgotten over time. CatPals gives volunteers **one organised record** they can **search**, **update**, and **export** whenever they need to coordinate care.

### Who are the target users?

- **NUS Cat Café CCA** members tracking many campus cats
- People who prefer a **desktop** tool with **keyboard-first** workflows
- Anyone comfortable typing short commands (no coding knowledge required)

### How do users interact with CatPals?

Type commands in the **command box** at the top (e.g. `add`, `find`, `update`). The **list** on the left and **detail** panel on the right update as you work. Use **↑ / ↓** to change the selected cat. When the app opens, the **splash screen** asks you to press **Space** to continue.

### What value does CatPals provide?

CatPals offers fast lookup, consistent fields, **HTML export**, and **offline** use. It is not a vet system, shelter operations tool, or public registry, it is simply a practical notebook for volunteers.

### Where can you find more information?

Visit the **[CatPals project site](https://ay2526s2-cs2103t-t16-3.github.io/tp/)** for releases, documentation, and team details.

---

## Getting Started

**Rough time:** About **10 minutes** if **Java 17+** is already installed. Add roughly **5 to 10 minutes** if you need to install Java first. All you need is a normal desktop computer and the steps below, no developer setup required.

---

### 1. Install Java 17 or newer

**CatPals requires Java 17 or higher.** Java is the standard runtime that CatPals runs on — you only need to install it once, and you do not need to configure anything else.

**Check what you have:**

* **Windows:** Start menu, type `cmd`, press Enter, then run `java -version`
* **Mac:** press `Cmd + Space`, search for Terminal, open it, then run `java -version`

You should see a version number of **17** or higher (e.g. `17.0.x`). If the command is not recognised or the major version is below 17, install a current JDK:

* **Recommended:** [Eclipse Temurin 17 (or newer)](https://adoptium.net/), pick your OS, run the installer, then **open a new command window** and run `java -version` again to confirm.
* **Alternative:** [Oracle Java downloads](https://www.oracle.com/java/technologies/downloads/)

---

### 2. Download CatPals and make a folder

1. Download the latest **`catpals.jar`** from the **[latest release](https://github.com/AY2526S2-CS2103T-T16-3/tp/releases)**.
2. Create a folder (e.g. **`CatPals`** on your Desktop) and move the `.jar` file into it.

---

### 3. Open a command window in that folder

* **Windows:** Press `Win + R`, type `cmd`, press Enter. Then navigate to your folder, e.g. `cd C:\Users\YourName\Desktop\CatPals`
* **Mac / Linux:** Open the Terminal app and navigate, e.g. `cd ~/Desktop/CatPals`

Use the actual path where your `.jar` file is saved.

---

### 4. Run the app

Type the following command and press Enter:

```
java -jar catpals.jar
```

<div markdown="block" class="alert alert-info">

**:information_source: Note:** The file you downloaded should be named `catpals.jar`. If it was saved with a version number in the filename (e.g. `catpals-1.4.jar`), either rename it to `catpals.jar` or replace `catpals.jar` in the command above with the actual filename.

</div>

You should see the **splash screen** first, then press **Space** to open the main window ([tour below](#a-quick-tour-of-the-interface)).

---

### 5. First commands

Try **`help`** in the command box. Other quick examples:

* `list` — show all cats
* `add n/Bowie t/Orange l/Utown h/Vaccinated` — add a cat
* `delete 3` — delete the **3rd cat** in the **current** list
* `clear` — remove every cat (with confirmation)
* `exit` — quit

Full syntax is in the [Features](#features) section.

---

## A Quick Tour of the Interface

CatPals uses a **warm, keyboard-friendly layout**: commands at the top, list on the left, full profile on the right.

### Splash screen

On launch, you will see the welcome screen. Simply press **Space** to continue.

<p align="center">
  <img src="images/splash_screen.png" alt="CatPals splash screen" width="70%">
  <br>
  <em>Welcome screen. Simply press <strong>Space</strong> to open the main window.</em>
</p>

### Main window

<p align="center">
  <img src="images/main_screen.png" alt="CatPals main window" width="70%">
  <br>
  <em>Main window: command row, result line, cat list (left), profile and photo (right), data path (footer).</em>
</p>

| Area | What it does |
| ---- | ------------ |
| **Header** | App title and branding |
| **Command field** | Type commands; press **Enter** to run |
| **Result line** | Message from the last command (success or error) |
| **Cat list (left)** | Numbered cats; **↑ / ↓** changes selection |
| **Detail panel (right)** | Name, location, traits, health, photo or placeholder |
| **Footer** | Path to your **data file** (inside the `data` folder next to the app) |

### Navigating the cat list

Use **↑** and **↓** to move the selection (works even when the cursor is in the command box). The detail panel updates automatically. **Clicking list rows does not select**. Instead, use the arrow keys. You can still **scroll** the list with the mouse wheel.

---

### Confirmation dialogs

For **`update`**, **`delete`**, and **`clear`**, a dialog will ask you to confirm. Press **Enter** to confirm or **Esc** to cancel. No mouse required. See [Update](#updating-a-cat-profile--update) and [Delete](#deleting-a-cat--delete) for examples.

---

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/Snowy`.
* Items in square brackets are optional.<br>
  e.g. `n/NAME t/TRAIT [h/HEALTH_STATUS]` can be used as `n/Snowy t/white h/vaccinated` or as `n/Snowy t/white`.
* Items with `…` after them can be used multiple times (>= 1).<br>
  e.g. `[t/TRAIT]…` can be used as `t/white`, `t/white t/small` etc.
* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.
* The `list` command does **not** accept extra parameters. Typing `list` followed by anything will show an error.
* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

<p align="center">
  <img src="images/helpMessage.png" alt="main_screen" width="70%">
</p>

Format: `help`

### Adding a cat: `add`

Adds a cat profile to the cat notebook.

Format: `add n/NAME t/TRAIT [t/MORE_TRAITS]… l/LOCATION [h/HEALTH_STATUS]`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A cat can have up to 3 traits. Duplicate traits (traits with the exact same spelling, regardless of spacing) are not allowed. Health status is optional and defaults to `Unknown` if not provided.
To attach a photo after adding, use the `attach` command.
</div>

* `n/NAME`, `t/TRAIT`, and `l/LOCATION` are required.
* `NAME` must contain at least one letter. It can be a mix of letters and numbers (e.g. `R2D2`), but it **cannot be only numbers** (e.g. `123`), because number-only names are ambiguous with index-based commands like `update` and `delete`.
* `h/HEALTH_STATUS` is optional. Common values include `Vaccinated`, `Neutered`, `Vaccinated and Neutered`, `Injured`, or `Healthy`.
* You can specify up to 3 `t/TRAIT` prefixes, but duplicate traits (same exact spelling) are not allowed.

Examples:

* `add n/Bowie t/Orange l/Utown h/Vaccinated`
* `add n/Whiskers t/Fluffy t/Playful l/Science h/Neutered`

<p align="center">
  <img src="images/feature_add.png" alt="feature_add" width="70%">
  <br>
  <em>Running <code>add n/Whiskers t/Fluffy t/Playful l/Science</code> adds <code>Whiskers</code> to the cat list.</em>
</p>

### Attaching a cat photo: `attach`

CatPals can display a photo on each cat's card. Photos are loaded from image files stored on your computer.

**Folder setup**

Your `CatPals` folder must be structured as follows:

```
CatPals/
├── catpals.jar
├── data/
│   └── addressbook.json
└── images/
    ├── bowie.png
    └── whiskers.jpg
```

1. Inside your `CatPals` folder (the same folder that contains `catpals.jar`), create a subfolder named **`images`**.
2. Copy your cat photos into the `images` folder.

**Naming your image files**

* Use simple filenames with no spaces, e.g. prefer `snowy_cat.jpg` over `snowy cat.jpg`.
* Supported formats: `.png`, `.jpg`, `.jpeg`.
* **Case sensitivity depends on your operating system.** Linux and macOS treat `Bowie.png` and `bowie.png` as different files; Windows does not. To avoid surprises, always match capitalisation exactly.

<div markdown="block" class="alert alert-info">

**:information_source: iPhone / HEIC photos:** Photos taken on an iPhone are often saved in HEIC format (`.heic`), which CatPals does not support. Before using an iPhone photo, convert it to `.jpg` or `.png` first. You can do this by opening the photo on a Windows PC and using **Photos > Save as**, or on a Mac by opening it in **Preview** and choosing **File > Export** and selecting JPEG or PNG.

</div>

**Option 1 — Auto-detection (recommended)**

Place the image file in the `images/` folder with no `attach` command needed. CatPals automatically searches for it when the cat's card is displayed.

For a cat named `Bowie`, CatPals checks these filenames **in this exact order**, stopping at the first match:

1. `images/Bowie.png`
2. `images/bowie.png`
3. `images/Bowie.jpg`
4. `images/bowie.jpg`
5. `images/Bowie.jpeg`
6. `images/bowie.jpeg`

Only the cat's exact name and its all-lowercase form are tried. On **Linux and macOS** (case-sensitive filesystems), no other capitalisation variants (e.g. `BOWIE.png` or `Bowie.PNG`) will be found. On **Windows** (case-insensitive), any capitalisation variant such as `BOWIE.PNG` will also be matched because the OS itself ignores case when looking up files.

| Cat name in CatPals | Files that will be found automatically |
| ------------------- | -------------------------------------- |
| `Bowie`             | `Bowie.png`, `bowie.png`, `Bowie.jpg`, `bowie.jpg`, etc. |
| `Snowy`             | `Snowy.png`, `snowy.png`, `Snowy.jpg`, `snowy.jpg`, etc. |

**Option 2 — Manual path with `attach` (for custom filenames)**

Use the `attach` command to explicitly link any image file to a cat:

Format: `attach INDEX IMAGE_PATH` or `attach CAT_NAME IMAGE_PATH`

```
attach 1 images/my_cat_photo.png
attach Bowie images/bowie.png
```

The path you type is stored exactly as-is. CatPals does not modify or normalise it. Whether the file is found on each launch depends on your operating system's case sensitivity — on Linux/macOS, `images/Bowie.png` and `images/bowie.png` are different files; on Windows they are treated as the same.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
CatPals must be launched from the command window inside your `CatPals` folder (i.e. using `java -jar catpals.jar`) for relative image paths to work correctly. Double-clicking the `.jar` file may cause images not to load.
</div>

If the image file cannot be found at the given path, the cat's card will simply show no photo, while the rest of the data is unaffected.

<p align="center">
<img src="images/feature_attach.png" alt="feature_attach" width="70%">
<br>
<em>Running <code>attach Whiskers images/Whiskers.png</code> links a custom photo to Whisker's profile.</em>

</p>

### Listing all cats: `list`

Shows a list of all cats in the app.
<br>
:warning: By default, CatPals displays your list of cats in the left panel. You can use the keyboard up arrow :arrow_up: or down arrow :arrow_down: to browse through your cats.

Format: `list`

:no_entry_sign: `list` does not accept extra parameters. Typing `list foo` will show an error and suggest you use `list` instead.

### Updating a cat profile : `update`

Updates an existing cat in the app.

**Format:** `update INDEX [n/NAME] [t/TRAIT]… [l/LOCATION] [h/HEALTH_STATUS]` or `update CURRENT_NAME [n/NAME] [t/TRAIT]… [l/LOCATION] [h/HEALTH_STATUS]`


| Parameter     | Prefix | Description                                                                          |
| ------------- | ------ | ------------------------------------------------------------------------------------ |
| Name          | n/     | Updates the cat's name                                                               |
| Trait(s)      | t/     | Updates the cat's traits (up to 3, no duplicates). Use`t/` alone to clear all traits |
| Location      | l/     | Updates the cat's location                                                           |
| Health Status | h/     | Updates the cat's health status                                                      |


* Updates the cat at the specified `INDEX` or `CURRENT_NAME`. The index refers to the index number shown in the displayed cat list. The index **must be a positive integer** 1, 2, 3, …
* At least one field must be provided.
* Updating a trait will replace all currently existing traits, so if you just want to add a new one, make sure your command include the old traits.
* Existing values will be updated to the input values; fields not specified are kept unchanged.
* To change a cat's photo, use the `attach` command instead.

**Examples:**

* `update 1 n/Brownie t/Brown` — Updates the name and trait of the 1st cat.
* `update Browie n/Brownie t/Brown` — Updates the name of the cat with current name `Browie` to `Brownie`.
* `update 2 h/Vaccinated and Neutered` — Updates the health status of the 2nd cat.

Before any changes are applied, CatPals will show a confirmation dialog. Press **Enter** to confirm or **Esc** to cancel.

<p align="center">
  <img src="images/feature_update_prompt.png" alt="feature_update_prompt" width="70%">
  <br>
  <em>CatPals asks for confirmation before applying any changes.</em>
</p>

<p align="center">
<img src="images/feature_update.png" alt="feature_update" width="70%">
<br>
<em>After confirming, the cat formerly named <code>Browie</code> is updated to <code>Brownie</code></em>

</p>

### Locating cats by name, location, traits or health status : `find`

The `find` command lets you quickly filter the CatPals database. You can search for cats by **Name**, **Location**, **Traits**, or **Health Status**.

Format: `find n/CAT_NAME`, `find l/LOCATION`, `find t/TRAIT`, or `find h/HEALTH_STATUS`

**How it works:**

* The `find` command is case-insensitive (e.g. `find`, `Find`, and `FIND` are all acceptable).
* The search is case-insensitive. For example, `snowy` **will** match `Snowy`.
* The identifier flags (`n/`, `l/`, `t/`, `h/`) are all case-insensitive. For example, `find N/Snowy` and `find n/Snowy` are both valid.
* The keyword can match any part of the name. For example, `find n/now` will match `Snowy`, `find l/COM` will match `COM4`, `COM3`, `COM2` and `COM1`, `find t/strip` will match `Striped`.
* The order of the keywords does not matter. For example, `Snowy White` will match `White Snowy`.
* Cats matching all keywords will be returned.
  * e.g. `find l/COM3 t/friendly` will return all cats that are at `COM3` **and** have the trait `friendly`.
* Search terms cannot contain symbols.

**Examples:**


| Category      | Prefix | Example Command | Expected Result                               |
| ------------- | ------ | --------------- | --------------------------------------------- |
| Name          | n/     | find n/Snowy    | Shows cats with the word`Snowy` in their name |
| Location      | l/     | find l/COM3     | Shows all cats that are at`COM3`              |
| Traits        | t/     | find t/Striped  | Shows all cats that have the trait`Striped`   |
| Health Status | h/     | find h/healthy  | Shows all cats that are tagged as`healthy`    |

**More examples:** `find t/Friendly t/Playful` (either trait), `find n/Alex n/Li` (either keyword in the name field), `find l/COM3 t/Fluffy` (location **and** trait).

<div markdown="block" class="alert alert-warning">
:exclamation:
**Caution**:
Ensure there is an identifier flag before each keyword if multiple are used. For example, use t/friendly t/white,
not t/friendly white.
</div>

<p align="center">
  <img src="images/feature_find.png" alt="feature_find" width="70%">
  <br>
  <em>Running <code>find n/happy</code> shows cats named <code>happy</code>.</em>
</p>

### Deleting a cat : `delete`

Deletes the specified cat from the list.

Format: `delete INDEX` or `delete NAME`

* Deletes the cat at the specified `INDEX` or with the specific name `NAME`.
* The index refers to the index number shown in the displayed cat list.
* The index **must be a positive integer** 1, 2, 3, …

Examples:

* `list` followed by `delete 2` deletes the 2nd cat in the current list.
* `find Betsy` followed by `delete 1` deletes the 1st cat in the results of the `find` command.
* `delete Brownie` deletes the cat with the specific name "Brownie".

Before the cat is removed, CatPals will show a confirmation dialog. Press **Enter** to confirm or **Esc** to cancel.

<p align="center">
  <img src="images/feature_delete_prompt.png" alt="feature_delete_prompt" width="70%">
  <br>
  <em>CatPals asks for confirmation before deleting the cat.</em>
</p>

<p align="center">
  <img src="images/feature_delete.png" alt="feature_delete" width="70%">
  <br>
  <em>After confirming, <code>Brownie</code> is removed from the list.</em>
</p>

### Exporting the cat list : `export`

Exports the currently displayed cat list to an HTML file. Open it in any browser to view a formatted list with cat photos.

Format: `export [FILENAME]`

* `FILENAME` is optional. If omitted, the file is saved as `export.html` with the heading "Cat List".
* If a filename is provided, spaces are automatically converted to hyphens (e.g., `utown cats` → `utown-cats.html`), and the heading of the HTML file matches the filename you typed.
* The file is saved in the same folder as `catpals.jar`.
* Filenames must not contain any of these characters: `\ / : * ? " < > |`
* Exports whatever is currently shown. Use `find` first to export a filtered subset, or `list` to export everything.
* Images are embedded using the same paths stored in the app, so they appear correctly as long as the `images/` folder is in the same location.

Examples:

* `list` followed by `export` — exports all cats to `export.html`.
* `find l/Utown` followed by `export utown cats` — exports only cats at Utown to `utown-cats.html`.
* `export semester 1` — exports the current list to `semester-1.html` with the heading "semester 1".

<p align="center">
  <img src="images/feature_export.png" alt="feature_export" width="70%">
  <br>
  <em>Running <code>export</code> saves a list of cats to <code>export.html</code>.</em>
</p>

### Clearing all entries : `clear`

Clears all entries from the app.

Format: `clear`

Because this action permanently removes all your cat data, CatPals will show a confirmation dialog before proceeding. Press **Enter** to confirm or **Esc** to cancel.

<p align="center">
  <img src="images/feature_clear_propmt.png" alt="feature_clear_prompt" width="70%">
  <br>
  <em>CatPals asks for confirmation before clearing all entries.</em>
</p>

<p align="center">
  <img src="images/feature_clear.png" alt="feature_clear" width="70%">
  <br>
  <em>After confirming, all cat entries are removed and the list is empty.</em>
</p>

### Undo the previous action : `undo`

Reverses one data-changing step: the last command that actually modified the notebook.

Format: `undo`

| Recent command | What `undo` does |
| -------------- | ---------------- |
| [`add`](#adding-a-cat-add) | Removes that cat |
| [`delete`](#deleting-a-cat--delete) | Puts that cat back |
| [`update`](#updating-a-cat-profile--update) | Restores previous fields |
| [`attach`](#attaching-a-cat-photo-attach) | Removes the last attached photo (and its copied file where applicable) |
| [`help`](#viewing-help--help), [`list`](#listing-all-cats-list), [`find`](#locating-cats-by-name-location-traits-or-health-status--find) | No change (read-only) |
| [`export`](#exporting-the-cat-list--export), [`clear`](#clearing-all-entries--clear) | No change — **not** undoable; cleared data cannot be recovered with `undo` |
| [`undo`](#undo-the-previous-action--undo) again | No effect |

**Rules:** Only **one** level of undo is supported. Exported files stay on disk even if you `undo` another command afterward.
>There will be a confirmation window for `undo` command. Please press 'enter' to confirm or `esc` to cancel.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

CatPals saves your data to disk **automatically** after any command that changes it. You do **not** need a separate Save action.

### Editing the data file

Data is stored as JSON at **`[folder containing catpals.jar]/data/addressbook.json`**. Advanced users may edit this file directly; everyone else can ignore it.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Invalid JSON or unsupported values can make CatPals **drop all data** and start empty on the next launch. **Back up** `addressbook.json` before editing. Only edit the file if you know the expected format.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

---

## FAQ

**Q: How do I move CatPals to another computer?**
**A:** Install **Java 17+** on the new machine. Copy your whole **CatPals folder** (the `.jar`, the **`data`** folder, and optionally **`images`**). On the new computer, open a command window in that folder and run `java -jar catpals.jar` as usual.

**Q: The app says it needs Java / `java` is not recognised.**
**A:** Install **JDK 17 or newer** (e.g. from [Adoptium](https://adoptium.net/)). Close and reopen your command window, then run `java -version`. You must see **17** or higher.

**Q: Double-clicking the `.jar` does nothing or errors.**
**A:** Open a **command window**, navigate to the folder with `catpals.jar`, then run `java -jar catpals.jar`. This is the supported way to launch the app and also ensures that **relative paths** for `attach` and photos work correctly.

**Q: I'm stuck on the yellow welcome screen.**
**A:** Press the **Space** bar once to enter the main window.

**Q: Where is my data stored?**
**A:** In **`data/addressbook.json`** next to `catpals.jar`. Back up that file if you care about your records.

**Q: A command failed or said invalid format.**
**A:** Check spelling and prefixes (`n/`, `l/`, `t/`, `h/`). Use **`help`** for an overview. See the [Features](#features) section for exact formats.

**Q: Cat photos do not show / `attach` cannot find the file.**
**A:** Put images under an **`images`** folder beside the `.jar`, use paths relative to where you run the app, and launch with **`java -jar`** from that folder (see [Attaching a cat photo](#attaching-a-cat-photo-attach)). If your photo is from an iPhone, convert it from HEIC to JPG or PNG first.

**Q: Can I use CatPals on my phone?**
**A:** No. It is a **desktop** Java application for Windows, macOS, or Linux.

**Q: Does CatPals send my data online?**
**A:** No. Everything stays in **local files** on your computer unless you copy or export them yourself.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimise the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimised, and no new Help Window will appear. The remedy is to manually restore the minimised Help Window.
3. **If you launch CatPals by double-clicking the `.jar` file**, cat photos may fail to load even if the `images/` folder is set up correctly. This happens because the working directory may not be set to the CatPals folder when launching by double-click. Always launch using `java -jar catpals.jar` from a command window opened inside the CatPals folder.

---

## Command summary


| Command                           | Format                                                                         | Examples                                                             |
| --------------------------------- |--------------------------------------------------------------------------------| -------------------------------------------------------------------- |
| **Add** a cat                     | `add n/NAME t/TRAIT... l/LOCATION [h/HEALTH_STATUS]`                           | `add n/Bowie t/Orange l/Utown h/Vaccinated`                          |
| **Attach** a photo to a cat       | `attach INDEX IMAGE_PATH` or `attach CAT_NAME IMAGE_PATH`                      | `attach 1 images/bowie.png` or `attach Bowie images/bowie.png`       |
| **Delete** a cat by name or index | `delete CAT_NAME` or `delete CAT_NUMBER`                                       | `delete Snowy` or `delete 3`                                         |
| **Update** a cat by name or index | `update NAME/INDEX [n/NAME] [t/TRAIT] [l/LOCATION] [h/HEALTH]`                 | `update Snowy l/utown` or `update 3 l/PGPR`                          |
| **Find** cats                     | `find n/NAME` or `find l/LOCATION` or `find t/TRAIT` or `find h/HEALTH_STATUS` | `find n/Mochi` or `find t/Striped` or `find l/COM3` or `find h/Sick` |
| **Export** the cat list to HTML   | `export [FILENAME]`                                                            | `export` or `export utown cats`                                      |
| **Clear** all cats                | `clear`                                                                        | `clear`                                                              |
| **List** all cats                 | `list`                                                                         | `list`                                                               |
| **Help**                          | `help`                                                                         | `help`                                                               |
| **Exit**                          | `exit`                                                                         | `exit`                                                               |
| **Undo**                          | `undo`                                                                         | `undo`                                                               |

---

## Thank you for using CatPals

We hope CatPals makes it a little easier to look after the cats on campus. Every record you keep helps volunteers stay informed and coordinate care more effectively.

If you run into a bug or have a suggestion, feel free to open an issue on our [GitHub page](https://github.com/AY2526S2-CS2103T-T16-3/tp/issues). We would love to hear from you.

Happy cat-tracking!
