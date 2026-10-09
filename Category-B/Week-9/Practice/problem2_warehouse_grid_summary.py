def warehouseSummary(grid):
    total = 0
    max_val = -1
    max_coord = None
    for r in range(len(grid)):
        for c in range(len(grid[r])):
            val = grid[r][c]
            total += val
            if val > max_val:          # strict '>' keeps the first max in row-major order
                max_val = val
                max_coord = (r, c)
    return (total, max_coord)

# Time complexity : O(m * n) - every cell visited once
# Extra space     : O(1)     - only total, max value, coordinates

if __name__ == "__main__":
    grid = [[4, 9, 2],
            [7, 1, 6],
            [3, 12, 5]]
    print(warehouseSummary(grid))  # (49, (2, 1))
