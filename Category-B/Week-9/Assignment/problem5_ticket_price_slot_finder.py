# Linear scan: Time O(n), Space O(1)
def findSlot_linear(prices, newPrice):
    for i, p in enumerate(prices):
        if p >= newPrice:
            return i
    return len(prices)


# Binary search (optimal)
def findSlot(prices, newPrice):
    low, high = 0, len(prices) - 1
    while low <= high:
        mid = (low + high) // 2
        if prices[mid] == newPrice:
            return mid
        if prices[mid] < newPrice:
            low = mid + 1
        else:
            high = mid - 1
    return low                        # insertion position
# Time: O(log n)   Space: O(1)

if __name__ == "__main__":
    p = [120, 150, 200, 260]
    print(findSlot(p, 150))  # 1
    print(findSlot(p, 210))  # 3
    print(findSlot(p, 300))  # 4
    print(findSlot([], 5))   # 0
