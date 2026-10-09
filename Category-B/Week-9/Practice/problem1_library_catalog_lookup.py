def findBook(catalog, targetIsbn):
    """Binary search on a catalog sorted by ISBN."""
    low, high = 0, len(catalog) - 1
    while low <= high:
        mid = (low + high) // 2
        isbn, title = catalog[mid]
        if isbn == targetIsbn:
            return title
        if isbn < targetIsbn:      # same-length numeric strings compare correctly
            low = mid + 1
        else:
            high = mid - 1
    return "Not Found"

# Time complexity : O(log n)  - search space halves every step
# Extra space     : O(1)      - only a few index variables

if __name__ == "__main__":
    catalog = [("0001112223", "Introduction to Algebra"),
               ("0002223334", "Beginning Python"),
               ("0003334445", "Classic Mythology"),
               ("0004445556", "Data and Society"),
               ("0005556667", "European History")]
    print(findBook(catalog, "0003334445"))  # Classic Mythology
    print(findBook(catalog, "0009998887"))  # Not Found
