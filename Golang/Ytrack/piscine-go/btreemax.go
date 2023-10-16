package piscine

func BTreeMax(root *TreeNode) *TreeNode {
	if root == nil {
		return root
	}
	result := root
	leftMax := BTreeMax(root.Left)
	rightMax := BTreeMax(root.Right)

	if leftMax != nil && Compare(leftMax.Data, result.Data) == 1 {
		result = leftMax
	}
	if rightMax != nil && Compare(rightMax.Data, result.Data) == 1 {
		result = rightMax
	}
	return result
}
