---
layout: page
title: User Guide
---
![banner](../docs/images/CatPals_banner.png)

# Table of Contents
<!-- TOC -->
* [Table of Contents](#table-of-contents)
  * [About CatPals](#about-catpals)
    * [What is CatPals?](#what-is-catpals)
    * [What is the background of this project?](#what-is-the-background-of-this-project)
    * [Why is this app needed?](#why-is-this-app-needed)
    * [Who are the target users?](#who-are-the-target-users)
    * [How do users interact with CatPals?](#how-do-users-interact-with-catpals)
    * [What value does CatPals provide?](#what-value-does-catpals-provide)
    * [What is CatPals not designed for?](#what-is-catpals-not-designed-for)
    * [Where can you find more information?](#where-can-you-find-more-information)
  * [Getting Started](#getting-started)
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

CatPals is a **desktop application** created for **NUS Cat Café volunteers** who need a fast and reliable way to manage information about stray cats on the NUS campus.

Instead of managing people’s contact details, CatPals helps volunteers record, search for, and update important information about stray cats around campus. This makes it easier to identify individual cats, track their status, and keep records accurate over time.

### What is the background of this project?

CatPals was not built entirely from scratch. Instead, our team started from an existing sample application and adapted it for a new purpose. In software engineering, this is called a **brownfield project**. This simply means that we improved and repurposed an existing app instead of creating a completely new one from zero.

Our team of five used [**AddressBook Level 3 (AB3)**](https://se-education.org/addressbook-level3/) as the starting point for this project. AB3 is a sample desktop application provided by the CS2103T teaching team in AY2526 Semester 2. It gave us a base structure that we could build on, and we then redesigned it to better suit the needs of NUS Cat Café volunteers.

### Why is this app needed?

CatPals is designed for volunteers who may need to manage information for a **large number of cats** across different parts of the NUS campus. When there are many cats to keep track of, it becomes difficult to rely only on memory, scattered notes, or chat messages.

A centralised app helps volunteers work in a more organised and consistent way. It can reduce confusion, make records easier to update, and support smoother coordination, especially when multiple volunteers are caring for or monitoring the same cats.

### Who are the target users?

CatPals is especially suitable for users who:

- are members of **NUS Cat Café CCA**
- need to keep track of a significant number of stray cats on the NUS campus
- prefer using a **desktop app** rather than mobile apps or paper notes
- can type reasonably quickly
- prefer typing over repeated mouse clicking
- are comfortable with simple command-based interaction

In general, CatPals is intended for users who value **speed, efficiency, and organisation**. It is especially useful for volunteers who want quick access to cat records and a reliable way to keep key details up to date.

### How do users interact with CatPals?

CatPals is built around a **CLI-style workflow**. **CLI** stands for **Command Line Interface**, which means users mainly interact with the app by typing commands instead of clicking through many buttons or menus.

This may sound technical at first, but in practice, it simply means that common tasks can often be done **faster** once users become familiar with a few basic commands. For volunteers who are comfortable using the keyboard, this can make day-to-day work much more efficient.

The app does **not** assume that users are computer experts. However, it does assume that users are reasonably comfortable following instructions, entering commands accurately, and using a desktop-based workflow.

### What value does CatPals provide?

CatPals provides **fast, keyboard-friendly access** to information about stray cats living on the NUS campus. This helps volunteers:

- identify cats more reliably
- keep important status details updated
- manage records in a more organised way
- work more efficiently during volunteer activities

CatPals is designed for **personal use**. It is not meant to replace professional systems used in clinics, shelters, or public organisations.

### What is CatPals not designed for?

CatPals is **not** intended to be:

- a veterinary medical system
- a shelter operations platform
- a public registry for stray cats

Instead, it is a practical record-management tool built specifically for the needs of NUS Cat Café volunteers.

### Where can you find more information?

For more details about the project, please visit the **[CatPals Website](https://ay2526s2-cs2103t-t16-3.github.io/tp/)**.

---

## Getting Started
If you are new to using desktop applications or command-line interfaces, don't worry! This guide will walk you through the steps to get CatPals up and running on your computer. Just follow the instructions carefully, and you'll be managing cat profiles in no time.

---
**Step 1: Check Your Java Version**

Before starting, your computer needs a specific version of **Java** (think of it as the engine) to run this app.

* **On Windows:** Click your **Start** menu, type `cmd`, and press **Enter**. A black window will open. Type `java -version` and press **Enter**.
* **On Mac:** Press **Command + Space**, type `Terminal`, and press **Enter**. Type `java -version` and press **Enter**.
* Result: You should see something like `java version "17.0.1"`. If you see a version number less than 17, or an error message, you will need to install Java 17 or above.

---

**Step 2: Download the App and prepare CatPals folder**
1. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103T-T16-3/tp/releases/tag/branch-release1.0).
2.  **Create a new empty folder** on your Desktop or in your Documents and name it `CatPals`or any other name you prefer.
3.  **Move** the downloaded file into this new folder.

---

**Step 3: Open a Terminal and Navigate to Your CatPals Folder**

Open a terminal on your computer, then navigate to the folder containing `catpals.jar` using the `cd` command.

* **Windows:** Press `Win + R`, type `cmd`, and press **Enter**.
* **Mac/Linux:** Press `Command + Space`, type `Terminal`, and press **Enter**.

Then run:

```
cd path/to/CatPals
```

Replace `path/to/CatPals` with the actual path to your folder. For example:
* Windows: `cd C:\Users\YourName\Desktop\CatPals`
* Mac/Linux: `cd ~/Desktop/CatPals`

---

**Step 4: Run the Application**
Now that the terminal window is open, simply type the following command exactly as it appears and press **Enter**:

`java -jar catpals.jar`

> **Note:** If the file you downloaded has a different name (like `catpals-v1.2.jar`), make sure you type that exact name after the `-jar` part!

**Step 5: Start Using CatPals!**
If the app runs successfully, you should see a window pop up with a list of cats and a command box at the bottom. You can start typing commands to manage your cat profiles. For example, try typing `help` and pressing Enter to see the help message.

Some example commands you can try:

   * `list` : Lists all contacts.
   * `add n/Bowie t/Orange l/Utown h/Vaccinated` : Adds a cat named `Bowie` to the cat notebook.
   * `delete 3` : Deletes the 3rd contact shown in the current list.
   * `clear` : Deletes all contacts.
   * `exit` : Exits the app. 
Refer to the [Features](#features) below for details of each command.

---

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/Snowy`.
* Items in curly brackets are optional.<br>
  e.g `n/NAME t/TAG {h/HEALTH_STATUS}` can be used as `n/Snowy t/white h/vaccinated` or as `n/Snowy t/white`.
* Items with `…` after them can be used multiple times (>= 1).<br>
  e.g. `[t/TAG]…` can be used as `t/white`, `t/white t/small` etc.
* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.
* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a cat: `add`

Adds a cat profile to the cat notebook.

Format: `add n/NAME t/TRAIT [t/MORE_TRAITS]… l/LOCATION [h/HEALTH_STATUS]`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A cat can have up to 3 traits (no duplicates). Health status is optional and defaults to `Unknown` if not provided.
To attach a photo after adding, use the `attach` command.
</div>

* `n/NAME`, `t/TRAIT`, and `l/LOCATION` are required.
* `h/HEALTH_STATUS` is optional.
* You can specify up to 3 `t/TRAIT` prefixes, but duplicate traits are not allowed.

Examples:

* `add n/Bowie t/Orange l/Utown h/Vaccinated`
* `add n/Whiskers t/Fluffy t/Playful l/Science`

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

* Use simple filenames with no spaces, e.g. `bowie.png`, `snowy cat.jpg` → prefer `snowy_cat.jpg`.
* Supported formats: `.png`, `.jpg`, `.jpeg`.
* Filenames are **case-sensitive** on Linux/macOS (e.g. `Bowie.png` ≠ `bowie.png`).

**Option 1 — Auto-detection (recommended)**

Name the image file exactly after the cat (same capitalisation as the name in CatPals), place it in the `images/` folder, and CatPals will pick it up automatically — no command needed.

| Cat name in CatPals | File to place in `images/` |
|---------------------|---------------------------|
| `Bowie`             | `Bowie.png` or `bowie.png` |
| `Snowy`             | `Snowy.jpg` or `snowy.jpg` |

CatPals tries the original capitalisation first, then lowercase, for `.png`, `.jpg`, and `.jpeg`.

**Option 2 — Manual path with `attach` (for custom filenames)**

Use the `attach` command to explicitly set any image path for a cat:

Format: `attach INDEX IMAGE_PATH` or `attach CAT_NAME IMAGE_PATH`

```
attach 1 images/my_cat_photo.png
attach Bowie images/my_cat_photo.png
```

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
CatPals must be launched from the terminal inside your `CatPals` folder (i.e. using `java -jar catpals.jar`) for relative image paths to work correctly. Double-clicking the `.jar` file may cause images not to load.
</div>

If the image file cannot be found at the given path, the cat's card will simply show no photo — the rest of the data is unaffected.

### Listing all cats: `list`

Shows a list of all cats in the app.

Format: `list`

### Updating a cat profile : `update`

Updates an existing cat in the app.

**Format:** `update INDEX [n/NAME] [t/TRAIT]… [l/LOCATION] [h/HEALTH_STATUS]` or `update CURRENT_NAME [n/NAME] [t/TRAIT]… [l/LOCATION] [h/HEALTH_STATUS]`

| Parameter      | Prefix | Description                                                                 |
|----------------|--------|-----------------------------------------------------------------------------|
| Name           | n/     | Updates the cat's name                                                      |
| Trait(s)       | t/     | Updates the cat's traits (up to 3, no duplicates). Use `t/` alone to clear all traits |
| Location       | l/     | Updates the cat's location                                                  |
| Health Status  | h/     | Updates the cat's health status                                              |

* Updates the cat at the specified `INDEX` or `CURRENT_NAME`. The index refers to the index number shown in the displayed cat list. The index **must be a positive integer** 1, 2, 3, …
* At least one field must be provided.
* Existing values will be updated to the input values; fields not specified are kept unchanged.
* To change a cat’s photo, use the `attach` command instead.

**Examples:**

* `update 1 n/Brownie t/Brown` — Updates the name and trait of the 1st cat.
* `update Bronwie n/Brownie t/Brown` — Updates the name of the cat with current name `Bronwie` to `Brownie`.

### Locating cats by name, location, traits or health status : `find`

The `find` command is a powerful tool to quickly filter the CatPals database. You can now search for cats based on their **Name**, **Location**, **Traits**, or **Health Status**.

Format: `find n/CAT_NAME`, `find l/LOCATION`, `find t/TRAIT`, or `find h/HEALTH_STATUS`


**How it works:**
* The search is case-sensitive. e.g `snowy` will **NOT** match `Snowy`
* The order of the keywords **do not** matter. e.g. `Snowy White` will match `White Snowy`
* Cats matching at least one keyword will be returned (i.e. `OR` search).
  * e.g. `find n/Hans n/Bo` will return `Hans Gruber`, `Bo Yang`
* Search terms cannot contain symbols

**Examples:**


| Category      | Prefix | Example Command | Expected Result                                |
|---------------|--------|-----------------|------------------------------------------------|
| Name          | n/     | find n/Snowy    | Shows cats with the word `Snowy` in their name |
| Location      | l/     | find l/COM3     | Shows all cats that are at `COM3`              |
| Traits        | t/     | find t/Striped  | Shows all cats that have the trait `Striped`   |
| Health Status | h/     | find h/healthy  | Shows all cats that are tagged as `healthy`     |

**Advanced Examples:**

* **Searching for multiple traits:** `find t/Friendly t/Playful`
  Matches any cat that is marked as either Friendly OR Playful.
* **Finding names with multiple keywords:**`find n/Alex n/Li`
  Matches cats named Alex as well as cats with the last name Li (e.g., Jet Li).
* **Combining categories:** `find n/Snowy t/Fluffy`
  Matches any cat that has the name Snowy OR is tagged as Fluffy.
* **Searching by partial location:**`find l/Arts`
  Matches cats at Arts Canteen, Arts Plaza, etc.

>[!IMPORTANT]
> Ensure there is no space between the prefix (like n/) and your search word. For example, use n/Snowy, not n/ Snowy.

### Deleting a cat : `delete`

Deletes the specified cat from the list.

Format: `delete INDEX` or `delete NAME`

* Deletes the cat at the specified `INDEX` or with the specific name `NAME`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …

Examples:

* `list` followed by `delete 2` deletes the 2nd cat in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st cat in the results of the `find` command.
* `delete Brownie` deletes the cat with the specific name "Brownie".

### Exporting the cat list : `export`

Exports the currently displayed cat list to `export.html` in your CatPals folder. Open it in any browser to view a formatted list with cat photos.

Format: `export`

* Exports whatever is currently shown — use `find` first to export a filtered subset, or `list` to export everything.
* The file is saved as `export.html` in the same folder as `catpals.jar`.
* Images are embedded using the same paths stored in the app, so they appear correctly as long as the `images/` folder is in the same location.

Examples:

* `list` followed by `export` — exports all cats.
* `find l/Utown` followed by `export` — exports only cats located at Utown.

### Clearing all entries : `clear`

Clears all entries from the app.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

---

## Command summary


| Command                           | Format                                                                             | Examples                                                             |
|-----------------------------------|------------------------------------------------------------------------------------|----------------------------------------------------------------------|
| **Add** a cat                     | `add n/NAME t/TRAIT... l/LOCATION [h/HEALTH_STATUS]`                               | `add n/Bowie t/Orange l/Utown h/Vaccinated`                          |
| **Attach** a photo to a cat       | `attach INDEX IMAGE_PATH` or `attach CAT_NAME IMAGE_PATH`                          | `attach 1 images/bowie.png` or `attach Bowie images/bowie.png`       |
| **Delete** a cat by name or index | `delete [CAT_NAME]` or `delete [CAT_NUMBER]`                                       | `delete Snowy` or `delete 3`                                         |
| **Update** a cat by name or index | `update NAME/INDEX [n/NAME] [t/TRAIT] [l/LOCATION] [h/HEALTH]`                     | `update Snowy l/utown` or `update 3 l/PGPR`                          |
| **Find** cats                     | `find n/CAT_NAME` or `find l/LOCATION` or `find t/TRAIT` or `find h/HEALTH_STATUS` | `find n/Mochi` or `find t/Striped` or `find l/COM3` or `find h/Sick` |
| **Export** the cat list to HTML   | `export`                                                                           | `export`                                                             |
| **Clear** all cats                | `clear`                                                                            | `clear`                                                              |
| **List** all cats                 | `list`                                                                             | `list`                                                               |
| **Help**                          | `help`                                                                             | `help`                                                               |
| **Exit**                          | `exit`                                                                             | `exit`                                                               |
