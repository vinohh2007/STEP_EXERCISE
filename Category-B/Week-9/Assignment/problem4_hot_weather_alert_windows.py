# Recalculate each block's sum from scratch
def countAlerts_bruteforce(readings, k, threshold):
    count = 0
    for i in range(len(readings) - k + 1):
        if sum(readings[i:i + k]) >= k * threshold:
            count += 1
    return count
# Time: O(n * k)   Space: O(1)


# Sliding window (optimal)
def countAlerts(readings, k, threshold):
    window = sum(readings[:k])
    target = k * threshold            # avoids decimal division
    count = 1 if window >= target else 0
    for i in range(k, len(readings)):
        window += readings[i] - readings[i - k]   # add new reading, drop the old one
        if window >= target:
            count += 1
    return count
# Time: O(n)   Space: O(1)

if __name__ == "__main__":
    print(countAlerts([2, 2, 2, 2, 5, 5, 5, 8], 3, 4))  # 3
