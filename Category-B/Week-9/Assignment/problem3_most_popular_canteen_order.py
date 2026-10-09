# Approach 1: for each item, rescan the whole list to count it
def mostPopular_bruteforce(orders):
    best_item, best_count = None, 0
    for item in orders:
        count = 0
        for other in orders:
            if other == item:
                count += 1
        if count > best_count:
            best_item, best_count = item, count
    return (best_item, best_count)
# Time: O(n^2)   Space: O(1)


# Approach 2: hash map (optimal)
def mostPopular(orders):
    counts = {}
    for item in orders:
        counts[item] = counts.get(item, 0) + 1
    best_item, best_count = None, 0
    for item in orders:               # original order -> first-appearing item wins ties
        if counts[item] > best_count:
            best_item, best_count = item, counts[item]
    return (best_item, best_count)
# Time: O(n)   Space: O(k)  (k = number of distinct items, at most n)

if __name__ == "__main__":
    print(mostPopular(["dosa", "idli", "vada", "dosa", "idli", "dosa", "tea"]))  # ('dosa', 3)
    print(mostPopular(["tea", "coffee", "coffee", "tea"]))                       # ('tea', 2)
