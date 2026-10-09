# Approach 1: Brute force - check every pair
def hasPairWithSum_bruteforce(nums, target):
    n = len(nums)
    for i in range(n):
        for j in range(i + 1, n):
            if nums[i] + nums[j] == target:
                return True
    return False
# Time: O(n^2)   Space: O(1)


# Approach 2: Hash set (optimal) - look up the complement
def hasPairWithSum(nums, target):
    seen = set()
    for x in nums:
        if (target - x) in seen:
            return True
        seen.add(x)
    return False
# Time: O(n) average   Space: O(n)


# Approach 3: Sort + two pointers (space-saving alternative)
def hasPairWithSum_sorted(nums, target):
    arr = sorted(nums)
    l, r = 0, len(arr) - 1
    while l < r:
        s = arr[l] + arr[r]
        if s == target:
            return True
        if s < target:
            l += 1
        else:
            r -= 1
    return False
# Time: O(n log n)   Space: O(1) extra (O(n) if you copy the list as above)

# Trade-off: hash set is fastest but uses extra memory; brute force uses no
# extra memory but is slow for large n; sort + two pointers sits in between.

if __name__ == "__main__":
    print(hasPairWithSum([2, 7, 11, 15], 9))   # True
    print(hasPairWithSum([3, 4, 6], 20))       # False
    print(hasPairWithSum([3, 3], 6))           # True (duplicates, distinct positions)
    print(hasPairWithSum([3], 6))              # False (same element can't be reused)
