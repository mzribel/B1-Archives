package piscine

func BTreeMin(root *TreeNode) *TreeNode {
	if root == nil {
		return root
	}
	result := root
	leftMin := BTreeMin(root.Left)
	rightMin := BTreeMin(root.Right)

	if leftMin != nil && Compare(leftMin.Data, result.Data) == -1 {
		result = leftMin
	}
	if rightMin != nil && Compare(rightMin.Data, result.Data) == -1 {
		result = rightMin
	}
	return result
}
