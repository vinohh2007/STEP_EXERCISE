# Brute force: try every pair of walls
def maxContainerArea_bruteforce(heights):
    best = 0
    n = len(heights)
    for i in range(n):
        for j in range(i + 1, n):
            best = max(best, min(heights[i], heights[j]) * (j - i))
    return best
# Time: O(n^2)   Space: O(1)


# Optimal: two pointers from both ends
def maxContainerArea(heights):
    left, right = 0, len(heights) - 1
    best = 0
    while left < right:
        area = min(heights[left], heights[right]) * (right - left)
        best = max(best, area)
        # Move the shorter wall: the area is limited by it, and shrinking the
        # width can only help if we find a taller wall.
        if heights[left] < heights[right]:
            left += 1
        else:
            right -= 1
    return best
# Time: O(n)   Extra space: O(1)

# Why optimal is preferable: for n = 10^6, brute force needs ~5 * 10^11 pair
# checks, while two pointers needs only ~10^6 steps, with the same O(1) memory.

if __name__ == "__main__":
    print(maxContainerArea([1, 8, 6, 2, 5, 4, 8, 3, 7]))  # 49
